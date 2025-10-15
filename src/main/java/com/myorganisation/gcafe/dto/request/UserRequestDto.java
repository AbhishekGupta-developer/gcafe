package com.myorganisation.gcafe.dto.request;

import com.myorganisation.gcafe.enums.UserRole;
import lombok.Data;

@Data
public class UserRequestDto {
    private UserRole role;
    private String username;
    private String password;
}
