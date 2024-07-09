package org.example.comprasinteligentes.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.comprasinteligentes.Conexion;
import org.example.comprasinteligentes.clases.CompraCustom;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ReportesController { //00083723 Controlador para gestionar reportes
    private static Conexion conexion = Conexion.getInstance(); //00083723 Obtiene la instancia de conexion a la base de datos

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
        } catch (SQLException e) { //00083723 Captura las excepciones SQL
            e.printStackTrace(); //00083723 Imprime la pila de errores
        }

        tbComprasClienteA.setItems(compras); //00083723 Asigna la lista de compras al TableView
    }

    @FXML //00083723 Metodo para calcular el total gastado en el reporte B
    private void onBtnGastosClienteClick() {
        String mesStr = txtMesB.getText(); //00083723 Obtiene el mes ingresado
        String anioStr = txtAnioB.getText(); //00083723 Obtiene el año ingresado

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
            String query = "SELECT SUM(c.montoTotal) as totalGastado " +
                    "FROM tbCompra c " +
                    "JOIN tbTarjeta t ON c.idTarjeta = t.id " +
                    "WHERE t.idCliente = ? AND MONTH(c.fechaCompra) = ? AND YEAR(c.fechaCompra) = ?"; //00083723 Consulta SQL para obtener el total gastado por el cliente
            PreparedStatement ps = connection.prepareStatement(query); //00083723 Prepara la consulta
            ps.setInt(1, clientId); //00083723 Asigna el ID del cliente
            ps.setInt(2, mes); //00083723 Asigna el mes
            ps.setInt(3, anio); //00083723 Asigna el año
            ResultSet rs = ps.executeQuery(); //00083723 Ejecuta la consulta

            if (rs.next()) { //00083723 Verifica si hay resultados
                totalGastado = rs.getDouble("totalGastado"); //00083723 Obtiene el total gastado
            }
        } catch (SQLException e) { //00083723 Captura las excepciones SQL
            e.printStackTrace(); //00083723 Imprime la pila de errores
        }

        lblTotalGastadoB.setText("Total Gastado: $" + totalGastado); //00083723 Muestra el total gastado
    }

    @FXML //00083723 Metodo para mostrar las tarjetas asociadas en el reporte C
    private void onBtnTarjetasAsociadasClick() {
        int clientId = Integer.parseInt(cmbClienteC.getValue().split(" - ")[0]); //00083723 Obtiene el ID del cliente seleccionado
        StringBuilder tarjetasCredito = new StringBuilder(); //00083723 Inicializa el StringBuilder para tarjetas de credito
        StringBuilder tarjetasDebito = new StringBuilder(); //00083723 Inicializa el StringBuilder para tarjetas de debito

        try (Connection connection = conexion.conectar()) { //00083723 Conecta a la base de datos
            String query = "SELECT numeroTarjeta, tipo FROM tbTarjeta WHERE idCliente = ?"; //00083723 Consulta SQL para obtener las tarjetas del cliente
            PreparedStatement ps = connection.prepareStatement(query); //00083723 Prepara la consulta
            ps.setInt(1, clientId); //00083723 Asigna el ID del cliente
            ResultSet rs = ps.executeQuery(); //00083723 Ejecuta la consulta

            while (rs.next()) { //00083723 Recorre los resultados
                String tarjeta = rs.getString("numeroTarjeta"); //00083723 Obtiene el numero de la tarjeta
                String tipo = rs.getString("tipo"); //00083723 Obtiene el tipo de la tarjeta
                String tarjetaOcultada = "XXXX XXXX XXXX " + tarjeta.substring(tarjeta.length() - 4); //00083723 Oculta parte del numero de la tarjeta

                if (tipo.equals("Credito")) { //00083723 Si la tarjeta es de credito
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

            txtAreaTarjetasClienteC.setText("Tarjetas de credito:\n" + tarjetasCredito + "\nTarjetas de debito:\n" + tarjetasDebito); //00083723 Muestra las tarjetas en el TextArea
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
        } catch (SQLException e) { //00083723 Captura las excepciones SQL
            e.printStackTrace(); //00083723 Imprime la pila de errores
        }

        tbClientesFacilitadorD.setItems(compras); //00083723 Asigna la lista de compras al TableView
    }
}