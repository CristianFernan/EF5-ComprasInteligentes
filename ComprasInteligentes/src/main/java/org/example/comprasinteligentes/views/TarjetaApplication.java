package org.example.comprasinteligentes.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class TarjetaApplication extends Application { // 00068223 Clase principal que extiende de Application para iniciar la aplicacion JavaFX
    @Override // 00068223 Anotacion para sobrescribir el metodo start de la clase Application
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TarjetaApplication.class.getResource("/org/example/comprasinteligentes/tarjeta.fxml")); // 00068223 Carga el archivo FXML para la interfaz de tarjetas de credito
        Scene scene = new Scene(fxmlLoader.load(), 700, 500); // 00068223 Crea una nueva escena con el contenido del archivo FXML y establece sus dimensiones
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm()); //00016623 Cargando estilos css para la vista  
        stage.setTitle("Gestion tarjetas de credito"); // 00068223 Establece el titulo de la ventana
        stage.setResizable(false); // 00068223 Establece que la ventana no sea redimensionable
        stage.setScene(scene); // 00068223 Asigna la escena a la ventana
        stage.show(); // 00068223 Muestra la ventana
    }

    public static void main(String[] args) {
        launch(); // 00068223 Lanza/corre la aplicacion JavaFX
    }
}
