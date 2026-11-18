package com.reusa.modelo;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Articulo {
    private final int id;
    private final String nombre;
    private final String categoria;
    private final String descripcion;
    private final BigDecimal precio;
    private final String estado;
    private final int vendedorId;
    private final String vendedor;
    private final boolean vendido;
    private final Timestamp fechaPublicacion;

    public Articulo(int id, String nombre, String categoria, String descripcion,
            BigDecimal precio, String estado, int vendedorId, String vendedor,
            boolean vendido, Timestamp fechaPublicacion) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.precio = precio;
        this.estado = estado;
        this.vendedorId = vendedorId;
        this.vendedor = vendedor;
        this.vendido = vendido;
        this.fechaPublicacion = fechaPublicacion;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public String getDescripcion() { return descripcion; }
    public BigDecimal getPrecio() { return precio; }
    public String getEstado() { return estado; }
    public int getVendedorId() { return vendedorId; }
    public String getVendedor() { return vendedor; }
    public boolean isVendido() { return vendido; }
    public Timestamp getFechaPublicacion() { return fechaPublicacion; }
}
