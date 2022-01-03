package com.Application.springbootapp.Services;

import com.Application.springbootapp.Repositories.SalaKinowaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalaKinowaService implements iSalaKinowaService {

    @Autowired
    private SalaKinowaRepository salaKinowaRepository;
}
