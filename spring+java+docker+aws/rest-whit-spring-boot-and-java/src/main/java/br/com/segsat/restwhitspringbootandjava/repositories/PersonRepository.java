package br.com.segsat.restwhitspringbootandjava.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.segsat.restwhitspringbootandjava.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Modifying
    @Query("UPDATE Person p SET p.enabled = false WHERE p.id = :id")
	void disablePersonById(@Param("id") Long id);

    @Query("SELECT p FROM Person p WHERE p.firstName LIKE LOWER(CONCAT('%', :firstName, '%'))")
	Page<Person> findPerondByName(@Param("firstName") String firstName, Pageable pageable);
}