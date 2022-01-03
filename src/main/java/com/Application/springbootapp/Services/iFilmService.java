package com.Application.springbootapp.Services;

import com.Application.springbootapp.Entities.Film;


import java.util.Date;
import java.util.List;

public interface iFilmService {

    List<Film> findAllByRepertoireID(int repertoireID);
    Film addMovie(String title, int length, String description, String studio, Date date, int repertoireID,
                  int categoryID);
    Film findByTitle(String title);
    void deleteByID(int movieID);
    int findByScheduleID(int scheduleID);
    String findTitleByTicketID(int ticketID);
    Date findRepertoireDateByMovieIDAndTicketID(int movieID, int ticketID);
}
