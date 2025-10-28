package org.serratec.ecommerce.controller;

import java.io.IOException;

import org.serratec.ecommerce.dto.UsuarioResponseDTO;
import java.util.List;

import org.serratec.ecommerce.dto.FotoPerfilResponse;
import org.serratec.ecommerce.exception.ResourceNotFoundException;
import org.serratec.ecommerce.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	//Adiciona a foto
	@PostMapping("/{id}/foto")
	public ResponseEntity<Void> uploadFotoPerfil(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
		try {
			usuarioService.salvarFotoPerfil(id, file);
			return ResponseEntity.ok().build();

		} catch (ResourceNotFoundException e) {
			return ResponseEntity.notFound().build();

		} catch (IOException e) {
			System.err.println("Erro ao salvar foto para usuário ID " + id + ": " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		} catch (RuntimeException e) {
			System.err.println("Erro inesperado no upload da foto para usuário ID " + id + ": " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping
	public ResponseEntity<List<UsuarioResponseDTO>> listarTodosUsuarios() {
		List<UsuarioResponseDTO> usuarios = usuarioService.listarTodos();
		return ResponseEntity.ok(usuarios);
	}
	
	//Pega a foto do usuario
	@GetMapping("/{id}/foto")
	public ResponseEntity<Resource> buscarFotoPerfil(@PathVariable Long id) {
		try {
			FotoPerfilResponse fotoResponse = usuarioService.buscarFotoResponse(id);

			return ResponseEntity.ok().contentType(fotoResponse.getMediaType())
					.header(HttpHeaders.CONTENT_DISPOSITION,
							"inline; filename=\"" + fotoResponse.getResource().getFilename() + "\"")
					.body(fotoResponse.getResource());

		} catch (ResourceNotFoundException e) {
			return ResponseEntity.notFound().build();

		} catch (RuntimeException e) {
			System.err.println("Erro ao buscar foto para usuário ID " + id + ": " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}
	}

	@PutMapping("/{id}/promover-admin")
	public ResponseEntity<String> promoverParaAdmin(@PathVariable Long id) {
		try {
			usuarioService.promoverUsuarioParaAdmin(id);
			return ResponseEntity.ok("Usuário " + id + " foi promovido para ADMIN.");
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
}