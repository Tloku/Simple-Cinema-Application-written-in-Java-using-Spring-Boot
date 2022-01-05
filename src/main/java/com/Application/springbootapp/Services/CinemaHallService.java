package com.Application.springbootapp.Services;

import com.Application.springbootapp.Repositories.CinemaHallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CinemaHallService implements iCinemaHallService {

    @Autowired
    private CinemaHallRepository cinemaHallRepository;
}
