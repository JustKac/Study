package br.com.segsat.restwhitspringbootandjava.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.segsat.restwhitspringbootandjava.data.vo.v1.PersonVO;
import br.com.segsat.restwhitspringbootandjava.data.vo.v2.PersonVOV2;
import br.com.segsat.restwhitspringbootandjava.exceptions.ResourceNotFoundException;
import br.com.segsat.restwhitspringbootandjava.mapper.DozerMapper;
import br.com.segsat.restwhitspringbootandjava.mapper.PersonMapper;
import br.com.segsat.restwhitspringbootandjava.model.Person;
import br.com.segsat.restwhitspringbootandjava.repositories.PersonRepository;

@Service
public class PersonService {

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonMapper personMapper;

    public List<PersonVO> findAll() {

        return DozerMapper.parseListObjects(personRepository.findAll(), PersonVO.class);
    }

    public PersonVO findById(Long id) {

        var entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        return DozerMapper.parseObject(entity, PersonVO.class);
    }

    public PersonVO create(PersonVO person) {

        logger.info("Creating one person!");
        var entity = personRepository.save(DozerMapper.parseObject(person, Person.class));
        return DozerMapper.parseObject(entity, PersonVO.class);
    }

    public PersonVOV2 createV2(PersonVOV2 person) {

        logger.info("Creating one person!");
        var entity = personRepository.save(personMapper.convertVoToEntity(person));
        return personMapper.convertEntityToVo(entity);
    }

    public PersonVO update(Long id, PersonVO newPerson) {

        logger.info("Updating one person!");
        PersonVO oldPerson = findById(id);

        oldPerson.setFirstName(newPerson.getFirstName());
        oldPerson.setLastName(newPerson.getLastName());
        oldPerson.setAddress(newPerson.getAddress());
        oldPerson.setGender(newPerson.getGender());

        var entity = personRepository.save(DozerMapper.parseObject(oldPerson, Person.class));
        return DozerMapper.parseObject(entity, PersonVO.class);
    }

    public void delete(Long id) {

        logger.info("Deleting one person!");
        personRepository.delete(DozerMapper.parseObject(findById(id), Person.class));
    }

}
