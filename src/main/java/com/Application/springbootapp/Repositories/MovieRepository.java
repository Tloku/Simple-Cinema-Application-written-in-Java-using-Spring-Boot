package com.Application.springbootapp.Repositories;

import com.Application.springbootapp.Entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Query(
            value = "SELECT * FROM Film INNER JOIN Repertuar_kina ON Film.Repertuar_kinaRepertuar_Kina_ID = " +
                    "Repertuar_kina.Repertuar_kina_ID " +
                    "WHERE Repertuar_kina.Repertuar_kina_ID = ?1",
            nativeQuery = true
    )
    public List<Movie> findAllByRepertoireID(int repertoireID);

    @Query(
            value = "SELECT Film.Film_ID FROM Film INNER JOIN Harmonogram ON Film.Film_ID = " +
                    "Harmonogram.FilmFilm_ID " +
                    "WHERE Harmonogram.Harmonogram_ID = ?1",
            nativeQuery = true
    )
    public int findByScheduleID(int scheduleID);

    @Modifying
    @Query(
            value = "INSERT INTO Film(Tytul, Dlugosc, Opis, Wytwornia, " +
                    " Rok_wydania, Repertuar_kinaRepertuar_kina_ID, GatunekGatunek_ID) " +
                    " VALUES(:title, :length, :description, :studio, :date, :repertoireID, :categoryID)",
            nativeQuery = true
    )
    @Transactional
    public void add(@Param("title") String title,@Param("length") int length, @Param("description") String description,
                    @Param("studio") String studio, @Param("date") Date date, @Param("repertoireID") int repertoireID,
                    @Param("categoryID") int categoryID);

    @Query(
            value = "SELECT * FROM Film WHERE Film.tytul = ?1",
            nativeQuery = true
    )
    public Movie findByMovieTitle(String title);


    @Modifying
    @Query(
            value = "DELETE FROM Film WHERE Film.Film_ID = ?1",
            nativeQuery = true
    )
    @Transactional
    void deleteMovieById(int movieID);

    @Query(
            value = "SELECT Film.Tytul FROM Film INNER JOIN Bilet " +
                    "ON Film.Film_ID = Bilet.FilmFilm_ID " +
                    "WHERE Bilet.Bilet_ID = ?1",
            nativeQuery = true
    )
    String findByTicketID(int ticketID);

    @Query(
            value = "SELECT Repertuar_kina.Data FROM Film INNER JOIN " +
                    "Bilet ON Film.Film_ID = Bilet.FilmFilm_ID INNER JOIN " +
                    "Repertuar_kina ON Film.Repertuar_kinaRepertuar_kina_ID = Repertuar_kina.Repertuar_kina_ID " +
                    "WHERE Film.Film_ID = ?1 AND Bilet.Bilet_ID = ?2",
            nativeQuery = true
    )
    Date findDateByMovieID(int movieID, int ticketID);
}
