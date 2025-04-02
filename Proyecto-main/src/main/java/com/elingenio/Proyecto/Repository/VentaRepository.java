package com.elingenio.Proyecto.Repository;

import com.elingenio.Proyecto.Modelo.OrderItem;
import com.elingenio.Proyecto.Modelo.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    // Para encontrar ventas por vendedor
    List<Venta> findByVendedorIdVendedor(Long idVendedor);

    // Para encontrar ítems de pedido por ID de venta
    @Query("SELECT oi FROM OrderItem oi WHERE oi.venta.idVenta = :ventaId")
    List<OrderItem> findOrderItemsByVentaId(@Param("ventaId") Long ventaId);
}