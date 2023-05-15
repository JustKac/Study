package br.com.segsat.restwhitspringbootandjava.integrationtests.controller.json;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.segsat.restwhitspringbootandjava.configs.TestConfig;
import br.com.segsat.restwhitspringbootandjava.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.segsat.restwhitspringbootandjava.integrationtests.vo.v1.AccountCredentialsVO;
import br.com.segsat.restwhitspringbootandjava.integrationtests.vo.v1.PersonVO;
import br.com.segsat.restwhitspringbootandjava.integrationtests.vo.v1.TokenVO;
import br.com.segsat.restwhitspringbootandjava.integrationtests.vo.wrappers.WrapperPersonVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	private static String acessToken;

	private static PersonVO person;

	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		person = new PersonVO();
	}

	@Test
	@Order(0)
	public void authorization() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");

		acessToken = given()
				.basePath("/auth/signin")
				.port(TestConfig.SERVER_PORT)
				.contentType(TestConfig.CONTENT_TYPE_JSON)
					.body(user)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.as(TokenVO.class)
							.getAccessToken();

		specification = new RequestSpecBuilder()
				.addHeader(TestConfig.HEADER_PARAM_AUTHORIZATION, "Bearer " + acessToken)
				.addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_FROTA)
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

		var content = given().spec(specification)
				.contentType(TestConfig.CONTENT_TYPE_JSON)
					.body(person)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();

		PersonVO createdPeson = objectMapper.readValue(content, PersonVO.class);
		person = createdPeson;

		assertNotNull(createdPeson);
		assertNotNull(createdPeson.getId());
		assertNotNull(createdPeson.getFirstName());
		assertNotNull(createdPeson.getLastName());
		assertNotNull(createdPeson.getAddress());
		assertNotNull(createdPeson.getGender());
		assertTrue(createdPeson.isEnabled());
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

		var content = given().spec(specification)
				.contentType(TestConfig.CONTENT_TYPE_JSON)
					.pathParam("id", person.getId())
				.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();

		PersonVO persistedPerson = objectMapper.readValue(content, PersonVO.class);
		person = persistedPerson;

		assertNotNull(persistedPerson);
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());
		assertTrue(persistedPerson.isEnabled());

		assertEquals(persistedPerson.getId(), person.getId());

		assertEquals("Eduardo", persistedPerson.getFirstName());
		assertEquals("Farias", persistedPerson.getLastName());
		assertEquals("Pernambuco, BR", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());
	}

	@Test
	@Order(3)
	public void testDisableById() throws JsonMappingException, JsonProcessingException {

		var content = given().spec(specification)
				.contentType(TestConfig.CONTENT_TYPE_JSON)
					.pathParam("id", person.getId())
				.when()
					.patch("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();

		PersonVO persistedPerson = objectMapper.readValue(content, PersonVO.class);
		person = persistedPerson;

		assertNotNull(persistedPerson);
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());
		assertFalse(persistedPerson.isEnabled());

		assertEquals(persistedPerson.getId(), person.getId());

		assertEquals("Eduardo", persistedPerson.getFirstName());
		assertEquals("Farias", persistedPerson.getLastName());
		assertEquals("Pernambuco, BR", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());
	}

	@Test
	@Order(4)
	public void testUpdate() throws JsonMappingException, JsonProcessingException {

		person.setFirstName("Livia");
		person.setLastName("Lins");
		person.setAddress("Rio Grande do Norte, BR");
		person.setGender("Female");

		var content = given().spec(specification)
				.contentType(TestConfig.CONTENT_TYPE_JSON)
					.body(person)
					.pathParam("id", person.getId())
				.when()
					.put("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();

		PersonVO updatedPeson = objectMapper.readValue(content, PersonVO.class);
		person = updatedPeson;

		assertNotNull(updatedPeson);
		assertNotNull(updatedPeson.getId());
		assertNotNull(updatedPeson.getFirstName());
		assertNotNull(updatedPeson.getLastName());
		assertNotNull(updatedPeson.getAddress());
		assertNotNull(updatedPeson.getGender());
		assertFalse(updatedPeson.isEnabled());

		assertTrue(updatedPeson.getId() == person.getId());

		assertEquals("Livia", updatedPeson.getFirstName());
		assertEquals("Lins", updatedPeson.getLastName());
		assertEquals("Rio Grande do Norte, BR", updatedPeson.getAddress());
		assertEquals("Female", updatedPeson.getGender());
	}

	@Test
	@Order(5)
	public void testDelete() throws JsonMappingException, JsonProcessingException {

		given().spec(specification)
			.contentType(TestConfig.CONTENT_TYPE_JSON)
				.pathParam("id", person.getId())
			.when()
				.delete("{id}")
			.then()
				.statusCode(204);

	}

	@Test
	@Order(6)
	public void testFindAll() throws JsonMappingException, JsonProcessingException {

		var content = given().spec(specification)
				.contentType(TestConfig.CONTENT_TYPE_JSON)
				.queryParams("page", 3 , "size", 10, "direction", "asc")
				.when()
					.get("/getAll")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
						// .as(new TypeRef<List<PersonVO>>() {});

		// List<PersonVO> people = objectMapper.readValue(content, new TypeReference<List<PersonVO>>() {});
		WrapperPersonVO wrapper = objectMapper.readValue(content, WrapperPersonVO.class);
		List<PersonVO> people = wrapper.getEmbedded().getPersons();

		PersonVO firstPerson = people.get(0);
		PersonVO lastPerson = people.get(people.size() - 1);

		assertNotNull(firstPerson);
		assertNotNull(firstPerson.getId());
		assertNotNull(firstPerson.getFirstName());
		assertNotNull(firstPerson.getLastName());
		assertNotNull(firstPerson.getAddress());
		assertNotNull(firstPerson.getGender());
		assertFalse(firstPerson.isEnabled());

		assertEquals(firstPerson.getId(), 34);
		assertEquals("Maddy", firstPerson.getFirstName());
		assertEquals("Woolfitt", firstPerson.getLastName());
		assertEquals("54 Luster Street", firstPerson.getAddress());
		assertEquals("Male", firstPerson.getGender());

		assertNotNull(lastPerson);
		assertNotNull(lastPerson.getId());
		assertNotNull(lastPerson.getFirstName());
		assertNotNull(lastPerson.getLastName());
		assertNotNull(lastPerson.getAddress());
		assertNotNull(lastPerson.getGender());
		assertFalse(lastPerson.isEnabled());

		assertEquals(lastPerson.getId(), 43);
		assertEquals("Rockey", lastPerson.getFirstName());
		assertEquals("Fritchley", lastPerson.getLastName());
		assertEquals("2 Tennyson Drive", lastPerson.getAddress());
		assertEquals("Male", lastPerson.getGender());
	}

	@Test
	@Order(7)
	public void testFindAllWithoutToken() throws JsonMappingException, JsonProcessingException {

		RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
							.addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_FROTA)
							.setBasePath("/api/person/v1")
							.setPort(TestConfig.SERVER_PORT)
							.addFilter(new RequestLoggingFilter(LogDetail.ALL))
							.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
							.build();

		given().spec(specificationWithoutToken)
				.contentType(TestConfig.CONTENT_TYPE_JSON)
				.when()
					.get()
				.then()
					.statusCode(403);

	}

	@Test
	@Order(8)
	public void testFindPersonByName() throws JsonMappingException, JsonProcessingException {

		var content = given().spec(specification)
				.contentType(TestConfig.CONTENT_TYPE_JSON)
				.pathParam("firstName", "be")
				.queryParams("page", 0 , "size", 10, "direction", "asc")
				.when()
					.get("/findPersonByName/{firstName}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		WrapperPersonVO wrapper = objectMapper.readValue(content, WrapperPersonVO.class);
		List<PersonVO> people = wrapper.getEmbedded().getPersons();

		PersonVO person = people.get(0);

		assertNotNull(person);
		assertNotNull(person.getId());
		assertNotNull(person.getFirstName());
		assertNotNull(person.getLastName());
		assertNotNull(person.getAddress());
		assertNotNull(person.getGender());
		assertTrue(person.isEnabled());

		assertEquals(person.getId(), 68);
		assertEquals("Corbet", person.getFirstName());
		assertEquals("Batcheldor", person.getLastName());
		assertEquals("609 Duke Trail", person.getAddress());
		assertEquals("Male", person.getGender());

	}

	@Test
	@Order(9)
	public void testHateoas() throws JsonMappingException, JsonProcessingException {

		var content = given().spec(specification)
				.contentType(TestConfig.CONTENT_TYPE_JSON)
				.queryParams("page", 1 , "size", 100, "direction", "asc")
				.when()
					.get("/getAll")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();

		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/person/v1/201\"}}}"));
		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/person/v1/134\"}}}"));
		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/person/v1/180\"}}}"));

		assertTrue(content.contains("\"first\":{\"href\":\"http://localhost:8888/api/person/v1/getAll?direction=asc&page=0&size=100&sort=id,asc\"}"));
		assertTrue(content.contains("\"prev\":{\"href\":\"http://localhost:8888/api/person/v1/getAll?direction=asc&page=0&size=100&sort=id,asc\"}"));
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/api/person/v1/getAll?page=1&size=100&direction=asc\"}"));
		assertTrue(content.contains("\"next\":{\"href\":\"http://localhost:8888/api/person/v1/getAll?direction=asc&page=2&size=100&sort=id,asc\"}"));
		assertTrue(content.contains("\"last\":{\"href\":\"http://localhost:8888/api/person/v1/getAll?direction=asc&page=10&size=100&sort=id,asc\"}"));

		assertTrue(content.contains("\"page\":{\"size\":100,\"totalElements\":1006,\"totalPages\":11,\"number\":1}}"));

	}

	private void mockPerson() {
		person.setFirstName("Eduardo");
		person.setLastName("Farias");
		person.setAddress("Pernambuco, BR");
		person.setGender("Male");
		person.setEnabled(true);
	}

}
