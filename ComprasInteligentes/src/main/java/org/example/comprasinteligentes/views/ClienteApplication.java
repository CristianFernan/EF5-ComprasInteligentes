package org.example.comprasinteligentes.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClienteApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClienteApplication.class.getResource("/org/example/comprasinteligentes/cliente.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 575, 430);
        stage.setTitle("Gestion Clientes");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}