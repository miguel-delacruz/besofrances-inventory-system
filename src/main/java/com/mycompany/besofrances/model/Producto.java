package com.mycompany.besofrances.model;

public class Producto {
    private int idProducto;
    private String nombreProducto;
    private int idCategoria;
    private String nombreCategoria;
    private int idDetalle;
    private String fechaIngreso;
    private String fechaVencimiento;
    private String estado;
    private int cantidad;
    
    public Producto() {}
    
    public Producto(int idProducto, String nombreProducto, int idCategoria, String nombreCategoria) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
    }
    
    public Producto(int idProducto, String nombreProducto, String nombreCategoria, 
                   int idDetalle, String fechaIngreso, String fechaVencimiento, 
                   String estado, int cantidad) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.nombreCategoria = nombreCategoria;
        this.idDetalle = idDetalle;
        this.fechaIngreso = fechaIngreso;
        this.fechaVencimiento = fechaVencimiento;
        this.estado = estado;
        this.cantidad = cantidad;
    }
    
    public int getIdProducto() {
        return idProducto;
    }
    
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }
    
    public String getNombreProducto() {
        return nombreProducto;
    }
    
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }
    
    public int getIdCategoria() {
        return idCategoria;
    }
    
    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
    
    public String getNombreCategoria() {
        return nombreCategoria;
    }
    
    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
    
    public int getIdDetalle() {
        return idDetalle;
    }
    
    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }
    
    public String getFechaIngreso() {
        return fechaIngreso;
    }
    
    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    
    public String getFechaVencimiento() {
        return fechaVencimiento;
    }
    
    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public int getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    @Override
    public String toString() {
        return "Producto{" + "idProducto=" + idProducto + ", nombreProducto=" + nombreProducto + 
               ", nombreCategoria=" + nombreCategoria + ", idDetalle=" + idDetalle + 
               ", fechaIngreso=" + fechaIngreso + ", fechaVencimiento=" + fechaVencimiento + 
               ", estado=" + estado + ", cantidad=" + cantidad + '}';
    }
} 