package com.shop.library.service.impl;

import com.shop.library.model.City;
import com.shop.library.repository.CityRepository;
import com.shop.library.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; // Thêm import này

import java.util.List;

@Service // Thêm annotation này để đánh dấu đây là một Spring-managed bean
public class CityServiceImpl implements CityService {
    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<City> findAll() {
        return cityRepository.findAll();
    }
}
