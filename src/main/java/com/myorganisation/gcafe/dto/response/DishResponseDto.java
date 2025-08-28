package com.myorganisation.gcafe.dto.response;

import com.myorganisation.gcafe.enums.DishCategory;
import lombok.Data;

@Data
public class DishResponseDto {
    private Long id;
    private String name;
    private Double price;
    private DishCategory category;
}
