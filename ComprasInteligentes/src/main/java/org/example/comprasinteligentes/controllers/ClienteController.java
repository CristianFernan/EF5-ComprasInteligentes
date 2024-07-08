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
    private TextField txtIDCliente; // 00107223  objeto TextField txtIDCliente obtener dato para operaciones en BD
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
    private TableColumn<Cliente, String> identificador; // 00107223 objeto TableColumn para identificar en el controller

    @FXML
    private void agregarCliente(){
        Cliente cliente = new Cliente(0, txtNombre.getText(), txtApellido.getText(), txtDireccion.getText(), txtTelefono.getText());
        try{
            int result;
            PreparedStatement ps = conexion.conectar().prepareStatement("INSERT INTO tbCLIENTE (NOMBRE, APELLIDO, NUMEROTELEFONO, DIRECCION) VALUES (?,?,?,?)");
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getNumeroTelefono());
            ps.setString(4, cliente.getDireccion());
            result = ps.executeUpdate();

            //PlaceHolder creo que lo cambiaremos a un alert
            if (result > 0) tbListadoCliente.getItems().add(cliente);
            System.out.println(result>0 ? "Exito" : "Fracaso");
            limpiar();
            imprimirTabla();
            conexion.cerrarConexion();
        } catch (SQLException e){
            System.out.println("error de conexion: " + e);
        }
    }
    @FXML
    private void eliminarCliente(){
        try{
            int result;
            PreparedStatement ps = conexion.conectar().prepareStatement("DELETE FROM tbCLIENTE WHERE ID=?");
            ps.setInt(1, Integer.parseInt(txtIDCliente.getText()));
            result = ps.executeUpdate();

            if (result > 0){
                int id = buscarCliente(Integer.parseInt(txtIDCliente.getText()));
                if (id != -1) {
                    tbListadoCliente.getItems().remove(id);
                    tbListadoCliente.refresh(); // 00107223 Refrescar la tabla para mostrar los cambios de la tabl
                }
            }
            //PlaceHolder creo que lo cambiaremos a un alert
            System.out.println(result>0 ? "Exito" : "Fracaso");
            limpiar();
            imprimirTabla();
            conexion.cerrarConexion();
        } catch (SQLException e){
            System.out.println("error de conexion: " + e);
        }
    }

    @FXML
    private void modificarCliente(){
        try{
            int result;
            PreparedStatement ps = conexion.conectar().prepareStatement("UPDATE tbCLIENTE SET NOMBRE=?, APELLIDO=?, NUMEROTELEFONO=?, DIRECCION=? WHERE ID=?");
            ps.setString(1, txtNombre.getText());
            ps.setString(2, txtApellido.getText());
            ps.setString(3, txtTelefono.getText());
            ps.setString(4, txtDireccion.getText());
            ps.setInt(5, Integer.parseInt(txtIDCliente.getText()));
            result = ps.executeUpdate();

            if (result > 0){
                int id = buscarCliente(Integer.parseInt(txtIDCliente.getText()));
                if (id != -1){
                    Cliente cliente = tbListadoCliente.getItems().get(id);
                    cliente.setId(Integer.parseInt(txtIDCliente.getText()));
                    cliente.setNombre(txtNombre.getText());
                    cliente.setApellido(txtApellido.getText());
                    cliente.setNumeroTelefono(txtTelefono.getText());
                    cliente.setDireccion(txtDireccion.getText());

                    tbListadoCliente.refresh(); // 00107223 Refrescar la tabla para mostrar los cambios de la tabla
                }
            }

            //PlaceHolder creo que lo cambiaremos a un alert
            System.out.println(result>0 ? "Exito" : "Fracaso");
            limpiar();
            imprimirTabla();
            conexion.cerrarConexion();
        } catch (SQLException e){
            System.out.println("error de conexion: " + e);
        }
    }

    @FXML
    private void cargarDatosCliente(){
        imprimirTabla();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { // 00107233 Manejar validaciones a la hora de iniciar la vista
        // Tabla
        nombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre")); // 00107223 asignarle una value factory a la columna para que utilize el atributo nombre para guardarlos
        apellido.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellido")); // 00107223 asignarle una value factory a la columna para que utilize el atributo apellido para guardarlos
        numeroTelefono.setCellValueFactory(new PropertyValueFactory<Cliente, String>("numeroTelefono")); // 00107223 asignarle una value factory a la columna para que utilize el atributo numero telefono para guardarlos
        direccion.setCellValueFactory(new PropertyValueFactory<Cliente, String>("direccion")); // 00107223 asignarle una value factory a la columna para que utilize el atributo direccion para guardarlos
        identificador.setCellValueFactory(new PropertyValueFactory<Cliente, String>("id")); // 00107223 asignarle una value factory a la columna para que utilize el atributo id para guardarlos
        telefonoValidacion(); // 00107223 llamada a la validacion del txtTelefono
        idValidacion(); // 00107223 llamada a la validacion del txtIDCliente
    }


    private int buscarCliente(int ID){
        ObservableList<Cliente> clientes = tbListadoCliente.getItems();

        for (Cliente cliente : clientes){
            if (cliente.getId() == ID){
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
        txtIDCliente.setText("");
    }

    private void imprimirTabla(){
        tbListadoCliente.getItems().clear();
        try{
            Statement st = conexion.conectar().createStatement();
            ResultSet rs = st.executeQuery("SELECT ID, NOMBRE, APELLIDO, DIRECCION, NUMEROTELEFONO FROM tbCLIENTE");
            while (rs.next()){
                Cliente cliente = new Cliente(rs.getInt("ID"), rs.getString("NOMBRE"), rs.getString("APELLIDO"), rs.getString("DIRECCION"), rs.getString("NUMEROTELEFONO"));
                tbListadoCliente.getItems().add(cliente);
            }
            conexion.cerrarConexion();
        } catch (SQLException e){
            System.out.println("error de conexion: " + e);
        }
    }

    private void telefonoValidacion(){ // 00107223 Funcion para la validacion del campo numero telefono, siguiendo el patron XXXX-XXXX, donde cada "X" representa un numero del 0-9
        Pattern pattern = Pattern.compile("\\d{0,4}-?\\d{0,4}"); // 00107223 Crear un patron para el textField del numero de telefono, \\d representa los numeros 0-9 {0,4} que deben ser 4 digitos, es separado por un "-"

        UnaryOperator<TextFormatter.Change> filtro  = generarFiltro(pattern); // 00107223 Se obtiene el filtro en base al patron que se busca implementar

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

    private void idValidacion(){
        Pattern pattern = Pattern.compile("\\d*"); // 00107223 Crear un patron para el textField del ID, \\d representa los numeros 0-9

        UnaryOperator<TextFormatter.Change> filtro =  generarFiltro(pattern); // 00107223 Se obtiene el filtro en base al patron que se busca implementar

        TextFormatter<String> formatter = new TextFormatter<>(filtro); // 00107223 Crear un objeto textFormatter para aplicarlo en el txtIDCliente, este contiene el formato de filtro, que contiene el patron de solo enteros
        txtIDCliente.setTextFormatter(formatter); // 00107723 Asignacion del formato ya validado.

    }

    private UnaryOperator<TextFormatter.Change> generarFiltro(Pattern pattern) { // 00107223 Funcion unaria que sirve para filtrar los cambios hechos dentro del textField, recibe TextFormatter.Change que chequea cada vez que hay una modificacion en cada input del textField.
        return change -> { // 00107223 retornara si el cambio que se produjo es valido o no
            String nuevoTexto = change.getControlNewText(); // 00107223 captura y convierte en cadena el texto escrito en el textField para poder procesarlo
            if (pattern.matcher(nuevoTexto).matches()){ // 00107223 condicional que verifica si el texto escrito coincide con el patron ya definido
                return change; // 00107223 Si el texto coincide con el patron permite los cambios al textField
            } else {
                return null; // 00107223 Si se cumple, envia un nulo por lo que nada se escribe en el textField
            }
        };
    }
}