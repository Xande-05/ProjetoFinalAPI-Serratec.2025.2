package org.serratec.ecommerce.service;

import org.serratec.ecommerce.config.MailConfig;
import org.serratec.ecommerce.dto.PedidoDTO;
import org.serratec.ecommerce.dto.PedidoRequestDTO;
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
	ProdutoService produtoService;
	
	@Autowired
	MailConfig mailConfig;
	
	
	
	public PedidoDTO inserir(PedidoRequestDTO pedidoDTO) throws Exception {
		
		if(pedidoDTO.getCliente() == null || pedidoDTO.getItens().isEmpty()) {
			throw new Exception("Pedido inválido: cliente e itens são obrigatórios.");
		}
		
		
	}
}
