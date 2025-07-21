package com.treina.recife.sgp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treina.recife.sgp.model.Tarefa;
import com.treina.recife.sgp.model.Usuario;
import java.util.List;


@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    // CORRIGIDO: Se a intenção é buscar tarefas pelo usuário ATRIBUÍDO diretamente à tarefa:
    List<Tarefa> findByUsuarioUserId(Long userId); // Use Long para consistência com o ID da entidade

    // Se você quer buscar tarefas por quem é o RESPONSÁVEL DO PROJETO ao qual a tarefa pertence:
    List<Tarefa> findByProjetoResponsavelUserId(Long userId); // Note a navegação: Projeto -> Responsavel -> UserId

    // CORRIGIDO: O método abaixo estava com um erro de digitação ("Reponsavel")
    // e provavelmente se referia ao método acima. Removi-o para evitar redundância/confusão.
    // List<Tarefa> findByReponsavelUserId(long userId); // <-- REMOVER OU CORRIGIR PARA findByProjetoResponsavelUserId

    // Este método está correto para buscar tarefas por um objeto Usuario completo
    List<Tarefa> findByUsuario(Usuario usuario);

    boolean existsByTitulo(String title);

    // CORRIGIDO: Removida a repetição "ByBy"
    // Se a intenção é verificar se existem tarefas para um determinado usuário:
    boolean existsByUsuarioUserId(Long userId); // Use Long
}