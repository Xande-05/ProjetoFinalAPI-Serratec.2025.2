package org.serratec.ecommerce.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.serratec.ecommerce.config.MailConfig;
import org.serratec.ecommerce.dto.ItemPedidoRequestDTO;
import org.serratec.ecommerce.dto.PedidoDTO;
import org.serratec.ecommerce.dto.PedidoRequestDTO;
import org.serratec.ecommerce.dto.ProdutoResponseDTO;
import org.serratec.ecommerce.entity.Cliente;
import org.serratec.ecommerce.entity.ItemPedido;
import org.serratec.ecommerce.entity.Pedido;
import org.serratec.ecommerce.entity.PedidoStatus;
import org.serratec.ecommerce.exception.ResourceNotFoundException;
import org.serratec.ecommerce.repository.ClienteRepository;
import org.serratec.ecommerce.repository.ItemPedidoRepository;
import org.serratec.ecommerce.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

	@Autowired
	PedidoRepository pedidoRepository;
	
	@Autowired
	ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	ProdutoService produtoService;
	
	@Autowired
	MailConfig mailConfig;
	
	
	public Pedido buscarPorId(Long id) {
		
		Optional<Pedido> pedido = pedidoRepository.findById(id);
		
        return pedido.get();
    }
	
	public Page<ItemPedido> buscarPorTotal(Double valorMinimo, Double valorMaximo, Pageable pageable) {
	    return itemPedidoRepository.findByValorBetween(valorMinimo, valorMaximo, pageable);
	}

	public PedidoDTO inserir(PedidoRequestDTO pedidoDTO) throws ResourceNotFoundException {
		
		if(pedidoDTO.getClienteId() == null || pedidoDTO.getItens().isEmpty()) {
			throw new Exception("Pedido inválido: cliente e itens são obrigatórios.");
		}
		
		Optional<Cliente> clienteOP = clienteRepository.findById(pedidoDTO.getClienteId());
		
		if (!clienteOP.isPresent()) {
			throw new Exception("Cliente nao existe");
		}
		
		Cliente cliente = clienteOP.get();
		
		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);
		pedido.setStatus(PedidoStatus.AGUARDANDO_PAGAMENTO);
		
		Set<ItemPedido> itensPedido = new HashSet<>();
		for (ItemPedidoRequestDTO produto : pedidoDTO.getItens()) {
			produto = produtoService.buscarPorId(produto.getProdutoId());
			ItemPedido itemPedido = new ItemPedido();
			itensPedido.add(itemPedido);
		}
		pedido.setItens(itensPedido);
		pedido = pedidoRepository.save(pedido);

		mailConfig.javaMailSander(cliente.getEmail(), "Cadastro de Usuario!", cliente.toString());
		
		return new PedidoDTO();
		
	 new ResourceNotFoundException("Lógica de criação de ItemPedido e salvamento ainda pendente.");
	}
	
	
	
}