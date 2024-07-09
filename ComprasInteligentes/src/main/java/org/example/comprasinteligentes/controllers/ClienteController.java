package org.example.comprasinteligentes.controllers; // 00107223 Paquete al que pertenecen todos los controladores de las vistas

import javafx.fxml.FXML; // 00107223 Librería vital para que se puedan llamar elementos FXML al controlador
import javafx.scene.control.*; // 00107223 Librería que permite crear objetos de los controles en la vista (button, textField, etc....)
import javafx.scene.control.cell.PropertyValueFactory; // 00107223 Librería que permite acceder al PropertyValueFactory para insertar datos del objeto cliente a sus respectivas columnas en tableView
import javafx.collections.ObservableList; // 00107223 Librería que permite acceder a las listas observables útiles para realizar modificaciones a datos ya insertados en tableView
import javafx.stage.Stage;
import org.example.comprasinteligentes.Alerts;
import org.example.comprasinteligentes.Conexion; // 00107223 Importar la clase conexión que maneja la conexión con la base de datos
import org.example.comprasinteligentes.OpenWindows;
import org.example.comprasinteligentes.clases.Cliente; // 00107223 Importar la clase Cliente, para insertar al tableView y manejar operaciones de tipo Cliente.
import org.example.comprasinteligentes.views.ComprasApplication;
import org.example.comprasinteligentes.views.ReporteApplication;
import org.example.comprasinteligentes.views.TarjetaApplication;

import java.sql.*; // 00107223 Acceder a todas la funciónes tipo SQL
import java.util.function.UnaryOperator; // 00107223 Función Unaria para la creación de los filtros del textField
import java.util.regex.Pattern; // 00107223 Acceder al tipo Pattern para crear patrones para los filtros

public class ClienteController { // 00107223 Controlador de la vista 'cliente.fxml', maneja todas las funciones utilizadas en el CRUD, es initializable para realizar validaciones y cargar elementos.
    private static Conexion conexion = Conexion.getInstance(); // 00107223 llamo una instancia global para la conexión
    @FXML
    private TextField txtNombre; // 00107223 objeto TextField txtNombre para obtener datos
    @FXML
    private TextField txtApellido;// 00107223 objeto TextField txtApellido para obtener datos
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
    private TableColumn<Cliente, String> identificador; // 00107223 objeto TableColumn para identificar en el controller

