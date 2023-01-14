package br.com.segsat.restwhitspringbootandjava.integrationtests.controller.yaml;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
public class AuthControllerYamlTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static YamlMapper objectMapper;
	private static TokenVO tokenVO;

	@BeforeAll
	public static void setup() {
		objectMapper = new YamlMapper();
	}

	@Test
	@Order(1)
	public void testSignin() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");

		specification = new RequestSpecBuilder()
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();

		tokenVO = given()
				.spec(specification)
				.config(
					RestAssuredConfig
						.config()
						.encoderConfig(EncoderConfig.encoderConfig()
							.encodeContentTypeAs(TestConfig.CONTENT_TYPE_YML, ContentType.TEXT)))
				.accept(TestConfig.CONTENT_TYPE_YML)
				.basePath("/auth/signin")
				.port(TestConfig.SERVER_PORT)
				.contentType(TestConfig.CONTENT_TYPE_YML)
					.body(user, objectMapper)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.as(TokenVO.class, objectMapper);

		assertNotNull(tokenVO.getAcessToken());
		assertNotNull(tokenVO.getRefreshToken());

	}

	@Test
	@Order(2)
	public void testRefresh() throws JsonMappingException, JsonProcessingException {

		var newTokenVO = given()
				.spec(specification)
				.config(
					RestAssuredConfig
						.config()
						.encoderConfig(EncoderConfig.encoderConfig()
							.encodeContentTypeAs(TestConfig.CONTENT_TYPE_YML, ContentType.TEXT)))
				.accept(TestConfig.CONTENT_TYPE_YML)
				.basePath("/auth/refresh")
				.port(TestConfig.SERVER_PORT)
				.contentType(TestConfig.CONTENT_TYPE_YML)
					.header(TestConfig.HEADER_PARAM_AUTHORIZATION, "Bearer " +  tokenVO.getRefreshToken())
					.pathParam("username", tokenVO.getUsername())
				.when()
					.put("{username}")
				.then()
				.statusCode(200)
				.extract()
				.body()
				.as(TokenVO.class, objectMapper);

		assertNotNull(newTokenVO.getAcessToken());
		assertNotNull(newTokenVO.getRefreshToken());

	}

}
