package com.myorganisation.gcafe.dto.request;

import com.myorganisation.gcafe.enums.DishCategory;
import lombok.Data;

@Data
public class DishRequestDto {
    private String name;
    private Double price;
    private DishCategory category;
}
