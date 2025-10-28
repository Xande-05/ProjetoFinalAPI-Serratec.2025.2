package org.serratec.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CepNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L; 

    public CepNotFoundException(String cep) {
        super("CEP '" + cep + "' não encontrado ou inválido.");
    }

    public CepNotFoundException(String cep, Throwable cause) {
        super("CEP '" + cep + "' não encontrado ou inválido.", cause);
    }
}