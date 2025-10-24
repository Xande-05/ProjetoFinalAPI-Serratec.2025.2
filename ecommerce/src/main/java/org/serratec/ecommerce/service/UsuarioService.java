package org.serratec.ecommerce.service;

import org.serratec.ecommerce.entity.Usuario;
import org.serratec.ecommerce.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioService {


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
	

