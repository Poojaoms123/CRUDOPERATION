package com.anervea.employee.model.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * class defines the response to be shown in api
 */

@Getter
@Setter
@AllArgsConstructor
public class CustomResponseMessage {
    private String response;
    private int status;
}
