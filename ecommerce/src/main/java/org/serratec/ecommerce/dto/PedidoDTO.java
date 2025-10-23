package org.serratec.ecommerce.dto;

import java.util.List;
import java.util.Objects;

import org.serratec.ecommerce.entity.Cliente;
import org.serratec.ecommerce.entity.ItemPedido;
import org.serratec.ecommerce.entity.PedidoStatus;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

public class PedidoDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	@Enumerated(EnumType.STRING)
	private PedidoStatus status;
	
	@OneToOne
	@JoinColumn(name = "id_cliente" )
	private Cliente clienteID;
	
	
	
	
	public PedidoDTO(Long id, PedidoStatus status, Cliente clienteID, List<ItemPedido> itens) {
		super();
		this.id = id;
		this.status = status;
		this.clienteID = clienteID;
		this.itens = itens;
	}


	public PedidoDTO() {
		super();
	}


	private List<ItemPedido> itens;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public PedidoStatus getStatus() {
		return status;
	}


	public void setStatus(PedidoStatus status) {
		this.status = status;
	}


	public Cliente getCliente() {
		return clienteID;
	}


	public void setCliente(Cliente cliente) {
		this.clienteID = cliente;
	}


	public List<ItemPedido> getItens() {
		return itens;
	}


	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}
	

	


	@Override
	public int hashCode() {
		return Objects.hash(clienteID, id, itens, status);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PedidoDTO other = (PedidoDTO) obj;
		return Objects.equals(clienteID, other.clienteID) && Objects.equals(id, other.id)
				&& Objects.equals(itens, other.itens) && status == other.status;
	}


	@Override
	public String toString() {
		return "PedidoRequestDTO [id=" + id + ", itens=" + itens + ", getId()=" + getId() + ", getItens()=" + getItens()
				+ ", hashCode()=" + hashCode() + ", getClass()=" + getClass() + ", toString()=" + super.toString()
				+ "]";
	}
	
}
