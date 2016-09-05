package com.kineo.web.controller;

import com.kineo.model.DocumentType;
import com.kineo.model.Professional;
import com.kineo.model.ProfessionalCategory;
import com.kineo.service.internal.ProfessionalService;
import com.kineo.util.MediaType;
import com.kineo.web.dto.ProfessionalDto;
import com.kineo.web.exception.EntityNotFoundException;
import com.kineo.web.exception.ValidationException;
import com.kineo.web.request.professional.ProfessionalRequest;
import com.kineo.web.response.PaginationResponse;
import com.kineo.web.response.Paging;
import com.kineo.web.response.professional.ProfessionalResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mlischetti on 11/29/15.
 */
@RestController
@RequestMapping("/api/professionals")
public class ProfessionalController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfessionalController.class);

    private static final Integer FIRST_RESULT = 0;
    private static final Integer MAX_RESULT = 100;

    private final ProfessionalService professionalService;

    @Autowired
    public ProfessionalController(ProfessionalService professionalService) {
        this.professionalService = professionalService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF_8)
    public ProfessionalDto getProfessional(@PathVariable("id") Long id) {
        LOGGER.debug("Retrieving professional: {}", id);
        Professional professional = professionalService.findById(id);
        if (professional == null) {
            LOGGER.debug("Could not found professional: {}", id);
            EntityNotFoundException exception = new EntityNotFoundException(Professional.ENTITY);
            exception.setSearchMessage("id = " + id);
            throw exception;
        }
        return new ProfessionalDto(professional);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF_8)
    public PaginationResponse<ProfessionalDto> getProfessionals(@RequestParam(value = "limit", required = false) Integer limit, @RequestParam(value = "offset", required = false) Integer offset) {
        LOGGER.debug("Retrieving professionals...");
        int fistResult = FIRST_RESULT;
        if (offset != null) {
            fistResult = offset;
        }
        int maxResult = MAX_RESULT;
        if (limit != null) {
            if (limit > MAX_RESULT) {
                ValidationException exception = new ValidationException();
                exception.errorField("limit", "Could not be greater than " + MAX_RESULT);
                throw exception;
            }
            maxResult = limit;
        }
        LOGGER.debug("Retrieve professionals from:{}, limit:{}", fistResult, maxResult);
        PaginationResponse<ProfessionalDto> response = new PaginationResponse<>();
        List<Professional> professionals = professionalService.find(fistResult, maxResult);
        response.setItems(professionals.stream().map((Professional professional) -> new ProfessionalDto(professional)).collect(Collectors.toList()));
        Paging paging = new Paging();
        paging.setLimit(maxResult);
        paging.setOffset(fistResult);
        paging.setTotal(professionalService.count());
        response.setPaging(paging);
        return response;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON_UTF_8)
    public ProfessionalResponse createProfessional(@Valid @RequestBody ProfessionalRequest professionalRequest) {
        LOGGER.debug("Creating professional...");
        Professional professional = new Professional();
        saveOrUpdate(professional, professionalRequest);
        LOGGER.debug("Created professional: {}", professional.getId());
        return new ProfessionalResponse(professional.getId());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON_UTF_8)
    public ProfessionalResponse updateProfessional(@PathVariable("id") Long id,
                                       @Valid @RequestBody ProfessionalRequest professionalRequest) {
        LOGGER.debug("Updating professional: {}", id);
        Professional professional = professionalService.findById(id);
        if (professional== null) {
            LOGGER.debug("Could not found professional: {}", id);
            EntityNotFoundException exception = new EntityNotFoundException(Professional.ENTITY);
            exception.setSearchMessage("id = " + id);
            throw exception;
        }
        saveOrUpdate(professional, professionalRequest);
        LOGGER.debug("Updated professional: {}", id);
        return new ProfessionalResponse(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteProfessional(@PathVariable("id") Long id) {
        LOGGER.debug("Deleting professional: {}", id);
        Professional professional = professionalService.findById(id);
        if (professional == null) {
            LOGGER.debug("Could not found professional: {}", id);
            EntityNotFoundException exception = new EntityNotFoundException(Professional.ENTITY);
            exception.setSearchMessage("id = " + id);
            throw exception;
        }
        professionalService.delete(professional);
        LOGGER.debug("Deleted professional: {}", id);
    }

    private void saveOrUpdate(Professional professional, ProfessionalRequest professionalRequest) {
        professional.setCategory(ProfessionalCategory.valueOf(professionalRequest.getCategory()));
        professional.setFirstName(professionalRequest.getFirstName());
        professional.setLastName(professionalRequest.getLastName());
        professional.setDateOfBirth(professionalRequest.getDateOfBirth());
        professional.setEmail(professionalRequest.getEmail());
        professional.setPhone(professionalRequest.getPhone());
        professional.setDocumentType(DocumentType.valueOf(professionalRequest.getDocumentType()));
        professional.setDocumentNumber(professionalRequest.getDocumentNumber());
        professionalService.save(professional);
    }
}
