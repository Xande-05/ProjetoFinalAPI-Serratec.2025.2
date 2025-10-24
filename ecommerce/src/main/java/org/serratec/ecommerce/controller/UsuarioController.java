package org.serratec.ecommerce.controller;

import org.serratec.ecommerce.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping("/{id}/foto")
	public ResponseEntity<Void> uploadFotoPerfil(
			@PathVariable Long id,
			@RequestParam("file") MultipartFile file){
		usuarioService.salvarFotoPerfil(id,file);
		return ResponseEntity.ok().build();
	}
}
