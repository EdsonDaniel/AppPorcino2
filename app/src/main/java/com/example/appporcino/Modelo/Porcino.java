package com.example.appporcino.Modelo;

import android.content.Context;

import com.example.appporcino.data.OperacionesDB;
import com.example.appporcino.data.Validacion;

import org.threeten.bp.LocalDate;
import org.threeten.bp.chrono.ChronoLocalDate;
import org.threeten.bp.chrono.ChronoPeriod;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.temporal.ChronoUnit;

import java.io.Serializable;
import java.util.Formatter;
import java.util.Locale;

public class Porcino implements Serializable {
    protected String nombre;
    protected String fecha_nac;
    protected String tipo;
    protected String foto_url;
    protected String genero;
    protected String raza;
    protected String descripcion;
    protected String fecha_compra;
    protected String fecha_venta;
    protected double costo;
    protected String estado;
    protected double peso;
    protected int numero;
    protected String edad;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    protected long id;

    public Porcino(String nombre, String fecha_nac, String tipo,
                   String foto_url, String genero, String raza,
                   String descripcion, String fecha_compra, String fecha_venta,
                   double peso, double costo, String estado,int numero) {
        this.nombre = nombre;
        this.fecha_nac = fecha_nac;
        this.tipo = tipo;
        this.foto_url = foto_url;
        this.genero = genero;
        this.raza = raza;
        this.descripcion = descripcion;
        this.fecha_compra = fecha_compra;
        this.fecha_venta = fecha_venta;
        this.peso = peso;
        this.costo = costo;
        this.estado = estado;
        this.numero = numero;
        edad = calcularEdad(fecha_nac);
        id = -1;
    }
    public Porcino(String nombre, String fecha_nac, String tipo,
                   String foto_url, String genero, String raza,
                   String descripcion, String fecha_compra, String fecha_venta,
                   double peso, double costo, String estado,int numero,long id) {
        this.nombre = nombre;
        this.fecha_nac = fecha_nac;
        this.tipo = tipo;
        this.foto_url = foto_url;
        this.genero = genero;
        this.raza = raza;
        this.descripcion = descripcion;
        this.fecha_compra = fecha_compra;
        this.fecha_venta = fecha_venta;
        this.peso = peso;
        this.costo = costo;
        this.estado = estado;
        this.numero = numero;
        edad = calcularEdad(fecha_nac);
        this.id = id;
    }
    private String calcularEdad(String fecha_n){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy",
                Locale.getDefault());
        LocalDate now = LocalDate.now();
        ChronoLocalDate from = ChronoLocalDate.from(formatter.parse(fecha_n));
        ChronoLocalDate to = ChronoLocalDate.from(now);
        ChronoPeriod period = ChronoPeriod.between(from, to);

        Formatter fmt = new Formatter();
        if (period.get(ChronoUnit.YEARS) > 0) {
            fmt.format("%d años ", period.get(ChronoUnit.YEARS));
        }
        if (period.get(ChronoUnit.MONTHS) > 0) {
            fmt.format("%d meses ", period.get(ChronoUnit.MONTHS));
        }
        if (period.get(ChronoUnit.YEARS) <= 0 && period.get(ChronoUnit.DAYS) > 0) {
            fmt.format("%d días ", period.get(ChronoUnit.DAYS));
        }
        if(fmt.toString().equalsIgnoreCase("")){
            return "Nacido hoy";
        }
        return fmt.toString();
    }

    public long getEdadDias(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy",
                Locale.getDefault());
        LocalDate now = LocalDate.now();
        ChronoLocalDate from = ChronoLocalDate.from(formatter.parse(fecha_nac));
        ChronoLocalDate to = ChronoLocalDate.from(now);
        return ChronoUnit.DAYS.between(from, to);
    }

    public void calcularEstado(Context context){
        if(id!=-1){
            String edo = Validacion.validaEstado(fecha_nac,estado,tipo,foto_url);
            if(!edo.equalsIgnoreCase("")){
                estado = edo;
                OperacionesDB.getInstancia(context).updatePorcino(this);
            }
        }
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(String fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFoto_url() {
        return foto_url;
    }

    public void setFoto_url(String foto_url) {
        this.foto_url = foto_url;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha_compra() {
        return fecha_compra;
    }

    public void setFecha_compra(String fecha_compra) {
        this.fecha_compra = fecha_compra;
    }

    public String getFecha_venta() {
        return fecha_venta;
    }

    public void setFecha_venta(String fecha_venta) {
        this.fecha_venta = fecha_venta;
    }

    public double getPeso() { return peso; }

    public void setPeso(double peso) { this.peso = peso; }
}
