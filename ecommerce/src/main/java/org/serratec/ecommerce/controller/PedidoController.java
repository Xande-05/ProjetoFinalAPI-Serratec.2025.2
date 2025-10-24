package org.serratec.ecommerce.controller;

import org.serratec.ecommerce.dto.PedidoRequestDTO;
import org.serratec.ecommerce.dto.PedidoResponseDTO;
import org.serratec.ecommerce.entity.ItemPedido;
import org.serratec.ecommerce.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid; 

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable Long id) { 
        PedidoResponseDTO pedido = pedidoService.buscarPorId(id); 
        return ResponseEntity.ok(pedido);
    }

    @GetMapping("/total")
    public ResponseEntity<Page<ItemPedido>> buscarPorTotal(
            @RequestParam Double valorMinimo,
            @RequestParam Double valorMaximo,
            Pageable pageable) {
        Page<ItemPedido> itens = pedidoService.buscarPorTotal(valorMinimo, valorMaximo, pageable);
        return ResponseEntity.ok(itens);
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> inserir(@Valid @RequestBody PedidoRequestDTO pedidoDTO) throws Exception { 
        PedidoResponseDTO novoPedido = pedidoService.inserir(pedidoDTO); 
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody PedidoRequestDTO pedidoDTO) { 
        PedidoResponseDTO pedidoAtualizado = pedidoService.atualizar(id, pedidoDTO); 
        return ResponseEntity.ok(pedidoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pedidoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}