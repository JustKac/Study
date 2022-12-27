package br.com.segsat.restwhitspringbootandjava.mapper;

import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.segsat.restwhitspringbootandjava.data.vo.v2.PersonVOV2;
import br.com.segsat.restwhitspringbootandjava.model.Person;

@Service
public class PersonMapper {

    public PersonVOV2 convertEntityToVo(Person person){
        PersonVOV2 vo = new PersonVOV2();

        vo.setId(person.getId());
        vo.setFirstName(person.getFirstName());
        vo.setLastName(null);
        vo.setBirthDay(new Date());
        vo.setAddress(person.getAddress());
        vo.setGender(person.getGender());

        return vo;
    }

    public Person convertVoToEntity(PersonVOV2 vo){
        Person person = new Person();

        person.setId(person.getId());
        person.setFirstName(person.getFirstName());
        person.setLastName(null);
    //    person.setBirthDay(vo.getBirthDay());
        person.setAddress(person.getAddress());
        person.setGender(person.getGender());

        return person;
    }

}
