package org.serratec.ecommerce.service;

import org.serratec.ecommerce.entity.Perfil;
import org.serratec.ecommerce.entity.Usuario;
import org.serratec.ecommerce.repository.PerfilRepository;
import org.serratec.ecommerce.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	public Usuario criarUsuarioParaCliente(String nome, String email) {
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha("defaultPassword");
	
        Perfil perfilCliente = perfilRepository.findByNome("ROLE_CLIENTE");
        if (perfilCliente == null) {
            perfilCliente = new Perfil();
            perfilCliente.setNome("ROLE_CLIENTE");
            perfilRepository.save(perfilCliente);
        }


        usuario.getUsuarioPerfis().add(perfilCliente);
        return usuarioRepository.save(usuario);
    }
	
}
