package org.example.comprasinteligentes.clases;

import java.sql.Date;

public class CompraCustom { //00016623 Clase personalizada de compra para la tabla de compras en vista
    private double monto; //00016623 Atributo monto de la compra
    private String descripcion; //00016623 Atributo descripcion de la compra
    private Date fecha; //00016623 Atributo fecha de la compra

    private String tarjeta; //00016623 Atributo tarjeta asociada a la compra
    private String cliente; //00016623 Atributo nombre del cliente asociado a la compra

    public CompraCustom(double monto, String descripcion, Date fecha, String tarjeta, String cliente) { //00016623 Constructor para inicializar clase con los datos proporcionados
        this.monto = monto; //00016623 Asigna el monto de la compra
        this.descripcion = descripcion; //00016623 Asigna la descripción de la compra
        this.fecha = fecha; //00016623 Asigna la fecha de la compra
        this.tarjeta = tarjeta; //00016623 Asigna la información de la tarjeta asociada
        this.cliente = cliente; //00016623 Asigna el nombre del cliente asociado
    }

    public double getMonto() { //00016623 Método para obtener el monto de la compra
        return monto; //00016623 Retorna el monto de la compra
    }

    public String getDescripcion() { //00016623 Método para obtener la descripción de la compra
        return descripcion; //00016623 Retorna la descripción de la compra
    }

    public Date getFecha() { //00016623 Método para obtener la fecha de la compra
        return fecha; //00016623 Retorna la fecha de la compra
    }

    public String getTarjeta() { //00016623 Método para obtener la información de la tarjeta asociada
        return tarjeta; //00016623 Retorna la información de la tarjeta asociada
    }

    public String getCliente() { //00016623 Método para obtener el nombre del cliente asociado
        return cliente; //00016623 Retorna el nombre del cliente asociado
    }
}