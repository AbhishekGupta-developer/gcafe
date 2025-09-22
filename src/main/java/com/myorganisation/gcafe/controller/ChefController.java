package com.myorganisation.gcafe.controller;

import com.myorganisation.gcafe.dto.request.ChefRequestDto;
import com.myorganisation.gcafe.dto.response.ChefResponseDto;
import com.myorganisation.gcafe.dto.response.GenericResponseDto;
import com.myorganisation.gcafe.service.ChefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chef")
public class ChefController {

    @Autowired
    private ChefService chefService;

    @PostMapping
    public ResponseEntity<ChefResponseDto> registerChef(@RequestBody ChefRequestDto chefRequestDto) {
        return new ResponseEntity<>(chefService.registerChef(chefRequestDto), HttpStatusCode.valueOf(201));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChefResponseDto> getChef(@PathVariable Long id) {
        return new ResponseEntity<>(chefService.getChef(id), HttpStatusCode.valueOf(200));
    }

    @GetMapping
    public ResponseEntity<List<ChefResponseDto>> getAllChef() {
        return new ResponseEntity<>(chefService.getAllChef(), HttpStatusCode.valueOf(200));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChefResponseDto> updateChef(@PathVariable Long id, @RequestBody ChefRequestDto chefRequestDto) {
        return new ResponseEntity<>(chefService.updateChef(id, chefRequestDto), HttpStatusCode.valueOf(200));
    }

    @DeleteMapping
    public ResponseEntity<GenericResponseDto> removeChef(@RequestParam Long id) {
        return new ResponseEntity<>(chefService.removeChef(id), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ChefResponseDto>> getChefPage(
            @RequestParam(defaultValue = "0") Integer pageIndex,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortByAttribute,
            @RequestParam(defaultValue = "asc") String sortInOrder
    ) {
        return new ResponseEntity<>(chefService.getChefPage(pageIndex, pageSize, sortByAttribute, sortInOrder), HttpStatusCode.valueOf(200));
    }
}
