package com.Application.springbootapp.Services;

import com.Application.springbootapp.Entities.Role;
import com.Application.springbootapp.Entities.User;
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
    public List<User> findAll() {
        return uzytkownikRepository.findAll();
    }
    @Override
    public User findUzytkownikByEmail(String email){
        User user = uzytkownikRepository.findByEmail(email);
        if(user == null) return null;
        Role role = new Role();
        role.setRoleID(findRolaByEmail(email));
        if(role.getRoleID() == 1)
            role.setRoleName("Pracownik");
        else if(role.getRoleID() == 2)
            role.setRoleName("Klient");
        else
            role.setRoleName("Nieznane");
        user.setRola(role);
        return user;
    }

    @Override
    public int findRolaByEmail(String email){
        return uzytkownikRepository.findRolaUzytkownikaByEmail(email);
    }
}
