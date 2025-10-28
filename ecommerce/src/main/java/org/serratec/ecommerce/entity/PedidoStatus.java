package org.serratec.ecommerce.entity;

import org.serratec.ecommerce.exception.EnumValidationException;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum PedidoStatus {
	AGUARDANDO_PAGAMENTO("Aguardando Pagamento"), PAGAMENTO_APROVADO("Pagamento Aprovado"), ENVIADO("Enviado"),
	ENTREGUE("Entregue"), CANCELADO("Cancelado");

	private final String descricao;

	private PedidoStatus(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	@JsonCreator
	public static PedidoStatus verifica(String valorEntrada) throws EnumValidationException {
		if (valorEntrada == null) {
			throw new EnumValidationException("Status do pedido não pode ser nulo.");
		}
		for (PedidoStatus statusEnum : values()) {

			if (statusEnum.name().equalsIgnoreCase(valorEntrada)
					|| statusEnum.getDescricao().equalsIgnoreCase(valorEntrada)) {
				return statusEnum;
			}
		}
		throw new EnumValidationException(
				"Status '" + valorEntrada + "' é inválido. Valores aceitos (ou suas descrições) são: "
						+ "AGUARDANDO_PAGAMENTO, PAGAMENTO_APROVADO, ENVIADO, ENTREGUE, CANCELADO.");
	}

	@Override
	public String toString() {

		return this.descricao;
	}
}