package org.example.comprasinteligentes.clases; // 00068223 Define el paquete al que pertenece esta clase

public class Facilitador { // 00068223 Creacion de la clase Facilitador para poder gestionar la tabla facilitador de la base de datos
    private int id; // 00068223 atributo entero que contiene el id de la tarjeta
    private String facilitador; // 00068223 atributo cadena que contiene el facilitador de la tarjeta

    public Facilitador(int id, String facilitador) { // 00068223 constructor personalizado para la captura de los atributos de la tabla facilitador
        this.id = id; // 00068223 asignacion del id
        this.facilitador = facilitador; // 00068223 asignacion del facilitador de la tarjeta
    }

    public int getId() {
        return id; // 00068223 Retorna el valor del atributo id
    }

    public void setId(int id) {
        this.id = id; // 00068223 Asigna el valor proporcionado al atributo id
    }

    public String getFacilitador() {
        return facilitador; // 00068223 Retorna el valor del atributo facilitador
    }

    public void setFacilitador(String facilitador) {
        this.facilitador = facilitador;; // 00068223 Asigna el valor proporcionado al atributo facilitador
    }
    @Override // 00068223 Anotacion para sobreescribir el metodo toString de la clase
    public String toString() { // 00068223 Metodo toString para representar el objeto como cadena
        return facilitador; // 00068223 Retorna la construccion de la cadena de texto
    }
}
