package com.kineo.web.controller;

import com.kineo.model.DocumentType;
import com.kineo.util.MediaType;
import com.kineo.web.dto.DocumentTypeDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mlischetti on 30/04/16.
 */
@RestController
@RequestMapping("/api/catalogs")
public class CatalogController {

    @RequestMapping(value = "/document-types", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF_8)
    public List<DocumentTypeDto> getDocumentTypes() {
        List<DocumentTypeDto> response = EnumSet.allOf(DocumentType.class).stream().map(
                (DocumentType documentType) -> new DocumentTypeDto(documentType)).collect(Collectors.toList());
        return response;
    }

    @RequestMapping(value = "/appointment-summaries", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF_8)
    public List<String> getAppointmentSummaries() {
        List<String> response = new ArrayList<>();
        response.add("Kinesiologia");
        response.add("Osteopatia");
        response.add("RPG");
        return response;
    }
}
