package com.zion.school.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by Lenovo on 22-09-2021.
 */
@Data
public class ErrorMessage {
    @JsonProperty("description")
    private String message;

    public ErrorMessage(String message) {

    }
}
