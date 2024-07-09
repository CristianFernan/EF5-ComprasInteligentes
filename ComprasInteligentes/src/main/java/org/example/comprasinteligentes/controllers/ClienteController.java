package org.example.comprasinteligentes.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.comprasinteligentes.Alerts;
import org.example.comprasinteligentes.Conexion;
import org.example.comprasinteligentes.clases.Cliente;
import org.example.comprasinteligentes.views.ClienteApplication;
import org.example.comprasinteligentes.views.ComprasApplication;
import org.example.comprasinteligentes.views.TarjetaApplication;

import java.sql.*;
import java.util.regex.Pattern;

public class ClienteController {
    private static final Conexion conexion = Conexion.getInstance(); // 00107223 Obtenemos instancia unica singleton de conexion

    @FXML
    private TextField txtNombre; // 00107223 objeto TextField txtNombre para obtener datos
    @FXML
    private TextField txtApellido; // 00107223 objeto TextField txtApellido para obtener datos
    @FXML
    private TextField txtTelefono; // 00107223 objeto TextField txtTelefono para obtener datos y aplicar validaciones
    @FXML
    private TextField txtIDCliente; // 00107223 objeto TextField txtIDCliente obtener dato para operaciones en BD
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
    private void agregarCliente() { // 00107223 Metodo para agregar un nuevo cliente
        if (camposIncompletos()) { // 00068223 Verifica si hay campos incompletos
            Alerts.showAlert("Campos incompletos", "Favor llenar todos los campos necesarios para la creacion del cliente", 2); // 00068223 Muestra alerta de campos incompletos
            return; // 00068223 Sale del metodo si hay campos incompletos
        }
        Cliente cliente = new Cliente(0, txtNombre.getText(), txtApellido.getText(), txtDireccion.getText(), txtTelefono.getText()); // 00107223 Crea un nuevo cliente con los datos ingresados
        try {
            int result; // 00107223 Variable para almacenar el resultado de la ejecucion de la consulta
            PreparedStatement ps = conexion.conectar().prepareStatement("INSERT INTO tbCLIENTE (NOMBRE, APELLIDO, NUMEROTELEFONO, DIRECCION) VALUES (?,?,?,?)"); // 00107223 Prepara la consulta SQL para insertar un cliente
            ps.setString(1, cliente.getNombre()); // 00107223 Asigna el nombre del cliente al primer parametro de la consulta
            ps.setString(2, cliente.getApellido()); // 00107223 Asigna el apellido del cliente al segundo parametro de la consulta
            ps.setString(3, cliente.getNumeroTelefono()); // 00107223 Asigna el numero de telefono del cliente al tercer parametro de la consulta
            ps.setString(4, cliente.getDireccion()); // 00107223 Asigna la direccion del cliente al cuarto parametro de la consulta
            result = ps.executeUpdate(); // 00107223 Ejecuta la consulta SQL

            if (result > 0) tbListadoCliente.getItems().add(cliente); // 00107223 Si la insercion fue exitosa, agrega el cliente a la tabla
            System.out.println(result > 0 ? "Exito" : "Fracaso"); // 00107223 Imprime el resultado de la insercion en la consola
            limpiar(); // 00107223 Limpia los campos del formulario
            conexion.cerrarConexion(); // 00107223 Cierra la conexion a la base de datos
        } catch (SQLException e) {
            System.out.println("error de conexion: " + e); // 00107223 Imprime el mensaje de error de conexion en la consola
        }
    }

