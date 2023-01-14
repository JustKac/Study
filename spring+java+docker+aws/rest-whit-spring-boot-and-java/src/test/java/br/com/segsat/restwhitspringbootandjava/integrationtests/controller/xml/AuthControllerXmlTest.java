package br.com.segsat.restwhitspringbootandjava.integrationtests.controller.xml;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.segsat.restwhitspringbootandjava.configs.TestConfig;
import br.com.segsat.restwhitspringbootandjava.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.segsat.restwhitspringbootandjava.integrationtests.vo.v1.AccountCredentialsVO;
import br.com.segsat.restwhitspringbootandjava.integrationtests.vo.v1.TokenVO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class AuthControllerXmlTest extends AbstractIntegrationTest {

	private static TokenVO tokenVO;

	@Test
	@Order(1)
	public void testSignin() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");

		tokenVO = given()
				.basePath("/auth/signin")
				.port(TestConfig.SERVER_PORT)
				.contentType(TestConfig.CONTENT_TYPE_XML)
				.body(user)
				.when()
				.post()
				.then()
				.statusCode(200)
				.extract()
				.body()
				.as(TokenVO.class);

		assertNotNull(tokenVO.getAcessToken());
		assertNotNull(tokenVO.getRefreshToken());

	}

	@Test
	@Order(2)
	public void testRefresh() throws JsonMappingException, JsonProcessingException {

		var newTokenVO = given()
				.basePath("/auth/refresh")
				.port(TestConfig.SERVER_PORT)
				.contentType(TestConfig.CONTENT_TYPE_XML)
					.header(TestConfig.HEADER_PARAM_AUTHORIZATION, "Bearer " +  tokenVO.getRefreshToken())
					.pathParam("username", tokenVO.getUsername())
				.when()
					.put("{username}")
				.then()
				.statusCode(200)
				.extract()
				.body()
				.as(TokenVO.class);

		assertNotNull(newTokenVO.getAcessToken());
		assertNotNull(newTokenVO.getRefreshToken());

	}

}
