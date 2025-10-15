package com.myorganisation.gcafe.dto.response;

import com.myorganisation.gcafe.enums.UserRole;
import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private UserRole role;
    private String username;
}
