package org.serratec.ecommerce.dto;

import java.math.BigDecimal;

public class ItemPedidoResponseDTO {

	private Long produtoId;
	private String produtoNome;
	private Integer quantidade;
	private BigDecimal precoVenda;
	private BigDecimal desconto;
	private BigDecimal subtotal;

	public ItemPedidoResponseDTO() {
	}

	public ItemPedidoResponseDTO(Long produtoId, String produtoNome, Integer quantidade, BigDecimal precoVenda,
			BigDecimal desconto, BigDecimal subtotal) {
		this.produtoId = produtoId;
		this.produtoNome = produtoNome;
		this.quantidade = quantidade;
		this.precoVenda = precoVenda;
		this.desconto = desconto;
		this.subtotal = subtotal;
	}

	public Long getProdutoId() {
		return produtoId;
	}

	public String getProdutoNome() {
		return produtoNome;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public BigDecimal getPrecoVenda() {
		return precoVenda;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}

	public void setProdutoNome(String produtoNome) {
		this.produtoNome = produtoNome;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public void setPrecoVenda(BigDecimal precoVenda) {
		this.precoVenda = precoVenda;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}
}
