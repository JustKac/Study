package br.com.segsat.restwhitspringbootandjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.segsat.restwhitspringbootandjava.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
