package com.myorganisation.gcafe.service;

import com.myorganisation.gcafe.dto.response.AccountResponseDto;
import com.myorganisation.gcafe.model.Account;
import com.myorganisation.gcafe.model.Chef;
import com.myorganisation.gcafe.repository.ChefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChefBalanceServiceImpl implements ChefBalanceService {

    @Autowired
    private ChefRepository chefRepository;

    @Override
    public AccountResponseDto addAmount(Long chefId, Double amount) {
        Chef chef = chefRepository.findById(chefId).orElse(null);

        Double existingAmount = chef.getAccount().getAmount();

        chef.getAccount().setAmount(existingAmount + amount);

        chefRepository.save(chef);

        Account account = chef.getAccount();

        AccountResponseDto accountResponseDto = new AccountResponseDto();
        accountResponseDto.setId(account.getId());
        accountResponseDto.setAmount(account.getAmount());
        accountResponseDto.setIsPaid(account.getIsPaid());
        accountResponseDto.setStatus(account.getStatus());

        return accountResponseDto;
    }

    @Override
    public AccountResponseDto deductAmount(Long chefId, Double amount) {
        return null;
    }
}
