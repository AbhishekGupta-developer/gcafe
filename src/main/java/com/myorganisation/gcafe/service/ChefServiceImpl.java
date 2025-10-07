package com.myorganisation.gcafe.service;

import com.myorganisation.gcafe.dto.request.ChefRequestDto;
import com.myorganisation.gcafe.dto.response.ChefResponseDto;
import com.myorganisation.gcafe.dto.response.GenericResponseDto;
import com.myorganisation.gcafe.exception.ChefNotFoundException;
import com.myorganisation.gcafe.model.Account;
import com.myorganisation.gcafe.model.Chef;
import com.myorganisation.gcafe.repository.AccountRepository;
import com.myorganisation.gcafe.repository.ChefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
public class ChefServiceImpl implements ChefService {

    @Autowired
    private ChefRepository chefRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public ChefResponseDto registerChef(ChefRequestDto chefRequestDto) {
        Chef chef = new Chef();
        chef.setName(chefRequestDto.getName());
        chef.setExperience(chefRequestDto.getExperience());
        chef.setCuisine(chefRequestDto.getCuisine());

        Account account = new Account();
//        accountRepository.save(account);
        account.setChef(chef);
        chef.setAccount(account);

        chefRepository.save(chef);

//        if(true) {
//            throw new RuntimeException("Amount not credited");
//        }
        chefRepository.save(chef);

//        account.setChef(chef);
//        accountRepository.save(account);

        return mapChefToChefResponseDto(chef);
    }

    @Override
    public ChefResponseDto getChef(Long id) {
        Chef chef = chefRepository.findById(id).orElseThrow(() -> new ChefNotFoundException("Chef id: " + id + " doesn't exist"));

        return mapChefToChefResponseDto(chef);
    }

    @Override
    public List<ChefResponseDto> getAllChef() {
        List<Chef> chefList = new LinkedList<>(chefRepository.findAll());

        if(chefList.isEmpty()) {
            throw new ChefNotFoundException("No chef found");
        }

        List<ChefResponseDto> chefResponseDtoList = new LinkedList<>();

        for(Chef chef : chefList) {
            chefResponseDtoList.add(mapChefToChefResponseDto(chef));
        }

        return chefResponseDtoList;
    }

    @Override
    public ChefResponseDto updateChef(Long id, ChefRequestDto chefRequestDto) {
        Chef chef = chefRepository.findById(id).orElseThrow(() -> new ChefNotFoundException("Chef id: " + id + " doesn't exist"));
        chef.setName(chefRequestDto.getName());
        chef.setExperience(chefRequestDto.getExperience());
        chef.setCuisine(chefRequestDto.getCuisine());

        chefRepository.save(chef);

        return mapChefToChefResponseDto(chef);
    }

    @Override
    public GenericResponseDto removeChef(Long id) {
        Chef chef = chefRepository.findById(id).orElseThrow(() -> new ChefNotFoundException("Chef id: " + id + " doesn't exist"));

        GenericResponseDto genericResponseDto = new GenericResponseDto();

        chefRepository.deleteById(id);

        String name = chef.getName();
        String message = "Chef name: " + name + "(" + id + ") has been removed";

        genericResponseDto.setSuccess(true);
        genericResponseDto.setMessage(message);

        return genericResponseDto;
    }

    @Override
    public Page<ChefResponseDto> getChefPage(Integer pageIndex, Integer pageSize, String sortByAttribute, String sortInOrder) {
        Sort sort = (sortInOrder.equalsIgnoreCase("desc")) ? Sort.by(sortByAttribute).descending() : Sort.by(sortByAttribute).ascending();

        Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);

        Page<Chef> chefPage = chefRepository.findAll(pageable);

        Page<ChefResponseDto> chefResponseDtoPage = chefPage.map(chef -> mapChefToChefResponseDto(chef));

        return chefResponseDtoPage;
    }

    // Helper methods

    // Map Chef to ChefResponseDto
    public ChefResponseDto mapChefToChefResponseDto(Chef chef) {
        ChefResponseDto chefResponseDto = new ChefResponseDto();

        chefResponseDto.setId(chef.getId());
        chefResponseDto.setName(chef.getName());
        chefResponseDto.setExperience(chef.getExperience());
        chefResponseDto.setCuisine(chef.getCuisine());
        chefResponseDto.setAccount(chef.getAccount());

        return chefResponseDto;
    }
}
