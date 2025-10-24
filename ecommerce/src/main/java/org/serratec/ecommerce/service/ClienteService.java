package org.serratec.ecommerce.service;

import java.util.List;

import org.serratec.ecommerce.dto.ClienteRequestDTO;
import org.serratec.ecommerce.dto.ClienteResponseDTO;
import org.serratec.ecommerce.entity.Cliente;
import org.serratec.ecommerce.entity.Endereco;
import org.serratec.ecommerce.repository.ClienteRepository;
import org.serratec.ecommerce.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private EnderecoService enderecoService;
	
	public ClienteResponseDTO criarCliente (ClienteRequestDTO dto) {
		Endereco endereco = enderecoService.buscarEnderecoPorCep(dto.getCep());
		Cliente cliente = new Cliente();
		cliente.setNome(dto.getNome());
		cliente.setEmail(dto.getEmail());
		cliente.setTelefone(dto.getTelefone());
		cliente.setCpf(dto.getCpf());
		cliente.setEndereco(endereco);
		
		clienteRepository.save(cliente);
		
		mailService.enviarEmail(dto.getEmail(), "Cadastro realizado com sucesso!", "Olá " + dto.getNome() + 
				", seu cadastro foi realizadfo com sucesso!");
		
		return new ClienteResponseDTO(cliente);
	}
	
	public ClienteResponseDTO buscarPorId(Long id) {
	    Cliente cliente = clienteRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + id));

	    return new ClienteResponseDTO(cliente);
	}

	public void deletar(Long id) {
	    if (!clienteRepository.existsById(id)) {
	        throw new RuntimeException("Cliente não encontrado com ID: " + id);
	    }
	    clienteRepository.deleteById(id);
	}

	
	public List<ClienteResponseDTO> listarTodos() {
        return clienteRepository.findAll()
                .stream()
                .map(ClienteResponseDTO::new)
                .toList();
    }
}
