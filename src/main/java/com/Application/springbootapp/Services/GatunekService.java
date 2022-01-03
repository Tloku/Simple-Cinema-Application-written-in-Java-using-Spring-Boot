package com.Application.springbootapp.Services;

import com.Application.springbootapp.Entities.Gatunek;
import com.Application.springbootapp.Repositories.GatunekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GatunekService implements iGatunekService {

    @Autowired
    private GatunekRepository gatunekRepository;

    @Override
    public List<Gatunek> findAll() {
        List<Gatunek> categories = gatunekRepository.findAll();
        return categories;
    }
}
