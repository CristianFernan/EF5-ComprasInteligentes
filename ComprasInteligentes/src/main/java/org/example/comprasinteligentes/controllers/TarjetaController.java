package org.example.comprasinteligentes.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.comprasinteligentes.Alerts;
import org.example.comprasinteligentes.Conexion;
import org.example.comprasinteligentes.clases.Facilitador;
import org.example.comprasinteligentes.clases.Tarjeta;
import org.example.comprasinteligentes.clases.Cliente; // 00068223 Importacion de la clase Cliente
import org.example.comprasinteligentes.views.ClienteApplication;
import org.example.comprasinteligentes.views.ComprasApplication;
import org.example.comprasinteligentes.views.TarjetaApplication;

import java.sql.*;

public class TarjetaController { //00068223 Clase controladora para manejar la logica de las tarjetas
    private static Conexion conexion = Conexion.getInstance(); //00068223 Obtenemos instancia única singleton de conexion

    @FXML
    private TextField txtNumeroTarjeta; //00068223 objeto TextField txtNumeroTarjeta de view para obtener datos

    @FXML
    private DatePicker dpFechaExpiracion; //00068223 objeto DatePicker dpFechaExpiracion de view para obtener datos

    @FXML
    private ComboBox<Facilitador> cmbFacilitador; //00068223 ComboBox de tipo Facilitador para llenar de facilitadores existentes, y obtener datos

    @FXML
    private TextField txtCliente; //00068223 objeto TextField txtCliente de view para obtener datos

    @FXML
    private TableView<Tarjeta> tbListadoTarjetas; //00068223 objeto TableView para acceder en el controller

    @FXML
    private TableColumn<Tarjeta, String> numeroTarjeta; //00068223 objeto TableColumn para acceder en el controller

    @FXML
    private TableColumn<Tarjeta, Date> fechaExpiracion; //00068223 objeto TableColumn para acceder en el controller

    @FXML
    private TableColumn<Tarjeta, String> facilitador; //00068223 objeto TableColumn para acceder en el controller

    @FXML
    private TableColumn<Tarjeta, String> cliente; //00068223 objeto TableColumn para acceder en el controller

