package org.example.comprasinteligentes;

import javafx.scene.control.Alert;

public class Alerts { //00016623 Clase utilitaria para mostrar alertas
    public static void showAlert(String title, String content, int type){ //00016623 Método estático para mostrar una alerta
        Alert alert = null; //00016623 Declaración de variable para la alerta
        switch (type){ //00016623 Inicio del switch para determinar el tipo de alerta según el valor de 'type'
            case 1: //00016623 caso para valor 1
                alert = new Alert(Alert.AlertType.INFORMATION); //00016623 Crea una alerta de tipo información
                break;//00016623 break salida de switch
            case 2://00016623 caso para valor 2
                alert = new Alert(Alert.AlertType.WARNING); //00016623 Crea una alerta de tipo advertencia
                break;//00016623 break salida de switch
            case 3://00016623 caso para valor 3
                alert = new Alert(Alert.AlertType.ERROR); //00016623 Crea una alerta de tipo error
                break;//00016623 break salida de switch
        }
        alert.setTitle(title); //00016623 Establece el título de la alerta
        alert.setContentText(content); //00016623 Establece el contenido de la alerta
        alert.showAndWait(); //00016623 Muestra la alerta y espera a que el usuario la cierre
    }
}