package br.com.segsat.restwhitspringbootandjava.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.segsat.restwhitspringbootandjava.data.vo.v1.PersonVO;
import br.com.segsat.restwhitspringbootandjava.services.PersonService;

@RestController
@RequestMapping(value = "api/person/v1")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PersonVO> findAll() throws Exception {

        return personService.findAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonVO findById(@PathVariable(value = "id") String id) throws Exception {

        return personService.findById(Long.parseLong(id));
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public PersonVO addPerson(@RequestBody PersonVO person) throws Exception {

        return personService.create(person);
    }

    @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public PersonVO updatePerson(@PathVariable(value = "id") String id, @RequestBody PersonVO person) throws Exception {

        return personService.update(Long.parseLong(id), person);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> DeletePerson(@PathVariable(value = "id") String id) throws Exception {
        personService.delete(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }

}
