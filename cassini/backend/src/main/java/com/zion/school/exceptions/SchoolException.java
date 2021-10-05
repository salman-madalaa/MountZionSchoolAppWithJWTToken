package com.zion.school.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Created by Lenovo on 09-09-2021.
 */
@Data
public class SchoolException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private Integer status;
    private ErrorCodes code;

    public SchoolException(String message) {
        super(message);
        this.code = ErrorCodes.GENERAL;
        this.status = Integer.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public SchoolException(HttpStatus status, ErrorCodes code) {
        this.status = Integer.valueOf(status.value());
        this.code = code;
    }

    public SchoolException(HttpStatus status, ErrorCodes code, String message) {
        super(message);
        this.status = Integer.valueOf(status.value());
        this.code = code;
    }
}
