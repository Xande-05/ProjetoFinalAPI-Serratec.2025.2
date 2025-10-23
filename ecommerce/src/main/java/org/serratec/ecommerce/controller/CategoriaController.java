package org.serratec.ecommerce.controller;

import java.util.List;
import org.serratec.ecommerce.dto.CategoriaDTO;
import org.serratec.ecommerce.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listarTodos() {
        List<CategoriaDTO> categorias = categoriaService.listarTodos();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> buscarPorId(@PathVariable Long id) {
        return categoriaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> inserir(@RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO categoriaCriada = categoriaService.inserir(categoriaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCriada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> editar(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDTO) {
        return categoriaService.editar(id, categoriaDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        boolean deletado = categoriaService.deletar(id);
        if (deletado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}