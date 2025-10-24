package org.serratec.ecommerce.service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.serratec.ecommerce.dto.ClienteResponseDTO;
import org.serratec.ecommerce.dto.ItemPedidoRequestDTO;
import org.serratec.ecommerce.dto.ItemPedidoResponseDTO;
import org.serratec.ecommerce.dto.PedidoRequestDTO;
import org.serratec.ecommerce.dto.PedidoResponseDTO;
import org.serratec.ecommerce.entity.Cliente;
import org.serratec.ecommerce.entity.ItemPedido;
import org.serratec.ecommerce.entity.ItemPedidoPK;
import org.serratec.ecommerce.entity.Pedido;
import org.serratec.ecommerce.entity.PedidoStatus;
import org.serratec.ecommerce.entity.Produto;
import org.serratec.ecommerce.exception.ResourceNotFoundException;
import org.serratec.ecommerce.repository.ClienteRepository;
import org.serratec.ecommerce.repository.ItemPedidoRepository;
import org.serratec.ecommerce.repository.PedidoRepository;
import org.serratec.ecommerce.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 
@Service
public class PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ItemPedidoRepository itemPedidoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    ProdutoService produtoService;

    @Autowired
    MailService mailService; 

    @Transactional(readOnly = true) 
    public PedidoResponseDTO buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + id));
        return mapToResponseDTO(pedido); 
    }

    @Transactional(readOnly = true) 
    public Page<ItemPedido> buscarPorTotal(Double valorMinimo, Double valorMaximo, Pageable pageable) {
        return itemPedidoRepository.findByValorBetween(valorMinimo, valorMaximo, pageable);
    }

    @Transactional 
    public PedidoResponseDTO inserir(PedidoRequestDTO pedidoRequestDTO) throws Exception {

        if (pedidoRequestDTO.getClienteId() == null || pedidoRequestDTO.getItens() == null || pedidoRequestDTO.getItens().isEmpty()) {
            throw new IllegalArgumentException("Pedido inválido: cliente e itens são obrigatórios."); 
        }

        Cliente cliente = clienteRepository.findById(pedidoRequestDTO.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + pedidoRequestDTO.getClienteId()));

        Pedido pedido = new Pedido();
        pedido.setClienteID(cliente); 
        pedido.setStatus(PedidoStatus.AGUARDANDO_PAGAMENTO);
       

        Set<ItemPedido> itensPedido = new HashSet<>();
        BigDecimal totalPedido = BigDecimal.ZERO;

        for (ItemPedidoRequestDTO itemDTO : pedidoRequestDTO.getItens()) {
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                 .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + itemDTO.getProdutoId()));

            if (itemDTO.getQuantidade() == null || itemDTO.getQuantidade() <= 0) {
                 throw new IllegalArgumentException("Quantidade inválida para o produto ID: " + produto.getId());
            }

            ItemPedido itemPedido = new ItemPedido();
            ItemPedidoPK pk = new ItemPedidoPK();
            pk.setPedido(pedido);
            pk.setProduto(produto);
            itemPedido.setId(pk);

            itemPedido.setQuantidade(itemDTO.getQuantidade());
            itemPedido.setValor(produto.getPreco()); 
          

            
            BigDecimal subtotalItem = produto.getPreco().multiply(new BigDecimal(itemDTO.getQuantidade()));
          

            totalPedido = totalPedido.add(subtotalItem);

            itensPedido.add(itemPedido);
        }
        pedido.setItens(itensPedido);
       

        pedido = pedidoRepository.save(pedido);

        

        return mapToResponseDTO(pedido);
    }

    @Transactional 
    public PedidoResponseDTO atualizar(Long id, PedidoRequestDTO pedidoRequestDTO) {
        Pedido pedidoExistente = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + id));

       

        pedidoExistente = pedidoRepository.save(pedidoExistente);
        return mapToResponseDTO(pedidoExistente);
    }

     @Transactional
    public void deletar(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pedido não encontrado com ID: " + id);
        }
        pedidoRepository.deleteById(id);
    }

    private PedidoResponseDTO mapToResponseDTO(Pedido pedido) {
        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setId(pedido.getId());
        dto.setStatus(pedido.getStatus());

        if (pedido.getClienteID() != null) {
            ClienteResponseDTO clienteDTO = new ClienteResponseDTO(pedido.getClienteID());
            dto.setCliente(clienteDTO);
        }

        if (pedido.getItens() != null) {
            List<ItemPedidoResponseDTO> itemDTOs = pedido.getItens().stream()
                .map(item -> {
                    ItemPedidoResponseDTO itemDto = new ItemPedidoResponseDTO();
                    if (item.getId() != null && item.getId().getProduto() != null) {
                        itemDto.setProdutoId(item.getId().getProduto().getId());
                        itemDto.setProdutoNome(item.getId().getProduto().getNome());
                    }
                    itemDto.setQuantidade(item.getQuantidade());
                    itemDto.setPrecoVenda(item.getValor());
                    itemDto.setDesconto(item.getDesconto());
                    BigDecimal subtotal = item.getValor().multiply(new BigDecimal(item.getQuantidade()));
                    if(item.getDesconto() != null) {
                         subtotal = subtotal.subtract(item.getDesconto());
                    }
                    itemDto.setSubtotal(subtotal);
                    return itemDto;
                })
                .collect(Collectors.toList());
            dto.setItens(itemDTOs);
        }

         BigDecimal totalCalculado = dto.getItens().stream()
                                     .map(ItemPedidoResponseDTO::getSubtotal)
                                     .reduce(BigDecimal.ZERO, BigDecimal::add);
         dto.setTotalPedido(totalCalculado);


        return dto;
    }
}