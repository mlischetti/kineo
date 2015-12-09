package com.kinesio.web.controller;

import com.kinesio.model.MedicalInsuranceCompany;
import com.kinesio.model.MedicalInsurancePlan;
import com.kinesio.service.internal.MedicalInsuranceService;
import com.kinesio.web.dto.MedicalInsuranceCompanyDto;
import com.kinesio.web.dto.MedicalInsurancePlanDto;
import com.kinesio.web.exception.EntityNotFoundException;
import com.kinesio.web.exception.ValidationException;
import com.kinesio.web.request.medical_insurance.MedicalInsuranceCompanyRequest;
import com.kinesio.web.request.medical_insurance.MedicalInsurancePlanRequest;
import com.kinesio.web.response.PaginationResponse;
import com.kinesio.web.response.Paging;
import com.kinesio.web.response.medical_insurance.MedicalInsuranceCompanyResponse;
import com.kinesio.web.response.medical_insurance.MedicalInsurancePlanResponse;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * Created by mlischetti on 12/8/15.
 */
@RestController
@RequestMapping(value = "/api")
public class MedicalInsuranceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MedicalInsuranceController.class);

    private static final Integer FIRST_RESULT = 0;
    private static final Integer MAX_RESULT = 100;

    private MedicalInsuranceService medicalInsuranceService;

    @Autowired
    public MedicalInsuranceController(MedicalInsuranceService medicalInsuranceService) {
        this.medicalInsuranceService = medicalInsuranceService;
    }

    //Medical-Company
    @RequestMapping(value = "/medical-insurances/companies/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public MedicalInsuranceCompanyDto getCompany(@PathVariable("id") Long id) {
        LOGGER.debug("Retrieving medical insurance company: {}", id);
        MedicalInsuranceCompany company = medicalInsuranceService.findCompanyById(id);
        if (company == null) {
            LOGGER.debug("Could not found medial insurance company: {}", id);
            EntityNotFoundException exception = new EntityNotFoundException(MedicalInsuranceCompany.ENTITY);
            exception.setSearchMessage("id = " + id);
            throw exception;
        }
        return new MedicalInsuranceCompanyDto(company);
    }

    @RequestMapping(value = "/medical-insurances/companies", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public PaginationResponse<MedicalInsuranceCompanyDto> getDoctors(@RequestParam(value = "limit", required = false) Integer limit, @RequestParam(value = "offset", required = false) Integer offset) {
        LOGGER.debug("Retrieving medical insurance companies...");
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
        LOGGER.debug("Retrieve medical insurance companies from:{}, limit:{}", fistResult, maxResult);
        PaginationResponse<MedicalInsuranceCompanyDto> response = new PaginationResponse<>();
        List<MedicalInsuranceCompany> companies = medicalInsuranceService.findCompanies(fistResult, maxResult);
        if (CollectionUtils.isNotEmpty(companies)) {
            for (MedicalInsuranceCompany company : companies) {
                response.addItem(new MedicalInsuranceCompanyDto(company));
            }
        }
        Paging paging = new Paging();
        paging.setLimit(maxResult);
        paging.setOffset(fistResult);
        paging.setTotal(medicalInsuranceService.countCompanies());
        response.setPaging(paging);
        return response;
    }

    @RequestMapping(value = "/medical-insurances/companies", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public MedicalInsuranceCompanyResponse createCompany(@Valid @RequestBody MedicalInsuranceCompanyRequest medicalCompanyRequest) {
        LOGGER.debug("Creating medical insurance company...");
        MedicalInsuranceCompany company = new MedicalInsuranceCompany();
        company.setName(medicalCompanyRequest.getName());
        medicalInsuranceService.save(company);
        LOGGER.debug("Created medical insurance company: {}", company.getId());
        return new MedicalInsuranceCompanyResponse(company.getId());
    }

    @RequestMapping(value = "/medical-insurances/companies/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public MedicalInsuranceCompanyResponse updateCompany(@PathVariable("id") Long id,
                                                         @Valid @RequestBody MedicalInsuranceCompanyRequest medicalCompanyRequest) {
        LOGGER.debug("Updating medical insurance company: {}", id);
        MedicalInsuranceCompany company = medicalInsuranceService.findCompanyById(id);
        if (company == null) {
            LOGGER.debug("Could not found medial insurance company: {}", id);
            EntityNotFoundException exception = new EntityNotFoundException(MedicalInsuranceCompany.ENTITY);
            exception.setSearchMessage("id = " + id);
            throw exception;
        }
        company.setName(medicalCompanyRequest.getName());
        medicalInsuranceService.save(company);
        LOGGER.debug("Updated medical insurance company: {}", id);
        return new MedicalInsuranceCompanyResponse(id);
    }

    //Medical-Plan
    @RequestMapping(value = "/medical-insurances/plans/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public MedicalInsurancePlanDto getPlan(@PathVariable("id") Long id) {
        LOGGER.debug("Retrieving medical insurance plan: {}", id);
        MedicalInsurancePlan plan = medicalInsuranceService.findPlanById(id);
        if (plan == null) {
            LOGGER.debug("Could not found medial insurance plan: {}", id);
            EntityNotFoundException exception = new EntityNotFoundException(MedicalInsurancePlan.ENTITY);
            exception.setSearchMessage("id = " + id);
            throw exception;
        }
        return new MedicalInsurancePlanDto(plan);
    }


    @RequestMapping(value = "/medical-insurances/plans", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public PaginationResponse<MedicalInsurancePlanDto> getPlans(@RequestParam(value = "limit", required = false) Integer limit, @RequestParam(value = "offset", required = false) Integer offset) {
        LOGGER.debug("Retrieving medical insurance plans...");
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
        LOGGER.debug("Retrieve medical insurance plans from:{}, limit:{}", fistResult, maxResult);
        PaginationResponse<MedicalInsurancePlanDto> response = new PaginationResponse<>();
        List<MedicalInsurancePlan> plans = medicalInsuranceService.findPlans(fistResult, maxResult);
        if (CollectionUtils.isNotEmpty(plans)) {
            for (MedicalInsurancePlan plan : plans) {
                response.addItem(new MedicalInsurancePlanDto(plan));
            }
        }
        Paging paging = new Paging();
        paging.setLimit(maxResult);
        paging.setOffset(fistResult);
        paging.setTotal(medicalInsuranceService.countPlans());
        response.setPaging(paging);
        return response;
    }

    @RequestMapping(value = "/medical-insurances/plans", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public MedicalInsurancePlanResponse createPlan(@Valid @RequestBody MedicalInsurancePlanRequest medicalPlanRequest) {
        LOGGER.debug("Creating medical insurance plan...");
        MedicalInsuranceCompany company = medicalInsuranceService.findCompanyById(medicalPlanRequest.getCompanyId());
        if (company == null) {
            LOGGER.debug("Could not found medial insurance company: {}", medicalPlanRequest.getCompanyId());
            EntityNotFoundException exception = new EntityNotFoundException(MedicalInsuranceCompany.ENTITY);
            exception.setSearchMessage("id = " + medicalPlanRequest.getCompanyId());
            throw exception;
        }
        MedicalInsurancePlan plan = new MedicalInsurancePlan();
        plan.setPlan(medicalPlanRequest.getPlan());
        plan.setCompany(company);
        medicalInsuranceService.save(plan);
        LOGGER.debug("Created medical insurance plan: {}", plan.getId());
        return new MedicalInsurancePlanResponse(plan.getId());
    }

    @RequestMapping(value = "/medical-insurances/plans/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public MedicalInsurancePlanResponse updatePlan(@PathVariable("id") Long id,
                                                   @Valid @RequestBody MedicalInsurancePlanRequest medicalPlanRequest) {
        LOGGER.debug("Updating medical insurance plan: {}", id);
        MedicalInsurancePlan plan = medicalInsuranceService.findPlanById(id);
        if (plan == null) {
            LOGGER.debug("Could not found medial insurance plan: {}", id);
            EntityNotFoundException exception = new EntityNotFoundException(MedicalInsurancePlan.ENTITY);
            exception.setSearchMessage("id = " + id);
            throw exception;
        }

        MedicalInsuranceCompany company = medicalInsuranceService.findCompanyById(medicalPlanRequest.getCompanyId());
        if (company == null) {
            LOGGER.debug("Could not found medial insurance company: {}", medicalPlanRequest.getCompanyId());
            EntityNotFoundException exception = new EntityNotFoundException(MedicalInsuranceCompany.ENTITY);
            exception.setSearchMessage("id = " + medicalPlanRequest.getCompanyId());
            throw exception;
        }

        plan.setPlan(medicalPlanRequest.getPlan());
        plan.setCompany(company);
        medicalInsuranceService.save(plan);
        LOGGER.debug("Updated medical insurance plan: {}", id);
        return new MedicalInsurancePlanResponse(id);
    }
}
