package org.example.comprasinteligentes.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import org.example.comprasinteligentes.Conexion;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class ClienteController implements Initializable{
    private static Conexion conexion = Conexion.getInstance();
    @FXML
    private TextField txtTelefono; // 00107223 el objeto txtTelefono para aplicarle validaciones


    @FXML
    private void agregarCliente(){
    }
    @FXML
    private void eliminarCliente(){}
    @FXML
    private void guardarCliente(){}
    @FXML
    private void cargarDatosCliente(){}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { // 00107233 Manejar validaciones a la hora de iniciar la vista
        telefonoValidacion(); // 00107223 llamada a la validacion del txtTelefono
    }

    private void telefonoValidacion(){ // 00107223 Funcion para la validacion del campo numero telefono, siguiendo el patron XXXX-XXXX, donde cada "X" representa un numero del 0-9
        Pattern pattern = Pattern.compile("\\d{0,4}-?\\d{0,4}"); // 00107223 Crear un patron para el textField del numero de telefono, \\d representa los numeros 0-9 {0,4} que deben ser 4 digitos, es separado por un "-"

        UnaryOperator<TextFormatter.Change> filtro = change -> { // 00107223 Funcion unaria que sirve para filtrar los cambios hechos dentro del textField, recibe TextFormatter.Change que chequea cada vez que hay una modificacion en cada input del textField.
            String nuevoTexto = change.getControlNewText(); // 00107223 captura y convierte en cadena el texto escrito en el textField para poder procesarlo
            if (pattern.matcher(nuevoTexto).matches()){ // 00107223 condicional que verifica si el texto escrito coincide con el patron ya definido
                return change; // 00107223 Si el texto coincide con el patron permite los cambios al textField
            } else {
                return null; // 00107223 Si se cumple, envia un nulo por lo que nada se escribe en el textField
            }
        };

        TextFormatter<String> formatter = new TextFormatter<>(filtro); // 00107223 Crear un objeto textFormatter para aplicarlo en el txtTelefono, este contiene el formato de filtro, que contiene el patron XXXX-XXXX
        txtTelefono.setTextFormatter(formatter); // 00107723 Asignacion del formato ya validado.

        txtTelefono.textProperty().addListener((observable, oldValue, newValue) -> { // 00107223 Asignacion de un addListener para cada vez que haya un input del teclado aplicar validaciones
            if ((newValue.length() == 4) && !newValue.contains("-")) { // 00107223 Condicional donde si la cadena en el textfield ya tiene 4 digitos, le agrege automaticamente el "-"
                txtTelefono.setText(newValue + "-"); // 00107223 insertarle al textField los 4 digitos ya ingresados + el nuevo "-"
            } else if (newValue.length() > 4 && !newValue.contains("-")) { // 00107223 Condicional donde valida la entrada
                txtTelefono.setText(newValue.substring(0,4) + "-" + newValue.substring(4)); // 00107223 insertarle al textField los 4 digitos ya ingresados + "-" + los posibles 4 digitos que le sigan
            }
        });
    }
}