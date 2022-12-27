package com.budgettracker.demo.userProfile.service.impl;

import com.budgettracker.demo.userProfile.models.Category;
import com.budgettracker.demo.userProfile.repository.CategoryRepository;
import com.budgettracker.demo.userProfile.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
@EnableAutoConfiguration
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return (List<Category>) categoryRepository.findAll();
    }
}
