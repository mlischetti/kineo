package com.kinesio.web.controller;

import com.kinesio.model.Doctor;
import com.kinesio.web.request.DoctorRequest;
import com.kinesio.web.response.DoctorResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mlischetti on 11/29/15.
 */
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Doctor> vouchers() throws Throwable {
        return new ArrayList<>();
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public DoctorResponse vouchers(@RequestBody DoctorRequest request) throws Throwable {
        return new DoctorResponse();
    }
}
