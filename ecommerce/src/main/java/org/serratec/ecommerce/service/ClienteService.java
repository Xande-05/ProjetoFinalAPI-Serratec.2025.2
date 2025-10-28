package org.serratec.ecommerce.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.serratec.ecommerce.dto.ClienteRequestDTO;
import org.serratec.ecommerce.dto.ClienteResponseDTO;
import org.serratec.ecommerce.entity.Cliente;
import org.serratec.ecommerce.entity.Endereco;
import org.serratec.ecommerce.exception.EmailException;
import org.serratec.ecommerce.exception.ResourceNotFoundException;
import org.serratec.ecommerce.repository.ClienteRepository;
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

    @Autowired
    private UsuarioService usuarioService;

    @Transactional
    public ClienteResponseDTO criarCliente(ClienteRequestDTO dto) {

        Endereco endereco = enderecoService.buscarEnderecoPorCep(dto.getCep()); //

        Cliente cliente = new Cliente(); 
        cliente.setNome(dto.getNome()); 
        cliente.setEmail(dto.getEmail()); 
        cliente.setTelefone(dto.getTelefone()); 
        cliente.setCpf(dto.getCpf());
        cliente.setEndereco(endereco); 

        try {
            usuarioService.criarUsuarioParaCliente(cliente.getNome(), cliente.getEmail()); 
        } catch (RuntimeException e) {
             throw new EmailException("Não foi possível criar o usuário associado: " + e.getMessage(), e); 
        }

        cliente = clienteRepository.save(cliente); 

        try {
            mailService.enviarEmail(cliente.getEmail(), "Cadastro E-commerce Serratec Realizado", 
                "Olá " + cliente.getNome() + ",\n\nSeu cadastro em nosso E-commerce foi realizado com sucesso!\n\n" + 
                "Seus dados:\n" + //
                "Nome: " + cliente.getNome() + "\n" + 
                "Email: " + cliente.getEmail() + "\n" + 
                "Telefone: " + cliente.getTelefone() + "\n" + 
                "CPF: " + cliente.getCpf() + "\n\n" + 
                "Endereço:\n" + //
                "CEP: " + endereco.getCep() + "\n" + 
                "Logradouro: " + endereco.getLogradouro() + "\n" + 
                (endereco.getComplemento() != null ? "Complemento: " + endereco.getComplemento() + "\n" : "") + 
                "Bairro: " + endereco.getBairro() + "\n" + 
                "Localidade: " + endereco.getLocalidade() + " - " + endereco.getUf() + "\n\n" + 
                "Atenciosamente,\nEquipe E-commerce Serratec"); 
        } catch (Exception e) {
            System.err.println("Falha ao enviar email de confirmação para " + cliente.getEmail() + ": " + e.getMessage()); //
        }

        return new ClienteResponseDTO(cliente); 
    }

 
    @Transactional
    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dto) {

        Cliente cliente = clienteRepository.findById(id) 
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id)); 


        Endereco endereco = enderecoService.buscarEnderecoPorCep(dto.getCep()); 

    
        cliente.setNome(dto.getNome()); 
        cliente.setTelefone(dto.getTelefone()); 
        cliente.setCpf(dto.getCpf()); 
        cliente.setEndereco(endereco); 

        if (!cliente.getEmail().equalsIgnoreCase(dto.getEmail())) { 
           
            cliente.setEmail(dto.getEmail()); 
          
        }

    
        cliente = clienteRepository.save(cliente); 

         try {
             mailService.enviarEmail(cliente.getEmail(), "Seu Cadastro Foi Atualizado", 
                 "Olá " + cliente.getNome() + ",\n\nInformamos que seus dados cadastrais foram atualizados em nosso sistema.\n\n" + 
                 "Atenciosamente,\nEquipe E-commerce Serratec"); 
         } catch (Exception e) {
             System.err.println("Falha ao enviar email de atualização para " + cliente.getEmail() + ": " + e.getMessage()); 
           
         }

        return new ClienteResponseDTO(cliente); 
    }



    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id)); 
        return new ClienteResponseDTO(cliente); 
    }

    @Transactional
    public void deletar(Long id) {
        if (!clienteRepository.existsById(id)) { 
            throw new ResourceNotFoundException("Cliente não encontrado com ID: " + id); 
        }

        try {
             clienteRepository.deleteById(id); 

        } catch (Exception e) {
             System.err.println("Erro ao deletar cliente ID " + id + ": " + e.getMessage()); 
            
             throw new RuntimeException("Não foi possível excluir o cliente. Verifique se existem pedidos associados.", e); //
        }

    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarTodos() {
        return clienteRepository.findAll() 
                .stream() //
                .map(ClienteResponseDTO::new) 
                .collect(Collectors.toList()); 
    }
}