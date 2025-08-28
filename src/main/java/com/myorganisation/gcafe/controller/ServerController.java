package com.myorganisation.gcafe.controller;

import com.myorganisation.gcafe.dto.response.ServerResponseDto;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ServerController {
    @GetMapping
    public ResponseEntity<ServerResponseDto> serverStatus() {
        return new ResponseEntity<>(new ServerResponseDto("Server is live"), HttpStatusCode.valueOf(200));
    }
}
