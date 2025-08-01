package com.treina.recife.sgp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treina.recife.sgp.model.Usuario;
import java.util.Optional;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCpf(String cpf);
    
    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByCpfAndEmail(String cpf, String email);

    boolean existsByEmail(String email);
   


}