    @FXML
    public void initialize() { //00068223 Funcion de inicializacion de la vista que se ejecuta antes de lo demas para pre configuraciones necesarias
        numeroTarjeta.setCellValueFactory(new PropertyValueFactory<>("numeroTarjeta")); //00068223 asignarle una value factory a la columna para que utilice el atributo numeroTarjeta para guardarlos
        fechaExpiracion.setCellValueFactory(new PropertyValueFactory<>("fechaExpiracion")); //00068223 asignarle una value factory a la columna para que utilice el atributo fechaExpiracion para guardarlos
        facilitador.setCellValueFactory(new PropertyValueFactory<>("facilitador")); //00068223 asignarle una value factory a la columna para que utilice el atributo facilitador para guardarlos
        cliente.setCellValueFactory(new PropertyValueFactory<>("cliente")); //00068223 asignarle una value factory a la columna para que utilice el atributo cliente para guardarlos
        cargarFacilitadores(); //00068223 llamado de funcion para rellenar cmb de facilitadores
        //cargarDatosTarjeta(); //00068223 llamado de funcion para rellenar la tabla de tarjetas
    }
    @FXML
    private void onBtnGuardarTarjetaClick() { //00068223 Funcion para manejar el evento de click en el boton guardar tarjeta
        if (!txtNumeroTarjeta.getText().isBlank() && cmbFacilitador.getValue() != null && !txtCliente.getText().isBlank() && dpFechaExpiracion.getValue() != null) { // 00068223 Verifica si los campos no estan vacios
            String numeroTarjeta = txtNumeroTarjeta.getText();
            Date fechaExpiracion = Date.valueOf(dpFechaExpiracion.getValue());
            Facilitador facilitador = cmbFacilitador.getValue();
            String clienteNombre = txtCliente.getText();

            try { //00068223 Inicio del bloque try para manejar excepciones SQL
                int result; //00068223 Variable para almacenar el resultado de la ejecucion de la consulta
                PreparedStatement ps = conexion.conectar().prepareStatement("INSERT INTO tbtarjeta (numeroTarjeta, fechaExpiracion, idFacilitador, cliente) VALUES (?,?,?,?)"); // 00068223 Prepara la consulta SQL para insertar una tarjeta
                ps.setString(1, numeroTarjeta); //00068223 Asigna el número de la tarjeta al primer parametro de la consulta
                ps.setDate(2, fechaExpiracion); //00068223 Asigna la fecha de expiracion al segundo parametro de la consulta
                ps.setInt(3, facilitador.getId()); //00068223 Asigna el ID del facilitador al tercer parametro de la consulta
                ps.setString(4, clienteNombre); //00068223 Asigna el nombre del cliente al cuarto parametro de la consulta

                result = ps.executeUpdate(); //00068223 Ejecuta la consulta SQL
                if (result > 0) {
                    Alerts.showAlert("exito", "Se ha ingresado la tarjeta satisfactoriamente", 1); //00068223 Muestra alerta de exito si la insercion fue exitosa
                } else {
                    Alerts.showAlert("Fracaso", "Ocurrio un error al ingresar la tarjeta", 3); //00068223 Muestra alerta de fracaso si la insercion fallo
                }
                conexion.cerrarConexion(); //00068223 Cierra la conexion a la base de datos
            } catch (SQLException e) { //00068223 Captura las excepciones SQL que ocurran en el bloque try
                Alerts.showAlert("Error", "Error de conexion", 3); //00068223 Muestra alerta de error de conexion
                System.out.println("Error de conexion " + e.getMessage()); //00068223 Imprime el mensaje de error de conexion en la consola
            }

            cargarDatosTarjeta(); //00068223 Carga los datos de la tarjeta en la vista
            limpiar(); //00068223 Limpia los campos del formulario
        } else { //00068223 else que se ejecuta cuando hay algún campo vacio
            Alerts.showAlert("Campos incompletos", "Favor llenar todos los campos necesarios para la creacion de la tarjeta", 2); //00068223 Muestra alerta de campos incompletos
        }
    }
    private void cargarDatosTarjeta() { //00068223 Funcion para cargar los datos de las tarjetas en la tabla
        tbListadoTarjetas.getItems().clear(); //00068223 Limpia los elementos de la tabla de tarjetas
        try { //00068223 Inicio del bloque try para manejar excepciones SQL
            Statement st = conexion.conectar().createStatement(); //00068223 Crea una declaracion SQL para ejecutar consultas
            ResultSet rs = st.executeQuery("SELECT t.id, t.numeroTarjeta, t.fechaExpiracion, t.tipo, f.id AS idFacilitador, f.facilitador, c.id AS idCliente, c.nombre AS clienteNombre FROM tbtarjeta t INNER JOIN tbFacilitador f ON t.idFacilitador = f.id INNER JOIN tbCliente c ON t.idCliente = c.id"); //00068223 Ejecuta una consulta SQL para obtener los datos de las tarjetas
            while (rs.next()) { //00068223 Itera sobre los resultados de la consulta
                Facilitador facilitador = new Facilitador(rs.getInt("idFacilitador"), rs.getString("facilitador"));
                Cliente cliente = new Cliente(rs.getInt("idCliente"), rs.getString("clienteNombre"));
                Tarjeta tarjeta = new Tarjeta(rs.getInt("id"), rs.getString("numeroTarjeta"), rs.getDate("fechaExpiracion"), rs.getString("tipo"), facilitador, cliente); //00068223 Crea una nueva instancia de Tarjeta con los datos obtenidos
                tbListadoTarjetas.getItems().add(tarjeta); //00068223 Agrega la tarjeta a la lista de la tabla
            }
            conexion.cerrarConexion(); //00068223 Cierra la conexion a la base de datos
        } catch (SQLException e) { //00068223 Captura las excepciones SQL que ocurran en el bloque try
            Alerts.showAlert("Error", "Error de conexion", 3); //00068223 Muestra alerta de error de conexion
            System.out.println("error de conexion: " + e); //00068223 Imprime el mensaje de error de conexion en la consola
        }
    }

