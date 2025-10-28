package org.serratec.ecommerce.controller;

import java.util.List;

import org.serratec.ecommerce.dto.ClienteRequestDTO;
import org.serratec.ecommerce.dto.ClienteResponseDTO;
import org.serratec.ecommerce.service.ClienteService;
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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService; 

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> criar(@Valid @RequestBody ClienteRequestDTO dto) { 
        ClienteResponseDTO novoCliente = clienteService.criarCliente(dto); 
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente); 
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listar() { 
        return ResponseEntity.ok(clienteService.listarTodos()); 
    }


    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) { 
        ClienteResponseDTO cliente = clienteService.buscarPorId(id); 
        return ResponseEntity.ok(cliente); 
    }



    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ClienteRequestDTO dto) { 

        ClienteResponseDTO clienteAtualizado = clienteService.atualizar(id, dto);
        return ResponseEntity.ok(clienteAtualizado); 
    }
 

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) { 
        clienteService.deletar(id); 
        return ResponseEntity.noContent().build(); 
    }
}