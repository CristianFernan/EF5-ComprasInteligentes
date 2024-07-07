package org.example.comprasinteligentes.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.comprasinteligentes.Alerts;
import org.example.comprasinteligentes.Conexion;
import org.example.comprasinteligentes.clases.Compra;
import org.example.comprasinteligentes.clases.CompraCustom;
import org.example.comprasinteligentes.clases.Tarjeta;
import java.sql.*;
import java.time.LocalDate;

public class ComprasController{//00016623 Clase controladora para manejar la lógica de las compras
    private static Conexion conexion = Conexion.getInstance(); //00016623 Obtenemos instancia única singleton de conexion
    @FXML
    private TextField txtMonto; //00016623 objeto TextField txtMonto de view para obtener datos
    @FXML
    private TextArea txtADescripcion;//00016623 objeto TextArea txtADescripcion de view para obtener datos
    @FXML
    private DatePicker dpFecha ;//00016623 objeto DatePicker dpFecha de view para obtener datos
    @FXML
    private ComboBox<Tarjeta> cmbTarjeta; //00016623 ComboBox de tipo Tarjeta cmbTarjeta para llenar de tarjetas existentes, y obtener datos
    @FXML
    private TableView<CompraCustom> tbListadoCompra; //00016623 objeto TableView para acceder en el controller
    @FXML
    private TableColumn<CompraCustom, Double> monto; //00016623 objeto TableColumn para acceder en el controller
    @FXML
    private TableColumn<CompraCustom, String> descripcion; //00016623 objeto TableColumn para acceder en el controller
    @FXML
    private TableColumn<CompraCustom, LocalDate> fecha; //00016623 objeto TableColumn para acceder en el controller
    @FXML
    private TableColumn<CompraCustom, String> tarjeta; //00016623 objeto TableColumn para acceder en el controller
    @FXML
    private TableColumn<CompraCustom, String> cliente; //00016623 objeto TableColumn para acceder en el controller


