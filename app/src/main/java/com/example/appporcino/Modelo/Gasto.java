package com.example.appporcino.Modelo;

import java.io.Serializable;

public class Gasto implements Serializable {
    private long id;
    private String concepto;
    private String fecha_pago;
    private double monto;
    private String descripcion;

    public Gasto(long id, String concepto, String fecha_pago, double monto, String descripcion) {
        this.id = id;
        this.concepto = concepto;
        this.fecha_pago = fecha_pago;
        this.monto = monto;
        this.descripcion = descripcion;
    }
    public Gasto(String concepto, String fecha_pago, double monto, String descripcion) {
        this.id = -1;
        this.concepto = concepto;
        this.fecha_pago = fecha_pago;
        this.monto = monto;
        this.descripcion = descripcion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(String fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
