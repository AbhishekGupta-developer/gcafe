package com.myorganisation.gcafe.service;

import com.myorganisation.gcafe.dto.request.ChefRequestDto;
import com.myorganisation.gcafe.dto.response.ChefResponseDto;
import com.myorganisation.gcafe.dto.response.GenericResponseDto;
import com.myorganisation.gcafe.model.Chef;
import com.myorganisation.gcafe.repository.ChefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChefServiceImpl implements ChefService {

    @Autowired
    private ChefRepository chefRepository;

    @Override
    public ChefResponseDto registerChef(ChefRequestDto chefRequestDto) {
        Chef chef = new Chef();
        chef.setName(chefRequestDto.getName());
        chef.setExperience(chefRequestDto.getExperience());
        chef.setCuisine(chefRequestDto.getCuisine());

        chefRepository.save(chef);

        ChefResponseDto chefResponseDto = new ChefResponseDto();

        chefResponseDto.setId(chef.getId());
        chefResponseDto.setName(chef.getName());
        chefResponseDto.setExperience(chef.getExperience());
        chefResponseDto.setCuisine(chef.getCuisine());

        return chefResponseDto;
    }

    @Override
    public ChefResponseDto getChef(Long id) {
        return null;
    }

    @Override
    public List<ChefResponseDto> getAllChef() {
        return List.of();
    }

    @Override
    public ChefResponseDto updateChef(Long id, ChefRequestDto chefRequestDto) {
        return null;
    }

    @Override
    public GenericResponseDto removeChef(Long id) {
        return null;
    }
}