    @FXML
    private void agregarCliente(){ // 00107223 Función onAction, que maneja la inserción de los clientes en la Base de datos como en el tableView
        Cliente cliente = new Cliente(0, txtNombre.getText(), txtApellido.getText(), txtDireccion.getText(), txtTelefono.getText()); // 00107223 Capturo todos los campos necesarios para crear un Nuevo Cliente, debido a que el id es autoincrementable en BD, se deja el id quemado
        try{ // 00107223 try necesario para la conexión a la base de datos
            int result; // 00107223 una variable entera para determinar si la operación fue exitosa
            PreparedStatement ps = conexion.conectar().prepareStatement("INSERT INTO tbCLIENTE (NOMBRE, APELLIDO, NUMEROTELEFONO, DIRECCION) VALUES (?,?,?,?)"); // 00107223 Se crea un PreparedStatement para insertar datos a la tabla tbCliente
            ps.setString(1, cliente.getNombre()); // 00107223 set del primer valor a insertar a tbCliente: nombre
            ps.setString(2, cliente.getApellido()); // 00107223 set del segundo valor a insertar a tbCliente: apellido
            ps.setString(3, cliente.getNumeroTelefono()); // 00107223 set del tercer valor a insertar a tbCliente: numerotelefono
            ps.setString(4, cliente.getDireccion()); // 00107223 set del cuarto valor a insertar a tbCliente: dirección
            result = ps.executeUpdate(); // 00107223 result captura el valor que devuelve la operación, si es mayor a cero, es exitosa, si es cero, no fue exitosa

            //PlaceHolder creo que lo cambiaremos a un alert
            if (result > 0){ // 00107223 Instrucciones si la operación es exitosa
                Statement st = conexion.conectar().createStatement(); // 00107223 Se crea una sentencia para obtener el identificador del cliente que se acaba de agregar
                ResultSet rs = st.executeQuery("SELECT ID FROM tbCliente order by ID desc limit 1"); // 00107223 Se obtiene el ID del cliente más reciente (el que colocamos en el CRUD)
                if (rs.next()){ // 00107223 si se obtuvo ID entonces se ejecuta estas instrucciones
                    cliente.setId(rs.getInt("ID")); // 00107223 modificar ID del cliente local con el ID de la BD
                }
                tbListadoCliente.getItems().add(cliente); // 00107223 si la operación es exitosa, la tableView agregará al cliente a su lista.
            }
            System.out.println(result>0 ? "Exito" : "Fracaso"); // 00107223 una impresión en la consola si fue exitosa o no la operación
            limpiar(); // 00107223 Llamar función limpiar todos los campos
            conexion.cerrarConexion(); // 0017223 Cerrar la conexión a la base de datos
        } catch (SQLException e){ // 00107223 catch necesario para capturar errores que ocurran durante la conexión
            System.out.println("error de conexion: " + e); // 00107223 se imprime el mensaje de error de la base de datos
        }
    }
    @FXML
    private void eliminarCliente(){ // 00107223 Función onAction, que maneja la eliminación de datos en la base de datos como en el tableView
        try{ // 00107223 try necesario para la conexión a la base de datos
            int result; // 00107223 una variable entera para determinar si la operación fue exitosa
            PreparedStatement ps = conexion.conectar().prepareStatement("DELETE FROM tbCLIENTE WHERE ID=?"); // 00107223 se crea un PreparedStatement para eliminar un registro de la tabla Cliente mediante ID proporcionado
            ps.setInt(1, Integer.parseInt(txtIDCliente.getText())); // 00107223 Se ingresa el ID extraído del txtIDCliente al parámetro
            result = ps.executeUpdate(); // 00107223 result captura el valor que devuelve la operación, si es mayor a cero, es exitosa, si es cero, no fue exitosa

            if (result > 0){ // 00107223 Instrucciones si la operación es exitosa
                int id = buscarClienteTabla(Integer.parseInt(txtIDCliente.getText())); // 00107223 obtener el ID de la fila en el tableView para realizar las modificaciónes correspondientes
                if (id != -1) { // 00107223 Si no se encuentra la fila entonces la función retornará -1, de ser así no se modifica nada en el tableView
                    tbListadoCliente.getItems().remove(id); // 00107223 obtiene todas las filas de la tableView y remueve una por su ID de fila, (diferente al ID del cliente)
                    tbListadoCliente.refresh(); // 00107223 Refrescar la tabla para mostrar los cambios de la tabla
                }
            }
            //PlaceHolder creo que lo cambiaremos a un alert
            System.out.println(result>0 ? "Exito" : "Fracaso"); // 00107223 una impresión en la consola si fue exitosa o no la operación
            limpiar(); // 00107223 Llamar función limpiar todos los campos
            conexion.cerrarConexion(); // 0017223 Cerrar la conexión a la base de datos
        } catch (SQLException e){ // 00107223 catch necesario para capturar errores que ocurran durante la conexión
            System.out.println("error de conexion: " + e); // 00107223 se imprime el mensaje de error de la base de datos
        }
    }

