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

import br.com.segsat.restwhitspringbootandjava.controllers.BookController;
import br.com.segsat.restwhitspringbootandjava.data.vo.v1.BookVO;
import br.com.segsat.restwhitspringbootandjava.exceptions.RequiredObjectIsNullException;
import br.com.segsat.restwhitspringbootandjava.exceptions.ResourceNotFoundException;
import br.com.segsat.restwhitspringbootandjava.mapper.DozerMapper;
import br.com.segsat.restwhitspringbootandjava.model.Book;
import br.com.segsat.restwhitspringbootandjava.repositories.BookRepository;

@Service
public class BookService {

    private Logger logger = Logger.getLogger(BookService.class.getName());

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PagedResourcesAssembler<BookVO> assembler;

    public PagedModel<EntityModel<BookVO>> findAll(Pageable pageable) {

        var bookPage = bookRepository.findAll(pageable);

        Page<BookVO> bookVosPage = bookPage.map(b -> DozerMapper.parseObject(b, BookVO.class))
                                        .map(b -> b.add(linkTo(methodOn(BookController.class)
                                        .findById(b.getKey())).withSelfRel()));

        Link link = linkTo(methodOn(BookController.class).findAll(
            pageable.getPageNumber(), 
            pageable.getPageSize(), 
            "asc")).withSelfRel();

        return assembler.toModel(bookVosPage, link);
    }

    public BookVO findById(Long id) {

        var entity = bookRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        BookVO vo = DozerMapper.parseObject(entity, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return vo;
    }

    public BookVO create(BookVO person) {

        if (person == null){
            throw new RequiredObjectIsNullException();
        }
        logger.info("Creating one person!");
        var entity = bookRepository.save(DozerMapper.parseObject(person, Book.class));
        var vo = DozerMapper.parseObject(entity, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public BookVO update(Long id, BookVO newBook) {

        if (newBook == null){
            throw new RequiredObjectIsNullException();
        }
        logger.info("Updating one person!");
        BookVO oldBook = findById(id);

        oldBook.setAuthor(newBook.getAuthor());
        oldBook.setTitle(newBook.getTitle());
        oldBook.setLaunchDate(newBook.getLaunchDate());
        oldBook.setPrice(newBook.getPrice());

        var entity = bookRepository.save(DozerMapper.parseObject(oldBook, Book.class));
        var vo = DozerMapper.parseObject(entity, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {

        logger.info("Deleting one person!");
        bookRepository.delete(DozerMapper.parseObject(findById(id), Book.class));
    }

}
