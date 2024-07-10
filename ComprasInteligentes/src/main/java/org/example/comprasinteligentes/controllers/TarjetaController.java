package org.example.comprasinteligentes.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.comprasinteligentes.Alerts;
import org.example.comprasinteligentes.Conexion;
import org.example.comprasinteligentes.OpenWindows;
import org.example.comprasinteligentes.clases.Cliente;
import org.example.comprasinteligentes.clases.Facilitador;
import org.example.comprasinteligentes.clases.Tarjeta;
import org.example.comprasinteligentes.views.ClienteApplication;
import org.example.comprasinteligentes.views.ComprasApplication;
import org.example.comprasinteligentes.views.ReporteApplication;

import java.sql.*;

public class TarjetaController { //00068223 Clase controladora para manejar la logica de las tarjetas
    private static Conexion conexion = Conexion.getInstance(); //00068223 Obtenemos instancia unica singleton de conexion

    @FXML
    private TextField txtNumeroTarjeta; //00068223 objeto TextField txtNumeroTarjeta de view para obtener datos

    @FXML
    private DatePicker dpFechaExpiracion; //00068223 objeto DatePicker dpFechaExpiracion de view para obtener datos

    @FXML
    private ComboBox<Facilitador> cmbFacilitador; //00068223 ComboBox de tipo Facilitador para llenar de facilitadores existentes, y obtener datos

    @FXML
    private ComboBox<String> cmbTipoTarjeta; //00068223 ComboBox de tipo String para llenar de tipos de tarjeta existentes, y obtener datos

