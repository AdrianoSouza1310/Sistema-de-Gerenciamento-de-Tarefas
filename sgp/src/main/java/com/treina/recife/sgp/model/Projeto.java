package com.treina.recife.sgp.model;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.treina.recife.sgp.constants.StatusProjeto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table; // Adicionado para consistência, embora você já tenha em outras entidades
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "projeto") // Boa prática para definir o nome da tabela (geralmente minúsculas)
public class Projeto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id") // Ajustado para snake_case, se seu DB usa isso. Se for 'projectId', mantenha.
    private Long projectId; // Mude para Long para consistência com IDs gerados

    @Column(name = "nome", nullable = false) // Ajustado para minúsculas para convenção
    private String nome;

    @Column(name = "descricao", nullable = false) // Ajustado para minúsculas para convenção
    private String descricao;

    @Column(name = "data_inicio", nullable = false) // Ajustado para snake_case
    @JsonFormat (pattern = "dd/MM/yyyy")
    private LocalDate dataInicio;

    @Column(name = "data_conclusao", nullable = false) // Ajustado para snake_case
    @JsonFormat (pattern = "dd/MM/yyyy")
    private LocalDate dataConclusao;

    @ManyToOne
    // 'name' é a coluna da FK na tabela 'projeto' (ex: 'responsavel_user_id')
    // 'referencedColumnName' é a PK na tabela 'usuarios' (que é 'user_id')
    @JoinColumn(name = "responsavel_user_id", referencedColumnName = "user_id", nullable = false)
    private Usuario responsavel;

    @Column(name = "status", nullable = false) // Ajustado para minúsculas para convenção
    @Enumerated(EnumType.STRING)
    private StatusProjeto status;
}