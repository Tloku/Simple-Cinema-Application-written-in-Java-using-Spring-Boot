package com.Application.springbootapp.Services;

import com.Application.springbootapp.Entities.Rola;
import com.Application.springbootapp.Repositories.RolaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolaService implements iRolaService {

    @Autowired
    private RolaRepository rolaRepository;

    @Override
    public List<Rola> findAll() {
        System.out.println("Wejszłem do metody findall w rolaService.java");
        List<Rola> rolaList = rolaRepository.findAll();
        System.out.println("Znalazlem i wyszlem");
        return rolaList;
    }
}
