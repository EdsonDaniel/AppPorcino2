package com.example.appporcino.Modelo;

import java.io.Serializable;

public class Venta implements Serializable {
    private int id;
    private String fecha_venta;
    private String cliente;
    private double monto;
    private String notas;
    private int ejemplares;

    public Venta(String fecha_venta, String cliente, double monto, String notas) {
        this.id = -1;
        this.ejemplares = 0;
        this.fecha_venta = fecha_venta;
        this.cliente = cliente;
        this.monto = monto;
        this.notas = notas;
    }
    public Venta(int id, String fecha_venta, String cliente, double monto, String notas, int ejemplares) {
        this.id = id;
        this.fecha_venta = fecha_venta;
        this.cliente = cliente;
        this.monto = monto;
        this.notas = notas;
        this.ejemplares = ejemplares;
    }

    public int getEjemplares() {
        return ejemplares;
    }

    public void setEjemplares(int ejemplares) {
        this.ejemplares = ejemplares;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha_venta() {
        return fecha_venta;
    }

    public void setFecha_venta(String fecha_venta) {
        this.fecha_venta = fecha_venta;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
}