    @FXML
    private void modificarCliente(){ // 00107223 Función onAction, que maneja la modificación de los registros en la base de datos como en la tableView
        try{ // 00107223 try necesario para la conexión a la base de datos
            int result; // 00107223 una variable entera para determinar si la operación fue exitosa
            PreparedStatement ps = conexion.conectar().prepareStatement("UPDATE tbCLIENTE SET NOMBRE=?, APELLIDO=?, NUMEROTELEFONO=?, DIRECCION=? WHERE ID=?"); // 00107223 se crea un PreparedStatement para modificar el registro cliente mediante su ID.
            ps.setString(1, txtNombre.getText()); // 00107223 set del primer valor a modificar a tbCliente: nombre
            ps.setString(2, txtApellido.getText()); // 00107223 set del segundo valor a modificar a tbCliente: apellido
            ps.setString(3, txtTelefono.getText()); // 00107223 set del tercero valor a modificar a tbCliente: numerotelefono
            ps.setString(4, txtDireccion.getText()); // 00107223 set del cuarto valor a modificar a tbCliente: dirección
            ps.setInt(5, Integer.parseInt(txtIDCliente.getText())); // 00107223 set del parámetro para modificar a tbCliente: ID
            result = ps.executeUpdate(); // 00107223 result captura el valor que devuelve la operación, si es mayor a cero, es exitosa, si es cero, no fue exitosa

            if (result > 0){ // 00107223 Instrucciones si la operación es exitosa
                int id = buscarClienteTabla(Integer.parseInt(txtIDCliente.getText())); // 00107223 obtener el ID de la fila en el tableView para realizar las modificaciónes correspondientes
                if (id != -1) { // 00107223 Si no se encuentra la fila entonces la función retornará -1, de ser así no se modifica nada en el tableView
                    Cliente cliente = tbListadoCliente.getItems().get(id); // 00107223 obtiene el objeto cliente respectivo al ID de la fila, esto para modificar localmente los cambios hechos al cliente.
                    cliente.setId(Integer.parseInt(txtIDCliente.getText())); // 00107223 set del nuevo ID para el objeto cliente.
                    cliente.setNombre(txtNombre.getText()); // 00107223 set del nuevo nombre para el objeto cliente.
                    cliente.setApellido(txtApellido.getText()); // 00107223 set del nuevo apellido para el objeto cliente.
                    cliente.setNumeroTelefono(txtTelefono.getText()); // 00107223 set del nuevo número de teléfono para el objeto cliente.
                    cliente.setDireccion(txtDireccion.getText()); // 00107223 set de la nueva dirección para el objeto cliente.

                    tbListadoCliente.refresh(); // 00107223 Refrescar la tabla para mostrar los cambios de la tabla
                }
            }

            //PlaceHolder creo que lo cambiaremos a un alert
            System.out.println(result>0 ? "Exito" : "Fracaso"); // 00107223 una impresión en la consola si fue exitosa o no la operación
            limpiar(); // 00107223 Llamar función limpiar todos los campos
            conexion.cerrarConexion(); // 0017223 Cerrar la conexión a la base de datos
        } catch (SQLException e){ // 00107223 catch necesario para capturar errores que ocurran durante la conexión
            System.out.println("error de conexion: " + e); // 00107223 se imprime el mensaje de error de la base de datos
        }
    }

    @FXML
    private void cargarDatosCliente(){ // 00107223 Función onAction, que maneja la consulta para obtener todos los registros provenientes de la base de datos y las imprime en la tableView
        tbListadoCliente.getItems().clear(); // 00107223 Limpia el tableView pasado para evitar la repetición de datos
        try{ // 00107223 try necesario para la conexión a la base de datos
            Statement st = conexion.conectar().createStatement(); // 00107223 Se crea una sentencia para obtener todos los registros de la tabla cliente
            ResultSet rs = st.executeQuery("SELECT ID, NOMBRE, APELLIDO, DIRECCION, NUMEROTELEFONO FROM tbCLIENTE"); // 00107223 se obtienen todos los datos de la tabla cliente
            while (rs.next()){ // 00107223 ejecutará todas las instrucciones mientras se encuentren más registros
                Cliente cliente = new Cliente(rs.getInt("ID"), rs.getString("NOMBRE"), rs.getString("APELLIDO"), rs.getString("DIRECCION"), rs.getString("NUMEROTELEFONO")); // 00107223 se crea un nuevo objeto cliente por cada registro, captura todos los datos de la base de datos y son guardados localmente en el objeto.
                tbListadoCliente.getItems().add(cliente); // 00107223 se agrega el cliente recién creado a su propia fila de la tableView
            }
            conexion.cerrarConexion(); // 0017223 Cerrar la conexión a la base de datos
        } catch (SQLException e){ // 00107223 catch necesario para capturar errores que ocurran durante la conexión
            System.out.println("error de conexion: " + e); // 00107223 se imprime el mensaje de error de la base de datos
        }
    }

