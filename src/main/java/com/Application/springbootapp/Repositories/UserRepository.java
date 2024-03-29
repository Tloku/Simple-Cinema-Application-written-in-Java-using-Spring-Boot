package com.Application.springbootapp.Repositories;

import com.Application.springbootapp.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Modifying
    @Query(
            value = "INSERT INTO użytkownik(imie, nazwisko, email, haslo, RolaRola_ID) " +
                    " VALUES (:name, :surname, :email, :haslo, :rola )",
            nativeQuery = true
    )
    @Transactional
    public void add(@Param("name") String name, @Param("surname") String surname, @Param("email") String email,
                    @Param("haslo") String haslo, @Param("rola") int rola);


    @Query(
            value = "SELECT * FROM użytkownik u WHERE u.email = ?1",
            nativeQuery = true
    )
    User findByEmail(String email);

    @Query(
            value = "SELECT u.RolaRola_ID FROM użytkownik u WHERE u.email = ?1",
            nativeQuery = true
    )
    int findRolaUzytkownikaByEmail(String email);


    @Modifying
    @Query(
            value = "UPDATE Użytkownik " +
                    "SET Użytkownik.Haslo = ?2 " +
                    "WHERE Użytkownik.Użytkownik_ID = ?1",
            nativeQuery = true
    )
    @Transactional
    void changePasswordByUserID(int userID, String password);


    @Query(
            value = "SELECT * FROM Użytkownik " +
                    "WHERE Użytkownik.Użytkownik_ID = ?1 ",
            nativeQuery = true
    )
    User findUserByID(int userID);
}
