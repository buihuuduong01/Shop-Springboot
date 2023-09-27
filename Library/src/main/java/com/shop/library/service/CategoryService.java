package com.shop.library.service;

import com.shop.library.dto.CategoryDto;
import com.shop.library.model.Category;

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
//    Category save(Category category);
//
//    Category update(Category category);
//
//    List<Category> findAllByActivatedTrue();
//
//    List<Category> findALl();
//
//    Optional<Category> findById(Long id);
//
//    void deleteById(Long id);
//
//    void enableById(Long id);
//
//    List<CategoryDto> getCategoriesAndSize();



}
