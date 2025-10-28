package org.serratec.ecommerce.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType; 

@Embeddable
public class ItemPedidoPK implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_produto")
	private Produto produto;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_pedido")
	private Pedido pedido;

	
	public ItemPedidoPK() {
		super();
	}

	
	public ItemPedidoPK(Produto produto, Pedido pedido) {
		super();
		this.produto = produto;
		this.pedido = pedido;
	}


	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	
	@Override
	public int hashCode() {
	    return Objects.hash(pedido, produto);
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj)
	        return true;
	    if (obj == null)
	        return false;
	    if (getClass() != obj.getClass())
	        return false;
	    ItemPedidoPK other = (ItemPedidoPK) obj;
	   
	    return Objects.equals(pedido != null ? pedido.getId() : null, other.pedido != null ? other.pedido.getId() : null) &&
	           Objects.equals(produto != null ? produto.getId() : null, other.produto != null ? other.produto.getId() : null);
	}

	
	@Override
	public String toString() {
		return "ItemPedidoPK [pedidoId=" + (pedido != null ? pedido.getId() : "null") +
               ", produtoId=" + (produto != null ? produto.getId() : "null") + "]";
	}
}