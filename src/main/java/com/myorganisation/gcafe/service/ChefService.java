package com.myorganisation.gcafe.service;

import com.myorganisation.gcafe.dto.request.ChefRequestDto;
import com.myorganisation.gcafe.dto.response.ChefResponseDto;
import com.myorganisation.gcafe.dto.response.GenericResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ChefService {
    ChefResponseDto registerChef(ChefRequestDto chefRequestDto);
    ChefResponseDto getChef(Long id);
    List<ChefResponseDto> getAllChef();
    ChefResponseDto updateChef(Long id, ChefRequestDto chefRequestDto);
    GenericResponseDto removeChef(Long id);

    Page<ChefResponseDto> getChefPage(Integer pageIndex, Integer pageSize, String sortByAttribute, String sortInOrder);
}
