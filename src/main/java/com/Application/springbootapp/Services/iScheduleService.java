package com.Application.springbootapp.Services;

import com.Application.springbootapp.Entities.Schedule;

import java.util.Date;
import java.util.List;

public interface iScheduleService {

    List<Schedule> findAllByMovieID(int movieID);
    void addScheduleForMovie(Date beginTime, Date endTime, int movieID, int repetorireID, int cinemaHallID);
    void deleteScheduleByMovieID(int movieID);
    void updateScheduleByID(Date beginTime, Date endTime, int scheduleID);

    Schedule findScheduleByID(int scheduleID);
}
