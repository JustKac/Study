package br.com.segsat.restwhitspringbootandjava.controllers;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.segsat.restwhitspringbootandjava.data.vo.v1.PersonVO;
import br.com.segsat.restwhitspringbootandjava.data.vo.v1.UploadFileResponseVO;
import br.com.segsat.restwhitspringbootandjava.exceptions.ExceptionResponse;
import br.com.segsat.restwhitspringbootandjava.services.FileStorageService;
import br.com.segsat.restwhitspringbootandjava.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "api/file/v1")
@Tag(name = "File", description = "Endpoints for Managing File")
public class FileController {

    private Logger logger = Logger.getLogger(FileController.class.getName());

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping(value = "/upload", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
            MediaType.APPLICATION_YML })
    @Operation(summary = "Upload a File", description = "Upload a File", tags = { "People" }, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = PersonVO.class)))
            }),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ExceptionResponse.class))
            }),
            @ApiResponse(description = "Unautorized", responseCode = "401", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ExceptionResponse.class))
            }),
            @ApiResponse(description = "Not Found", responseCode = "404", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ExceptionResponse.class))
            }),
            @ApiResponse(description = "Internal Server Errror", responseCode = "500", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ExceptionResponse.class))
            })
    })
    public UploadFileResponseVO uploadFile(
            @RequestParam(name = "file") MultipartFile file) {

        logger.info("Storing file '" + file.getOriginalFilename() + "' to disk");

        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("api/file/v1/download")
                .path(fileName)
                .toUriString();

        return new UploadFileResponseVO(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

}