package com.treina.recife.sgp.model;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
import jakarta.persistence.Table; 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "projeto") 
public class Projeto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id") 
    private Long projectId; 

    @Column(name = "nome", nullable = false) 
    private String nome;

    @Column(name = "descricao", nullable = false) 
    private String descricao;

    @Column(name = "data_inicio", nullable = false) 
    @JsonFormat (pattern = "dd/MM/yyyy")
    private LocalDate dataInicio;

    @Column(name = "data_conclusao", nullable = false) 
    @JsonFormat (pattern = "dd/MM/yyyy")
    private LocalDate dataConclusao;

    @ManyToOne
    @JoinColumn(name = "responsavel_user_id", referencedColumnName = "user_id", nullable = false)
    @JsonManagedReference
    private Usuario responsavel;

    @Column(name = "status", nullable = false) 
    @Enumerated(EnumType.STRING)
    private StatusProjeto status;
}