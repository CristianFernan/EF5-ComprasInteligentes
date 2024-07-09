package org.example.comprasinteligentes.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ReporteApplication extends Application { //00083723 Clase principal que extiende de Application para iniciar la aplicacion JavaFX
    @Override
    public void start(Stage stage) throws IOException { // Funcion start que configura la ventana principal
        FXMLLoader fxmlLoader = new FXMLLoader(ComprasApplication.class.getResource("/org/example/comprasinteligentes/reportes.fxml")); //00083723 Carga el archivo FXML para la interfaz de usuario
        Scene scene = new Scene(fxmlLoader.load(), 600, 670); //00083723 Crea una nueva escena con el contenido cargado y establece sus dimensiones
        stage.setTitle("Gestion de reportes"); //00083723 Establece el titulo de la ventana
        stage.setResizable(true); //00083723 Evita que se le pueda cambiar las dimensiones a la pantalla
        stage.setScene(scene); //00083723 Establece la escena en la ventana
        stage.show(); //00083723 Prepara para mostrar la ventana
    }

    public static void main(String[] args) { //00083723 Método main para iniciar la aplicacion
        launch(); //00083723 Llama al método launch para iniciar la aplicacion JavaFX
    }
}