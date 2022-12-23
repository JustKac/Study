package br.com.segsat.restwhitspringbootandjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.segsat.restwhitspringbootandjava.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
