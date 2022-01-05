package com.Application.springbootapp.Services;

import com.Application.springbootapp.Entities.Repertoire;

import java.util.Date;
import java.util.List;

public interface iRepertoireService {
    public List<Repertoire> findAllRepertoires();
    public Date getDateByRepertoireID(int repertoireID);
}
