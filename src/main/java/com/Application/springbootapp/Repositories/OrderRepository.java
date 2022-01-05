package com.Application.springbootapp.Repositories;

import com.Application.springbootapp.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query(
            value = "SELECT * FROM Zamówienie INNER JOIN Użytkownik ON Zamówienie.UżytkownikUżytkownik_ID = " +
                    "Użytkownik.Użytkownik_ID WHERE Użytkownik.email = ?1",
            nativeQuery = true
    )
    List<Order> findByEmail(String email);


    @Modifying
    @Query(
            value = "INSERT INTO Zamówienie(data_zamowienia, kwota_zamowienia, sposob_platnosci, użytkownikużytkownik_id) " +
                    "VALUES (:date, :orderValue, :paymentMethod, :userID)",
            nativeQuery = true
    )
    @Transactional
    int addOrder(@Param("date") Date date, @Param("orderValue") float orderValue,
                  @Param("paymentMethod") String paymentMethod, @Param("userID") int userID);


    @Modifying
    @Query(
            value = "UPDATE Zamówienie SET Zamówienie.Kwota_zamowienia = ?1 " +
                    "WHERE Zamówienie.Zamowienie_ID = ?2",
            nativeQuery = true
    )
    @Transactional
    void updateOrderValue(float orderValue, int orderID);


    @Query(
            value ="SELECT Zamówienie.Zamowienie_ID FROM Zamówienie " +
                    "WHERE Zamówienie.data_zamowienia = ?1 AND Zamówienie.użytkownikużytkownik_id = ?2",
            nativeQuery = true
    )
    public int findByDateAndUserID(String date, int userID);
}
