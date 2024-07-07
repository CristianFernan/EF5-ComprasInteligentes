package org.example.comprasinteligentes.clases;

import java.sql.Date;

public class Facilitador {
    private int id;
    private String facilitador;

    public Facilitador(String facilitador) {
        this.facilitador = facilitador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFacilitador() {
        return facilitador;
    }

    public void setFacilitador(String facilitador) {
        this.facilitador = facilitador;
    }
}
