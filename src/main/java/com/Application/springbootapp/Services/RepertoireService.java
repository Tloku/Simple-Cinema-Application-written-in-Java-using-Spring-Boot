package com.Application.springbootapp.Services;

import com.Application.springbootapp.Entities.Repertoire;
import com.Application.springbootapp.Repositories.RepertoireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RepertoireService implements iRepertoireService {

    @Autowired
    private RepertoireRepository repertoireRepository;

    @Override
    public List<Repertoire> findAllRepertoires() {
        return repertoireRepository.findAll();
    }

    @Override
    public Date getDateByRepertoireID(int repertoireID) {
        return repertoireRepository.getDateByRepertoireID(repertoireID);
    }
}
