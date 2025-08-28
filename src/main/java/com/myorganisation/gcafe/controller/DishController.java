package com.myorganisation.gcafe.controller;

import com.myorganisation.gcafe.dto.request.DishRequestDto;
import com.myorganisation.gcafe.dto.response.DishResponseDto;
import com.myorganisation.gcafe.dto.response.GenericResponseDto;
import com.myorganisation.gcafe.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @PostMapping
    public ResponseEntity<DishResponseDto> addDish(@RequestBody DishRequestDto dishRequestDto) {
        return new ResponseEntity<>(dishService.addDish(dishRequestDto), HttpStatus.valueOf(201));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishResponseDto> getDish(@PathVariable Long id) {
        return new ResponseEntity<>(dishService.getDish(id), HttpStatus.valueOf(200));
    }

    @GetMapping
    public ResponseEntity<List<DishResponseDto>> getAllDish() {
        return new ResponseEntity<>(dishService.getAllDish(), HttpStatus.valueOf(200));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DishResponseDto> updateDish(@PathVariable Long id, @RequestBody DishRequestDto dishRequestDto) {
        return new ResponseEntity<>(dishService.updateDish(id, dishRequestDto), HttpStatus.valueOf(200));
    }

    @DeleteMapping
    public ResponseEntity<GenericResponseDto> removeDish(@RequestParam Long id) {
        return new ResponseEntity<>(dishService.removeDish(id), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/search")
    public ResponseEntity<List<DishResponseDto>> getDishByName(
            @RequestParam String q,
            @RequestParam Double min,
            @RequestParam Double max
    ) {
        return new ResponseEntity<>(dishService.searchDish(q, min, max), HttpStatusCode.valueOf(200));
    }
}
