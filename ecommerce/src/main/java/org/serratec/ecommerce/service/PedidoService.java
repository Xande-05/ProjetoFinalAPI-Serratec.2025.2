package org.serratec.ecommerce.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.serratec.ecommerce.dto.ClienteResponseDTO;
import org.serratec.ecommerce.dto.ItemPedidoRequestDTO;
import org.serratec.ecommerce.dto.ItemPedidoResponseDTO;
import org.serratec.ecommerce.dto.PedidoRequestDTO;
import org.serratec.ecommerce.dto.PedidoResponseDTO;
import org.serratec.ecommerce.entity.Cliente;
import org.serratec.ecommerce.entity.ItemPedido;
import org.serratec.ecommerce.entity.ItemPedidoPK;
import org.serratec.ecommerce.entity.Pedido;
import org.serratec.ecommerce.entity.PedidoStatus;
import org.serratec.ecommerce.entity.Produto;
import org.serratec.ecommerce.exception.EnumValidationException;
import org.serratec.ecommerce.exception.ResourceNotFoundException;
import org.serratec.ecommerce.repository.ClienteRepository;
import org.serratec.ecommerce.repository.ItemPedidoRepository;
import org.serratec.ecommerce.repository.PedidoRepository;
import org.serratec.ecommerce.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {

	@Autowired
	PedidoRepository pedidoRepository;

	@Autowired
	ItemPedidoRepository itemPedidoRepository;

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	ProdutoRepository produtoRepository;
	
	// Adiciona as variaveis do Cupom e o Desconto 
	private static final String CUPOM_OUTUBRO = "OUTUBRO20";
	private static final BigDecimal DESCONTO_20_PORCENTO = new BigDecimal("0.20");
	
	//Metodos das Querys
	@Transactional(readOnly = true)
	public Optional<Pedido> buscarPedidoComCliente(Long id) {
        return pedidoRepository.findByIdWithCliente(id);
    }

    public ItemPedido buscarItemPorValorTotal(Double valorMinimo, Double valorMaximo) {
        return pedidoRepository.buscarPorTotalNativo(valorMinimo, valorMaximo);
    }
	
    //Paginação
	@Transactional(readOnly = true)
	public Page<PedidoResponseDTO> listarTodos(Pageable pageable) {
		Page<Pedido> pedidosPage = pedidoRepository.findAll(pageable);
		List<PedidoResponseDTO> dtos = pedidosPage.getContent().stream().map(this::mapToResponseDTO)
				.collect(Collectors.toList());
		return new PageImpl<>(dtos, pedidosPage.getPageable(), pedidosPage.getTotalElements());
	}

	@Transactional(readOnly = true)
	public PedidoResponseDTO buscarPorId(Long id) {
		Pedido pedido = pedidoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + id));
		return mapToResponseDTO(pedido);
	}

	//Paginação
	@Transactional(readOnly = true)
	public Page<ItemPedido> buscarPorTotal(Double valorMinimo, Double valorMaximo, Pageable pageable) {
		return itemPedidoRepository.findByValorBetween(valorMinimo, valorMaximo, pageable);
	}

	//Tras o cumpom para poder aplicar ou não
	@Transactional
	public PedidoResponseDTO inserir(PedidoRequestDTO pedidoRequestDTO) throws Exception {

		Cliente cliente = clienteRepository.findById(pedidoRequestDTO.getClienteId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Cliente não encontrado com ID: " + pedidoRequestDTO.getClienteId()));

		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);
		pedido.setStatus(PedidoStatus.AGUARDANDO_PAGAMENTO);
		pedido.setDataPedido(LocalDateTime.now());

		Set<ItemPedido> itensPedido = new HashSet<>();
		
		boolean aplicarDesconto = CUPOM_OUTUBRO.equalsIgnoreCase(pedidoRequestDTO.getCupom()) &&
                LocalDateTime.now().getMonth() == Month.OCTOBER;

		for (ItemPedidoRequestDTO itemDTO : pedidoRequestDTO.getItens()) {
			Produto produto = produtoRepository.findById(itemDTO.getProdutoId()).orElseThrow(
					() -> new ResourceNotFoundException("Produto não encontrado com ID: " + itemDTO.getProdutoId())); //

			ItemPedido itemPedido = new ItemPedido();
			ItemPedidoPK pk = new ItemPedidoPK();
			pk.setPedido(pedido);
			pk.setProduto(produto);
			itemPedido.setId(pk);

			itemPedido.setQuantidade(itemDTO.getQuantidade());
			itemPedido.setValor(produto.getPreco());
			
			if (aplicarDesconto) {
                BigDecimal descontoValor = produto.getPreco().multiply(DESCONTO_20_PORCENTO);
				itemPedido.setDesconto(descontoValor.setScale(2, RoundingMode.HALF_UP));
			} else {
				itemPedido.setDesconto(BigDecimal.ZERO); 
			}

			itensPedido.add(itemPedido);
		}

			
		
		pedido.setItens(itensPedido);

		pedido = pedidoRepository.save(pedido);

		return mapToResponseDTO(pedido);
	}
	
	@Transactional
	public PedidoResponseDTO atualizarPedido(Long id, PedidoRequestDTO pedidoRequestDTO) {

		Pedido pedido = pedidoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + id));

		if (pedido.getStatus() == PedidoStatus.ENVIADO || pedido.getStatus() == PedidoStatus.ENTREGUE
				|| pedido.getStatus() == PedidoStatus.CANCELADO) {
			throw new IllegalArgumentException(
					"Não é possível alterar um pedido com status: " + pedido.getStatus().getDescricao());
		}

		if (!pedido.getCliente().getId().equals(pedidoRequestDTO.getClienteId())) {
			throw new IllegalArgumentException("Não é permitido alterar o cliente associado ao pedido.");
		}

		pedido.getItens().clear();

		Set<ItemPedido> novosItens = new HashSet<>();
		
		boolean aplicarDesconto = CUPOM_OUTUBRO.equalsIgnoreCase(pedidoRequestDTO.getCupom()) &&
                LocalDateTime.now().getMonth() == Month.OCTOBER;
		
		for (ItemPedidoRequestDTO itemDTO : pedidoRequestDTO.getItens()) {
			Produto produto = produtoRepository.findById(itemDTO.getProdutoId()).orElseThrow(
					() -> new ResourceNotFoundException("Produto não encontrado com ID: " + itemDTO.getProdutoId())); //

			ItemPedido itemPedido = new ItemPedido();
			ItemPedidoPK pk = new ItemPedidoPK();
			pk.setPedido(pedido);
			pk.setProduto(produto);
			itemPedido.setId(pk);

			itemPedido.setQuantidade(itemDTO.getQuantidade());
			itemPedido.setValor(produto.getPreco());
			if (aplicarDesconto) {
                BigDecimal descontoValor = produto.getPreco().multiply(DESCONTO_20_PORCENTO);
				itemPedido.setDesconto(descontoValor.setScale(2, RoundingMode.HALF_UP));
			} else {
				itemPedido.setDesconto(BigDecimal.ZERO);
			}
			
			novosItens.add(itemPedido); 
		}
		pedido.getItens().addAll(novosItens);
		pedido = pedidoRepository.save(pedido);

		return mapToResponseDTO(pedido);
	}

	@Transactional
	public PedidoResponseDTO atualizarStatus(Long id, String novoStatusStr) throws EnumValidationException {

		Pedido pedidoExistente = pedidoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + id)); //

		PedidoStatus novoStatus = PedidoStatus.verifica(novoStatusStr);

		if (pedidoExistente.getStatus() == PedidoStatus.ENTREGUE //
				|| pedidoExistente.getStatus() == PedidoStatus.CANCELADO) {
			throw new IllegalArgumentException( //
					"Não é possível alterar o status de um pedido já entregue ou cancelado.");
		}

		pedidoExistente.setStatus(novoStatus);

		pedidoExistente = pedidoRepository.save(pedidoExistente);

		return mapToResponseDTO(pedidoExistente);
	}

	@Transactional
	public void deletar(Long id) {
		if (!pedidoRepository.existsById(id)) {
			throw new ResourceNotFoundException("Pedido não encontrado com ID: " + id);
		}
		pedidoRepository.deleteById(id);
	}

	private PedidoResponseDTO mapToResponseDTO(Pedido pedido) {
		PedidoResponseDTO dto = new PedidoResponseDTO();
		dto.setId(pedido.getId());
		dto.setStatus(pedido.getStatus());
		dto.setDataPedido(pedido.getDataPedido());

		if (pedido.getCliente() != null) {
			ClienteResponseDTO clienteDTO = new ClienteResponseDTO(pedido.getCliente());
			dto.setCliente(clienteDTO);
		}

		BigDecimal totalCalculado = BigDecimal.ZERO;
		if (pedido.getItens() != null) {
			List<ItemPedidoResponseDTO> itemDTOs = pedido.getItens().stream().map(item -> {
				ItemPedidoResponseDTO itemDto = new ItemPedidoResponseDTO();
				BigDecimal subtotalItem = BigDecimal.ZERO;
				if (item.getId() != null && item.getId().getProduto() != null) {
					itemDto.setProdutoId(item.getId().getProduto().getId());
					itemDto.setProdutoNome(item.getId().getProduto().getNome());
					if (item.getValor() != null && item.getQuantidade() != null) {
						subtotalItem = item.getValor().multiply(new BigDecimal(item.getQuantidade())); //
						if (item.getDesconto() != null) {
							subtotalItem = subtotalItem.subtract(item.getDesconto());
						}
					}
				}
				itemDto.setQuantidade(item.getQuantidade());
				itemDto.setPrecoVenda(item.getValor());
				itemDto.setDesconto(item.getDesconto());
				itemDto.setSubtotal(subtotalItem);
				return itemDto;
			}).collect(Collectors.toList());
			dto.setItens(itemDTOs);

			totalCalculado = itemDTOs.stream().map(ItemPedidoResponseDTO::getSubtotal).filter(Objects::nonNull)
					.reduce(BigDecimal.ZERO, BigDecimal::add);
		}

		dto.setTotalPedido(totalCalculado);

		return dto;
	}
}