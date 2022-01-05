package com.Application.springbootapp.Repositories;

import com.Application.springbootapp.Entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    @Query(
            value = "SELECT * FROM Film INNER JOIN " +
                    "Harmonogram ON Film.Film_ID = Harmonogram.FilmFilm_ID " +
                    "WHERE Film.Film_ID = ?1",
            nativeQuery = true
    )
    List<Schedule> findAllByMovieID(int movieID);


    @Modifying
    @Query(
            value = "INSERT INTO Harmonogram(Godzina_startu, Godzina_zakonczenia, FilmFilm_ID, " +
                    " Repertuar_kinaRepertuar_kina_ID, Sala_kinowaSala_ID) " +
                    " VALUES(:beginTime, :endTime, :movieID, :repetorireID, :cinemaHallID)",
            nativeQuery = true
    )
    @Transactional
    public void add(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime,
                    @Param("movieID") int movieID, @Param("repetorireID") int repetorireID,
                    @Param("cinemaHallID") int cinemaHallID);


    @Modifying
    @Query(
            value = "DELETE FROM Harmonogram WHERE Harmonogram.FilmFilm_ID = ?1",
            nativeQuery = true
    )
    @Transactional
    void deleteScheduleByMovieID(int movieID);

    @Modifying
    @Query (
            value = "UPDATE Harmonogram " +
                    "SET Harmonogram.Godzina_startu = :beginTime, " +
                    "Harmonogram.Godzina_zakonczenia = :endTime " +
                    "WHERE Harmonogram.Harmonogram_ID = :scheduleID",
            nativeQuery = true
    )
    @Transactional
    void updateScheduleByID(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime,
                            @Param("scheduleID") int scheduleID);


    @Query(
            value = "SELECT * FROM Harmonogram " +
                    "WHERE Harmonogram.Harmonogram_ID = ?1",
            nativeQuery = true
    )
    Schedule findByScheduleID(int scheduleID);

}
