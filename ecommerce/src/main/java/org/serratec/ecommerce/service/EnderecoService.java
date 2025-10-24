package org.serratec.ecommerce.service;

import org.serratec.ecommerce.entity.Endereco;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EnderecoService {

	private final RestTemplate restTemplate = new RestTemplate();
	
	public Endereco buscarEnderecoPorCep(String cep) {
		String url = "https://viacep.com.br/ws" + cep + "/json/";
		return restTemplate.getForObject(url, Endereco.class);
	}
}
