package com.Application.springbootapp.Services;

import com.Application.springbootapp.Entities.Harmonogram;
import com.Application.springbootapp.Repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@org.springframework.stereotype.Service
public class ScheduleService implements iScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public List<Harmonogram> findAllByMovieID(int movieID) {
        return scheduleRepository.findAllByMovieID(movieID);
    }

    @Override
    public void addScheduleForMovie(Date beginTime, Date endTime, int movieID, int repetorireID, int cinemaHallID) {
        scheduleRepository.add(beginTime, endTime, movieID, repetorireID, cinemaHallID);
    }

    @Override
    public void deleteScheduleByMovieID(int movieID) {
        scheduleRepository.deleteScheduleByMovieID(movieID);
    }

    @Override
    public void updateScheduleByID(Date beginTime, Date endTime, int scheduleID) {
        scheduleRepository.updateScheduleByID(beginTime, endTime, scheduleID);
    }

    @Override
    public Harmonogram findScheduleByID(int scheduleID) {
        return scheduleRepository.findByScheduleID(scheduleID);
    }
}
