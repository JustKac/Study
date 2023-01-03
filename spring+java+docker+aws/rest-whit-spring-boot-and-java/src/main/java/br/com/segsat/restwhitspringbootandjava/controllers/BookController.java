package br.com.segsat.restwhitspringbootandjava.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.segsat.restwhitspringbootandjava.data.vo.v1.BookVO;
import br.com.segsat.restwhitspringbootandjava.exceptions.ExceptionResponse;
import br.com.segsat.restwhitspringbootandjava.services.BookService;
import br.com.segsat.restwhitspringbootandjava.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "api/book/v1")
@Tag(name = "Book", description = "Endpoints for Managing Book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/getAll", 
        produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
    @Operation(summary = "Finds all Book", description = "Finds all Book", 
        tags = {"Book"}, responses = {
                @ApiResponse(description = "Success", responseCode = "200", 
                    content = {
                        @Content(mediaType = MediaType.APPLICATION_JSON,
                        array = @ArraySchema(schema = @Schema(implementation = BookVO.class)))
                    }),
                @ApiResponse(description = "Bad Request", responseCode = "400", 
                    content = {
                        @Content(mediaType = MediaType.APPLICATION_JSON,
                        schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                @ApiResponse(description = "Unautorized", responseCode = "401", 
                    content = {
                        @Content(mediaType = MediaType.APPLICATION_JSON,
                        schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                @ApiResponse(description = "Not Found", responseCode = "404", 
                    content = {
                        @Content(mediaType = MediaType.APPLICATION_JSON,
                        schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                @ApiResponse(description = "Internal Server Errror", responseCode = "500", 
                    content = {
                        @Content(mediaType = MediaType.APPLICATION_JSON,
                        schema = @Schema(implementation = ExceptionResponse.class))
                    })
            })
    public List<BookVO> findAll() {

        return bookService.findAll();
    }

    @GetMapping(value = "/{id}", 
        produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
    @Operation(summary = "Finds a Book", description = "Finds a Book", 
    tags = {"Book"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", 
                content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = BookVO.class))
                }),
            @ApiResponse(description = "Bad Request", responseCode = "400", 
                content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = ExceptionResponse.class))
                }),
            @ApiResponse(description = "Unautorized", responseCode = "401", 
                content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = ExceptionResponse.class))
                }),
            @ApiResponse(description = "Not Found", responseCode = "404", 
                content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = ExceptionResponse.class))
                }),
            @ApiResponse(description = "Internal Server Errror", responseCode = "500", 
                content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = ExceptionResponse.class))
                })
        })
    public BookVO findById(@PathVariable(value = "id") Long id) {

        return bookService.findById(id);
    }

    @PostMapping(value = "/add",
        produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }, 
        consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
    @Operation(summary = "Persist a Book", description = "Persist a Book", 
    tags = {"Book"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", 
                content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = BookVO.class))
                }),
            @ApiResponse(description = "Bad Request", responseCode = "400", 
                content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = ExceptionResponse.class))
                }),
            @ApiResponse(description = "Unautorized", responseCode = "401", 
                content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = ExceptionResponse.class))
                }),
            @ApiResponse(description = "Internal Server Errror", responseCode = "500", 
                content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = ExceptionResponse.class))
                })
        })
    public BookVO addPerson(@RequestBody BookVO person) {

        return bookService.create(person);
    }

    @PutMapping(value = "/update/{id}", 
        produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }, 
        consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
    @Operation(summary = "Updates a Book", description = "Updates a Book", 
    tags = {"Book"}, responses = {
        @ApiResponse(description = "Success", responseCode = "200", 
            content = {
                @Content(mediaType = MediaType.APPLICATION_JSON,
                schema = @Schema(implementation = BookVO.class))
            }),
        @ApiResponse(description = "Bad Request", responseCode = "400", 
            content = {
                @Content(mediaType = MediaType.APPLICATION_JSON,
                schema = @Schema(implementation = ExceptionResponse.class))
            }),
        @ApiResponse(description = "Unautorized", responseCode = "401", 
            content = {
                @Content(mediaType = MediaType.APPLICATION_JSON,
                schema = @Schema(implementation = ExceptionResponse.class))
            }),
        @ApiResponse(description = "Not Found", responseCode = "404", 
            content = {
                @Content(mediaType = MediaType.APPLICATION_JSON,
                schema = @Schema(implementation = ExceptionResponse.class))
            }),
        @ApiResponse(description = "Internal Server Errror", responseCode = "500", 
            content = {
                @Content(mediaType = MediaType.APPLICATION_JSON,
                schema = @Schema(implementation = ExceptionResponse.class))
            })
    })
    public BookVO updatePerson(@PathVariable(value = "id") Long id, @RequestBody BookVO person) {

        return bookService.update(id, person);
    }

    @DeleteMapping(value = "/delete/{id}")
    @Operation(summary = "Finds a Book", description = "Finds a Book", 
    tags = {"Book"}, responses = {
        @ApiResponse(description = "No Content", responseCode = "204",  content = @Content()),
        @ApiResponse(description = "Bad Request", responseCode = "400", 
            content = {
                @Content(mediaType = MediaType.APPLICATION_JSON,
                schema = @Schema(implementation = ExceptionResponse.class))
            }),
        @ApiResponse(description = "Not Found", responseCode = "404", 
            content = {
                @Content(mediaType = MediaType.APPLICATION_JSON,
                schema = @Schema(implementation = ExceptionResponse.class))
            }),
        @ApiResponse(description = "Internal Server Errror", responseCode = "500", 
            content = {
                @Content(mediaType = MediaType.APPLICATION_JSON,
                schema = @Schema(implementation = ExceptionResponse.class))
            })
    })
    public ResponseEntity<?> DeletePerson(@PathVariable(value = "id") Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
