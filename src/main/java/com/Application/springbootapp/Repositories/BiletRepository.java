package com.Application.springbootapp.Repositories;

import com.Application.springbootapp.Entities.Bilet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface BiletRepository extends JpaRepository<Bilet, Integer> {
   @Query(
            value = "SELECT * FROM Bilet INNER JOIN " +
                    "Zamówienie ON Bilet.ZamówienieZamowienie_ID = Zamówienie.Zamowienie_ID " +
                    "WHERE Zamówienie.Zamowienie_ID = ?1",
            nativeQuery = true
    )
    public List<Bilet> findByOrderID(int order_id);


   @Modifying
   @Transactional
   @Query(
           value = "DELETE FROM Bilet WHERE Bilet.Bilet_ID = ?1",
           nativeQuery = true
   )
   public void deleteTicketByID(int ticketID);

   @Modifying
   @Transactional
   @Query(
           value = "UPDATE Bilet " +
                   "SET Bilet.Godzina_rozpoczęcia = :sdf " +
                   "WHERE Bilet.Bilet_ID = :ticketID",
           nativeQuery = true
   )
   public void changeTicketBeginTimeByID(@Param("ticketID") int ticketID,@Param("sdf") Date sdf);

   @Modifying
   @Transactional
   @Query(
           value = "INSERT INTO Bilet(Godzina_rozpoczęcia, Numer_miejsca, ZamówienieZamowienie_ID, sala_kinowaSala_ID, " +
                   "FilmFilm_ID, UżytkownikUżytkownik_ID) " +
                   "VALUES(:beginTime, :sitNumber, :orderID, :cinemaHallID, :movieID, :userID)",
           nativeQuery = true
   )
   public void addTicket(@Param("beginTime") Date beginTime, @Param("sitNumber") int sitNumber, @Param("orderID") int orderID,
                         @Param("cinemaHallID") int cinemaHallID, @Param("movieID") int movieID, @Param("userID") int userID);

   @Query(
           value = "SELECT Film.Tytul FROM Bilet " +
                   "INNER JOIN Film ON " +
                   "Bilet.FilmFilm_ID = Film.Film_ID " +
                   "WHERE Bilet.Bilet_ID = ?1",
           nativeQuery = true
   )
   public String findMovieTitleByTicketID(int ticketID);


}

