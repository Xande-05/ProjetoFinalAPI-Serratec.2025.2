package org.serratec.ecommerce.dto;

import java.util.List;

import jakarta.validation.Valid; 
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class PedidoRequestDTO {

    @NotNull(message = "O ID do cliente é obrigatório para a criação do pedido.")
    private Long clienteId;

    @NotEmpty(message = "O pedido deve conter pelo menos um item.")
    @Valid 
    private List<ItemPedidoRequestDTO> itens;
    
    private String cupom;

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<ItemPedidoRequestDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoRequestDTO> itens) {
        this.itens = itens;
    }

	public String getCupom() {
		return cupom;
	}

	public void setCupom(String cupom) {
		this.cupom = cupom;
	}
    
}