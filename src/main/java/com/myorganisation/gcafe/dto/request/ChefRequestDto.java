package com.myorganisation.gcafe.dto.request;

import com.myorganisation.gcafe.enums.Cuisine;
import lombok.Data;

@Data
public class ChefRequestDto {
    private String name;
    private Double experience;
    private Cuisine cuisine;
}
