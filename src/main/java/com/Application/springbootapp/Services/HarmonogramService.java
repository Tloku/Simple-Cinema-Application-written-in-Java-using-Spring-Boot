package com.Application.springbootapp.Services;

import com.Application.springbootapp.Entities.Harmonogram;
import com.Application.springbootapp.Repositories.HarmonogramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HarmonogramService implements iHarmonogramService {

    @Autowired
    private HarmonogramRepository harmonogramRepository;

    @Override
    public List<Harmonogram> findAllByMovieID(int movieID) {
        System.out.println("Wejszlem do metody findAllByMovieID");
        List<Harmonogram> schedules = harmonogramRepository.findAllByMovieID(movieID);
        System.out.println("Znalazlem i wyjszlem");
        return schedules;
    }

    @Override
    public void addScheduleForMovie(Date beginTime, Date endTime, int movieID, int repetorireID, int cinemaHallID) {
        System.out.println("Wejszlem do metody addScheduleForMovie");
        harmonogramRepository.add(beginTime, endTime, movieID, repetorireID, cinemaHallID);
        System.out.println("Znalazlem i wyjszlem");
    }

    @Override
    public void deleteScheduleByMovieID(int movieID) {
        System.out.println("Wejszlem do metody deleteScheduleByMovieID");
        harmonogramRepository.deleteScheduleByMovieID(movieID);
        System.out.println("Znalazlem i wyjszlem");
    }

    @Override
    public void updateScheduleByID(Date beginTime, Date endTime, int scheduleID) {
        System.out.println("Wejszlem do metody updateScheduleByID");
        harmonogramRepository.updateScheduleByID(beginTime, endTime, scheduleID);
        System.out.println("Znalazlem i wyjszlem");
    }

    @Override
    public Harmonogram findScheduleByID(int scheduleID) {
        System.out.println("Wejszlem do metody findScheduleByID");
        return harmonogramRepository.findByScheduleID(scheduleID);
    }
}
