package com.kinesio.web;

import com.kinesio.web.exception.EntityNotFoundException;
import com.kinesio.web.exception.ErrorField;
import com.kinesio.web.exception.ValidationException;
import com.kinesio.web.response.RestErrorResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandlerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandlerAdvice.class);

    private static final String ENTITY_NOT_FOUND = "Could not found ";

    private MessageSource messageSource;

    @Autowired
    public RestExceptionHandlerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = Throwable.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public RestErrorResponse handleServerError(Throwable throwable) {
        LOGGER.error("InternalServerError", throwable);
        RestErrorResponse response = new RestErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        response.addCause("Internal server error has occurred");
        return response;
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public RestErrorResponse handleEntityNotFoundError(EntityNotFoundException exception) {
        RestErrorResponse response = new RestErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase());
        StringBuffer buffer = new StringBuffer();
        buffer.append(ENTITY_NOT_FOUND);
        buffer.append(exception.getEntityType());
        if (StringUtils.isNotBlank(exception.getSearchMessage())) {
            buffer.append(" by " + exception.getSearchMessage());
        }
        response.addCause(buffer.toString());
        return response;
    }

    @ExceptionHandler(value = ValidationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RestErrorResponse handleValidationError(ValidationException validationException) {
        RestErrorResponse response = new RestErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
        for (ErrorField field : validationException.getFields()) {
            response.addCause(field.getField() + ": " + field.getMessage());
        }
        return response;
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RestErrorResponse httpMessageConversionException(MissingServletRequestParameterException throwable) {
        LOGGER.error("Required parameter not found", throwable);
        RestErrorResponse response = new RestErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.addCause(throwable.getMessage());
        return response;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RestErrorResponse processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }

    private RestErrorResponse processFieldErrors(List<FieldError> fieldErrors) {
        RestErrorResponse dto = new RestErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());

        for (FieldError fieldError : fieldErrors) {
            dto.addCause(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }

        return dto;
    }
}