package com.Application.springbootapp.Services;

import com.Application.springbootapp.Entities.Film;
import com.Application.springbootapp.Repositories.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FilmService implements iFilmService {

    @Autowired
    private FilmRepository filmRepository;

    @Override
    public List<Film> findAllByRepertoireID(int repertoireID) {
        System.out.println("Wejszlem do metody findAllByRepertoireID");
        List<Film> movies = filmRepository.findAllByRepertoireID(repertoireID);
        System.out.println("znalazlem i wyjszlem");
        return movies;
    }

    @Override
    public Film addMovie(String title, int length, String description, String studio, Date date, int repertoireID,
                         int categoryID) {
        System.out.println("Wejszlem do metody addMovie");
        filmRepository.add(title, length, description, studio, date, repertoireID, categoryID);
        System.out.println("znalazlem i wyjszlem");

        Film film = findByTitle(title);
        return film;
    }

    @Override
    public Film findByTitle(String title) {
        return filmRepository.findByMovieTitle(title);
    }

    @Override
    public void deleteByID(int movieID) {
        System.out.println("Wejszlem do metody addMovie");
        filmRepository.deleteMovieById(movieID);
        System.out.println("znalazlem i wyjszlem");
    }

    @Override
    public int findByScheduleID(int scheduleID) {
        System.out.println("Wejszlem do metody addMovie");
        int movieID = filmRepository.findByScheduleID(scheduleID);
        System.out.println("znalazlem i wyjszlem");
        return  movieID;
    }

    @Override
    public String findTitleByTicketID(int ticketID) {
        return filmRepository.findByTicketID(ticketID);
    }

    @Override
    public Date findRepertoireDateByMovieIDAndTicketID(int movieID, int ticketID) {
        return filmRepository.findDateByMovieID(movieID, ticketID);
    }
}
