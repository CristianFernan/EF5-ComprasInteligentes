package org.example.comprasinteligentes.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ComprasApplication extends Application { //00016623 Clase principal que extiende de Application para iniciar la aplicación JavaFX
    @Override
    public void start(Stage stage) throws IOException { //00016623 Función start que configura la ventana principal
        FXMLLoader fxmlLoader = new FXMLLoader(ComprasApplication.class.getResource("/org/example/comprasinteligentes/compra.fxml")); //00016623 Carga el archivo FXML para la interfaz de usuario
        Scene scene = new Scene(fxmlLoader.load(), 575, 430); //00016623 Crea una nueva escena con el contenido cargado y establece sus dimensiones
        stage.setTitle("Gestion Compras"); //00016623 Establece el titulo de la ventana
        stage.setResizable(false); //00016623 Evita que se le pueda cambiar las dimensiones a la pantalla
        stage.setScene(scene); //00016623 Establece la escena en la ventana
        stage.show(); //00016623 Prepara para mostrar la ventana
    }

    public static void main(String[] args) { //00016623 Método main para iniciar la aplicación
        launch(); //00016623 Llama al método launch para iniciar la aplicación JavaFX
    }
}