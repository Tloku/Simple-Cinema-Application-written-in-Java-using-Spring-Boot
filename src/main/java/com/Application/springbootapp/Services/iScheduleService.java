package com.Application.springbootapp.Services;

import com.Application.springbootapp.Entities.Harmonogram;

import java.util.Date;
import java.util.List;

public interface iScheduleService {

    List<Harmonogram> findAllByMovieID(int movieID);
    void addScheduleForMovie(Date beginTime, Date endTime, int movieID, int repetorireID, int cinemaHallID);
    void deleteScheduleByMovieID(int movieID);
    void updateScheduleByID(Date beginTime, Date endTime, int scheduleID);

    Harmonogram findScheduleByID(int scheduleID);
}