    @FXML
    private void eliminarCliente() { // 00107223 Metodo para eliminar un cliente
        if (txtIDCliente.getText().isEmpty()) { // 00068223 Verifica si el campo ID del cliente esta vacio
            Alerts.showAlert("Error", "Debe indicar un ID primero", 2); // 00068223 Muestra alerta de error si el campo ID esta vacio
            return; // 00068223 Sale del metodo si el campo ID esta vacio
        }
        try {
            int result; // 00107223 Variable para almacenar el resultado de la ejecucion de la consulta
            PreparedStatement ps = conexion.conectar().prepareStatement("DELETE FROM tbCLIENTE WHERE ID=?"); // 00107223 Prepara la consulta SQL para eliminar un cliente
            ps.setInt(1, Integer.parseInt(txtIDCliente.getText())); // 00107223 Asigna el ID del cliente al primer parametro de la consulta
            result = ps.executeUpdate(); // 00107223 Ejecuta la consulta SQL

            if (result > 0) {
                int id = buscarCliente(Integer.parseInt(txtIDCliente.getText())); // 00107223 Busca el cliente en la tabla
                if (id != -1) {
                    tbListadoCliente.getItems().remove(id); // 00107223 Remueve el cliente de la tabla
                    tbListadoCliente.refresh(); // 00107223 Refresca la tabla para mostrar los cambios
                }
            }
            System.out.println(result > 0 ? "Exito" : "Fracaso"); // 00107223 Imprime el resultado de la eliminacion en la consola
            limpiar(); // 00107223 Limpia los campos del formulario
            conexion.cerrarConexion(); // 00107223 Cierra la conexion a la base de datos
        } catch (SQLException e) {
            System.out.println("error de conexion: " + e); // 00107223 Imprime el mensaje de error de conexion en la consola
        }
    }

    @FXML
    private void modificarCliente() { // 00107223 Metodo para modificar un cliente
        if (txtIDCliente.getText().isEmpty()) { // 00068223 Verifica si el campo ID del cliente esta vacio
            Alerts.showAlert("Error", "Debe indicar un ID primero", 2); // 00068223 Muestra alerta de error si el campo ID esta vacio
            return; // 00068223 Sale del metodo si el campo ID esta vacio
        }
        try {
            int result; // 00107223 Variable para almacenar el resultado de la ejecucion de la consulta
            PreparedStatement ps = conexion.conectar().prepareStatement("UPDATE tbCLIENTE SET NOMBRE=?, APELLIDO=?, NUMEROTELEFONO=?, DIRECCION=? WHERE ID=?"); // 00107223 Prepara la consulta SQL para modificar un cliente
            ps.setString(1, txtNombre.getText()); // 00107223 Asigna el nombre del cliente al primer parametro de la consulta
            ps.setString(2, txtApellido.getText()); // 00107223 Asigna el apellido del cliente al segundo parametro de la consulta
            ps.setString(3, txtTelefono.getText()); // 00107223 Asigna el numero de telefono del cliente al tercer parametro de la consulta
            ps.setString(4, txtDireccion.getText()); // 00107223 Asigna la direccion del cliente al cuarto parametro de la consulta
            ps.setInt(5, Integer.parseInt(txtIDCliente.getText())); // 00107223 Asigna el ID del cliente al quinto parametro de la consulta
            result = ps.executeUpdate(); // 00107223 Ejecuta la consulta SQL

            if (result > 0) {
                int id = buscarCliente(Integer.parseInt(txtIDCliente.getText())); // 00107223 Busca el cliente en la tabla
                if (id != -1) {
                    Cliente cliente = tbListadoCliente.getItems().get(id); // 00107223 Obtiene el cliente de la tabla
                    cliente.setNombre(txtNombre.getText()); // 00107223 Actualiza el nombre del cliente en la tabla
                    cliente.setApellido(txtApellido.getText()); // 00107223 Actualiza el apellido del cliente en la tabla
                    cliente.setNumeroTelefono(txtTelefono.getText()); // 00107223 Actualiza el numero de telefono del cliente en la tabla
                    cliente.setDireccion(txtDireccion.getText()); // 00107223 Actualiza la direccion del cliente en la tabla
                    tbListadoCliente.refresh(); // 00107223 Refresca la tabla para mostrar los cambios
                }
            }
            System.out.println(result > 0 ? "Exito" : "Fracaso"); // 00107223 Imprime el resultado de la modificacion en la consola
            limpiar(); // 00107223 Limpia los campos del formulario
            conexion.cerrarConexion(); // 00107223 Cierra la conexion a la base de datos
        } catch (SQLException e) {
            System.out.println("error de conexion: " + e); // 00107223 Imprime el mensaje de error de conexion en la consola
        }
    }

