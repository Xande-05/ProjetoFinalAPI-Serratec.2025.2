package org.serratec.ecommerce.dto;

import java.util.List;
import java.util.Objects;

import org.serratec.ecommerce.entity.Cliente;
import org.serratec.ecommerce.entity.ItemPedido;
import org.serratec.ecommerce.entity.Pedido;
import org.serratec.ecommerce.entity.PedidoStatus;


public class PedidoDTO {

    private Long id;
    private PedidoStatus status;
    private Cliente cliente; 
    private List<ItemPedido> itens; 

   
    public PedidoDTO() {
        super();
    }

     
    public PedidoDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.status = pedido.getStatus();
        this.cliente = pedido.getCliente(); 
        
    }


    public PedidoDTO(Long id, PedidoStatus status, Cliente cliente, List<ItemPedido> itens) {
        super();
        this.id = id;
        this.status = status;
        this.cliente = cliente;
        this.itens = itens;
    }

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
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PedidoDTO other = (PedidoDTO) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "PedidoDTO [id=" + id + ", status=" + status + "]";
    }
}