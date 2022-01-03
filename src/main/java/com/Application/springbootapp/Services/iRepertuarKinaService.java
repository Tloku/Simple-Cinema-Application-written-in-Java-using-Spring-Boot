package com.Application.springbootapp.Services;

import com.Application.springbootapp.Entities.RepertuarKina;

import java.util.Date;
import java.util.List;

public interface iRepertuarKinaService {
    public List<RepertuarKina> findAllRepertoires();
    public Date getDateByRepertoireID(int repertoireID);
}
