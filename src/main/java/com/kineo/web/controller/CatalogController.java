package com.kineo.web.controller;

import com.kineo.model.DocumentType;
import com.kineo.model.ProfessionalCategory;
import com.kineo.util.MediaType;
import com.kineo.web.dto.DocumentTypeDto;
import com.kineo.web.dto.ProfessionalCategoryDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/catalogs")
public class CatalogController {

    @RequestMapping(value = "/document-types", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF_8)
    public List<DocumentTypeDto> getDocumentTypes() {
        List<DocumentTypeDto> response = EnumSet.allOf(DocumentType.class).stream().map(
                documentType -> new DocumentTypeDto(documentType)).collect(Collectors.toList());
        return response;
    }

    @RequestMapping(value = "/appointment-services", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF_8)
    public List<String> getAppointmentServices() {
        List<String> response = new ArrayList<>();
        response.add("Kinesiologia");
        response.add("Osteopatia");
        response.add("RPG");
        return response;
    }

    @RequestMapping(value = "/professional-categories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF_8)
    public List<ProfessionalCategoryDto> getProfessionalCategories() {
        List<ProfessionalCategoryDto> response = EnumSet.allOf(ProfessionalCategory.class).stream().map(
                professionalCategory -> new ProfessionalCategoryDto(professionalCategory)).collect(Collectors.toList());
        return response;
    }
}
