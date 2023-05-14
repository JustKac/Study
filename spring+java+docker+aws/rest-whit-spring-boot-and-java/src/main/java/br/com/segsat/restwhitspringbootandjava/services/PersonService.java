package br.com.segsat.restwhitspringbootandjava.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
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

    @Autowired
    private PagedResourcesAssembler<PersonVO> assembler;

    public PagedModel<EntityModel<PersonVO>> findAll(Pageable pageable) {

        var personPage = personRepository.findAll(pageable);

        Page<PersonVO> personVosPage = personPage.map(p -> DozerMapper.parseObject(p, PersonVO.class))
                                                .map(p -> p.add(linkTo(methodOn(PersonController.class)
                                                .findById(p.getKey())).withSelfRel()));
                                                
        Link link = linkTo(methodOn(PersonController.class).findAll(
            pageable.getPageNumber(), 
            pageable.getPageSize(), 
            "asc")).withSelfRel();
        return assembler.toModel(personVosPage, link);
    }

    public PagedModel<EntityModel<PersonVO>> findPersonByName(String firstName, Pageable pageable) {

        var personPage = personRepository.findPerondByName(firstName, pageable);

        Page<PersonVO> personVosPage = personPage.map(p -> DozerMapper.parseObject(p, PersonVO.class))
                                                .map(p -> p.add(linkTo(methodOn(PersonController.class)
                                                .findById(p.getKey())).withSelfRel()));
                                                
        Link link = linkTo(methodOn(PersonController.class).findPersonByName(
            firstName,
            pageable.getPageNumber(), 
            pageable.getPageSize(), 
            "asc")).withSelfRel();
        return assembler.toModel(personVosPage, link);
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