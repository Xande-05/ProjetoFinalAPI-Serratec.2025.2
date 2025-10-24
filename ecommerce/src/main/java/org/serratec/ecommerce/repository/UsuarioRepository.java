package org.serratec.ecommerce.repository;

import java.util.Optional;

import org.serratec.ecommerce.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
@Service
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String email);

public static final UsuarioRepository usuarioRepository = null;

public static void atualizaFotoDePerfil(Long usuarioId, String novoCaminhoFoto) {
	Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
	 if(usuarioOptional.isPresent()) {
		 Usuario usuario = usuarioOptional.get();
		 Usuario.setFotoPerfilPath(novoCaminhoFoto);
		 usuarioRepository.save(usuario);
	 }else {
		 throw new RuntimeException ("Usuario não encontrado com o ID:" + usuarioId);
	 }
  }

}
