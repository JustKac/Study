package br.com.segsat.restwhitspringbootandjava.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import br.com.segsat.restwhitspringbootandjava.model.Person;

@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public List<Person> findAll() {

        List<Person> list = new ArrayList<>();
        logger.info("Finding all persons!");
        for (int i = 0; i < 10; i++) {

            Person person = mockperson(i);
            list.add(person);
        }

        return list;
    }

    public Person findById(String id) {

        logger.info("Finding one person!");
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Caio");
        person.setLastName("Lins");
        person.setAdress("Paulista - Pernambuco, Brasil");
        person.setGender("Male");

        return person;
    }

    public Person create(Person person) {

        logger.info("Creating one person!");
        person.setId(counter.incrementAndGet());

        return person;
    }

    public Person update(Person person) {

        logger.info("Updating one person!");

        return person;
    }

    private Person mockperson(int i) {

        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Person name " + i);
        person.setLastName("Last name " + i);
        person.setAdress("Paulista - Pernambuco, Brasil " + i);
        person.setGender("Male");
        return person;
    }

    public void delete(String id) {

        logger.info("Deleting one person!");

    }

}