    private void cargarFacilitadores() { //00068223 Funcion para cargar los facilitadores en el comboBox cmbFacilitador
        try { //00068223 Inicio del bloque try para manejar excepciones SQL
            Statement st = conexion.conectar().createStatement(); //00068223 Crea una declaracion SQL para ejecutar consultas
            ResultSet rs = st.executeQuery("SELECT id, facilitador FROM tbFacilitador"); //00068223 Ejecuta una consulta SQL para obtener los datos de los facilitadores
            while (rs.next()) { //00068223 Itera sobre los resultados de la consulta
                Facilitador facilitador = new Facilitador(rs.getInt("id"), rs.getString("facilitador"));
                cmbFacilitador.getItems().add(facilitador); //00068223 Agrega cada facilitador al comboBox
            }
            conexion.cerrarConexion(); //00068223 Cierra la conexion a la base de datos
        } catch (SQLException e) { //00068223 Captura las excepciones SQL que ocurran en el bloque try
            Alerts.showAlert("Error", "Error de conexion", 3); //00068223 Muestra alerta de error de conexion
            System.out.println("Error de conexion " + e.getMessage()); //00068223 Imprime el mensaje de error de conexion en la consola
        }
    }

    private void limpiar() { //00068223 Funcion para limpiar los campos del formulario
        txtNumeroTarjeta.clear(); //00068223 Limpia el campo de texto de numeroTarjeta
        dpFechaExpiracion.setValue(null); //00068223 Restablece el selector de fecha a su valor por defecto
        cmbFacilitador.setValue(null); //00068223 Restablece el comboBox de facilitadores a su valor por defecto
        txtCliente.clear(); //00068223 Limpia el campo de texto de cliente
    }

    @FXML
    private void onBtnLimpiarTarjetaClick() { //00068223 Funcion para manejar el evento de click en el boton limpiar tarjeta
        limpiar(); //00068223 Llama al metodo limpiar para restablecer los campos del formulario
    }

    @FXML
    private void onBtnCargarDatosTarjetaClick() { //00068223 Funcion para manejar el evento de click en el boton cargar datos
        cargarDatosTarjeta(); //00068223 Llama al metodo cargarDatosTarjeta para actualizar la tabla de tarjetas
    }

    @FXML
    private void onBtnClientesClick() { // 00016623 Método para manejar el evento de click en el botón de btnClientes del menu,
        // Cerrando ventana actual
        ((Stage) tbListadoTarjetas.getScene().getWindow()).close(); //00016623 Cierra la ventana actual casteando en stage la ventana de la tabla tbListadoTarjetas
        try {//00068223 Inicio del bloque try para manejar excepciones al abrir ventana
            Stage stage = new Stage(); //00016623 Crea una nueva instancia de Stage para la nueva ventana
            ClienteApplication app = new ClienteApplication(); //00016623 Crea una instancia de la aplicación de Clientes
            app.start(stage); //00016623 Inicia la aplicación de Clientes en el nuevo Stage
        } catch (Exception e) {//00016623 Captura las excepciones que ocurran en el bloque try
            Alerts.showAlert("Error", "Error al intentar abrir ventana", 3); //00016623 Muestra una alerta en caso de error
            System.out.println("No se pudo abrir la ventana de tareas, " + e.getMessage()); //00016623 Imprime el mensaje de error en la consola
        }
    }
    @FXML
    private void onBtnComprasClick() { // 00016623 Método para manejar el evento de click en el botón de Compras,
        // Cerrando ventana actual
        ((Stage) tbListadoTarjetas.getScene().getWindow()).close(); //00016623 Cierra la ventana actual casteando en stage la ventana de la tabla tbListadoTarjetas
        //00016623 Abriendo nueva ventana de Compras
        try {//00068223 Inicio del bloque try para manejar excepciones al abrir ventana
            Stage stage = new Stage(); //00016623 Crea una nueva instancia de Stage para la nueva ventana
            ComprasApplication app = new ComprasApplication(); //00016623 Crea una instancia de la aplicación de Compras
            app.start(stage); //00016623 Inicia la aplicación de Compras en el nuevo Stage
        } catch (Exception e) {//00016623 Captura las excepciones que ocurran en el bloque try
            Alerts.showAlert("Error", "Error al intentar abrir ventana", 3); //00016623 Muestra una alerta en caso de error
            System.out.println("No se pudo abrir la ventana de tareas, " + e.getMessage()); //00016623 Imprime el mensaje de error en la consola
        }
    }
}
