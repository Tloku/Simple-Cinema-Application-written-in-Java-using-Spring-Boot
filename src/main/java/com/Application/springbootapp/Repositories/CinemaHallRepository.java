package com.Application.springbootapp.Repositories;

import com.Application.springbootapp.Entities.SalaKinowa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaHallRepository extends JpaRepository<SalaKinowa, Integer> {
}
