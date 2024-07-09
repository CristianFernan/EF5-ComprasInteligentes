package org.example.comprasinteligentes.clases;

import java.sql.Date;

public class Compra { //00016623 Clase que representa tabla de compra de la base de datos
    private int id; //00016623 Identificador de la compra
    private Date fechaCompra; //00016623 Fecha en que se realizó la compra
    private double monto; //00016623 Monto total de la compra
    private String descripcion; //00016623 Descripción de la compra
    private Tarjeta tarjeta; //00016623 Tarjeta asociada a la compra

    public Compra(Date fechaCompra, double monto, String descripcion, Tarjeta tarjeta) { //00016623 Constructor para inicializar una compra con los datos proporcionados
        this.fechaCompra = fechaCompra; //00016623 Asigna la fecha de compra
        this.monto = monto; //00016623 Asigna el monto
        this.descripcion = descripcion; //00016623 Asigna la descripción
        this.tarjeta = tarjeta; //00016623 Asigna la tarjeta asociada
    }

    public int getId() { //00016623 Método para obtener el ID de la compra
        return id; //00016623 Retorna el ID de la compra
    }

    public Date getFechaCompra() { //00016623 Método para obtener la fecha de la compra
        return fechaCompra; //00016623 Retorna la fecha de la compra
    }

    public double getMonto() { //00016623 Método para obtener el monto de la compra
        return monto; //00016623 Retorna el monto de la compra
    }

    public String getDescripcion() { //00016623 Método para obtener la descripcion de la compra
        return descripcion; //00016623 Retorna la descripción de la compra
    }

    public Tarjeta getTarjeta() { // 00016623 Método para obtener la tarjeta asociada a la compra
        return tarjeta; // 00016623 Retorna la tarjeta asociada a la compra
    }
}