    @FXML
    private void cargarDatosCliente() { // 00107223 Metodo para cargar los datos de los clientes en la tabla
        tbListadoCliente.getItems().clear(); // 00107223 Limpia los elementos de la tabla de clientes
        try {
            Statement st = conexion.conectar().createStatement(); // 00107223 Crea una declaracion SQL para ejecutar consultas
            ResultSet rs = st.executeQuery("SELECT ID, NOMBRE, APELLIDO, DIRECCION, NUMEROTELEFONO FROM tbCLIENTE"); // 00107223 Ejecuta una consulta SQL para obtener los datos de los clientes
            if (!rs.isBeforeFirst()) { // 00068223 Verifica si hay datos en la base de datos
                Alerts.showAlert("Sin datos", "No hay datos en la base de datos", 2); // 00068223 Muestra una alerta si no hay datos en la base de datos
                return; // 00068223 Sale del metodo si no hay datos en la base de datos
            }
            while (rs.next()) {
                Cliente cliente = new Cliente(rs.getInt("ID"), rs.getString("NOMBRE"), rs.getString("APELLIDO"), rs.getString("DIRECCION"), rs.getString("NUMEROTELEFONO")); // 00107223 Crea una nueva instancia de Cliente con los datos obtenidos
                tbListadoCliente.getItems().add(cliente); // 00107223 Agrega el cliente a la tabla
            }
            conexion.cerrarConexion(); // 00107223 Cierra la conexion a la base de datos
        } catch (SQLException e) {
            System.out.println("error de conexion: " + e); // 00107223 Imprime el mensaje de error de conexion en la consola
        }
    }

    @FXML
    public void initialize() { // 00107223 Manejar validaciones a la hora de iniciar la vista
        nombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre")); // 00107223 Asignarle una value factory a la columna para que utilice el atributo nombre para guardarlos
        apellido.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellido")); // 00107223 Asignarle una value factory a la columna para que utilice el atributo apellido para guardarlos
        numeroTelefono.setCellValueFactory(new PropertyValueFactory<Cliente, String>("numeroTelefono")); // 00107223 Asignarle una value factory a la columna para que utilice el atributo numero telefono para guardarlos
        direccion.setCellValueFactory(new PropertyValueFactory<Cliente, String>("direccion")); // 00107223 Asignarle una value factory a la columna para que utilice el atributo direccion para guardarlos
        telefonoValidacion(); // 00107223 Llamada a la validacion del txtTelefono
        idValidacion(); // 00107223 Llamada a la validacion del txtIDCliente
    }

    private int buscarCliente(int ID) { // 00107223 Metodo para buscar un cliente en la tabla por su ID
        ObservableList<Cliente> clientes = tbListadoCliente.getItems(); // 00107223 Obtiene la lista de clientes de la tabla

        for (Cliente cliente : clientes) {
            if (cliente.getId() == ID) {
                return clientes.indexOf(cliente); // 00107223 Retorna el indice de la fila del cliente
            }
        }
        return -1; // 00107223 Retorna un indice fuera de los limites, por lo que no removeria nada
    }

    private void limpiar() { // 00107223 Metodo para limpiar los campos del formulario
        txtTelefono.setText(""); // 00107223 Limpia el campo de texto del numero de telefono
        txtTelefono.setPromptText("XXXX-XXXX"); // 00107223 Establece el texto de sugerencia para el campo de texto del numero de telefono
        txtApellido.setText(""); // 00107223 Limpia el campo de texto del apellido
        txtNombre.setText(""); // 00107223 Limpia el campo de texto del nombre
        txtDireccion.setText(""); // 00107223 Limpia el campo de texto de la direccion
        txtIDCliente.setText(""); // 00107223 Limpia el campo de texto del ID del cliente
    }

