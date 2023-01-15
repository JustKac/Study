package br.com.segsat.restwhitspringbootandjava.integrationtests.controller.yaml;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.segsat.restwhitspringbootandjava.configs.TestConfig;
import br.com.segsat.restwhitspringbootandjava.integrationtests.controller.yaml.mapper.YamlMapper;
import br.com.segsat.restwhitspringbootandjava.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.segsat.restwhitspringbootandjava.integrationtests.vo.v1.AccountCredentialsVO;
import br.com.segsat.restwhitspringbootandjava.integrationtests.vo.v1.PersonVO;
import br.com.segsat.restwhitspringbootandjava.integrationtests.vo.v1.TokenVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerYamlTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static YamlMapper objectMapper;
	private static String acessToken;

	private static PersonVO person;

	@BeforeAll
	public static void setup() {
		objectMapper = new YamlMapper();

		person = new PersonVO();
	}

	@Test
	@Order(0)
	public void authorization() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");

		acessToken = given()
				.basePath("/auth/signin")
				.port(TestConfig.SERVER_PORT)
				.config(
					RestAssuredConfig
						.config()
						.encoderConfig(EncoderConfig.encoderConfig()
							.encodeContentTypeAs(TestConfig.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfig.CONTENT_TYPE_YML)
					.body(user, objectMapper)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.as(TokenVO.class, objectMapper)
							.getAccessToken();

		specification = new RequestSpecBuilder()
				.addHeader(TestConfig.HEADER_PARAM_AUTHORIZATION, "Bearer " + acessToken)
				.addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_FROTA)
				.addHeader("Accept", TestConfig.CONTENT_TYPE_YML)
				.setBasePath("/api/person/v1")
				.setPort(TestConfig.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();

	}

	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		mockPerson();

		PersonVO createdPeson = given().spec(specification)
						.config(
							RestAssuredConfig
								.config()
								.encoderConfig(EncoderConfig.encoderConfig()
									.encodeContentTypeAs(TestConfig.CONTENT_TYPE_YML, ContentType.TEXT)))
						.contentType(TestConfig.CONTENT_TYPE_YML)
							.body(person, objectMapper)
						.when()
							.post()
						.then()
							.statusCode(200)
						.extract()
							.body()
								.as(PersonVO.class, objectMapper);

		person = createdPeson;

		assertNotNull(createdPeson);
		assertNotNull(createdPeson.getId());
		assertNotNull(createdPeson.getFirstName());
		assertNotNull(createdPeson.getLastName());
		assertNotNull(createdPeson.getAddress());
		assertNotNull(createdPeson.getGender());
		assertTrue(createdPeson.getId() > 0);

		assertEquals("Eduardo", createdPeson.getFirstName());
		assertEquals("Farias", createdPeson.getLastName());
		assertEquals("Pernambuco, BR", createdPeson.getAddress());
		assertEquals("Male", createdPeson.getGender());
	}

	@Test
	@Order(2)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		mockPerson();

		PersonVO persistedPerson = given().spec(specification)
						.config(
							RestAssuredConfig
								.config()
								.encoderConfig(EncoderConfig.encoderConfig()
									.encodeContentTypeAs(TestConfig.CONTENT_TYPE_YML, ContentType.TEXT)))
						.contentType(TestConfig.CONTENT_TYPE_YML)
							.pathParam("id", person.getId())
						.when()
							.get("{id}")
						.then()
							.statusCode(200)
						.extract()
							.body()
								.as(PersonVO.class, objectMapper);

		person = persistedPerson;

		assertNotNull(persistedPerson);
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());

		assertEquals(persistedPerson.getId(), person.getId());

		assertEquals("Eduardo", persistedPerson.getFirstName());
		assertEquals("Farias", persistedPerson.getLastName());
		assertEquals("Pernambuco, BR", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());
	}

	@Test
	@Order(3)
	public void testUpdate() throws JsonMappingException, JsonProcessingException {

		person.setFirstName("Livia");
		person.setLastName("Lins");
		person.setAddress("Rio Grande do Norte, BR");
		person.setGender("Female");

		PersonVO updatedPeson = given().spec(specification)
						.config(
							RestAssuredConfig
								.config()
								.encoderConfig(EncoderConfig.encoderConfig()
									.encodeContentTypeAs(TestConfig.CONTENT_TYPE_YML, ContentType.TEXT)))
						.contentType(TestConfig.CONTENT_TYPE_YML)
							.body(person, objectMapper)
							.pathParam("id", person.getId())
						.when()
							.put("{id}")
						.then()
							.statusCode(200)
						.extract()
							.body()
								.as(PersonVO.class, objectMapper);

		person = updatedPeson;

		assertNotNull(updatedPeson);
		assertNotNull(updatedPeson.getId());
		assertNotNull(updatedPeson.getFirstName());
		assertNotNull(updatedPeson.getLastName());
		assertNotNull(updatedPeson.getAddress());
		assertNotNull(updatedPeson.getGender());

		assertTrue(updatedPeson.getId() == person.getId());

		assertEquals("Livia", updatedPeson.getFirstName());
		assertEquals("Lins", updatedPeson.getLastName());
		assertEquals("Rio Grande do Norte, BR", updatedPeson.getAddress());
		assertEquals("Female", updatedPeson.getGender());
	}

	@Test
	@Order(4)
	public void testDelete() throws JsonMappingException, JsonProcessingException {

		given().spec(specification)
			.config(
					RestAssuredConfig
						.config()
						.encoderConfig(EncoderConfig.encoderConfig()
							.encodeContentTypeAs(TestConfig.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfig.CONTENT_TYPE_YML)
				.pathParam("id", person.getId())
			.when()
				.delete("{id}")
			.then()
				.statusCode(204);

	}

	@Test
	@Order(5)
	public void testFindAll() throws JsonMappingException, JsonProcessingException {

		var content = given().spec(specification)
				.config(
					RestAssuredConfig
						.config()
						.encoderConfig(EncoderConfig.encoderConfig()
							.encodeContentTypeAs(TestConfig.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfig.CONTENT_TYPE_YML)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.as(PersonVO[].class, objectMapper);
						// .as(new TypeRef<List<PersonVO>>() {});

		List<PersonVO> people = Arrays.asList(content);
		
		PersonVO firstPerson = people.get(0);
		PersonVO lastPerson = people.get(people.size() - 1);

		assertNotNull(firstPerson);
		assertNotNull(firstPerson.getId());
		assertNotNull(firstPerson.getFirstName());
		assertNotNull(firstPerson.getLastName());
		assertNotNull(firstPerson.getAddress());
		assertNotNull(firstPerson.getGender());

		assertEquals(firstPerson.getId(), 1);
		assertEquals("Leandro", firstPerson.getFirstName());
		assertEquals("Costa", firstPerson.getLastName());
		assertEquals("Uberlândia - Minas Gerais - Brasil", firstPerson.getAddress());
		assertEquals("Male", firstPerson.getGender());

		assertNotNull(lastPerson);
		assertNotNull(lastPerson.getId());
		assertNotNull(lastPerson.getFirstName());
		assertNotNull(lastPerson.getLastName());
		assertNotNull(lastPerson.getAddress());
		assertNotNull(lastPerson.getGender());

		assertEquals(lastPerson.getId(), 9);
		assertEquals("Marcos", lastPerson.getFirstName());
		assertEquals("Paulo", lastPerson.getLastName());
		assertEquals("Patos de Minas - Minas Gerais - Brasil", lastPerson.getAddress());
		assertEquals("Male", lastPerson.getGender());
	}

	@Test
	@Order(6)
	public void testFindAllWithoutToken() throws JsonMappingException, JsonProcessingException {

		RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
							.addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_FROTA)
							.addHeader("Accept", TestConfig.CONTENT_TYPE_YML)
							.setBasePath("/api/person/v1")
							.setPort(TestConfig.SERVER_PORT)
							.addFilter(new RequestLoggingFilter(LogDetail.ALL))
							.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
							.build();

		given().spec(specificationWithoutToken)
				.config(
					RestAssuredConfig
						.config()
						.encoderConfig(EncoderConfig.encoderConfig()
							.encodeContentTypeAs(TestConfig.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfig.CONTENT_TYPE_YML)
				.when()
					.get()
				.then()
					.statusCode(403);

	}

	private void mockPerson() {
		person.setFirstName("Eduardo");
		person.setLastName("Farias");
		person.setAddress("Pernambuco, BR");
		person.setGender("Male");
	}

}