    @FXML
    public void initialize() { // 00107233 Manejar validaciones a la hora de iniciar la vista
        // Tabla
        nombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre")); // 00107223 asignarle una value factory a la columna para que utilize el atributo nombre para guardarlos
        apellido.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellido")); // 00107223 asignarle una value factory a la columna para que utilize el atributo apellido para guardarlos
        numeroTelefono.setCellValueFactory(new PropertyValueFactory<Cliente, String>("numeroTelefono")); // 00107223 asignarle una value factory a la columna para que utilize el atributo numero telefono para guardarlos
        direccion.setCellValueFactory(new PropertyValueFactory<Cliente, String>("direccion")); // 00107223 asignarle una value factory a la columna para que utilize el atributo direccion para guardarlos
        identificador.setCellValueFactory(new PropertyValueFactory<Cliente, String>("id")); // 00107223 asignarle una value factory a la columna para que utilize el atributo id para guardarlos
        initTelefono(); // 00107223 llamada a validación del txtTelefono
        initID(); // 00107223 llamada a validación del txtIDCliente
    }


    private void obtenerCliente(int ID){ // 00107223 Función void, obtiene un ID digitado y realiza una consulta para buscar los datos del cliente relacionado con ese ID.
        try{ // 00107223 try necesario para la conexión a la base de datos
            Statement st = conexion.conectar().createStatement(); // 00107223 Se crea una sentencia para obtener los datos del cliente por su ID
            ResultSet rs = st.executeQuery("SELECT ID, NOMBRE, APELLIDO, DIRECCION, NUMEROTELEFONO FROM tbCLIENTE WHERE ID ="+ID+";"); // 00107223 se obtienen todos los datos del cliente por medio de la ID digitada en txtIDCliente.

            if (rs.next()){ // 00107223 Si el resultSet obtuvo un registro entonces realizara las siguientes instrucciones
                txtNombre.setText(rs.getString("NOMBRE")); // 00107223 colocar el nombre obtenido de la base datos al txtNombre
                txtApellido.setText(rs.getString("APELLIDO"));  // 00107223 colocar el apellido obtenido de la base datos al txtApellido
                txtTelefono.setText(rs.getString("NUMEROTELEFONO"));  // 00107223 colocar el número de teléfono obtenido de la base datos al txtTelefono
                txtDireccion.setText(rs.getString("DIRECCION"));  // 00107223 colocar la dirección obtenida de la base datos al txtDireccion
            }

            conexion.cerrarConexion(); // 0017223 Cerrar la conexión a la base de datos
        } catch (SQLException e){ // 00107223 catch necesario para capturar errores que ocurran durante la conexión
            System.out.println("error de conexion: " + e); // 00107223 se imprime el mensaje de error de la base de datos
        }

    }

    private int buscarClienteTabla(int ID){ // 00107223 Función que devuelve el índice de la tableView para ser modificada o eliminada
        ObservableList<Cliente> clientes = tbListadoCliente.getItems(); // 00107223 Obtener un Listado de los clientes
        for (Cliente cliente : clientes){ // 00107223 un bucle foreach qué cliente por cliente para buscar cliente que tenga el ID que coincida con el insertado
            if (cliente.getId() == ID){ // 00107223 un condicional que válida si los ID coinciden
                return clientes.indexOf(cliente); // 00107223 se devuelve el indice de la fila del cliente
            }
        }
        return -1; // 00107223 se devuelve un índice fuera de los límites, por lo que no removería nada
    }


    private void limpiar(){ // 00107223 Función para limpiar cada campo de la vista.
        txtTelefono.setText(""); // 00107223 asignarle texto vacío al textfield
        txtTelefono.setPromptText("XXXX-XXXX"); // 00107223 asignarle texto referencia al textfield
        txtApellido.setText(""); // 00107223 asignarle texto vacío al textfield
        txtNombre.setText(""); // 00107223 asignarle texto vacío al textfield
        txtDireccion.setText(""); // 00107223 asignarle texto vacío al textarea
        txtIDCliente.setText(""); // 00107223 asignarle texto vacío al textfield
    }

