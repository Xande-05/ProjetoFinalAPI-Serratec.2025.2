package org.serratec.ecommerce.service;

import java.util.Optional; 
import org.serratec.ecommerce.entity.Endereco;
import org.serratec.ecommerce.exception.CepNotFoundException; 
import org.serratec.ecommerce.repository.EnderecoRepository; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException; 
import org.springframework.web.client.RestTemplate;
import org.springframework.transaction.annotation.Transactional; 
@Service
public class EnderecoService {


	private final RestTemplate restTemplate = new RestTemplate();

	@Autowired
	private EnderecoRepository enderecoRepository; 

	
	@Transactional
	public Endereco buscarEnderecoPorCep(String cep) {	  
	    String cepNormalizado = cep.replaceAll("[^0-9]", "");
	    if (cepNormalizado.length() != 8) {
	        throw new CepNotFoundException(cep); 
	    }

	    
	    Optional<Endereco> enderecoLocal = enderecoRepository.findByCep(cepNormalizado);
	    if (enderecoLocal.isPresent()) {
	        return enderecoLocal.get(); 
	    }

	  
	    String url = "https://viacep.com.br/ws/" + cepNormalizado + "/json/";
	    Endereco enderecoViaCep = null;
	    try {
	        enderecoViaCep = restTemplate.getForObject(url, Endereco.class);
	    } catch (HttpClientErrorException.BadRequest e) { 
	        throw new CepNotFoundException(cep, e);
	    } catch (Exception e) { 	        
	        throw new RuntimeException("Erro ao consultar serviço externo ViaCEP.", e);
	    }


	   
	    if (enderecoViaCep == null || enderecoViaCep.getCep() == null) {	         
	        throw new CepNotFoundException(cep); 
	    }

        // Normaliza o CEP retornado pela API (pode vir com traço)
        enderecoViaCep.setCep(enderecoViaCep.getCep().replaceAll("[^0-9]", ""));

	    
	    return enderecoRepository.save(enderecoViaCep);
	}

}