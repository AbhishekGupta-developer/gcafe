package com.myorganisation.gcafe.service;

import com.myorganisation.gcafe.dto.request.ChefRequestDto;
import com.myorganisation.gcafe.dto.response.ChefResponseDto;
import com.myorganisation.gcafe.dto.response.GenericResponseDto;

import java.util.List;

public interface ChefService {
    ChefResponseDto registerChef(ChefRequestDto chefRequestDto);
    ChefResponseDto getChef(Long id);
    List<ChefResponseDto> getAllChef();
    ChefResponseDto updateChef(Long id, ChefRequestDto chefRequestDto);
    GenericResponseDto removeChef(Long id);
}
