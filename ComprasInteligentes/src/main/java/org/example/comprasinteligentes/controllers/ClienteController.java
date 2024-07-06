package org.example.comprasinteligentes.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.comprasinteligentes.Conexion;
import org.example.comprasinteligentes.clases.Cliente;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class ClienteController implements Initializable{
    private static Conexion conexion = Conexion.getInstance();
    @FXML
    private TextField txtNombre; // 00107223 objeto TextField txtNombre para obtener datos
    @FXML
    private TextField txtApellido;// 00107223 objeto TextField txtApellido para obtener datos
    @FXML
    private TextField txtTelefono; // 00107223 objeto TextField txtTelefono para obtener datos y aplicar validaciones
    @FXML
    private TextArea txtDireccion; // 00107223 objeto TextArea txtDireccion para obtener datos
    @FXML
    private TableView<Cliente> tbListadoCliente; // 00107223 objeto TableView para acceder en el controller
    @FXML
    private TableColumn<Cliente, String> nombre; // 00107223 objeto TableColumn para acceder en el controller
    @FXML
    private TableColumn<Cliente, String> apellido; // 00107223 objeto TableColumn para acceder en el controller
    @FXML
    private TableColumn<Cliente, String> numeroTelefono; // 00107223 objeto TableColumn para acceder en el controller
    @FXML
    private TableColumn<Cliente, String> direccion; // 00107223 objeto TableColumn para acceder en el controller

    @FXML
    private void agregarCliente(){
        Cliente cliente = new Cliente(0, txtNombre.getText(), txtApellido.getText(), txtDireccion.getText(), txtTelefono.getText());
        tbListadoCliente.getItems().add(cliente);
        limpiar();
    }
    @FXML
    private void eliminarCliente(){
        try{
            int result;
            PreparedStatement ps = conexion.conectar().prepareStatement("DELETE FROM tbCLIENTE WHERE NOMBRE=? AND APELLIDO=?");
            ps.setString(1, txtNombre.getText());
            ps.setString(2, txtApellido.getText());
            result = ps.executeUpdate();

            if (result > 0){
                int id = buscarCliente(txtNombre.getText(), txtApellido.getText());
                tbListadoCliente.getItems().remove(id);
            }
            //PlaceHolder creo que lo cambiaremos a un alert
            System.out.println(result>0 ? "Exito" : "Fracaso");
            limpiar();
            conexion.cerrarConexion();
        } catch (SQLException e){
            System.out.println("error de conexion: " + e);
        }
    }
    @FXML
    private void guardarCliente(){
        ObservableList<Cliente> clientes = tbListadoCliente.getItems();
        for (Cliente cliente : clientes){
            try{
                int result;
                PreparedStatement ps = conexion.conectar().prepareStatement("INSERT INTO tbCLIENTE (NOMBRE, APELLIDO, NUMEROTELEFONO, DIRECCION) VALUES (?,?,?,?)");
                ps.setString(1, cliente.getNombre());
                ps.setString(2, cliente.getApellido());
                ps.setString(3, cliente.getNumeroTelefono());
                ps.setString(4, cliente.getNumeroTelefono());

                //PlaceHolder creo que lo cambiaremos a un alert
                result = ps.executeUpdate();
                System.out.println(result>0 ? "Exito" : "Fracaso");

                conexion.cerrarConexion();
            } catch (SQLException e){
                System.out.println("error de conexion: " + e);
            }
        }
    }
    @FXML
    private void cargarDatosCliente(){
        try{
            Statement st = conexion.conectar().createStatement();
            ResultSet rs = st.executeQuery("SELECT ID, NOMBRE, APELLIDO, NUMEROTELEFONO, DIRECCION FROM tbCLIENTE");
            while (rs.next()){
                Cliente cliente = new Cliente(rs.getInt("ID"), rs.getString("NOMBRE"), rs.getString("APELLIDO"), rs.getString("DIRECCION"), rs.getString("NUMEROTELEFONO"));
                tbListadoCliente.getItems().add(cliente);
            }
            conexion.cerrarConexion();
        } catch (SQLException e){
            System.out.println("error de conexion: " + e);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { // 00107233 Manejar validaciones a la hora de iniciar la vista
        // Tabla
        nombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre")); // 00107223 asignarle una value factory a la columna para que utilize el atributo nombre para guardarlos
        apellido.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellido")); // 00107223 asignarle una value factory a la columna para que utilize el atributo apellido para guardarlos
        numeroTelefono.setCellValueFactory(new PropertyValueFactory<Cliente, String>("numeroTelefono")); // 00107223 asignarle una value factory a la columna para que utilize el atributo numero telefono para guardarlos
        direccion.setCellValueFactory(new PropertyValueFactory<Cliente, String>("direccion")); // 00107223 asignarle una value factory a la columna para que utilize el atributo direccion para guardarlos
        telefonoValidacion(); // 00107223 llamada a la validacion del txtTelefono
    }


    private int buscarCliente(String nombre, String apellido){
        ObservableList<Cliente> clientes = tbListadoCliente.getItems();

        for (Cliente cliente : clientes){
            if (cliente.getNombre().equalsIgnoreCase(nombre) && cliente.getApellido().equalsIgnoreCase(apellido)){
                return clientes.indexOf(cliente); // 00107223 se devuelve el indice de la fila del cliente
            }
        }
        return -1; // 00107223 se devuelve un indice fuera de los limites, por lo que no removeria nada
    }

    private void limpiar(){
        txtTelefono.setText("");
        txtTelefono.setPromptText("XXXX-XXXX");
        txtApellido.setText("");
        txtNombre.setText("");
        txtDireccion.setText("");
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