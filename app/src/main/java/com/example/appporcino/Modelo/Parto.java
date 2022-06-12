package com.example.appporcino.Modelo;

import java.io.Serializable;

public class Parto implements Serializable {
    private int id;
    private String fecha_parto;
    private String fecha_cruza;
    private String fecha_destete;
    private String semental;
    private int natural;
    private int exitoso;
    private int idhembra;
    private int lechones;
    private int vivos;
    private String notas;

    public Parto(int id, String fecha_parto, String fecha_cruza, String fecha_destete, String semental, int natural, int exitoso, int idhembra, int lechones, int vivos, String notas) {
        this.id = id;
        this.fecha_parto = fecha_parto;
        this.fecha_cruza = fecha_cruza;
        this.fecha_destete = fecha_destete;
        this.semental = semental;
        this.natural = natural;
        this.exitoso = exitoso;
        this.idhembra = idhembra;
        this.lechones = lechones;
        this.vivos = vivos;
        this.notas = notas;
    }
    public Parto(String fecha_parto, String fecha_cruza, String fecha_destete, String semental, int natural, int exitoso, int idhembra, int lechones, int vivos, String notas) {
        this.id = -1;
        this.fecha_parto = fecha_parto;
        this.fecha_cruza = fecha_cruza;
        this.fecha_destete = fecha_destete;
        this.semental = semental;
        this.natural = natural;
        this.exitoso = exitoso;
        this.idhembra = idhembra;
        this.lechones = lechones;
        this.vivos = vivos;
        this.notas = notas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha_parto() {
        return fecha_parto;
    }

    public void setFecha_parto(String fecha_parto) {
        this.fecha_parto = fecha_parto;
    }

    public String getFecha_cruza() {
        return fecha_cruza;
    }

    public void setFecha_cruza(String fecha_cruza) {
        this.fecha_cruza = fecha_cruza;
    }

    public String getFecha_destete() {
        return fecha_destete;
    }

    public void setFecha_destete(String fecha_destete) {
        this.fecha_destete = fecha_destete;
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

    public int getExitoso() {
        return exitoso;
    }

    public void setExitoso(int exitoso) {
        this.exitoso = exitoso;
    }

    public int getIdhembra() {
        return idhembra;
    }

    public void setIdhembra(int idhembra) {
        this.idhembra = idhembra;
    }

    public int getLechones() {
        return lechones;
    }

    public void setLechones(int lechones) {
        this.lechones = lechones;
    }

    public int getVivos() {
        return vivos;
    }

    public void setVivos(int vivos) {
        this.vivos = vivos;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
}
