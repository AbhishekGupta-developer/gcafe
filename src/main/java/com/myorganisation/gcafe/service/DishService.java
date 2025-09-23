package com.myorganisation.gcafe.service;

import com.myorganisation.gcafe.dto.request.DishRequestDto;
import com.myorganisation.gcafe.dto.response.DishResponseDto;
import com.myorganisation.gcafe.dto.response.GenericResponseDto;
import com.myorganisation.gcafe.enums.DishCategory;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DishService {
    //CRUD
    DishResponseDto addDish(DishRequestDto dishRequestDto);
    DishResponseDto getDish(Long id);
    List<DishResponseDto> getAllDish();
    DishResponseDto updateDish(Long id, DishRequestDto dishRequestDto);
    GenericResponseDto removeDish(Long id);

    //Custom finder methods
    List<DishResponseDto> searchDish(String q, Double min, Double max);

    List<DishResponseDto> getAllDishByCategory(DishCategory dishCategory);

    Page<DishResponseDto> getDishPage(Integer pageIndex, Integer pageSize, String sortByAttribute, String sortInOrder);

}
