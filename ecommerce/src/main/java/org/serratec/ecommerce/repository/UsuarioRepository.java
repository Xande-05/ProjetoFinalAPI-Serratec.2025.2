package org.serratec.ecommerce.repository;

import java.util.Optional;

import org.serratec.ecommerce.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

   
    Optional<Usuario> findByEmail(String email);

}