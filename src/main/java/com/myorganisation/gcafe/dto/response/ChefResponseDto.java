package com.myorganisation.gcafe.dto.response;

import com.myorganisation.gcafe.enums.Cuisine;
import com.myorganisation.gcafe.model.Account;
import lombok.Data;

@Data
public class ChefResponseDto {
    private Long id;
    private String name;
    private Double experience;
    private Cuisine cuisine;
    private Account account;
}
