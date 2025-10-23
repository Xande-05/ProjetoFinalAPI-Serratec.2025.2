package org.serratec.ecommerce.service;

import java.util.Optional;

import org.serratec.ecommerce.dto.PedidoDTO;
import org.serratec.ecommerce.dto.PedidoRequestDTO;
import org.serratec.ecommerce.entity.Cliente;
import org.serratec.ecommerce.entity.Pedido;
import org.serratec.ecommerce.entity.PedidoStatus;
import org.serratec.ecommerce.repository.ClienteRepository;
import org.serratec.ecommerce.repository.ItemPedidoRepository;
import org.serratec.ecommerce.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	
	public PedidoDTO inserir(PedidoRequestDTO pedidoDTO) throws Exception {
		
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
		
		
		throw new UnsupportedOperationException("Lógica de criação de ItemPedido e salvamento ainda pendente.");
	}
}