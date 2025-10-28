package org.serratec.ecommerce.controller;

import org.serratec.ecommerce.dto.PedidoRequestDTO; 
import org.serratec.ecommerce.dto.PedidoResponseDTO;
import org.serratec.ecommerce.entity.ItemPedido;
import org.serratec.ecommerce.exception.EnumValidationException;
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
import org.springframework.data.web.PageableDefault;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;
    //Get com paginação
    @GetMapping
    public ResponseEntity<Page<PedidoResponseDTO>> listar(
            @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable) {

        Page<PedidoResponseDTO> pedidos = pedidoService.listarTodos(pageable);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable Long id) {
        PedidoResponseDTO pedido = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(pedido);
    }

    @GetMapping("/itens-por-valor-unitario")
    public ResponseEntity<Page<ItemPedido>> buscarItensPorValorUnitario(
            @RequestParam(required = false, defaultValue = "0.0") Double valorMinimo,
            @RequestParam(required = false, defaultValue = "" + Double.MAX_VALUE) Double valorMaximo,
            Pageable pageable) {
        Page<ItemPedido> itens = pedidoService.buscarPorTotal(valorMinimo, valorMaximo, pageable);
        return ResponseEntity.ok(itens);
    }
    
    //Crud para testar a query
    @GetMapping("/itens/total")
    public ResponseEntity<ItemPedido> listarItemPorTotal(
            @RequestParam("min") Double valorMinimo,
            @RequestParam("max") Double valorMaximo) {

        ItemPedido item = pedidoService.buscarItemPorValorTotal(valorMinimo, valorMaximo);
        
        if (item != null) {
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> inserir(@Valid @RequestBody PedidoRequestDTO pedidoDTO) throws Exception {
        PedidoResponseDTO novoPedido = pedidoService.inserir(pedidoDTO);
     
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
    }

    
    @PutMapping("/{id}") 
    public ResponseEntity<PedidoResponseDTO> atualizarPedido(
            @PathVariable Long id,
            @Valid @RequestBody PedidoRequestDTO pedidoDTO) {
        try {
            PedidoResponseDTO pedidoAtualizado = pedidoService.atualizarPedido(id, pedidoDTO);
            return ResponseEntity.ok(pedidoAtualizado);
        } catch (ResourceNotFoundException e) {
  
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
         
            return ResponseEntity.unprocessableEntity().body(null); 
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


 
    @PutMapping("/{id}/status")
    public ResponseEntity<PedidoResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestBody String novoStatus
    ) {
        try {
        	PedidoResponseDTO pedidoAtualizado = pedidoService.atualizarStatus(id, novoStatus);
            return ResponseEntity.ok(pedidoAtualizado); 
        } catch (EnumValidationException e) {

             throw new RuntimeException(e); 
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {

            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null); 
        }

    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pedidoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}