package com.example.appporcino.Modelo;

public class Detalle_venta {
    private int idventa;
    private double monto;
    private int idporcino;

    public Detalle_venta(int idventa, double monto, int idporcino) {
        this.idventa = idventa;
        this.monto = monto;
        this.idporcino = idporcino;
    }

    public int getIdventa() {
        return idventa;
    }

    public void setIdventa(int idventa) {
        this.idventa = idventa;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public int getIdporcino() {
        return idporcino;
    }

    public void setIdporcino(int idporcino) {
        this.idporcino = idporcino;
    }
}
