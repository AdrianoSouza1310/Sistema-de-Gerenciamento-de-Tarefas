package com.treina.recife.sgp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.treina.recife.sgp.constants.StatusUsuario; // Certifique-se de que este enum está correto e no caminho certo

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id") // Garante que a coluna no DB é 'user_id'
    private Long userId; // O nome da propriedade é 'userId'

    // ... (restante dos seus campos) ...

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "datanascimento", nullable = false)
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusUsuario status;

    @OneToMany(mappedBy = "responsavel") // Supondo que na entidade Projeto, você tem um campo 'responsavel' que mapeia para este Usuario
    private List<Projeto> projetos = new ArrayList<>();

}