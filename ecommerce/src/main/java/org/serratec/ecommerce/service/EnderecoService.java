package org.serratec.ecommerce.service;

import org.serratec.ecommerce.entity.Endereco;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EnderecoService {

	private final RestTemplate restTemplate = new RestTemplate();
	
	public Endereco buscarEnderecoPorCep(String cep) {
	    String url = "https://viacep.com.br/ws/" + cep + "/json/";
	    Endereco endereco = restTemplate.getForObject(url, Endereco.class);

	    if (endereco == null || endereco.getCep() == null) {
	        throw new RuntimeException("CEP inválido ou não encontrado!");
	    }

	    return endereco;
	}

}
