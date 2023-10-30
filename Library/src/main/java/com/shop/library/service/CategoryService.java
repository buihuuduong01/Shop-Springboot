package com.shop.library.service;

import com.shop.library.dto.CategoryDto;
import com.shop.library.model.Category;
import com.shop.library.model.Product;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    /*Admin*/
    List<Category> findAll();
    Category save(Category category);
    Category findById(Long id);
    Category update(Category category);
    void deleteById(Long id);
    void enabledById(Long id);
    List<Category> findAllByActivated();

    List<CategoryDto> getCategoryAndProduct();





}
