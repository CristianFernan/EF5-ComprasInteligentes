package org.example.comprasinteligentes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion { // 00107223 Creacion de un singleton para la conexion con la base de datos
    private static Connection conexion; // 00107223 Estableciendo el objeto conexion a utilizar
    private static Conexion instance; // 00107223 Estableciendo la instancia de la clase conexion
    private final String URL = "jdbc:mysql://localhost:3306/dbComprasInteligentes"; // 00107223 Estableciendo el URL para establecer la conexion
    private final String user = "root";// 00107223 Estableciendo el usuario que hara la conexion
    private final String password = "ganglyPie44311"; // 00107223 Estableciendo la contraseña del usuario

    private Conexion(){} // 00107223 constructor privado para que no se pueda llamar afuera de la clase

    public Connection conectar(){ // 00107223 funcion que establece la conexión con la base de datos
        try { // 00107223 Try obligatorio para atrapar cualquier error provocado durante la conexión
             conexion = DriverManager.getConnection(URL, user, password); // 00107223 asignandole una conexion al objeto conexion
            return conexion; // 00107223 retornando la conexión
        } catch (SQLException e) { // 00107223 catch que atrapa los errores durante la conexión
            System.out.println("error en la base de datos: " + e); // 00107223 mensaje personalizado de error
        }
        return conexion; // 00107223 retorna el objeto conexion aun si la conexion no se logra establecer correctamente
    }

    public void cerrarConexion(){ // 00107223 funcion que establece la conexión con la base de datos
        try { // 00107223 Try obligatorio para atrapar cualquier error provocado durante la conexión
            if(!conexion.isClosed() && conexion != null){ // 00107223 condicional que verifica si existe una conexión y si dicha conexión no esta ya cerrada
                conexion.close(); // 00107223 cierra la conexión con la base de datos
            }
        } catch (SQLException e){ // 00107223 catch que atrapa los errores durante la conexión
            System.out.println("error en la base de datos" + e); // 00107223 mensaje personalizado de error
        }
    }

    public static Conexion getInstance(){ // 00107223 patron de diseño singleton, obtiene la unica intancia de la conexión
        if (instance == null){ // 00107223 verifica si no existe una instancia ya creada
            instance = new Conexion(); // 00107223 crea una instancia
        }
        return instance; // 00107223 retorna la instancia de la clase
    }

}
