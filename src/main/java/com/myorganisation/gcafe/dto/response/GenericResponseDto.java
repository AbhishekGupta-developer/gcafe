package com.myorganisation.gcafe.dto.response;

import lombok.Data;

@Data
public class GenericResponseDto {
    private boolean success;
    private String message;
    private Object detail;
}
