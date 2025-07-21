package com.treina.recife.sgp.model;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.treina.recife.sgp.constants.Prioridade;
import com.treina.recife.sgp.constants.StatusTarefa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table; // Importe Table
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tarefa") // Nome da tabela no banco de dados (geralmente em minúsculas)
public class Tarefa implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id") // Ajustado para "task_id" conforme seu log de criação de tabela
    private Long taskId; // Usar Long para IDs gerados

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "data_criacao", nullable = false) // Ajustado para "data_criacao"
    private LocalDate dataCriacao;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "data_conclusao", nullable = false) // Ajustado para "data_conclusao"
    private LocalDate dataConclusao;

    @Column(name = "prioridade", nullable = false) // Ajustado para "prioridade"
    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    @Column(name = "status", nullable = false) // Ajustado para "status"
    @Enumerated (EnumType.STRING)
    private StatusTarefa status; // Verifique se o enum StatusTarefa está corretamente configurado

    @ManyToOne
    // name = "project_id": Coluna de FK na tabela 'tarefa'
    // referencedColumnName = "project_id": Coluna de PK na tabela 'projeto' (referenciada)
    @JoinColumn(name = "project_id", referencedColumnName = "project_id", nullable = false)
    private Projeto projeto;

@ManyToOne
    // name = "user_id": Coluna de FK na tabela 'tarefa'
    // referencedColumnName = "user_id": Coluna de PK na tabela 'usuarios' (referenciada)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id" , nullable = false)
    private Usuario usuario;
}