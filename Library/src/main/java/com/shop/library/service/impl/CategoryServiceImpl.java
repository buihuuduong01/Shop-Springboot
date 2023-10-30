package com.shop.library.service.impl;


import com.shop.library.dto.CategoryDto;
import com.shop.library.model.Category;
import com.shop.library.repository.CategoryRepository;
import com.shop.library.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository repo;


    @Override
    public List<Category> findAll() {
        return repo.findAll();
    }

    @Override
    public Category save(Category category) {
        Category categorySave = new Category(category.getName());
        return repo.save(categorySave);
    }

    @Override
    public Category findById(Long id) {
        return repo.findById(id).get();
    }

@Override
public Category update(Category category) {
    // check id
    Category existingCategory = repo.findById(category.getId()).orElse(null);

    if (existingCategory != null) {
        // thực hiện update
        existingCategory.setName(category.getName());
        existingCategory.set_activated(category.is_activated());
        existingCategory.set_deleted(category.is_deleted());

        // lưu lại
        return repo.save(existingCategory);
    } else {
        // trường hợp id k tồn tại
        throw new EntityNotFoundException("Category not found with ID: " + category.getId());
    }
}

    @Override
    public void deleteById(Long id) {
        Category category = repo.getById(id);
        category.set_deleted(true);
        category.set_activated(false);
        repo.save(category);
    }

    @Override
    public void enabledById(Long id) {
        Category category = repo.getById(id);
        category.set_activated(true);
        category.set_deleted(false);
        repo.save(category);
    }

    @Override
    public List<Category> findAllByActivated() {
        return repo.findAllByActivatedTrue();
    }

    @Override
    public List<CategoryDto> getCategoryAndProduct() {
        return repo.getCategoryAndProduct();
    }


}
