package com.Application.springbootapp.Services;

import com.Application.springbootapp.Entities.Rola;
import com.Application.springbootapp.Entities.Użytkownik;
import com.Application.springbootapp.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements iUserService {

    @Autowired
    private UserRepository uzytkownikRepository;

    @Override
    public void add(String name, String surname, String email, String haslo, int rola) {
        uzytkownikRepository.add(name, surname, email, haslo, 2);
    }

    @Override
    public List<Użytkownik> findAll() {
        return uzytkownikRepository.findAll();
    }
    @Override
    public Użytkownik findUzytkownikByEmail(String email){
        Użytkownik user = uzytkownikRepository.findByEmail(email);
        if(user == null) return null;
        Rola role = new Rola();
        role.setRolaID(findRolaByEmail(email));
        if(role.getRolaID() == 1)
            role.setNazwaRoli("Pracownik");
        else if(role.getRolaID() == 2)
            role.setNazwaRoli("Klient");
        else
            role.setNazwaRoli("Nieznane");
        user.setRola(role);
        return user;
    }

    @Override
    public int findRolaByEmail(String email){
        return uzytkownikRepository.findRolaUzytkownikaByEmail(email);
    }
}
