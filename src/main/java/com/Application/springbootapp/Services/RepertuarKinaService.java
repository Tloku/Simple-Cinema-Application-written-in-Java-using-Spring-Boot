package com.Application.springbootapp.Services;

import com.Application.springbootapp.Entities.RepertuarKina;
import com.Application.springbootapp.Repositories.RepertuarKinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RepertuarKinaService implements iRepertuarKinaService {

    @Autowired
    private RepertuarKinaRepository repertuarKinaRepository;

    @Override
    public List<RepertuarKina> findAllRepertoires() {
        System.out.println("Wejszlem do metody findallrepertoires");
        List<RepertuarKina> cinemaRepertoires =repertuarKinaRepository.findAll();
        System.out.println("Znalazlem i wyjszlem");
        return cinemaRepertoires;
    }

    @Override
    public Date getDateByRepertoireID(int repertoireID) {
        return repertuarKinaRepository.getDateByRepertoireID(repertoireID);
    }
}
