package com.Application.springbootapp.Services;

import com.Application.springbootapp.Entities.Gatunek;

import java.util.List;

public interface iCategoryService {

    public List<Gatunek> findAll();
}
