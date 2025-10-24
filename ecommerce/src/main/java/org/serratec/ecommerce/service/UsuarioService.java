package org.serratec.ecommerce.service;

import org.springframework.web.multipart.MultipartFile;

import org.serratec.ecommerce.entity.Perfil;
import org.serratec.ecommerce.entity.Usuario;
import org.serratec.ecommerce.repository.PerfilRepository;
import org.serratec.ecommerce.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {



  @Autowired
	private PerfilRepository perfilRepository;
	
	    @Autowired
	    private UsuarioRepository usuarioRepository;

	    public void salvarFotoPerfil(Long usuarioId, MultipartFile file) {    
	        Usuario usuario = usuarioRepository.findById(usuarioId)
	                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
	        String urlDaFoto = salvarArquivoFisico(file); 
	        usuario.setUrlFotoPerfil(urlDaFoto);
	        usuarioRepository.save(usuario);
	    }

	    private String salvarArquivoFisico(MultipartFile file) {
	        return "caminho/para/a/foto.jpg";
	    }
	}	
	


	
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

