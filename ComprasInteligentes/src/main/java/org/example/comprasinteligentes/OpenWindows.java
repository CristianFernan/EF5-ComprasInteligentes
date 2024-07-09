package org.example.comprasinteligentes;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.example.comprasinteligentes.views.ClienteApplication;
import org.example.comprasinteligentes.views.ComprasApplication;
import org.example.comprasinteligentes.views.ReporteApplication;
import org.example.comprasinteligentes.views.TarjetaApplication;

import java.io.BufferedReader;

public class OpenWindows { //00016623 Clase utilitaria para mostrar alertas
    public static void openWindow(TableView nodo, int application){ //00016623 Método estático para mostrar una alerta
        // Cerrando ventana actual
        ((Stage) nodo.getScene().getWindow()).close(); //00016623 Cierra la ventana actual casteando en stage la ventana de la tabla tbListadoCliente
        //00016623 Abriendo nueva ventana de Compras
        try {//00068223 Inicio del bloque try para manejar excepciones al abrir ventana
            Stage stage = new Stage(); //00016623 Crea una nueva instancia de Stage para la nueva ventana
            Application app = null;
            switch (application){
                case 1:
                    app = new ClienteApplication();
                    break;
                case 2:
                    app = new ComprasApplication();
                    break;
                case 3:
                    app = new ReporteApplication();
                    break;
                case 4:
                    app = new TarjetaApplication();
                    break;
            }
            app.start(stage); //00016623 Inicia la aplicación de Compras en el nuevo Stage
        } catch (Exception e) {//00016623 Captura las excepciones que ocurran en el bloque try
            Alerts.showAlert("Error", "Error al intentar abrir ventana", 3); //00016623 Muestra una alerta en caso de error
            System.out.println("No se pudo abrir la ventana" + e.getMessage()); //00016623 Imprime el mensaje de error en la consola
        }
    }
}