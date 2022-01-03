package com.Application.springbootapp.Repositories;

import com.Application.springbootapp.Entities.SalaKinowa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaKinowaRepository extends JpaRepository<SalaKinowa, Integer> {
}
