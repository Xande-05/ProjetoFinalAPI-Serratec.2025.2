package org.serratec.ecommerce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "item_pedido")
public class ItemPedido {

	@EmbeddedId
	private ItemPedidoPK id = new ItemPedidoPK();
	
	@NotNull(message = "Necessário atribuir valor ao Pedido")
	@Column(nullable = false)
	private Double valor;
	
	@Column
	private Double desconto;
	
	@Column
	private Double quantidade;

	public ItemPedido() {
		super();
	}

	public ItemPedido(ItemPedidoPK id, @NotNull(message = "Necessário atribuir valor ao Pedido") Double valor,
			Double desconto, Double quantidade) {
		super();
		this.id = id;
		this.valor = valor;
		this.desconto = desconto;
		this.quantidade = quantidade;
	}

	public ItemPedidoPK getId() {
		return id;
	}

	public void setId(ItemPedidoPK id) {
		this.id = id;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}
	
	
}
