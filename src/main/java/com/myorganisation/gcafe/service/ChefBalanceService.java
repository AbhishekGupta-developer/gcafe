package com.myorganisation.gcafe.service;

import com.myorganisation.gcafe.dto.response.AccountResponseDto;

public interface ChefBalanceService {
    AccountResponseDto addAmount(Long chefId, Double amount);
    AccountResponseDto deductAmount(Long chefId, Double amount);
}
