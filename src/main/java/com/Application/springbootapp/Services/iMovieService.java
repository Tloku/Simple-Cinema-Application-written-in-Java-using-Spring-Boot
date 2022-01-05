package com.Application.springbootapp.Services;

import com.Application.springbootapp.Entities.Movie;


import java.util.Date;
import java.util.List;

public interface iMovieService {

    List<Movie> findAllByRepertoireID(int repertoireID);
    Movie addMovie(String title, int length, String description, String studio, Date date, int repertoireID,
                   int categoryID);
    Movie findByTitle(String title);
    void deleteByID(int movieID);
    int findByScheduleID(int scheduleID);
    String findTitleByTicketID(int ticketID);
    Date findRepertoireDateByMovieIDAndTicketID(int movieID, int ticketID);
}
