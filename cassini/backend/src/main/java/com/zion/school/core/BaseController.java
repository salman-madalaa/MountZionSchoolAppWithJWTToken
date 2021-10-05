package com.zion.school.core;

import com.zion.school.exceptions.APIError;
import com.zion.school.exceptions.SchoolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.sql.SQLException;


@CrossOrigin(origins = "*", maxAge = 3600)
@ControllerAdvice
public abstract class BaseController  {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    public BaseController() {
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ModelMap handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, HttpServletResponse response) throws Exception {
        LOGGER.error(ex.getMessage(), ex);
        return new APIError(ex.getMessage());
    }

    @ResponseStatus(
            value = HttpStatus.CONFLICT,
            reason = "Data integrity violation"
    )
    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseBody
    public APIError dataIntegrityViolationException(DataIntegrityViolationException ex) {
        LOGGER.error(ex.getMessage(), ex);
        return new APIError(ex.getMessage());
    }

    @ResponseStatus(
            value = HttpStatus.INTERNAL_SERVER_ERROR,
            reason = "Database error"
    )
    @ExceptionHandler({SQLException.class, DataAccessException.class})
    @ResponseBody
    public APIError databaseError(Exception ex) {
        LOGGER.error(ex.getMessage(), ex);
        return new APIError(ex.getMessage());
    }


    @ExceptionHandler({AccessDeniedException.class})
    @ResponseBody
    public APIError accessDeniedError(AccessDeniedException ex, HttpServletResponse response) {
        LOGGER.error("You do not have permission to perform this action", ex);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        return new APIError("You do not have permission to perform this action");
    }

    @ExceptionHandler({SchoolException.class})
    @ResponseBody
    public APIError handleIsException(SchoolException ex, HttpServletResponse response) {
        LOGGER.error(ex.getMessage(), ex);
        response.setStatus(ex.getStatus().intValue());
        return new APIError(ex.getCode().toString(), ex.getMessage());
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ModelMap handleAllException(Exception ex, HttpServletResponse response) throws Exception {
        LOGGER.error(ex.getMessage(), ex);
        if(AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class) != null) {
            throw ex;
        } else {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new APIError(ex.getMessage());
        }
    }

//
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    @ResponseBody
//    public ResponseEntity<ErrorMessage> handleException(
//            HttpMessageNotReadableException ex,
//            HttpServletResponse response) {
//        return getErrorResponse(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseBody
//    public ResponseEntity<ErrorMessage> handleException(
//            MethodArgumentNotValidException ex,
//            HttpServletResponse response) {
//        BindingResult result = ex.getBindingResult();
//        String message = "Missing required fields:";
//        for (FieldError error: result.getFieldErrors()) {
//            message += " " + error.getField();
//        }
//        return getErrorResponse(message, HttpStatus.UNPROCESSABLE_ENTITY);
//    }
//
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public ResponseEntity<ErrorMessage> handleException(
//            Exception ex,
//            HttpServletResponse response) {
//        LOGGER.warn("Exception", ex);
//        return getErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//
//    public ResponseEntity<ErrorMessage> getErrorResponse(String message, HttpStatus status) {
//        return new ResponseEntity<ErrorMessage>(new ErrorMessage(message),
//                status);
//    }
//


}
