package com.myorganisation.gcafe.service;

import com.myorganisation.gcafe.dto.request.ChefRequestDto;
import com.myorganisation.gcafe.dto.response.ChefResponseDto;
import com.myorganisation.gcafe.dto.response.GenericResponseDto;
import com.myorganisation.gcafe.model.Chef;
import com.myorganisation.gcafe.repository.ChefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
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
        Chef chef = chefRepository.findById(id).orElse(null);

        ChefResponseDto chefResponseDto = new ChefResponseDto();

        chefResponseDto.setId(chef.getId());
        chefResponseDto.setName(chef.getName());
        chefResponseDto.setExperience(chef.getExperience());
        chefResponseDto.setCuisine(chef.getCuisine());

        return chefResponseDto;
    }

    @Override
    public List<ChefResponseDto> getAllChef() {
        List<Chef> chefList = new LinkedList<>(chefRepository.findAll());
        List<ChefResponseDto> chefResponseDtoList = new LinkedList<>();

        for(Chef chef : chefList) {
            ChefResponseDto chefResponseDto = new ChefResponseDto();

            chefResponseDto.setId(chef.getId());
            chefResponseDto.setName(chef.getName());
            chefResponseDto.setExperience(chef.getExperience());
            chefResponseDto.setCuisine(chef.getCuisine());

            chefResponseDtoList.add(chefResponseDto);
        }

        return chefResponseDtoList;
    }

    @Override
    public ChefResponseDto updateChef(Long id, ChefRequestDto chefRequestDto) {
        Chef chef = chefRepository.findById(id).orElse(null);
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
    public GenericResponseDto removeChef(Long id) {
        return null;
    }
}
