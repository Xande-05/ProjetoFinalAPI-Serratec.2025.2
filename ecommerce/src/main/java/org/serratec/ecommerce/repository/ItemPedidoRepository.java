package org.serratec.ecommerce.repository;

import org.serratec.ecommerce.entity.ItemPedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long>{

	Page<ItemPedido>findByValorBetween(Double valorMinimo, Double valorMaximo, Pageable pageable);

}
