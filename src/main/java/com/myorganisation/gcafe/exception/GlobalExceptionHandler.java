package com.myorganisation.gcafe.exception;

import com.myorganisation.gcafe.dto.response.GenericResponseDto;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ChefNotFoundException.class)
    public ResponseEntity<GenericResponseDto> handleChefNotFoundException(ChefNotFoundException e) {
        GenericResponseDto genericResponseDto = new GenericResponseDto();

        genericResponseDto.setSuccess(false);
        genericResponseDto.setMessage(e.getMessage());
        genericResponseDto.setDetail(null);

        return new ResponseEntity<>(genericResponseDto, HttpStatusCode.valueOf(404));
    }

    @ExceptionHandler(InvalidOtpException.class)
    public ResponseEntity<GenericResponseDto> handleInvalidOtpException(InvalidOtpException e) {
        GenericResponseDto genericResponseDto = new GenericResponseDto();
        genericResponseDto.setSuccess(false);
        genericResponseDto.setMessage(e.getMessage());

        return new ResponseEntity<>(genericResponseDto, HttpStatusCode.valueOf(400));
    }

}
