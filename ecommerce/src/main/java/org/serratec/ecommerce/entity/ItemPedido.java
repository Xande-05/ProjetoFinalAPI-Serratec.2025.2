package org.serratec.ecommerce.entity;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "item_pedido")
public class ItemPedido {

	@EmbeddedId
	private ItemPedidoPK id = new ItemPedidoPK();

	@NotNull(message = "Necessário atribuir valor ao Pedido")
	@Column(nullable = false)
	private BigDecimal valor;

	@Column
	private BigDecimal desconto;

	@Column
	private Integer quantidade;

	
	public ItemPedido() {
		super();
	}

	
	public ItemPedido(ItemPedidoPK id, BigDecimal valor, BigDecimal desconto, Integer quantidade) {
		super();
		this.id = id;
		this.valor = valor;
		this.desconto = desconto;
		this.quantidade = quantidade;
	}

	// Getters e Setters
	public ItemPedidoPK getId() {
		return id;
	}

	public void setId(ItemPedidoPK id) {
		this.id = id;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id); 
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemPedido other = (ItemPedido) obj;
		return Objects.equals(id, other.id); 
	}

	
	@Override
	public String toString() {
		return "ItemPedido [id=" + id + ", valor=" + valor + ", quantidade=" + quantidade + "]";
	}
}