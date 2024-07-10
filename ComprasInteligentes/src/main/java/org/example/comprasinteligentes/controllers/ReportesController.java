package org.example.comprasinteligentes.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.comprasinteligentes.Alerts;
import org.example.comprasinteligentes.Conexion;
import org.example.comprasinteligentes.OpenWindows;
import org.example.comprasinteligentes.clases.CompraCustom;
import org.example.comprasinteligentes.clases.Tarjeta;
import org.example.comprasinteligentes.views.ClienteApplication;
import org.example.comprasinteligentes.views.ReporteApplication;
import org.example.comprasinteligentes.views.TarjetaApplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

public class ReportesController { //00083723 Controlador para gestionar reportes
    private static Conexion conexion = Conexion.getInstance(); //00083723 Obtiene la instancia de conexion a la base de datos
    private final  String reportesRuta = "./src/main/java/org/example/comprasinteligentes/reportes"; // 00107223 se declara la ruta hacia la carpeta reportes, debido a que todos los reportes se encontraran en la misma carpeta
    @FXML //00083723 Inyecta el ComboBox de clientes para el reporte A
    private ComboBox<String> cmbClienteA; //00083723 ComboBox para seleccionar el cliente en el reporte A
    @FXML //00083723 Inyecta el DatePicker para la fecha inicial en el reporte A
    private DatePicker dpFechaInicialA; //00083723 DatePicker para seleccionar la fecha inicial en el reporte A
    @FXML //00083723 Inyecta el DatePicker para la fecha final en el reporte A
    private DatePicker dpFechaFinalA; //00083723 DatePicker para seleccionar la fecha final en el reporte A
    @FXML //00083723 Inyecta el TableView para mostrar las compras del cliente en el reporte A
    private TableView<CompraCustom> tbComprasClienteA; //00083723 TableView para mostrar las compras en el reporte A
    @FXML //00083723 Inyecta la columna del ID de la compra en el reporte A
    private TableColumn<CompraCustom, Integer> colIDCompraA; //00083723 TableColumn para el ID de la compra en el reporte A
    @FXML //00083723 Inyecta la columna del monto en el reporte A
    private TableColumn<CompraCustom, Double> colMontoA; //00083723 TableColumn para el monto en el reporte A
    @FXML //00083723 Inyecta la columna de la descripcion en el reporte A
    private TableColumn<CompraCustom, String> colDescripcionA; //00083723 TableColumn para la descripcion en el reporte A
    @FXML //00083723 Inyecta la columna de la fecha en el reporte A
    private TableColumn<CompraCustom, Date> colFechaA; //00083723 TableColumn para la fecha en el reporte A
    @FXML //00083723 Inyecta el boton para buscar compras en el reporte A
    private Button btnBuscarCompras; //00083723 Boton para buscar compras en el reporte A

    @FXML //00083723 Inyecta el ComboBox de clientes para el reporte B
    private ComboBox<String> cmbClienteB; //00083723 ComboBox para seleccionar el cliente en el reporte B
    @FXML //00083723 Inyecta el campo de texto para el mes en el reporte B
    private TextField txtMesB; //00083723 TextField para ingresar el mes en el reporte B
    @FXML //00083723 Inyecta el campo de texto para el año en el reporte B
    private TextField txtAnioB; //00083723 TextField para ingresar el año en el reporte B
    @FXML //00083723 Inyecta la etiqueta para mostrar el total gastado en el reporte B
    private Label lblTotalGastadoB; //00083723 Label para mostrar el total gastado en el reporte B

    @FXML //00083723 Inyecta el ComboBox de clientes para el reporte C
    private ComboBox<String> cmbClienteC; //00083723 ComboBox para seleccionar el cliente en el reporte C
    @FXML //00083723 Inyecta el area de texto para mostrar las tarjetas asociadas en el reporte C
    private TextArea txtAreaTarjetasClienteC; //00083723 TextArea para mostrar las tarjetas asociadas en el reporte C
    @FXML //00083723 Inyecta el boton para ver las tarjetas asociadas en el reporte C
    private Button idTarjetasAsociadas; //00083723 Boton para ver las tarjetas asociadas en el reporte C

