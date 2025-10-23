package org.serratec.ecommerce.controller;

import org.serratec.ecommerce.dto.PedidoDTO;
import org.serratec.ecommerce.dto.PedidoRequestDTO;
import org.serratec.ecommerce.entity.ItemPedido;
import org.serratec.ecommerce.entity.Pedido;
import org.serratec.ecommerce.exception.ResourceNotFoundException;
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

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPorId(id);
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
    public ResponseEntity<PedidoDTO> inserir(@RequestBody PedidoRequestDTO pedidoDTO) throws ResourceNotFoundException {
        PedidoDTO novoPedido = pedidoService.inserir(pedidoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizar(@PathVariable Long id, @RequestBody Pedido novoPedido) {
        Pedido pedidoExistente = pedidoService.buscarPorId(id);
        if (pedidoExistente == null) {
            return ResponseEntity.notFound().build();
        }
        pedidoExistente.setStatus(novoPedido.getStatus());
        return ResponseEntity.ok(pedidoExistente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPorId(id);
        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
