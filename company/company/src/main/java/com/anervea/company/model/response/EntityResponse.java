package com.anervea.company.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * class defines the response to be shown in api
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntityResponse {
    private Object response;
    private int status;
}
