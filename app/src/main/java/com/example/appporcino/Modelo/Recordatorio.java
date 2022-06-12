package com.example.appporcino.Modelo;

import java.io.Serializable;

public class Recordatorio implements Serializable {
    private int id;
    private String fecha;
    private String titulo;
    private String descripcion;
    private String hora;

    public Recordatorio(int id, String fecha, String titulo, String descripcion, String hora) {
        this.id = id;
        this.fecha = fecha;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.hora = hora;
    }
    public Recordatorio(String fecha, String titulo, String descripcion, String hora) {
        this.id = -1;
        this.fecha = fecha;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.hora = hora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