    @FXML //00083723 Inyecta el ComboBox de facilitadores para el reporte D
    private ComboBox<String> cmbFacilitadorD; //00083723 ComboBox para seleccionar el facilitador en el reporte D
    @FXML //00083723 Inyecta el TableView para mostrar los clientes del facilitador en el reporte D
    private TableView<CompraCustom> tbClientesFacilitadorD; //00083723 TableView para mostrar los clientes en el reporte D
    @FXML //00083723 Inyecta la columna del ID del cliente en el reporte D
    private TableColumn<CompraCustom, Integer> colIDClienteD; //00083723 TableColumn para el ID del cliente en el reporte D
    @FXML //00083723 Inyecta la columna del nombre del cliente en el reporte D
    private TableColumn<CompraCustom, String> colNombreClienteD; //00083723 TableColumn para el nombre del cliente en el reporte D
    @FXML //00083723 Inyecta la columna de la cantidad de compras en el reporte D
    private TableColumn<CompraCustom, Integer> colCantidadComprasD; //00083723 TableColumn para la cantidad de compras en el reporte D
    @FXML //00083723 Inyecta la columna del total gastado en el reporte D
    private TableColumn<CompraCustom, Double> colTotalGastadoD; //00083723 TableColumn para el total gastado en el reporte D

