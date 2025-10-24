package org.serratec.ecommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.ecommerce.dto.ClienteRequestDTO;
import org.serratec.ecommerce.dto.ClienteResponseDTO;
import org.serratec.ecommerce.entity.Cliente;
import org.serratec.ecommerce.entity.Endereco;
import org.serratec.ecommerce.exception.ResourceNotFoundException;
import org.serratec.ecommerce.repository.ClienteRepository;
import org.serratec.ecommerce.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private EnderecoService enderecoService;

    @Transactional
    public ClienteResponseDTO criarCliente(ClienteRequestDTO dto) {
        

        Endereco endereco = enderecoService.buscarEnderecoPorCep(dto.getCep());
       

        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefone(dto.getTelefone());
        cliente.setCpf(dto.getCpf());
        cliente.setEndereco(endereco);

        cliente = clienteRepository.save(cliente);

        mailService.enviarEmail(dto.getEmail(), "Cadastro realizado com sucesso!", "Olá " + dto.getNome() +
                ", seu cadastro foi realizado com sucesso!");

        return new ClienteResponseDTO(cliente);
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id)); // Usar exceção customizada

        return new ClienteResponseDTO(cliente);
    }

    @Transactional
    public void deletar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente não encontrado com ID: " + id);
        }
        
        clienteRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarTodos() {
        return clienteRepository.findAll()
                .stream()
                .map(ClienteResponseDTO::new)
                .collect(Collectors.toList());
    }
}