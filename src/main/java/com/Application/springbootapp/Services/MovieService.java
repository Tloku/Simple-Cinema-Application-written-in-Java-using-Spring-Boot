package com.Application.springbootapp.Services;

import com.Application.springbootapp.Entities.Film;
import com.Application.springbootapp.Repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MovieService implements iMovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<Film> findAllByRepertoireID(int repertoireID) {
        return movieRepository.findAllByRepertoireID(repertoireID);
    }

    @Override
    public Film addMovie(String title, int length, String description, String studio, Date date, int repertoireID,
                         int categoryID) {
        movieRepository.add(title, length, description, studio, date, repertoireID, categoryID);
        return findByTitle(title);
    }

    @Override
    public Film findByTitle(String title) {
        return movieRepository.findByMovieTitle(title);
    }

    @Override
    public void deleteByID(int movieID) {
        movieRepository.deleteMovieById(movieID);
    }

    @Override
    public int findByScheduleID(int scheduleID) {
        return movieRepository.findByScheduleID(scheduleID);
    }

    @Override
    public String findTitleByTicketID(int ticketID) {
        return movieRepository.findByTicketID(ticketID);
    }

    @Override
    public Date findRepertoireDateByMovieIDAndTicketID(int movieID, int ticketID) {
        return movieRepository.findDateByMovieID(movieID, ticketID);
    }
}