    @FXML
    public void initialize() { //00016623 Función de inicialización de la vista que se ejecuta antes de lo demás para pre configuraciones necesarias.
        monto.setCellValueFactory(new PropertyValueFactory<>("monto")); //00016623 asignarle una value factory a la columna para que utilize el atributo nombre para guardarlos
        descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));//00016623 asignarle una value factory a la columna para que utilize el atributo nombre para guardarlos
        fecha.setCellValueFactory(new PropertyValueFactory<>("fecha")); //00016623 asignarle una value factory a la columna para que utilize el atributo apellido para guardarlos
        tarjeta.setCellValueFactory(new PropertyValueFactory<>("tarjeta")); //00016623 asignarle una value factory a la columna para que utilize el atributo numero telefono para guardarlos
        cliente.setCellValueFactory(new PropertyValueFactory<>("cliente")); //00016623 asignarle una value factory a la columna para que utilize el atributo direccion para guardarlos
        cargarTarjetas(); //00016623 llamado de función para rellenar cmb de tarjetas
    }

    @FXML
    private void onBtnAgregarCompraClick(){ //00016623 Función para manejar el evento de click en el botón agregar compra
        if(!txtMonto.getText().isBlank() || !txtADescripcion.getText().isBlank() || cmbTarjeta.getValue() != null || dpFecha.getValue() != null){ // 00016623 Verifica si los campos no están vacíos
            Compra compra = new Compra(Date.valueOf(dpFecha.getValue()), Double.parseDouble(txtMonto.getText()), txtADescripcion.getText(), cmbTarjeta.getValue()); // 00016623 Crea una nueva instancia de Compra de los datos ingresados
            try { //00016623 Inicio del bloque try para manejar excepciones SQL
                int result; //00016623 Variable para almacenar el resultado de la ejecución de la consulta
                PreparedStatement ps = conexion.conectar().prepareStatement("INSERT INTO tbCompra (fechaCompra, montoTotal, descripcion, idTarjeta) VALUES (?,?,?,?)"); // 00016623 Prepara la consulta SQL para insertar una compra
                ps.setDate(1, compra.getFechaCompra()); //00016623 Asigna la fecha de la compra al primer parámetro de la consulta
                ps.setDouble(2, compra.getMonto()); //00016623 Asigna el monto de la compra al segundo parámetro de la consulta
                ps.setString(3, compra.getDescripcion()); //00016623 Asigna la descripción de la compra al tercer parámetro de la consulta
                ps.setInt(4, compra.getTarjeta().getId()); //00016623 Asigna el ID de la tarjeta al cuarto parámetro de la consulta

                result = ps.executeUpdate(); //00016623 Ejecuta la consulta SQL
                if(result > 0 ) Alerts.showAlert("Éxito", "Se ha ingresado la compra satisfactoriamente", 1); //00016623 Muestra alerta de éxito si la inserción fue exitosa
                else Alerts.showAlert("Fracaso", "Ocurrió un error al ingresar la compra", 3); //00016623 Muestra alerta de fracaso si la inserción falló
                conexion.cerrarConexion(); //00016623 Cierra la conexión a la base de datos
            } catch (SQLException e) {//00016623 Captura las excepciones SQL que ocurran en el bloque try
                Alerts.showAlert("Error", "Error de conexión", 3); //00016623 Muestra alerta de error de conexión
                System.out.println("Error de conexión " + e.getMessage()); // 00016623 Imprime el mensaje de error de conexión en la consola
            }

            cargarDatosCompra(); //00016623 Carga los datos de la compra en la vista
            limpiar(); //00016623 Limpia los campos del formulario
        } else { //00016623 else que se ejecuta cuando hay algún campo vacío
            Alerts.showAlert("Campos incompletos", "Favor llenar todos los campos necesarios para la creación de la transacción", 2); //00016623 Muestra alerta de campos incompletos
        }
    }
    private void cargarDatosCompra(){ //00016623 Función para cargar los datos de las compras en la tabla
        tbListadoCompra.getItems().clear(); //00016623 Limpia los elementos de la tabla de compras
        try{ //00016623 Inicio del bloque try para manejar excepciones SQL
            Statement st = conexion.conectar().createStatement(); //00016623 Crea una declaración SQL para ejecutar consultas
            ResultSet rs = st.executeQuery("select montoTotal as monto, descripcion, fechaCompra as fecha, concat(t.tipo, ', ', f.facilitador) as tarjeta, concat (cl.nombre, ' ', cl.apellido) as cliente\n" +
                    "from tbCompra c inner join tbtarjeta t on c.idTarjeta = t.id inner join tbfacilitador f on t.idFacilitador = f.id inner join tbcliente cl on t.idCliente = cl.id"); // 00016623 Ejecuta una consulta SQL para obtener los datos de las compras
            while (rs.next()){ //00016623 Itera sobre los resultados de la consulta
                CompraCustom compra = new CompraCustom(rs.getDouble("monto"), rs.getString("descripcion"), rs.getDate("fecha"), rs.getString("tarjeta"), rs.getString("cliente")); // 00016623 Crea una nueva instancia de CompraCustom con los datos obtenidos de la query
                tbListadoCompra.getItems().add(compra); //00016623 Agrega la compra a la lista de la tabla
            }
            conexion.cerrarConexion(); //00016623 Cierra la conexión a la base de datos
        } catch (SQLException e){ //00016623 Captura las excepciones SQL que ocurran en el bloque try
            Alerts.showAlert("Error", "Error de conexión", 3); //00016623 Muestra alerta de error de conexión
            System.out.println("error de conexion: " + e); //00016623 Imprime el mensaje de error de conexión en la consola
        }
    }
    private void cargarTarjetas(){ //00016623 Función para cargar las tarjetas en el comboBox cmbTarjeta
        try { //00016623 Inicio del bloque try para manejar excepciones SQL
            Statement st = conexion.conectar().createStatement(); //00016623 Crea una declaración SQL para ejecutar consultas
            ResultSet rs = st.executeQuery("select id, numeroTarjeta, fechaExpiracion, tipo from tbtarjeta"); // 00016623 Ejecuta una consulta SQL para obtener los datos de las tarjetas
            while (rs.next()){ //00016623 Itera sobre los resultados de la consulta
                cmbTarjeta.getItems().add(new Tarjeta(rs.getInt("id"), rs.getString("numeroTarjeta"), rs.getDate("fechaExpiracion"), rs.getString("tipo"))); // 00016623 Agrega una nueva Tarjeta al comboBox con los datos obtenidos
            }
            conexion.cerrarConexion(); //00016623 Cierra la conexión a la base de datos
        } catch (SQLException e){ //00016623 Captura las excepciones SQL que ocurran en el bloque try
            Alerts.showAlert("Error", "Error de conexión", 3); //00016623 Muestra alerta de error de conexión
            System.out.println("Error de conexión " + e.getMessage()); //00016623 Imprime el mensaje de error de conexión en la consola
        }
    }

    private void limpiar(){ //00016623 Función para limpiar los campos del formulario
        txtADescripcion.setText(""); //00016623 Limpia el campo de texto de descripcion
        txtMonto.setText(""); //00016623 Limpia el campo de texto de monto
        cmbTarjeta.setValue(null); //00016623 Restablece el comboBox de tarjetas a su valor por defecto
        dpFecha.setValue(null); //00016623 Restablece el selector de fecha a su valor por defecto
    }

    @FXML
    private void onBtnlimpiarClick(){ //00016623 Función para manejar el evento de click en el boton limpiar
        limpiar(); //00016623 Llama al método limpiar para restablecer los campos del formulario
    }

    @FXML
    private void onBtnCargarDatosClick(){ //00016623 Función para manejar el evento de click en el boton cargar datos
        cargarDatosCompra(); //00016623 Llama al método cargarDatosCompra para actualizar la tabla de compras
    }

}