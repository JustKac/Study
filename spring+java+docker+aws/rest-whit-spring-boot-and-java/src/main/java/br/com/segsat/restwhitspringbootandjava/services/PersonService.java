package br.com.segsat.restwhitspringbootandjava.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import br.com.segsat.restwhitspringbootandjava.controllers.PersonController;
import br.com.segsat.restwhitspringbootandjava.data.vo.v1.PersonVO;
import br.com.segsat.restwhitspringbootandjava.exceptions.RequiredObjectIsNullException;
import br.com.segsat.restwhitspringbootandjava.exceptions.ResourceNotFoundException;
import br.com.segsat.restwhitspringbootandjava.mapper.DozerMapper;
import br.com.segsat.restwhitspringbootandjava.model.Person;
import br.com.segsat.restwhitspringbootandjava.repositories.PersonRepository;
import jakarta.transaction.Transactional;

@Service
public class PersonService {

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    private PersonRepository personRepository;

    public List<PersonVO> findAll() {

        var persons = DozerMapper.parseListObjects(personRepository.findAll(), PersonVO.class);
        persons.stream().forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

        return persons;
    }

    public PersonVO findById(Long id) {

        var entity = personRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public PersonVO create(PersonVO person) {

        if (person == null){
            throw new RequiredObjectIsNullException();
        }
        logger.info("Creating one person!");
        var entity = personRepository.save(DozerMapper.parseObject(person, Person.class));
        var vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PersonVO update(Long id, PersonVO newPerson) {

        if (newPerson == null){
            throw new RequiredObjectIsNullException();
        }
        logger.info("Updating one person!");
        PersonVO oldPerson = findById(id);

        oldPerson.setFirstName(newPerson.getFirstName());
        oldPerson.setLastName(newPerson.getLastName());
        oldPerson.setAddress(newPerson.getAddress());
        oldPerson.setGender(newPerson.getGender());

        var entity = personRepository.save(DozerMapper.parseObject(oldPerson, Person.class));
        var vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    @Transactional
    public PersonVO disablePersonById(Long id) {

        logger.info("Disabling one person!");

        personRepository.disablePersonById(id);

        var entity = personRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public void delete(Long id) {

        logger.info("Deleting one person!");
        personRepository.delete(DozerMapper.parseObject(findById(id), Person.class));
    }

}