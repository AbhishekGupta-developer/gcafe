package com.myorganisation.gcafe.service;

import com.myorganisation.gcafe.dto.request.DishRequestDto;
import com.myorganisation.gcafe.dto.response.DishResponseDto;
import com.myorganisation.gcafe.dto.response.GenericResponseDto;
import com.myorganisation.gcafe.enums.DishCategory;
import com.myorganisation.gcafe.model.Dish;
import com.myorganisation.gcafe.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishRepository dishRepository;

    @Override
    public DishResponseDto addDish(DishRequestDto dishRequestDto) {
        Dish dish = new Dish();

        dish = mapDishRequestDtoToDish(dish, dishRequestDto);

        dishRepository.save(dish);

        return mapDishToDishResponseDto(dish);
    }

    @Override
    public DishResponseDto getDish(Long id) {
        Dish dish = dishRepository.findById(id).orElse(null);

        return mapDishToDishResponseDto(dish);
    }

    @Override
    public List<DishResponseDto> getAllDish() {
        List<Dish> dishList = new ArrayList<>(dishRepository.findAll());

        List<DishResponseDto> dishResponseDtoList = new ArrayList<>();

        for(Dish dish : dishList) {
            dishResponseDtoList.add(mapDishToDishResponseDto(dish));
        }

        return dishResponseDtoList;
    }

    @Override
    public DishResponseDto updateDish(Long id, DishRequestDto dishRequestDto) {
        Dish dish = dishRepository.findById(id).orElse(null);

        dish = mapDishRequestDtoToDish(dish, dishRequestDto);

        dishRepository.save(dish);

        return mapDishToDishResponseDto(dish);
    }

    @Override
    public GenericResponseDto removeDish(Long id) {
        Dish dish = dishRepository.findById(id).orElse(null);

        GenericResponseDto genericResponseDto = new GenericResponseDto();

        if(dish != null) {
            String name = dish.getName();
            dishRepository.deleteById(id);


            genericResponseDto.setSuccess(true);
            genericResponseDto.setMessage("Dish (" + id + "): " + name + " has been removed successfully.");
            return genericResponseDto;
        } else {
            genericResponseDto.setSuccess(false);
            genericResponseDto.setMessage("Dish (" + id + "): Not found.");
            return genericResponseDto;
        }
    }

    @Override
    public List<DishResponseDto> searchDish(String q, Double min, Double max) {
        List<Dish> dishList = dishRepository.findByNameContainingAndPriceBetween(q, min, max);

        List<DishResponseDto> dishResponseDtoList = new ArrayList<>();

        for(Dish dish : dishList) {
            dishResponseDtoList.add(mapDishToDishResponseDto(dish));
        }

        return dishResponseDtoList;
    }

    @Override
    public List<DishResponseDto> getAllDishByCategory(DishCategory dishCategory) {
        List<Dish> dishList = dishRepository.getAllDishByCategory(dishCategory);
        List<DishResponseDto> dishResponseDtoList = new ArrayList<>();

        for(Dish dish : dishList) {
            dishResponseDtoList.add(mapDishToDishResponseDto(dish));
        }

        return dishResponseDtoList;
    }

    //Helper methods

    //Map Dish to DishResponseDto
    private DishResponseDto mapDishToDishResponseDto(Dish dish) {
        DishResponseDto dishResponseDto = new DishResponseDto();

        dishResponseDto.setId(dish.getId());
        dishResponseDto.setName(dish.getName());
        dishResponseDto.setPrice(dish.getPrice());
        dishResponseDto.setCategory(dish.getCategory());

        return dishResponseDto;
    }

    //Map DishRequestDto to Dish
    private Dish mapDishRequestDtoToDish(Dish dish, DishRequestDto dishRequestDto) {
        dish.setName(dishRequestDto.getName());
        dish.setPrice(dishRequestDto.getPrice());
        dish.setCategory(dishRequestDto.getCategory());

        return dish;
    }
}
