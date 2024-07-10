package org.example.comprasinteligentes.clases; // 00107223 paquete al que pertenece la clase

public class Cliente { //00107223 Creación de clase Cliente para poder gestionar la información de la tabla cliente de la base de datos
    private int id; //00107223 atributo entero que contiene el id del Clinete
    private String nombre; //00107223 atributo cadena que contiene el nombre del Cliente
    private String apellido; //00107223 atributo cadena que contiene el apellido del Cliente
    private String direccion; //00107223 atributo cadena que contiene la direccion del Cliente
    private String numeroTelefono; //00107223 atributo cadena que contiene el número de telefono del Cliente

    public Cliente(){} //00107223 constructor de cliente

    public Cliente(int id, String nombre, String apellido, String direccion, String numeroTelefono){ //00107223 constructor personalizado para la captura de los atributos de la tabla clientes
        this.id = id; // 00107223 asignacion del id
        this.nombre = nombre; // 00107223 asignacion del nombre
        this.apellido = apellido; // 00107223 asignacion del apellido
        this.direccion = direccion; // 00107223 asignacion del direccion
        this.numeroTelefono = numeroTelefono; // 00107223 asignacion del numero telefono
    }

    public Cliente(int id, String apellido) {
        this.id = id;
        this.apellido = apellido;
    }

    //Getters
    public int getId() { // 00107223 getter del id para su manejo en el CRUD
        return id; // 00107223 retorno del id
    }

    public String getNombre() { // 00107223 getter del nombre para su manejo en el CRUD
        return nombre; // 00107223 retorno del nombre
    }

    public String getApellido() { // 00107223 getter del apellido para su manejo en el CRUD
        return apellido; // 00107223 retorno del apellido
    }

    public String getDireccion() { // 00107223 getter del dirección para su manejo en el CRUD
        return direccion; // 00107223 retorno del direccion
    }

    public String getNumeroTelefono() { // 00107223 getter del número telefono para su manejo en el CRUD
        return numeroTelefono; // 00107223 retorno del número telefono
    }

    //Setters

    public void setId(int id) { // 00107223 setter del identificador para su manejo en el CRUD: tableview
        this.id = id; // 00107223 retorno del identificador
    }
    public void setNombre(String nombre) { // 00107223 setter del nombre para su manejo en el CRUD: tableview
        this.nombre = nombre;  // 00107223 retorno del nombre
    }

    public void setApellido(String apellido) {  // 00107223 setter del apellido para su manejo en el CRUD: tableview
        this.apellido = apellido; // 00107223 retorno del apellido
    }

    public void setDireccion(String direccion) { // 00107223 setter del direccion para su manejo en el CRUD: tableview
        this.direccion = direccion; // 00107223 retorno del direccion
    }

    public void setNumeroTelefono(String numeroTelefono) { // 00107223 setter del numero telefono para su manejo en el CRUD: tableview
        this.numeroTelefono = numeroTelefono; // 00107223 retorno del numero telefono
    }

    @Override
    public String toString() {
        return (nombre != null ? nombre : "") + " " + (apellido != null ? apellido : ""); // 00068223 haciendo una verificacion de que los valores no sean nulos
    }
}