    @FXML
    private ComboBox<Cliente> cmbNombreCliente; //00068223 ComboBox de tipo Cliente para llenar de clientes existentes, y obtener datos

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
        numeroTarjeta.setCellValueFactory(new PropertyValueFactory<>("numeroTarjeta")); //00068223 asignar una value factory a la columna para que utilice el atributo numeroTarjeta para guardarlos
        fechaExpiracion.setCellValueFactory(new PropertyValueFactory<>("fechaExpiracion")); //00068223 asignar una value factory a la columna para que utilice el atributo fechaExpiracion para guardarlos
        facilitador.setCellValueFactory(new PropertyValueFactory<>("facilitador")); //00068223 asignar una value factory a la columna para que utilice el atributo facilitador para guardarlos
        cliente.setCellValueFactory(new PropertyValueFactory<>("cliente")); //00068223 asignar una value factory a la columna para que utilice el atributo cliente para guardarlos
        cargarFacilitadores(); //00068223 llamado de funcion para rellenar cmb de facilitadores
        //cargarDatosTarjeta(); //00068223 llamado de funcion para rellenar la tabla de tarjetas
        cargarClientes(); //00068223 llamado de funcion para rellenar el cmb de clientes
        tarjetaValidacion(); //00068223 llamado de funcion de validacion de numero de tarjeta
        cargarTiposTarjeta(); //00068223 llamado de funcion de tipos de tarjeta
    }

    private void tarjetaValidacion() { // 00068223 Funcion para la validacion del campo numero tarjeta, siguiendo el patron XXXX-XXXX-XXXX-XXXX
        txtNumeroTarjeta.setOnKeyReleased(event -> { // 00068223 Añade un listener para cambios en el texto
            String newValue = txtNumeroTarjeta.getText().replaceAll("-", ""); // 00068223 Elimina los guiones del texto
            if (!newValue.matches("\\d*")){// 00068223 Verifica si el nuevo valor contiene solo numeros
                Alerts.showAlert("Error", "Ingrese valores válidos", 3);// 00068223 Muestra un mensaje de error si se ingresan letras
                txtNumeroTarjeta.clear(); // 00068223 Limpia el campo de numero de tarjeta
                return; // 00068223 sale del metodo si se ingresan caracteres no validos
            }
            if (newValue.length() > 16) { // 00068223 Condicional donde se revisa el maximo de digitos posibles
                Alerts.showAlert("Error", "Usted ha sobrepasado el máximo de dígitos permitidos.", 3); // 00068223 Muestra un mensaje de error si se excede el maximo de digitos permitidos
                newValue = newValue.substring(0, 16); // 00068223 Elimina los caracteres extra
            }
            if (newValue.length() > 4) newValue = newValue.substring(0, 4) + "-" + newValue.substring(4); // 00068223 Añade un guion después de 4 caracteres
            if (newValue.length() > 9) newValue = newValue.substring(0, 9) + "-" + newValue.substring(9); // 00068223 Añade un guion después de 9 caracteres
            if (newValue.length() > 14) newValue = newValue.substring(0, 14) + "-" + newValue.substring(14); // 00068223 Añade un guion después de 14 caracteres
            txtNumeroTarjeta.setText(newValue); // 00068223 Actualiza el texto del campo de texto
            txtNumeroTarjeta.positionCaret(newValue.length()); // 00068223 Posiciona el cursor al final del texto);

        });
    }

    private void cargarTiposTarjeta() { //00068223 Funcion para cargar los tipos de tarjeta en el comboBox cmbTipoTarjeta
        cmbTipoTarjeta.getItems().addAll("Débito", "Crédito"); //00068223 Agregar "Debito" y "Credito" al comboBox
    }

    @FXML
    private void onBtnGuardarTarjetaClick() { //00068223 Funcion para manejar el evento de click en el boton guardar tarjeta
        if (!txtNumeroTarjeta.getText().isBlank() && cmbFacilitador.getValue() != null && cmbNombreCliente.getValue() != null && dpFechaExpiracion.getValue() != null) { //00068223 Verifica si los campos no estan vacios
            String numeroTarjeta = txtNumeroTarjeta.getText(); //00068223 Obtener el numero de tarjeta del campo de texto
            Date fechaExpiracion = Date.valueOf(dpFechaExpiracion.getValue()); //00068223 Obtener la fecha de expiracion del DatePicker
            Facilitador facilitador = cmbFacilitador.getValue(); //00068223 Obtener el facilitador seleccionado
            Cliente cliente = cmbNombreCliente.getValue(); //00068223 Obtener el cliente seleccionado
            String tipoTarjeta = cmbTipoTarjeta.getValue(); //00068223 Obtener el tipo de tarjeta seleccionado

            if (tarjetaExiste(numeroTarjeta)){ //00068223 Verifica si la tarjeta ya existe
                Alerts.showAlert("Error", "Ya existe una tarjeta registrada con este numero", 3); //00068223 Muestra alerta de error si la tarjeta ya existe
                return; //00068223 Sale del metodo si la tarjeta ya existe
            }

            try { //00068223 Inicio del bloque try para manejar excepciones SQL
                int result; //00068223 Variable para almacenar el resultado de la ejecucion de la consulta
                PreparedStatement ps = conexion.conectar().prepareStatement("INSERT INTO tbtarjeta (numeroTarjeta, fechaExpiracion, tipo, idFacilitador, idCliente) VALUES (?,?,?,?,?)"); //00068223 Prepara la consulta SQL para insertar una tarjeta
                ps.setString(1, numeroTarjeta); //00068223 Asigna el numero de la tarjeta al primer parametro de la consulta
                ps.setDate(2, fechaExpiracion); //00068223 Asigna la fecha de expiracion al segundo parametro de la consulta
                ps.setString(3, tipoTarjeta); //00068223 Asigna el tipo de tarjeta al tercer parametro de la consulta
                ps.setInt(4, facilitador.getId()); //00068223 Asigna el ID del facilitador al cuarto parametro de la consulta
                ps.setInt(5, cliente.getId()); //00068223 Asigna el ID del cliente al quinto parametro de la consulta

                result = ps.executeUpdate(); //00068223 Ejecuta la consulta SQL
                if (result > 0) { //00068223 Verifica si la insercion fue exitosa
                    Alerts.showAlert("Éxito", "Se ha ingresado la tarjeta satisfactoriamente", 1); //00068223 Muestra alerta de exito si la insercion fue exitosa
                } else { //00068223 else que se ejecuta cuando la insercion fallo
                    Alerts.showAlert("Fracaso", "Ocurrio un error al ingresar la tarjeta", 3); //00068223 Muestra alerta de fracaso si la insercion fallo
                }
                conexion.cerrarConexion(); //00068223 Cierra la conexion a la base de datos
            } catch (SQLException e) { //00068223 Captura las excepciones SQL que ocurran en el bloque try
                Alerts.showAlert("Error", "Error de conexion", 3); //00068223 Muestra alerta de error de conexion
                System.out.println("Error de conexion " + e.getMessage()); //00068223 Imprime el mensaje de error de conexion en la consola
            }

            cargarDatosTarjeta(); //00068223 Carga los datos de la tarjeta en la vista
            limpiar(); //00068223 Limpia los campos del formulario
        } else { //00068223 else que se ejecuta cuando hay algun campo vacio
            Alerts.showAlert("Campos incompletos", "Favor llenar todos los campos necesarios para la creacion de la tarjeta", 2); //00068223 Muestra alerta de campos incompletos
        }
    }
    private boolean tarjetaExiste(String numeroTarjeta) { //00068223 Funcion para verificar si una tarjeta ya existe en la base de datos
        try { //00068223 Inicio del bloque try para manejar excepciones SQL
            PreparedStatement ps = conexion.conectar().prepareStatement("SELECT COUNT(*) FROM tbtarjeta WHERE numeroTarjeta = ?"); //00068223 Prepara la consulta SQL para verificar si la tarjeta ya existe
            ps.setString(1, numeroTarjeta); //00068223 Asigna el numero de la tarjeta al primer parametro de la consulta
            ResultSet rs = ps.executeQuery(); //00068223 Ejecuta la consulta SQL
            if (rs.next() && rs.getInt(1) > 0) { //00068223 Verifica si el resultado de la consulta es mayor a 0
                return true; //00068223 Retorna verdadero si la tarjeta ya existe
            }
            conexion.cerrarConexion(); //00068223 Cierra la conexion a la base de datos
        } catch (SQLException e) { //00068223 Captura las excepciones SQL que ocurran en el bloque try
            System.out.println("Error de conexion: " + e.getMessage()); //00068223 Imprime el mensaje de error de conexion en la consola
        }
        return false; //00068223 Retorna falso si la tarjeta no existe
    }

        private void cargarDatosTarjeta() { //00068223 Funcion para cargar los datos de las tarjetas en la tabla
        tbListadoTarjetas.getItems().clear(); //00068223 Limpia los elementos de la tabla de tarjetas
        try { //00068223 Inicio del bloque try para manejar excepciones SQL
            Statement st = conexion.conectar().createStatement(); //00068223 Crea una declaracion SQL para ejecutar consultas
            ResultSet rs = st.executeQuery("SELECT t.id, t.numeroTarjeta, t.fechaExpiracion, t.tipo, f.id AS idFacilitador, f.facilitador, c.id AS idCliente, c.nombre AS clienteNombre, c.apellido AS clienteApellido FROM tbtarjeta t INNER JOIN tbFacilitador f ON t.idFacilitador = f.id INNER JOIN tbCliente c ON t.idCliente = c.id"); //00068223 Ejecuta una consulta SQL para obtener los datos de las tarjetas
            while (rs.next()) { //00068223 Itera sobre los resultados de la consulta
                Facilitador facilitador = new Facilitador(rs.getInt("idFacilitador"), rs.getString("facilitador")); //00068223 Crea una nueva instancia de Facilitador con los datos obtenidos
                Cliente cliente = new Cliente(rs.getInt("idCliente"), rs.getString("clienteNombre"), rs.getString("clienteApellido"), "", ""); //00068223 Crea una nueva instancia de Cliente con los datos obtenidos
                Tarjeta tarjeta = new Tarjeta(rs.getInt("id"), rs.getString("numeroTarjeta"), rs.getDate("fechaExpiracion"), rs.getString("tipo"), facilitador, cliente); //00068223 Crea una nueva instancia de Tarjeta con los datos obtenidos
                tbListadoTarjetas.getItems().add(tarjeta); //00068223 Agrega la tarjeta a la lista de la tabla
            }
            conexion.cerrarConexion(); //00068223 Cierra la conexion a la base de datos
        } catch (SQLException e) { //00068223 Captura las excepciones SQL que ocurran en el bloque try
            Alerts.showAlert("Error", "Error de conexion", 3); //00068223 Muestra alerta de error de conexion
            System.out.println("error de conexion: " + e); //00068223 Imprime el mensaje de error de conexion en la consola
        }
    }

    private void cargarClientes() { //00068223 Funcion para cargar los clientes en el comboBox cmbNombreCliente
        try { //00068223 Inicio del bloque try para manejar excepciones SQL
            Statement st = conexion.conectar().createStatement(); //00068223 Crea una declaracion SQL para ejecutar consultas
            ResultSet rs = st.executeQuery("SELECT id, nombre, apellido FROM tbCliente"); //00068223 Ejecuta una consulta SQL para obtener los datos de los clientes
            while (rs.next()) { //00068223 Itera sobre los resultados de la consulta
                String nombre = rs.getString("nombre"); // 00068223 Obtener el nombre del cliente del resultado de la consulta SQL
                String apellido = rs.getString("apellido"); // 00068223 Obtener el apellido del cliente del resultado de la consulta SQL
                Cliente cliente = new Cliente(rs.getInt("id"), nombre != null ? nombre : "", apellido != null ? apellido : "", "", ""); //00068223 Crea una nueva instancia de Cliente con los datos obtenidos
                cmbNombreCliente.getItems().add(cliente); //00068223 Agrega cada cliente al comboBox
            }
            conexion.cerrarConexion(); //00068223 Cierra la conexion a la base de datos
        } catch (SQLException e) { //00068223 Captura las excepciones SQL que ocurran en el bloque try
            Alerts.showAlert("Error", "Error de conexion", 3); //00068223 Muestra alerta de error de conexion
            System.out.println("Error de conexion " + e.getMessage()); //00068223 Imprime el mensaje de error de conexion en la consola
        }
    }

    private void cargarFacilitadores() { //00068223 Funcion para cargar los facilitadores en el comboBox cmbFacilitador
        try { //00068223 Inicio del bloque try para manejar excepciones SQL
            Statement st = conexion.conectar().createStatement(); //00068223 Crea una declaracion SQL para ejecutar consultas
            ResultSet rs = st.executeQuery("SELECT id, facilitador FROM tbFacilitador"); //00068223 Ejecuta una consulta SQL para obtener los datos de los facilitadores
            while (rs.next()) { //00068223 Itera sobre los resultados de la consulta
                Facilitador facilitador = new Facilitador(rs.getInt("id"), rs.getString("facilitador")); //00068223 Crea una nueva instancia de Facilitador con los datos obtenidos
                cmbFacilitador.getItems().add(facilitador); //00068223 Agrega cada facilitador al comboBox
            }
            conexion.cerrarConexion(); //00068223 Cierra la conexion a la base de datos
        } catch (SQLException e) { //00068223 Captura las excepciones SQL que ocurran en el bloque try
            Alerts.showAlert("Error", "Error de conexion", 3); //00068223 Muestra alerta de error de conexion
            System.out.println("Error de conexion " + e.getMessage()); //00068223 Imprime el mensaje de error de conexion en la consola
        }
    }

    private void limpiar() { //00068223 Funcion para limpiar los campos del formulario
        txtNumeroTarjeta.clear(); //00068223 Limpiar el campo de texto de numeroTarjeta
        dpFechaExpiracion.setValue(null); //00068223 Restablecer el selector de fecha a su valor por defecto
        cmbFacilitador.setValue(null); //00068223 Restablecer el comboBox de facilitadores a su valor por defecto
        cmbTipoTarjeta.setValue(null); //00068223 Restablecer el comboBox de tipo de tarjeta a su valor por defecto
        cmbNombreCliente.setValue(null); //00068223 Restablecer el comboBox de cliente a su valor por defecto
    }

    @FXML
    private void onBtnLimpiarTarjetaClick() { //00068223 Funcion para manejar el evento de click en el boton limpiar tarjeta
        limpiar(); //00068223 Llamar al metodo limpiar para restablecer los campos del formulario
    }

    @FXML
    private void onBtnCargarDatosTarjetaClick() { //00068223 Funcion para manejar el evento de click en el boton cargar datos
        cargarDatosTarjeta(); //00068223 Llamar al metodo cargarDatosTarjeta para actualizar la tabla de tarjetas
    }
    @FXML
    private void onBtnClientesClick() { // 00016623 Método para manejar el evento de click en el botón de btnClientes del menu,
        OpenWindows.openWindow(tbListadoTarjetas, 1);// 00016623 llamando método utilitario para abrir ventanas
    }

    @FXML
    private void onBtnReportesClick() { // 00016623 Método para manejar el evento de click en el botón de Reporte,
        OpenWindows.openWindow(tbListadoTarjetas, 3);// 00016623 llamando método utilitario para abrir ventanas
    }

    @FXML
    private void onBtnComprasClick() { // 00016623 Método para manejar el evento de click en el botón de Reporte,
        OpenWindows.openWindow(tbListadoTarjetas, 2);// 00016623 llamando método utilitario para abrir ventanas
    }
}