package org.example.comprasinteligentes.clases;

import java.sql.Date;
import java.util.Arrays;

public class Tarjeta {
    private int id;
    private String numeroTarjeta;
    private Date fechaExpiracion;
    private String tipo;
    private Facilitador facilitador;
    private Cliente cliente;

    public Tarjeta(String numeroTarjeta, Date fechaExpiracion, String tipo, Facilitador facilitador, Cliente cliente) {
        this.numeroTarjeta = numeroTarjeta;
        this.fechaExpiracion = fechaExpiracion;
        this.tipo = tipo;
        this.facilitador = facilitador;
        this.cliente = cliente;
    }

    public Tarjeta(int id, String numeroTarjeta, Date fechaExpiracion, String tipo) {
        this.id = id;
        this.numeroTarjeta = numeroTarjeta;
        this.fechaExpiracion = fechaExpiracion;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public Date getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(Date fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Facilitador getFacilitador() {
        return facilitador;
    }

    public void setFacilitador(Facilitador facilitador) {
        this.facilitador = facilitador;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString(){
        return censurarTarjeta(numeroTarjeta) + ", " + tipo;
    }

    public String censurarTarjeta(String numeroTarjeta){
        StringBuilder tarjetaBuilder = new StringBuilder(numeroTarjeta);
        for (int i = 0; i < tarjetaBuilder.length(); ++i) {
            if(tarjetaBuilder.charAt(i) != '-') tarjetaBuilder.setCharAt(i, 'X');
            if(i == 14) break;
        }
        return tarjetaBuilder.toString();
    }
}
