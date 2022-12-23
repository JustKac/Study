package br.com.segsat.restwhitspringbootandjava.services;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.segsat.restwhitspringbootandjava.exceptions.ResourceNotFoundException;
import br.com.segsat.restwhitspringbootandjava.model.Person;
import br.com.segsat.restwhitspringbootandjava.repositories.PersonRepository;

@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    private PersonRepository personRepository;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findById(Long id) {

        Optional<Person> person = personRepository.findById(id);

        return person.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
    }

    public Person create(Person person) {

        logger.info("Creating one person!");
        return personRepository.save(person);
    }

    public Person update(Long id, Person newPerson) {

        logger.info("Updating one person!");
        Person oldPerson = findById(id);

        oldPerson.setFirstName(newPerson.getfirstName());
        oldPerson.setLastName(newPerson.getLastName());
        oldPerson.setAdress(newPerson.getAdress());
        oldPerson.setGender(newPerson.getGender());

        return personRepository.save(oldPerson);
    }

    public void delete(Long id) {

        logger.info("Deleting one person!");
        personRepository.delete(findById(id));
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

}
