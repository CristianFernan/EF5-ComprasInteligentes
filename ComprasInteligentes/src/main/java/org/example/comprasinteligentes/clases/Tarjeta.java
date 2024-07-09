package org.example.comprasinteligentes.clases; // 00068223 Define el paquete al que pertenece esta clase

import java.sql.Date;

public class Tarjeta { // 00068223 Creacion de la clase Tarjeta para poder gestionar la tabla tarjeta de la base de datos
    private int id; // 00068223 atributo entero que contiene el id de la tarjeta
    private String numeroTarjeta; // 00068223 atributo cadena que contiene el numero de la tarjeta
    private Date fechaExpiracion; // 00068223 atributo Date que contiene la fecha de expiracion de la tarjeta
    private String tipo; // 00068223 atributo cadena que contiene el tipo de tarjeta
    private Facilitador facilitador; // 00068223 atributo facilitador de tipo Facilitador
    private Cliente cliente; // 00068223 atributo cliente de tipo Cliente

    public Tarjeta(int id, String numeroTarjeta, Date fechaExpiracion, String tipo, Facilitador facilitador, Cliente cliente) { // 00068223 constructor personalizado para la captura de los atributos de la tabla tarjeta
        this.id = id; // 00068223 asignacion del id
        this.numeroTarjeta = numeroTarjeta; // 00068223 asignacion del numero de tarjeta
        this.fechaExpiracion = fechaExpiracion; // 00068223 asignacion de la fecha de expiracion de la tarjeta
        this.tipo = tipo; // 00068223 asignacion del tipo de la tarjeta
        this.facilitador = facilitador; // 00068223 asignacion del facilitador de la tarjeta
        this.cliente = cliente; // 00068223 asignacion del cliente al que pertenece la tarjeta
    }

    public Tarjeta(int id, String numeroTarjeta, Date fechaExpiracion, String tipo) { // 00016623 Constructor para inicializar una tarjeta con los datos proporcionados, usado en cmb de ComprasController
        this.id = id; // 00016623 Asigna el ID de la tarjeta
        this.numeroTarjeta = numeroTarjeta; // 00016623 Asigna el número de tarjeta
        this.fechaExpiracion = fechaExpiracion; // 00016623 Asigna la fecha de expiración de la tarjeta
        this.tipo = tipo; // 00016623 Asigna el tipo de tarjeta
    }

    public Tarjeta(String numeroTarjeta, String tipo){ // 00107723 Constructor para su aplicación en la generación del Reporte C
        this.numeroTarjeta = numeroTarjeta; // 00017223 Asigna el número censurado de la tarjeta
        this.tipo = tipo; // 00107223 asigna el tipo de tarjeta
    }

    public int getId() {
        return id; // 00068223 Retorna el valor del atributo id
    }

    public void setId(int id) {
        this.id = id; // 00068223 Asigna el valor proporcionado al atributo id
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta; // 00068223 Retorna el valor del atributo numeroTarjeta
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta; // 00068223 Asigna el valor proporcionado al atributo numeroTarjeta
    }

    public Date getFechaExpiracion() {
        return fechaExpiracion; // 00068223 Retorna el valor atributo fechaExpiracion
    }

    public void setFechaExpiracion(Date fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion; // 00068223 Asigna el valor proporcionado al atributo fechaExpiracion
    }

    public String getTipo() {
        return tipo; // 00068223 Retorna el valor atributo tipo
    }

    public void setTipo(String tipo) {
        this.tipo = tipo; // 00068223 Asigna el valor proporcionado al atributo tipo
    }

    public Facilitador getFacilitador() {
        return facilitador; // 00068223 Retorna el valor atributo facilitador
    }

    public void setFacilitador(Facilitador facilitador) {
        this.facilitador = facilitador; // 00068223 Asigna el valor proporcionado al atributo facilitador
    }

    public Cliente getCliente() {
        return cliente; // 00068223 Retorna el valor atributo cliente
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente; // 00068223 Asigna el valor proporcionado al atributo cliente
    }
    @Override // 00068223 Anotacion para sobreescribir el metodo toString de la clase
    public String toString(){ // 00068223 Metodo toString para representar el objeto como cadena
        return getTarjetaCensurada() + ", " + tipo; // 00068223 Retorna la construccion de la cadena de texto
    }
  
  public String getTarjetaCensurada(){ // 00016623 Método para censurar el número de tarjeta
        return "XXXX XXXX XXXX " + numeroTarjeta.substring(numeroTarjeta.length() - 4); // 00068223 Censura el numero de la tarjeta, mostrando solo los ultimos cuatro digitos
    }
}