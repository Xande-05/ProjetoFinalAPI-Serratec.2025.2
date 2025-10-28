package org.serratec.ecommerce.repository;

import java.util.Optional;

import org.serratec.ecommerce.entity.ItemPedido;
import org.serratec.ecommerce.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	//Querys 
	@Query(value = "SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE p.id = :id")
	Optional<Pedido> findByIdWithCliente(@Param("id") Long id);

	@Query(value = "SELECT * FROM item_pedido WHERE valor_total BETWEEN :minimo AND :maximo", nativeQuery = true)
	ItemPedido buscarPorTotalNativo(@Param("minimo") Double valorMinimo, @Param("maximo") Double valorMaximo);
}
