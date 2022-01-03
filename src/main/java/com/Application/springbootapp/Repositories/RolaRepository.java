package com.Application.springbootapp.Repositories;

import com.Application.springbootapp.Entities.Rola;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolaRepository extends JpaRepository<Rola, Integer> {
}
