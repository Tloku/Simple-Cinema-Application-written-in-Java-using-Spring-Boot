package com.Application.springbootapp.Services;

import com.Application.springbootapp.Entities.Rola;
import com.Application.springbootapp.Entities.Użytkownik;
import com.Application.springbootapp.Repositories.UżytkownikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UżytkownikService implements iUżytkownikService {

    @Autowired
    private UżytkownikRepository uzytkownikRepository;

    @Override
    public void add(String name, String surname, String email, String haslo, int rola) {
        System.out.println("Wejszłem do funkcji add w uzytkownikservice");
        uzytkownikRepository.add(name, surname, email, haslo, 2);
        System.out.println("A i udalo mi sie zapisac uzytkownika hurrraaa");
    }

    @Override
    public List<Użytkownik> findAll() {
        System.out.println("Wejszłem do metody findall w uzytkownikservice.java");
        List<Użytkownik> rolaList = uzytkownikRepository.findAll();
        System.out.println("Znalazlem i wyszlem");
        return rolaList;
    }
    @Override
    public Użytkownik findUzytkownikByEmail(String email){

        //TODO rozwiazac problem gdy poda sie nieistniejacy email
        System.out.println("Wejszłem do metody finduzytkownikbyemail w uzytkownikservice.java");
        Użytkownik uzytkownik = uzytkownikRepository.findByEmail(email);
        Rola rola = new Rola();
        rola.setRolaID(findRolaByEmail(email));

        if(rola.getRolaID() == 1)
            rola.setNazwaRoli("Pracownik");
        else if(rola.getRolaID() == 2)
            rola.setNazwaRoli("Klient");
        else
            rola.setNazwaRoli("Nieznane");

        uzytkownik.setRola(rola);

        System.out.println("Znalazlem i wyszlem");
        return uzytkownik;
    }

    @Override
    public int findRolaByEmail(String email){
        System.out.println("Wejszłem do metody findROLAuzytkownikbyemail w uzytkownikservice.java");
        int rola_ID = uzytkownikRepository.findRolaUzytkownikaByEmail(email);

        System.out.println("Znalazlem i wyszlem");
        return rola_ID;
    }
}
