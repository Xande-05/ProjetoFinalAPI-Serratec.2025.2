package org.serratec.ecommerce.controller;

import org.serratec.ecommerce.dto.CategoriaDTO;
import org.serratec.ecommerce.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;
	
	// categoria insert (Post)
	
	@PostMapping
	public ResponseEntity<CategoriaDTO> inserir(@RequestBody CategoriaDTO categoriaDTO) {
		CategoriaDTO categoriaCriada = categoriaService.inserir(categoriaDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCriada);
	}
	
	// categoria edit (Put
	@PutMapping("/{id}") 
	public ResponseEntity<CategoriaDTO> editar(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDTO) {
		CategoriaDTO categoriaAtualizada = categoriaService.editar(id, categoriaDTO);
		return ResponseEntity.ok(categoriaAtualizada);
	}
}
