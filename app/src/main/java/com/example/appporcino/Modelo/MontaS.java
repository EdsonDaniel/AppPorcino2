package com.example.appporcino.Modelo;

public class MontaS {
    private int id;
    private int iddemental;
    private String fecha_monta;
    private String cliente;
    private double monto;
    private int natural;
    private String notas;
    private String hembra;

    public MontaS(int id, int iddemental, String fecha_monta, String cliente, double monto, int natural, String notas,String hembra) {
        this.id = id;
        this.iddemental = iddemental;
        this.fecha_monta = fecha_monta;
        this.cliente = cliente;
        this.monto = monto;
        this.natural = natural;
        this.notas = notas;
        this.hembra = hembra;
    }
    public MontaS(int iddemental, String fecha_monta, String cliente, double monto, int natural, String notas,String hembra) {
        this.id = -1;
        this.iddemental = iddemental;
        this.fecha_monta = fecha_monta;
        this.cliente = cliente;
        this.monto = monto;
        this.natural = natural;
        this.notas = notas;
        this.hembra = hembra;
    }

    public String getHembra() {
        return hembra;
    }

    public void setHembra(String hembra) {
        this.hembra = hembra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIddemental() {
        return iddemental;
    }

    public void setIddemental(int iddemental) {
        this.iddemental = iddemental;
    }

    public String getFecha_monta() {
        return fecha_monta;
    }

    public void setFecha_monta(String fecha_monta) {
        this.fecha_monta = fecha_monta;
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

    public int getNatural() {
        return natural;
    }

    public void setNatural(int natural) {
        this.natural = natural;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
}
