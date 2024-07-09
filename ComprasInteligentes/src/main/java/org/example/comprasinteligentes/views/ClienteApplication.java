package org.example.comprasinteligentes.views; // 00107223 Paquete al que pertenecen todos las aplicaciones de las vistas

import javafx.application.Application; // 00107223 Librería Vital para demostrar que el archivo es de tipo Application para que pueda correr el programa
import javafx.fxml.FXMLLoader; // 00107223 Librería Vital para correr el programa
import javafx.scene.Scene; // 00107223 Scene porque cada vista es un scene y se obtiene la escena del cliente.fxml
import javafx.stage.Stage; // 00107223 Stage para armar la ventana de la escena

import java.io.IOException; // 00107223 Excepciones que puede dar el programa al tratar de correr.

public class ClienteApplication extends Application { // 00107223 Clase cuyo único propósito es correr el programa
    @Override
    public void start(Stage stage) throws IOException { // 00107223 Función que corre el programa
        FXMLLoader fxmlLoader = new FXMLLoader(ClienteApplication.class.getResource("/org/example/comprasinteligentes/cliente.fxml")); //00107223 Se obtiene la vista, mediante su ruta
        Scene scene = new Scene(fxmlLoader.load(), 575, 430); // 00107223 Se crea la escena y se define sus dimensiones
        stage.setTitle("Gestion Clientes"); // 00107223 Se le asigna un titulo a la ventana
        stage.setResizable(false); // 00107223 Se le quita el atributo de poderse modificar el tamaño
        stage.setScene(scene); // 00107223 Se le asigna la escena a la ventana
        stage.show(); // 00107223 Se abre la ventana
    }

    public static void main(String[] args) {
        launch();
    } // 00107223 Inicia el ciclo de vida de JavaFX
}