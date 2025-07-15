package com.treina.recife.sgp.service;

import java.util.Optional;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;

import com.treina.recife.sgp.model.Projeto;

public interface ProjetoService {

Page<Projeto> getProjetos(Pageable pageable);

    Optional<Projeto> getProjetoById(long projectId);

    Projeto creaTeProject(Projeto projeto);

    Projeto updateProjeto(Projeto projeto);

    void deleteProjeto(long projectId);    


}