    @FXML //00083723 Metodo que se ejecuta al inicializar el controlador
    private void initialize() {
        colIDCompraA.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdCompra()).asObject()); //00083723 Configura la columna ID de compra
        colMontoA.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getMonto()).asObject()); //00083723 Configura la columna monto
        colDescripcionA.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion())); //00083723 Configura la columna descripcion
        colFechaA.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFecha())); //00083723 Configura la columna fecha

        colIDClienteD.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdCliente()).asObject()); //00083723 Configura la columna ID cliente
        colNombreClienteD.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion())); //00083723 Configura la columna nombre cliente
        colCantidadComprasD.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCantidadCompras()).asObject()); //00083723 Configura la columna cantidad de compras
        colTotalGastadoD.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getMonto()).asObject()); //00083723 Configura la columna total gastado

        loadClients(); //00083723 Carga la lista de clientes
        loadFacilitators(); //00083723 Carga la lista de facilitadores
    }

    @FXML //00083723 Metodo para cargar los clientes en los ComboBox
    private void loadClients() {
        try (Connection connection = conexion.conectar()) { //00083723 Conecta a la base de datos
            String query = "SELECT id, nombre, apellido FROM tbCliente"; //00083723 Consulta SQL para obtener los clientes
            PreparedStatement statement = connection.prepareStatement(query); //00083723 Prepara la consulta
            ResultSet resultSet = statement.executeQuery(); //00083723 Ejecuta la consulta

            while (resultSet.next()) { //00083723 Recorre los resultados
                String cliente = resultSet.getInt("id") + " - " + resultSet.getString("nombre") + " " + resultSet.getString("apellido"); //00083723 Formatea el nombre del cliente
                cmbClienteA.getItems().add(cliente); //00083723 Agrega el cliente al ComboBox A
                cmbClienteB.getItems().add(cliente); //00083723 Agrega el cliente al ComboBox B
                cmbClienteC.getItems().add(cliente); //00083723 Agrega el cliente al ComboBox C
            }
        } catch (SQLException e) { //00083723 Captura las excepciones SQL
            e.printStackTrace(); //00083723 Imprime la pila de errores
        }
    }

    @FXML //00083723 Metodo para cargar los facilitadores en el ComboBox
    private void loadFacilitators() {
        try (Connection connection = conexion.conectar()) { //00083723 Conecta a la base de datos
            String query = "SELECT facilitador FROM tbFacilitador"; //00083723 Consulta SQL para obtener los facilitadores
            PreparedStatement statement = connection.prepareStatement(query); //00083723 Prepara la consulta
            ResultSet resultSet = statement.executeQuery(); //00083723 Ejecuta la consulta

            while (resultSet.next()) { //00083723 Recorre los resultados
                System.out.println("Facilitador: " + resultSet.getString("facilitador")); //00083723 Imprime el nombre del facilitador para depuracion
                cmbFacilitadorD.getItems().add(resultSet.getString("facilitador")); //00083723 Agrega el facilitador al ComboBox D
            }
        } catch (SQLException e) { //00083723 Captura las excepciones SQL
            e.printStackTrace(); //00083723 Imprime la pila de errores
        }
    }

    @FXML //00083723 Metodo para buscar compras en el reporte A
    private void onBtnBuscarComprasClick() {
        int clientId = Integer.parseInt(cmbClienteA.getValue().split(" - ")[0]); //00083723 Obtiene el ID del cliente seleccionado
        LocalDate fechaInicial = dpFechaInicialA.getValue(); //00083723 Obtiene la fecha inicial
        LocalDate fechaFinal = dpFechaFinalA.getValue(); //00083723 Obtiene la fecha final

        System.out.println("Cliente ID: " + clientId); //00083723 Imprime el ID del cliente para depuracion
        System.out.println("Fecha Inicial: " + fechaInicial); //00083723 Imprime la fecha inicial para depuracion
        System.out.println("Fecha Final: " + fechaFinal); //00083723 Imprime la fecha final para depuracion

        ObservableList<CompraCustom> compras = FXCollections.observableArrayList(); //00083723 Crea una lista observable para las compras

        try (Connection connection = conexion.conectar()) { //00083723 Conecta a la base de datos
            String query = "SELECT c.id, c.montoTotal, c.descripcion, c.fechaCompra " +
                    "FROM tbCompra c " +
                    "JOIN tbTarjeta t ON c.idTarjeta = t.id " +
                    "JOIN tbCliente cl ON t.idCliente = cl.id " +
                    "WHERE t.idCliente = ? AND c.fechaCompra BETWEEN ? AND ?"; //00083723 Consulta SQL para obtener las compras del cliente
            PreparedStatement ps = connection.prepareStatement(query); //00083723 Prepara la consulta
            ps.setInt(1, clientId); //00083723 Asigna el ID del cliente
            ps.setDate(2, Date.valueOf(fechaInicial)); //00083723 Asigna la fecha inicial
            ps.setDate(3, Date.valueOf(fechaFinal)); //00083723 Asigna la fecha final
            ResultSet rs = ps.executeQuery(); //00083723 Ejecuta la consulta

            while (rs.next()) { //00083723 Recorre los resultados
                System.out.println("Resultado: " + rs.getInt("id") + ", " + rs.getDouble("montoTotal") + ", " + rs.getString("descripcion") + ", " + rs.getDate("fechaCompra")); //00083723 Imprime los resultados para depuracion
                compras.add(new CompraCustom( //00083723 Agrega una nueva compra a la lista
                        rs.getInt("id"), //00083723 ID de la compra
                        rs.getDouble("montoTotal"), //00083723 Monto de la compra
                        rs.getString("descripcion"), //00083723 Descripcion de la compra
                        rs.getDate("fechaCompra") //00083723 Fecha de la compra
                ));
            }
            generarReporteA(compras); // 00107223 Llamar la función que imprime los resultados en un .txt
        } catch (SQLException e) { //00083723 Captura las excepciones SQL
            e.printStackTrace(); //00083723 Imprime la pila de errores
        }

        tbComprasClienteA.setItems(compras); //00083723 Asigna la lista de compras al TableView
    }

    @FXML //00083723 Metodo para calcular el total gastado en el reporte B
    private void onBtnGastosClienteClick() {
        String mesStr = txtMesB.getText(); //00083723 Obtiene el mes ingresado
        String anioStr = txtAnioB.getText(); //00083723 Obtiene el año ingresado
        String nombreCliente = "";
        String apellidoCliente = "";

        if (mesStr.isEmpty() || anioStr.isEmpty() || mesStr.length() > 2 || anioStr.length() != 4) { //00083723 Verifica que los campos no esten vacios y tengan el formato correcto
            lblTotalGastadoB.setText("Total Gastado: $ Error: Mes debe ser 1-12 y Año debe tener 4 digitos"); //00083723 Muestra un mensaje de error
            return; //00083723 Sale del metodo
        }

        int mes = Integer.parseInt(mesStr); //00083723 Convierte el mes a entero
        int anio = Integer.parseInt(anioStr); //00083723 Convierte el año a entero

        if (mes < 1 || mes > 12) { //00083723 Verifica que el mes este en el rango correcto
            lblTotalGastadoB.setText("Total Gastado: $ Error: Mes debe ser 1-12"); //00083723 Muestra un mensaje de error
            return; //00083723 Sale del metodo
        }

        int clientId = Integer.parseInt(cmbClienteB.getValue().split(" - ")[0]); //00083723 Obtiene el ID del cliente seleccionado

        double totalGastado = 0; //00083723 Inicializa el total gastado

        try (Connection connection = conexion.conectar()) { //00083723 Conecta a la base de datos
            String query = "SELECT SUM(c.montoTotal) as totalGastado, cl.nombre as nombre, cl.apellido as apellido "+
            "FROM tbCompra c " +
            "JOIN tbTarjeta t ON c.idTarjeta = t.id " +
            "JOIN tbCliente cl ON cl.id = t.idCliente " +
            "WHERE t.idCliente = ? AND MONTH(c.fechaCompra) = ? AND YEAR(c.fechaCompra) = ? " +
            "order by cl.nombre, cl.apellido;"; //00083723 Consulta SQL para obtener el total gastado, el nombre y el apellido por el cliente
            PreparedStatement ps = connection.prepareStatement(query); //00083723 Prepara la consulta
            ps.setInt(1, clientId); //00083723 Asigna el ID del cliente
            ps.setInt(2, mes); //00083723 Asigna el mes
            ps.setInt(3, anio); //00083723 Asigna el año
            ResultSet rs = ps.executeQuery(); //00083723 Ejecuta la consulta

            if (rs.next()) { //00083723 Verifica si hay resultados
                totalGastado = rs.getDouble("totalGastado"); //00083723 Obtiene el total gastado
                nombreCliente = rs.getString("nombre");
                apellidoCliente = rs.getString("apellido");
            }

        } catch (SQLException e) { //00083723 Captura las excepciones SQL
            e.printStackTrace(); //00083723 Imprime la pila de errores
        }

        lblTotalGastadoB.setText("Total Gastado: $" + totalGastado); //00083723 Muestra el total gastado
        generarReporteB(anio, mes, totalGastado, nombreCliente, apellidoCliente); // 00107223 Llamar la función que imprime los resultados en un .txt
    }

    @FXML //00083723 Metodo para mostrar las tarjetas asociadas en el reporte C
    private void onBtnTarjetasAsociadasClick() {
        int clientId = Integer.parseInt(cmbClienteC.getValue().split(" - ")[0]); //00083723 Obtiene el ID del cliente seleccionado
        StringBuilder tarjetasCredito = new StringBuilder(); //00083723 Inicializa el StringBuilder para tarjetas de credito
        StringBuilder tarjetasDebito = new StringBuilder(); //00083723 Inicializa el StringBuilder para tarjetas de debito

        ObservableList<Tarjeta> tarjetas = FXCollections.observableArrayList();;

        try (Connection connection = conexion.conectar()) { //00083723 Conecta a la base de datos
            String query = "SELECT numeroTarjeta, tipo FROM tbTarjeta WHERE idCliente = ?"; //00083723 Consulta SQL para obtener las tarjetas del cliente
            PreparedStatement ps = connection.prepareStatement(query); //00083723 Prepara la consulta
            ps.setInt(1, clientId); //00083723 Asigna el ID del cliente
            ResultSet rs = ps.executeQuery(); //00083723 Ejecuta la consulta

            while (rs.next()) { //00083723 Recorre los resultados
                String tarjeta = rs.getString("numeroTarjeta"); //00083723 Obtiene el numero de la tarjeta
                String tipo = rs.getString("tipo"); //00083723 Obtiene el tipo de la tarjeta
                String tarjetaOcultada = "XXXX XXXX XXXX " + tarjeta.substring(tarjeta.length() - 4); //00083723 Oculta parte del numero de la tarjeta

                tarjetas.add( new Tarjeta(tarjetaOcultada, tipo));

                if (tipo.equalsIgnoreCase("Crédito")) { //00083723 Si la tarjeta es de credito
                    tarjetasCredito.append(tarjetaOcultada).append("\n"); //00083723 Agrega la tarjeta al StringBuilder de tarjetas de credito
                } else { //00083723 Si la tarjeta es de debito
                    tarjetasDebito.append(tarjetaOcultada).append("\n"); //00083723 Agrega la tarjeta al StringBuilder de tarjetas de debito
                }

            }
            if (tarjetasCredito.length() == 0) { //00083723 Si no hay tarjetas de credito
                tarjetasCredito.append("N/A"); //00083723 Agrega N/A
            }

            if (tarjetasDebito.length() == 0) { //00083723 Si no hay tarjetas de debito
                tarjetasDebito.append("N/A"); //00083723 Agrega N/A
            }

            generarReporteC(tarjetas); // 00107223 Llamar la función que imprime los resultados en un .txt

            txtAreaTarjetasClienteC.setText("Tarjetas de crédito:\n" + tarjetasCredito + "\nTarjetas de débito:\n" + tarjetasDebito); //00083723 Muestra las tarjetas en el TextArea
        } catch (SQLException e) { //00083723 Captura las excepciones SQL
            e.printStackTrace(); //00083723 Imprime la pila de errores
        }
    }

    @FXML //00083723 Metodo para ver las compras de un facilitador en el reporte D
    private void onBtnVerComprasClick() {
        String facilitador = cmbFacilitadorD.getValue(); //00083723 Obtiene el facilitador seleccionado
        ObservableList<CompraCustom> compras = FXCollections.observableArrayList(); //00083723 Crea una lista observable para las compras

        try (Connection connection = conexion.conectar()) { //00083723 Conecta a la base de datos
            String query = "SELECT cl.id AS clienteId, cl.nombre, cl.apellido, COUNT(c.id) AS cantidadCompras, SUM(c.montoTotal) AS totalGastado " +
                    "FROM tbCliente cl " +
                    "JOIN tbTarjeta t ON cl.id = t.idCliente " +
                    "JOIN tbCompra c ON t.id = c.idTarjeta " +
                    "JOIN tbFacilitador f ON t.idFacilitador = f.id " +
                    "WHERE f.facilitador = ? " +
                    "GROUP BY cl.id, cl.nombre, cl.apellido"; //00083723 Consulta SQL para obtener las compras por facilitador
            PreparedStatement ps = connection.prepareStatement(query); //00083723 Prepara la consulta
            ps.setString(1, facilitador); //00083723 Asigna el facilitador
            ResultSet rs = ps.executeQuery(); //00083723 Ejecuta la consulta

            while (rs.next()) { //00083723 Recorre los resultados
                compras.add(new CompraCustom( //00083723 Agrega una nueva compra a la lista
                        rs.getInt("clienteId"), //00083723 ID del cliente
                        rs.getDouble("totalGastado"), //00083723 Total gastado
                        rs.getString("nombre") + " " + rs.getString("apellido"), //00083723 Nombre del cliente
                        null, //00083723 No se necesita la fecha aqui
                        "", //00083723 No se necesita la tarjeta aqui
                        rs.getInt("cantidadCompras") //00083723 Cantidad de compras
                ));
            }
            generarReporteD(compras); // 00107223 Llamar la función que imprime los resultados en un .txt
        } catch (SQLException e) { //00083723 Captura las excepciones SQL
            e.printStackTrace(); //00083723 Imprime la pila de errores
        }

        tbClientesFacilitadorD.setItems(compras); //00083723 Asigna la lista de compras al TableView
    }

    private void generarReporteA(ObservableList<CompraCustom> compras){ // 00107223 Función que genera los reportes A, recibe el listado de compras que va a estar Imprimiendo.
        String fechaHoraActual = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(Calendar.getInstance().getTime()); // 00107223 obtengo la fecha y hora en el momento que se llama la función para asignársela al nombre
        String rutaArchivo = reportesRuta + "/A"+fechaHoraActual+".txt"; // 00107223 se genera el la ruta junto al nombre completo del archivo
        int contador = 1; // 00107223 contador que servirá para diferenciar con más facilidad las diferentes compras
        try{ // 00107223 Try necesario para la creación de archivos
            File reporteA = new File(rutaArchivo); // 00107223 se crea el objeto File y se le asigna la ruta en la que este operará

            reporteA.createNewFile(); // 00107223 se crea el archivo

            FileWriter escritor = new FileWriter(rutaArchivo); // 00107223 se crea el objeto FileWriter que modificara el archivo

            escritor.write("=== REPORTE A ====\n"); // 00107223 se le da un título al reporte
            for (CompraCustom compra : compras){ // 00107223 un bucle foreach para que se imprima la información pertinente de cada compra
                escritor.write("=================\n"); // 00107223 separador entre compras
                escritor.write("Numero Cliente #"+contador+":\n"); // 00107223 se imprime el numero de la compra
                escritor.write("N° de compra: #"+compra.getIdCompra()+"\n"); // 00107223 se imprime el ID de la compra
                escritor.write("Monto total: $"+compra.getMonto()+"\n"); // 00107223 se imprime el monto total de la compra
                escritor.write("Fecha de compra: "+compra.getFecha()+"\n"); // 00107223 se imprime la fecha en que se realizó la compra
                escritor.write("Descripción de compra:"+compra.getDescripcion()+"\n"); // 00107223 se imprime la descripción de la compra
                escritor.write("=================\n"); // 00107223 separador entre compras

                contador++; // 00107223 se aumenta al contador
            }

            escritor.close(); // 00107223 Se cierra el escritor

        } catch (IOException e){ // 00107223 catch necesario para la creación de archivos, captura todos los errores a la hora de manejar el archivo
            System.out.println("Error al crear el generar reporte:" + e); // 00107223 se imprime el mensaje de error
        }
    }

    private void generarReporteB(int anio, int mes, double monto ,String nombre, String apellido){ // 00107223  Función que genera los reportes B, recibe, el año y mes de la consulta, el nombre y apellido del cliente y el monto total que ha gastado en ese periodo de tiempo
        String fechaHoraActual = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(Calendar.getInstance().getTime()); // 00107223 obtengo la fecha y hora en el momento que se llama la función para asignársela al nombre
        String rutaArchivo = reportesRuta + "/B"+fechaHoraActual+".txt"; // 00107223 se genera el la ruta junto al nombre completo del archivo
        String nombreMes = stringifyMes(mes); // 00107223 el nombre del mes para mejor legibilidad

        try{ // 00107223 Try necesario para la creación de archivos
            File reporteA = new File(rutaArchivo); // 00107223 se crea el objeto File y se le asigna la ruta en la que este operará

            reporteA.createNewFile(); // 00107223 se crea el archivo

            FileWriter escritor = new FileWriter(rutaArchivo); // 00107223 se crea el objeto FileWriter que modificara el archivo

            escritor.write("=== REPORTE B ====\n"); // 00107223 se le da un título al reporte
            escritor.write("===========================\n"); // 00107223 separador estetico
            escritor.write("Fecha("+anio+"/"+mes+")" + " Cliente: " + nombre + " " + apellido + "\n"); //00107223 impresion de datos del cliente
            escritor.write("Monto Total Gastado en el mes de "+nombreMes+": $" +monto +"\n"); //00107223 impresion de los gatos por el mes
            escritor.write("===========================\n"); // 00107223 separador estetico

            escritor.close(); // 00107223 Se cierra el escritor

        } catch (IOException e){ // 00107223 catch necesario para la creación de archivos, captura todos los errores a la hora de manejar el archivo
            System.out.println("Error al crear el generar reporte:" + e); // 00107223 se imprime el mensaje de error
        }
    }

    private void generarReporteC(ObservableList<Tarjeta> tarjetas){
        String fechaHoraActual = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(Calendar.getInstance().getTime()); // 00107223 obtengo la fecha y hora en el momento que se llama la función para asignársela al nombre
        String rutaArchivo = reportesRuta + "/C"+fechaHoraActual+".txt"; // 00107223 se genera el la ruta junto al nombre completo del archivo
        int contadorCredito = 0; // 00107223 Contador de las tarjetas de crédito que posee el cliente
        int contadorDebito = 0; // 00107223 Contador de las tarjetas de dédito que posee el cliente
        try{ // 00107223 Try necesario para la creación de archivos
            File reporteA = new File(rutaArchivo); // 00107223 se crea el objeto File y se le asigna la ruta en la que este operará

            reporteA.createNewFile(); // 00107223 se crea el archivo

            FileWriter escritor = new FileWriter(rutaArchivo); // 00107223 se crea el objeto FileWriter que modificara el archivo

            escritor.write("=== REPORTE C ====\n"); // 00107223 se le da un título al reporte
            escritor.write("Tárjetas de Crédito:\n"); // 00107223 Imprime el título de las tarjetas de crédito
            for (Tarjeta tarjeta : tarjetas){ // 00107223 un bucle foreach para evaluar si el cliente posee una tarjeta de crédito y si es asi que la imprima
                if (tarjeta.getTipo().equalsIgnoreCase("CRÉDITO")){ // 00107223 condicional que evaluá si el tipo coincide con crédito el ignoreCase es para que no le importe si el tipo posee Mayúsculas o Minúsculas
                    contadorCredito++; // 00107223 se aumenta en uno el contador de tarjeta de crédito
                    escritor.write("\t"+tarjeta.getNumeroTarjeta()+"\n"); // 00107223 se le da un título al reporte
                }
            }
            if (contadorCredito == 0){ // 00107223 si no hubo ninguna tarjeta de ese tipo entonces se imprime N/A
                escritor.write("\tN/A\n"); // 00107223 Impresion  de N/A

            }
            escritor.write("Tárjetas de Débito:\n"); // 00107223 Imprime el título de las tarjetas de dédito
            for (Tarjeta tarjeta : tarjetas){ // 00107223 un bucle foreach para evaluar si el cliente posee una tarjeta de crédito y si es asi que la imprima
                if (tarjeta.getTipo().equalsIgnoreCase("DÉBITO")){  // 00107223 condicional que evaluá si el tipo coincide con crédito el ignoreCase es para que no le importe si el tipo posee Mayúsculas o Minúsculas
                    contadorDebito++; // 00107223 se aumenta en uno el contador de tarjeta de débito
                    escritor.write("\t"+tarjeta.getNumeroTarjeta()+"\n"); // 00107223 se le da un título al reporte
                }
            }
            if (contadorDebito == 0){ // 00107223 si no hubo ninguna tarjeta de ese tipo entonces se imprime N/A
                escritor.write("\tN/A\n"); // 00107223 Impresion  de N/A

            }

            escritor.close(); // 00107223 Se cierra el escritor
        } catch (IOException e){ // 00107223 catch necesario para la creación de archivos, captura todos los errores a la hora de manejar el archivo
            System.out.println("Error al crear el generar reporte:" + e); // 00107223 se imprime el mensaje de error
        }
    }

    private void generarReporteD(ObservableList<CompraCustom> compras){
        String fechaHoraActual = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(Calendar.getInstance().getTime()); // 00107223 obtengo la fecha y hora en el momento que se llama la función para asignársela al nombre
        String rutaArchivo = reportesRuta + "/D"+fechaHoraActual+".txt"; // 00107223 se genera el la ruta junto al nombre completo del archivo
        int contador = 1; // 00107223 contador que servirá para diferenciar con más facilidad las diferentes compras
        try{ // 00107223 Try necesario para la creación de archivos
            File reporteA = new File(rutaArchivo); // 00107223 se crea el objeto File y se le asigna la ruta en la que este operará

            reporteA.createNewFile(); // 00107223 se crea el archivo

            FileWriter escritor = new FileWriter(rutaArchivo); // 00107223 se crea el objeto FileWriter que modificara el archivo

            escritor.write("=== REPORTE D ====\n"); // 00107223 se le da un título al reporte

            for (CompraCustom compra : compras){
                escritor.write("=================\n"); // 00107223 separador entre compras
                escritor.write("Cliente #"+contador+":\n"); // 00107223 se imprime el numero del cliente
                escritor.write("N° de Cliente: #"+compra.getIdCliente()+"\n"); // 00107223 se imprime el ID del cliente
                escritor.write("Nombre del Cliente: "+compra.getDescripcion()+"\n"); // 00107223 se imprime el nombre del cliente
                escritor.write("Total gastado: "+compra.getMonto()+"\n"); // 00107223 se imprime el monto total gastado por el cliente
                escritor.write("Cantidad de compras:"+compra.getCantidadCompras()+"\n"); // 00107223 se imprime la descripción de la compra
                escritor.write("=================\n"); // 00107223 separador entre compras

                contador++; // 00107223 se aumenta al contador
            }

            escritor.close(); // 00107223 Se cierra el escritor

        } catch (IOException e){ // 00107223 catch necesario para la creación de archivos, captura todos los errores a la hora de manejar el archivo
            System.out.println("Error al crear el generar reporte:" + e); // 00107223 se imprime el mensaje de error
        }
    }

    private String stringifyMes(int mes){ // 00107223 el mes de entero a cadena para la impresion en txt
        String nombreMes; // 00107223 variable cadena que retornara con el nombre del mes
        switch (mes){ // 00107223 switch para evaluar que mes es mediante su valor numérico
            case 1:
                nombreMes = "Enero"; // 00107223 asignarle al mes 1 "Enero"
                break;
            case 2:
                nombreMes = "Febrero"; // 00107223 asignarle al mes 2 "Febrero"
                break;
            case 3:
                nombreMes = "Marzo"; // 00107223 asignarle al mes 3 "Marzo"
                break;
            case 4:
                nombreMes = "Abril"; // 00107223 asignarle al mes 4 "Abril"
                break;
            case 5:
                nombreMes = "Mayo"; // 00107223 asignarle al mes 5 "Mayo"
                break;
            case 6:
                nombreMes = "Junio"; // 00107223 asignarle al mes 6 "Junio"
                break;
            case 7:
                nombreMes = "Julio"; // 00107223 asignarle al mes 7 "Julio"
                break;
            case 8:
                nombreMes = "Agosto"; // 00107223 asignarle al mes 8 "Agosto"
                break;
            case 9:
                nombreMes = "Septiembre"; // 00107223 asignarle al mes 9 "Septiembre"
                break;
            case 10:
                nombreMes = "Octubre"; // 00107223 asignarle al mes 10 "Octubre"
                break;
            case 11:
                nombreMes = "Noviembre"; // 00107223 asignarle al mes 11 "Noviembre"
                break;
            default:
                nombreMes = "Diciembre"; // 00107223 asignarle al mes restante (12) "Diciembre"
                break;
        }
        return nombreMes; // 00107223 se retorna la cadena del mes
    }

    @FXML
    private void onBtnClientesClick() { // 00016623 Método para manejar el evento de click en el botón de btnClientes del menu,
        OpenWindows.openWindow(tbClientesFacilitadorD, 1);// 00016623 llamando método utilitario para abrir ventanas
    }

    @FXML
    private void onBtnTarjetasClick() { // 00016623 Método para manejar el evento de click en el botón de Reporte,
        OpenWindows.openWindow(tbClientesFacilitadorD, 4);// 00016623 llamando método utilitario para abrir ventanas
    }

    @FXML
    private void onBtnComprasClick() { // 00016623 Método para manejar el evento de click en el botón de Reporte,
        OpenWindows.openWindow(tbClientesFacilitadorD, 2);// 00016623 llamando método utilitario para abrir ventanas
    }
}