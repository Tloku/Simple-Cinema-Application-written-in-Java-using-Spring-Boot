package com.Application.springbootapp.Services;

import com.Application.springbootapp.Entities.Gatunek;
import com.Application.springbootapp.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements iCategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Gatunek> findAll() {
        return categoryRepository.findAll();
    }
}
