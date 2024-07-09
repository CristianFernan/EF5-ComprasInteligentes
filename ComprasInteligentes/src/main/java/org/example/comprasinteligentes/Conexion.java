package org.example.comprasinteligentes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion { // 00107223 Creacion de un singleton para la conexion con la base de datos
    private static Connection conexion; // 00107223 Estableciendo el objeto conexion a utilizar
    private static Conexion instance; // 00107223 Estableciendo la instancia de la clase conexion
    private final String URL = "jdbc:mysql://localhost:33060/dbComprasInteligentes"; // 00107223 Estableciendo el URL para establecer la conexion
    private final String user = "root";// 00107223 Estableciendo el usuario que hara la conexion
    private final String password = "Luis1234"; // 00107223 Estableciendo la contraseña del usuario

    private Conexion(){} // 00107223 constructor privado para que no se pueda llamar afuera de la clase

    public Connection conectar() throws SQLException{ // 00107223 funcion que establece la conexión con la base de datos, throws hará que pueda ser atrapapo por try catch en controllers
        conexion = DriverManager.getConnection(URL, user, password); // 00107223 asignandole una conexion al objeto conexion
        return conexion; // 00107223 retornando la conexión
    }

    public void cerrarConexion() throws SQLException{ // 00107223 funcion que establece la conexión con la base de datos, throw atrapado por catch
        if(!conexion.isClosed() && conexion != null){ // 00107223 condicional que verifica si existe una conexión y si dicha conexión no esta ya cerrada
            conexion.close(); // 00107223 cierra la conexión con la base de datos
        }

    }

    public static Conexion getInstance(){ // 00107223 patron de diseño singleton, obtiene la unica intancia de la conexión
        if (instance == null){ // 00107223 verifica si no existe una instancia ya creada
            instance = new Conexion(); // 00107223 crea una instancia
        }
        return instance; // 00107223 retorna la instancia de la clase
    }

}
