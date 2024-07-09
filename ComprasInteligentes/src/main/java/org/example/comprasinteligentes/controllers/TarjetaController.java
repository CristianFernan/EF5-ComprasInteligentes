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
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

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
        Pattern pattern = Pattern.compile("\\d{0,4}-?\\d{0,4}-?\\d{0,4}-?\\d{0,4}"); //00068223 Crear un patron para el textField del numero de tarjeta

        UnaryOperator<TextFormatter.Change> filtro = generarFiltro(pattern); //00068223 Obtener el filtro en base al patron que se busca implementar

        TextFormatter<String> formatter = new TextFormatter<>(filtro); //00068223 Crear un objeto textFormatter para aplicarlo en el txtNumeroTarjeta, este contiene el formato de filtro, que contiene el patron XXXX-XXXX-XXXX-XXXX
        txtNumeroTarjeta.setTextFormatter(formatter); //00068223 Asignacion del formato ya validado.

        txtNumeroTarjeta.textProperty().addListener((observable, oldValue, newValue) -> { //00068223 Asignacion de un addListener para cada vez que haya un input del teclado aplicar validaciones
            if ((newValue.length() == 4 || newValue.length() == 9 || newValue.length() == 14) && !newValue.endsWith("-")) { //00068223 Condicional donde si la cadena en el textField ya tiene 4, 9 o 14 digitos, le agrega automaticamente el "-"
                txtNumeroTarjeta.setText(newValue + "-"); //00068223 insertarle al textField los digitos ya ingresados + el nuevo "-"
            } else if ((newValue.length() > 4 && newValue.length() < 9 && !newValue.contains("-")) ||
                    (newValue.length() > 9 && newValue.length() < 14 && !newValue.substring(5).contains("-")) ||
                    (newValue.length() > 14 && !newValue.substring(10).contains("-"))) { //00068223 Condicional donde valida la entrada
                txtNumeroTarjeta.setText(formatearNumeroTarjeta(newValue)); //00068223 insertarle al textField los digitos ya ingresados + "-" + los posibles digitos que le sigan
            }
        });
    }

    private UnaryOperator<TextFormatter.Change> generarFiltro(Pattern pattern) { //00068223 Generar filtro para el patron especificado
        return change -> { //00068223 Crear un objeto de cambio
            String newText = change.getControlNewText(); //00068223 Obtener el nuevo texto del control
            if (pattern.matcher(newText).matches()) { //00068223 Validar si el nuevo texto coincide con el patron
                return change; //00068223 Retornar el cambio si coincide
            }
            return null; //00068223 Retornar null si no coincide
        };
    }

    private String formatearNumeroTarjeta(String numero) { //00068223 Funcion para formatear el numero de tarjeta
        StringBuilder sb = new StringBuilder(numero); //00068223 Crear un StringBuilder con el numero de tarjeta
        if (sb.length() > 4 && sb.charAt(4) != '-') { //00068223 Validar si la longitud es mayor a 4 y el caracter en la posicion 4 no es '-'
            sb.insert(4, '-'); //00068223 Insertar '-' en la posicion 4
        }
        if (sb.length() > 9 && sb.charAt(9) != '-') { //00068223 Validar si la longitud es mayor a 9 y el caracter en la posicion 9 no es '-'
            sb.insert(9, '-'); //00068223 Insertar '-' en la posicion 9
        }
        if (sb.length() > 14 && sb.charAt(14) != '-') { //00068223 Validar si la longitud es mayor a 14 y el caracter en la posicion 14 no es '-'
            sb.insert(14, '-'); //00068223 Insertar '-' en la posicion 14
        }
        return sb.toString(); //00068223 Retornar el numero formateado
    }

    private void cargarTiposTarjeta() { //00068223 Funcion para cargar los tipos de tarjeta en el comboBox cmbTipoTarjeta
        cmbTipoTarjeta.getItems().addAll("Debito", "Credito"); //00068223 Agregar "Debito" y "Credito" al comboBox
    }

    @FXML
    private void onBtnGuardarTarjetaClick() { //00068223 Funcion para manejar el evento de click en el boton guardar tarjeta
        if (!txtNumeroTarjeta.getText().isBlank() && cmbFacilitador.getValue() != null && cmbNombreCliente.getValue() != null && dpFechaExpiracion.getValue() != null) { //00068223 Verifica si los campos no estan vacios
            String numeroTarjeta = txtNumeroTarjeta.getText(); //00068223 Obtener el numero de tarjeta del campo de texto
            Date fechaExpiracion = Date.valueOf(dpFechaExpiracion.getValue()); //00068223 Obtener la fecha de expiracion del DatePicker
            Facilitador facilitador = cmbFacilitador.getValue(); //00068223 Obtener el facilitador seleccionado
            Cliente cliente = cmbNombreCliente.getValue(); //00068223 Obtener el cliente seleccionado
            String tipoTarjeta = cmbTipoTarjeta.getValue(); //00068223 Obtener el tipo de tarjeta seleccionado

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
                    Alerts.showAlert("exito", "Se ha ingresado la tarjeta satisfactoriamente", 1); //00068223 Muestra alerta de exito si la insercion fue exitosa
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

    private int obtenerClienteId(String nombre) { //00068223 Funcion para obtener el ID del cliente basado en su nombre
        int id = -1; //00068223 Inicializar el ID del cliente con -1
        try { //00068223 Inicio del bloque try para manejar excepciones SQL
            PreparedStatement ps = conexion.conectar().prepareStatement("SELECT id FROM tbCliente WHERE CONCAT(nombre, ' ', apellido) = ?"); //00068223 Prepara la consulta SQL para obtener el ID del cliente
            ps.setString(1, nombre); //00068223 Asigna el nombre del cliente al primer parametro de la consulta
            ResultSet rs = ps.executeQuery(); //00068223 Ejecuta la consulta SQL
            if (rs.next()){ //00068223 Verifica si se encontraron resultados
                id = rs.getInt("id"); //00068223 Asigna el ID del cliente al valor obtenido en la consulta
            }
            conexion.cerrarConexion(); //00068223 Cierra la conexion a la base de datos
        } catch (SQLException e) { //00068223 Captura las excepciones SQL que ocurran en el bloque try
            System.out.println("Error de conexion: " + e); //00068223 Imprime el mensaje de error de conexion en la consola
        }
        return id; //00068223 Retorna el ID del cliente
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
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
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

    private void cargarTipoTarjeta(){ //00068223 Funcion para cargar los tipos de tarjeta en el comboBox cmbTipoTarjeta
        cmbTipoTarjeta.getItems().addAll("Debito", "Credito"); //00068223 Agregar "Debito" y "Credito" al comboBox
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