    private void telefonoValidacion() { // 00068223 Funcion para la validacion del campo numero telefono, siguiendo el patron XXXX-XXXX
        txtTelefono.setOnKeyReleased(event -> { // 00068223 Añade un listener para cambios en el texto
            String newValue = txtTelefono.getText().replaceAll("-", ""); // 00107223 Elimina los guiones del texto
            if (!newValue.matches("\\d*")) { // 00068223 Verifica si el nuevo valor contiene solo números
                Alerts.showAlert("Error", "Ingrese valores válidos.", 3); // 00068223 Muestra un mensaje de error si se ingresan letras
                txtTelefono.clear(); // 00068223 Borra el contenido del campo de texto
                return; // 00068223 Sale del método si se ingresan caracteres no válidos
            }
            if (newValue.length() > 8) { // 00068223 Verifica si el nuevo valor excede los 8 caracteres permitidos
                Alerts.showAlert("Error", "Usted ha sobrepasado el máximo de dígitos permitidos (8).", 3); // 00068223 Muestra un mensaje de error si se excede el maximo de digitos permitidos
                newValue = newValue.substring(0, 8); // 00068223 Elimina los caracteres extra
            }
            if (newValue.length() > 4) newValue = newValue.substring(0, 4) + "-" + newValue.substring(4); // 00068223 Añade un guion despues de 4 caracteres
            txtTelefono.setText(newValue); // 00068223 Actualiza el texto del campo de texto
            txtTelefono.positionCaret(newValue.length()); // 00068223 Posiciona el cursor al final del texto
        });
    }

    private void idValidacion() { // 00068223 Funcion para la validacion del campo ID, siguiendo el patron de solo numeros
        txtIDCliente.setOnKeyReleased(event -> { // 00068223 Añade un listener para cambios en el texto
            String newValue = txtIDCliente.getText(); // 00068223 Obtiene el nuevo valor del campo de texto
            if (!newValue.matches("\\d*")) { // 00068223 Verifica si el nuevo valor contiene solo numeros
                Alerts.showAlert("Error", "Ingrese valores válidos. Solo se permiten dígitos.", 3); // 00068223 Muestra un mensaje de error si se ingresan letras
                txtIDCliente.clear(); // 00068223 Borra el contenido del campo de texto
            }
        });
    }

    private boolean camposIncompletos() { // 00107223 Funcion para verificar si hay campos incompletos en el formulario
        return txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty() || txtTelefono.getText().isEmpty() || txtDireccion.getText().isEmpty(); // 00107223 Verifica si alguno de los campos esta vacio
    }

    @FXML
    private void onBtnTarjetasClick() { // 00016623 Metodo para manejar el evento de click en el boton de btnTarjetas del menu
        ((Stage) tbListadoCliente.getScene().getWindow()).close(); // 00016623 Cierra la ventana actual casteando en stage la ventana de la tabla tbListadoCliente
        try { // 00068223 Inicio del bloque try para manejar excepciones al abrir ventana
            Stage stage = new Stage(); // 00016623 Crea una nueva instancia de Stage para la nueva ventana
            TarjetaApplication app = new TarjetaApplication(); // 00016623 Crea una instancia de la aplicacion de Tarjetas
            app.start(stage); // 00016623 Inicia la aplicacion de Tarjetas en el nuevo Stage
        } catch (Exception e) { // 00016623 Captura las excepciones que ocurran en el bloque try
            Alerts.showAlert("Error", "Error al intentar abrir ventana", 3); // 00068223 Muestra una alerta en caso de error
            System.out.println("No se pudo abrir la ventana de tareas, " + e.getMessage()); // 00068223 Imprime el mensaje de error en la consola
        }
    }

    @FXML
    private void onBtnComprasClick() { // 00016623 Metodo para manejar el evento de click en el boton de Compras
        ((Stage) tbListadoCliente.getScene().getWindow()).close(); // 00016623 Cierra la ventana actual casteando en stage la ventana de la tabla tbListadoCliente
        try { // 00068223 Inicio del bloque try para manejar excepciones al abrir ventana
            Stage stage = new Stage(); // 00016623 Crea una nueva instancia de Stage para la nueva ventana
            ComprasApplication app = new ComprasApplication(); // 00016623 Crea una instancia de la aplicacion de Compras
            app.start(stage); // 00016623 Inicia la aplicacion de Compras en el nuevo Stage
        } catch (Exception e) { // 00016623 Captura las excepciones que ocurran en el bloque try
            Alerts.showAlert("Error", "Error al intentar abrir ventana", 3); // 00068223 Muestra una alerta en caso de error
            System.out.println("No se pudo abrir la ventana de tareas, " + e.getMessage()); // 00068223 Imprime el mensaje de error en la consola
        }
    }
}