    private void initTelefono(){ // 00107223 Funcion para la validacion del campo número teléfono, siguiendo el patron XXXX-XXXX, donde cada "X" representa un número del 0-9
        Pattern pattern = Pattern.compile("\\d{0,4}-?\\d{0,4}"); // 00107223 Crear un patron para el textField del número de teléfono, \\d representa los números 0-9 {0,4} que deben ser 4 digitos, es separado por un "-"

        UnaryOperator<TextFormatter.Change> filtro  = generarFiltro(pattern); // 00107223 Se obtiene el filtro basándonos en el patron que se busca implementar

        TextFormatter<String> formatter = new TextFormatter<>(filtro); // 00107223 Crear un objeto textFormatter para aplicarlo en el txtTelefono, este contiene el formato de filtro, que contiene el patron XXXX-XXXX
        txtTelefono.setTextFormatter(formatter); // 00107723 Asignacion del formato ya validado.

        txtTelefono.textProperty().addListener((observable, oldValue, newValue) -> { // 00107223 Asignación de un addListener para cada vez que haya un input del teclado aplicar validaciones
            if ((newValue.length() == 4) && !newValue.contains("-")) { // 00107223 Condicional donde si la cadena en el textfield ya tiene 4 dígitos, le agregue automáticamente el "-"
                txtTelefono.setText(newValue + "-"); // 00107223 insertarle al textField los 4 dígitos ya ingresados + el nuevo "-"
            } else if (newValue.length() > 4 && !newValue.contains("-")) { // 00107223 Condicional donde se valida la entrada
                txtTelefono.setText(newValue.substring(0,4) + "-" + newValue.substring(4)); // 00107223 insertarle al textField los 4 dígitos ya ingresados + "-" + los posibles 4 digitos que le sigan
            }
        });
    }

    private void initID(){
        Pattern pattern = Pattern.compile("\\d*"); // 00107223 Crear un patron para el textField del ID, \\d representa los numeros 0-9

        UnaryOperator<TextFormatter.Change> filtro =  generarFiltro(pattern); // 00107223 Se obtiene el filtro basándonos en el patron que se busca implementar

        TextFormatter<String> formatter = new TextFormatter<>(filtro); // 00107223 Crear un objeto textFormatter para aplicarlo en el txtIDCliente, este contiene el formato de filtro, que contiene el patron de solo enteros
        txtIDCliente.setTextFormatter(formatter); // 00107723 Asignación del formato ya validado.

        txtIDCliente.textProperty().addListener((observable, oldValue, newValue) ->{ // 00107223 Crear un addListener que al ingresar un numero llame a la función de obtenerCliente
            obtenerCliente(Integer.parseInt(txtIDCliente.getText())); // 00107223 se llama a la función que busca al cliente en la Base de datos y llenar los campos para comodidad al modificar/borrar
        });
    }

    private UnaryOperator<TextFormatter.Change> generarFiltro(Pattern pattern) { // 00107223 Función unaria que sirve para filtrar los cambios hechos dentro del textField, recibe TextFormatter.Change que chequea cada vez que hay una modificacion en cada input del textField.
        return change -> { // 00107223 retornará si el cambio que se produjo es válido o no
            String nuevoTexto = change.getControlNewText(); // 00107223 se captura y convierte en cadena el texto escrito en el textField para poder procesarlo
            if (pattern.matcher(nuevoTexto).matches()){ // 00107223 una condicional que verifica si el texto escrito coincide con el patron ya definido
                return change; // 00107223 Si el texto coincide con el patron permite los cambios al textField
            } else {
                return null; // 00107223 Si se cumple, envía un nulo por lo que nada se escribe en el textField
            }
        };
    }
    @FXML
    private void onBtnTarjetasClick() { // 00016623 Método para manejar el evento de click en el botón de btnTarjetas del menu,
        OpenWindows.openWindow(tbListadoCliente, 4);// 00016623 llamando método utilitario para abrir ventanas
    }

    @FXML
    private void onBtnComprasClick() { // 00016623 Método para manejar el evento de click en el botón de Compras,
        OpenWindows.openWindow(tbListadoCliente, 2);// 00016623 llamando método utilitario para abrir ventanas
    }

    @FXML
    private void onBtnReportesClick() { // 00016623 Método para manejar el evento de click en el botón de Reporte,
        OpenWindows.openWindow(tbListadoCliente, 3);// 00016623 llamando método utilitario para abrir ventanas
    }
}