package com.elingenio.Proyecto.Modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class CustomerData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int edad;
    private int frecuenciaCompra;
    private double valorTotalCompra;
    private int ultimaCompra;
    private String metodoPago;
    private String categoriaCliente;
}