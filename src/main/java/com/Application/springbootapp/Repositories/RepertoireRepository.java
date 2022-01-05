package com.Application.springbootapp.Repositories;

import com.Application.springbootapp.Entities.Repertoire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface RepertoireRepository extends JpaRepository<Repertoire, Integer> {

    @Query(
            value = "SELECT Repertuar_Kina.Data FROM Repertuar_Kina " +
                    "WHERE Repertuar_Kina.Repertuar_Kina_ID = ?1",
            nativeQuery = true
    )
    Date getDateByRepertoireID(int repertoireID);
}
