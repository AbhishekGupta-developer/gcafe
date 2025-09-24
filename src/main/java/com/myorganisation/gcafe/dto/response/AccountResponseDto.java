package com.myorganisation.gcafe.dto.response;

import com.myorganisation.gcafe.enums.AccountStatus;
import lombok.Data;

@Data
public class AccountResponseDto {
    private Long id;
    private Double amount;
    private Boolean isPaid;
    private AccountStatus status;
}
