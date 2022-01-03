package com.Application.springbootapp.Repositories;

import com.Application.springbootapp.Entities.Gatunek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GatunekRepository extends JpaRepository<Gatunek, Integer> {
}
