package com.example.appporcino.Modelo;

public class MontasH {
    private int id;
    private int idhembra;
    private String fecha_monta;
    private String semental;
    private int natural;
    private String notas;

    public MontasH(int id, int idhembra, String fecha_monta, String semental, int natural, String notas) {
        this.id = id;
        this.idhembra = idhembra;
        this.fecha_monta = fecha_monta;
        this.semental = semental;
        this.natural = natural;
        this.notas = notas;
    }

    public MontasH(int idhembra, String fecha_monta, String semental, int natural, String notas) {
        this.id = -1;
        this.idhembra = idhembra;
        this.fecha_monta = fecha_monta;
        this.semental = semental;
        this.natural = natural;
        this.notas = notas;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdhembra() {
        return idhembra;
    }

    public void setIdhembra(int idhembra) {
        this.idhembra = idhembra;
    }

    public String getFecha_monta() {
        return fecha_monta;
    }

    public void setFecha_monta(String fecha_monta) {
        this.fecha_monta = fecha_monta;
    }

    public String getSemental() {
        return semental;
    }

    public void setSemental(String semental) {
        this.semental = semental;
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
