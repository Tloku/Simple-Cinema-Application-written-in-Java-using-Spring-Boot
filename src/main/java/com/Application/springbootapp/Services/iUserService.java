package com.Application.springbootapp.Services;

import com.Application.springbootapp.Entities.Użytkownik;
import java.util.List;

public interface iUserService {

    void add(String name, String surname, String email, String haslo, int rola);
    List<Użytkownik> findAll();
    Użytkownik findUzytkownikByEmail(String email);
    int findRolaByEmail(String email);
}
