package com.Application.springbootapp.Repositories;

import com.Application.springbootapp.Entities.Użytkownik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UżytkownikRepository extends JpaRepository<Użytkownik, Integer> {

    @Modifying
    @Query(
            value = "insert into użytkownik(imie, nazwisko, email, haslo, RolaRola_ID) Values(:name, :surname, :email, :haslo, :rola )",
            nativeQuery = true
    )
    @Transactional
    public void add(@Param("name") String name, @Param("surname") String surname, @Param("email") String email,
                    @Param("haslo") String haslo, @Param("rola") int rola);


    @Query(
            value = "Select * from użytkownik u where u.email = ?1",
            nativeQuery = true
    )
    Użytkownik findByEmail(String email);

    @Query(
            value = "Select u.RolaRola_ID from użytkownik u where u.email = ?1",
            nativeQuery = true
    )
    int findRolaUzytkownikaByEmail(String email);
}
