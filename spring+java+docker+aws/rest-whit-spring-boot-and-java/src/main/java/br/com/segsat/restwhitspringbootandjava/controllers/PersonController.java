package br.com.segsat.restwhitspringbootandjava.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.segsat.restwhitspringbootandjava.data.vo.v1.PersonVO;
import br.com.segsat.restwhitspringbootandjava.exceptions.ExceptionResponse;
import br.com.segsat.restwhitspringbootandjava.services.PersonService;
import br.com.segsat.restwhitspringbootandjava.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "api/person/v1")
@Tag(name = "People", description = "Endpoints for Managing People")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping( 
        produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
    @Operation(summary = "Finds all People", description = "Finds all People", 
        tags = {"People"}, responses = {
                @ApiResponse(description = "Success", responseCode = "200", 
                    content = {
                        @Content(mediaType = MediaType.APPLICATION_JSON,
                        array = @ArraySchema(schema = @Schema(implementation = PersonVO.class)))
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
    public List<PersonVO> findAll() {

        return personService.findAll();
    }

    @GetMapping(value = "/{id}", 
        produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
    @Operation(summary = "Finds a People", description = "Finds a People", 
    tags = {"People"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", 
                content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = PersonVO.class))
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
    @CrossOrigin(origins = {"http://localhost:8080"})
    public PersonVO findById(@PathVariable(value = "id") Long id) {

        return personService.findById(id);
    }

    @PostMapping(
        produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }, 
        consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
    @Operation(summary = "Persist a People", description = "Persist a People", 
    tags = {"People"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", 
                content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = PersonVO.class))
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
    @CrossOrigin(origins = {"http://localhost:8080", "https://frota.setsat.com"})
    public PersonVO addPerson(@RequestBody PersonVO person) {

        return personService.create(person);
    }

    @PutMapping(value = "/{id}", 
        produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }, 
        consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
    @Operation(summary = "Updates a People", description = "Updates a People", 
    tags = {"People"}, responses = {
        @ApiResponse(description = "Success", responseCode = "200", 
            content = {
                @Content(mediaType = MediaType.APPLICATION_JSON,
                schema = @Schema(implementation = PersonVO.class))
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
    public PersonVO updatePerson(@PathVariable(value = "id") Long id, @RequestBody PersonVO person) {

        return personService.update(id, person);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Finds a People", description = "Finds a People", 
    tags = {"People"}, responses = {
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
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
