package org.serratec.ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.serratec.ecommerce.entity.PedidoStatus;

public class PedidoResponseDTO {

	private Long id;
	private LocalDateTime dataPedido;
	private PedidoStatus status;
	private ClienteResponseDTO cliente;
	private List<ItemPedidoResponseDTO> itens; 
	private BigDecimal totalPedido;

	
	public PedidoResponseDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(LocalDateTime dataPedido) {
		this.dataPedido = dataPedido;
	}

	public PedidoStatus getStatus() {
		return status;
	}

	public void setStatus(PedidoStatus status) {
		this.status = status;
	}

	public ClienteResponseDTO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteResponseDTO cliente) {
		this.cliente = cliente;
	}

	public List<ItemPedidoResponseDTO> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedidoResponseDTO> itens) {
		this.itens = itens;
	}

	public BigDecimal getTotalPedido() {
		return totalPedido;
	}

	public void setTotalPedido(BigDecimal totalPedido) {
		this.totalPedido = totalPedido;
	}
}