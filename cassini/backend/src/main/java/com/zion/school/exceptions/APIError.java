package com.zion.school.exceptions;

import org.springframework.ui.ModelMap;

/**
 * Created by Lenovo on 22-09-2021.
 */
public class APIError extends ModelMap {
    private static final long serialVersionUID = 1L;
    private String code;
    private String message;

    public APIError() {
        this.code = ErrorCodes.GENERAL.toString();
        this.message = "Unknown error";
        this.addAttribute("error", Boolean.valueOf(true));
        this.addAttribute("code", this.code);
        this.addAttribute("message", this.message);
    }

    public APIError(String code, String message) {
        this.code = ErrorCodes.GENERAL.toString();
        this.message = "Unknown error";
        this.addAttribute("error", Boolean.valueOf(true));
        this.addAttribute("code", code);
        this.addAttribute("message", message);
    }

    public APIError(String message) {
        this.code = ErrorCodes.GENERAL.toString();
        this.message = "Unknown error";
        this.addAttribute("error", Boolean.valueOf(true));
        this.addAttribute("code", this.code);
        this.addAttribute("message", message);
    }
}
