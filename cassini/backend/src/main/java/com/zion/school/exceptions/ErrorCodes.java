package com.zion.school.exceptions;

import java.io.Serializable;

/**
 * Created by Lenovo on 09-09-2021.
 */
public enum ErrorCodes implements Serializable {
    GENERAL,
    UNAUTHORIZED,
    RESOURCE_NOT_FOUND,
    SESSION_TIME_OUT;

    private ErrorCodes() {
    }
}
