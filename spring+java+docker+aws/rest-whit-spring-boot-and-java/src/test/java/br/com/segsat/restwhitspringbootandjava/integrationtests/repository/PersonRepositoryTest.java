package br.com.segsat.restwhitspringbootandjava.integrationtests.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.segsat.restwhitspringbootandjava.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.segsat.restwhitspringbootandjava.model.Person;
import br.com.segsat.restwhitspringbootandjava.repositories.PersonRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class PersonRepositoryTest extends AbstractIntegrationTest {

	@Autowired
	PersonRepository repository;

	private static Person person;

	@BeforeAll
	public static void setup() {
		person = new Person();
	}

	@Test
	@Order(1)
	public void testFindPersonByName() throws JsonMappingException, JsonProcessingException {

		Pageable pageable = PageRequest.of(0, 10, Sort.by(Direction.ASC, "id"));
		person = repository.findPerondByName("be", pageable).getContent().get(0);

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
	@Order(2)
	public void disablePersonById() throws JsonMappingException, JsonProcessingException {

		repository.disablePersonById(person.getId());
		person = repository.findById(person.getId()).get();

		assertNotNull(person);
		assertNotNull(person.getId());
		assertNotNull(person.getFirstName());
		assertNotNull(person.getLastName());
		assertNotNull(person.getAddress());
		assertNotNull(person.getGender());
		assertFalse(person.isEnabled());

		assertEquals(person.getId(), 68);
		assertEquals("Corbet", person.getFirstName());
		assertEquals("Batcheldor", person.getLastName());
		assertEquals("609 Duke Trail", person.getAddress());
		assertEquals("Male", person.getGender());
	}

}
