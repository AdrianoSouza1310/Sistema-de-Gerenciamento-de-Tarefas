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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Projeto")

public class Projeto implements Serializable {
    private static final long serialVersionUID = 1L;

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "projectId")
private long projectId;

@Column(name = "NOME", nullable = false)
private String Nome;

@Column(name = "DESCRIÇÃO", nullable = false)
private String DESCRIÇÃO;

@Column(name = "dataInicio", nullable = false)
@JsonFormat (pattern =  "dd/MM/yyyy")
private LocalDate dataInicio;

@Column(name = "dataConclusao", nullable = false)
@JsonFormat (pattern =  "dd/MM/yyyy")
private LocalDate dataConclusao;

@ManyToOne
@JoinColumn(name = "userId", referencedColumnName = "userId")
private Usuario responsavel;

@Column(name = "status", nullable = false)
@Enumerated(EnumType.STRING)
private StatusProjeto status;

}
