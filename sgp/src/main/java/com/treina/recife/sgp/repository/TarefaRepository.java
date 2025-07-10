package com.treina.recife.sgp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treina.recife.sgp.model.Projeto;
import com.treina.recife.sgp.model.Tarefa;
import com.treina.recife.sgp.model.Usuario;
import java.util.List;


@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    List<Tarefa> findByReponsavelUserId(long userId);

    List<Tarefa> findByUsuario(Usuario usuario);

    List<Tarefa> findByUsuarioUserId(long userId);

    boolean exexistsByTitulo(String title);

    boolean exexistsByUsuarioUserId(long userId);


}
