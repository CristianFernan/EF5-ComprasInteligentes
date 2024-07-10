module org.example.comprasinteligentes {
    requires javafx.controls; //00016623 Requiere el módulo JavaFX para los controles de interfaz grafica
    requires javafx.fxml; //00016623 Requiere el módulo JavaFX para archivos FXML
    requires java.sql; //00016623 Requiere el módulo Java SQL para la conexión y manipulación de bases de datos SQL

    opens org.example.comprasinteligentes.clases to javafx.base; //00016623 Abre el paquete clases para permitir el acceso
    opens org.example.comprasinteligentes to javafx.fxml; //00016623 Abre el paquete principal para permitir el acceso
    exports org.example.comprasinteligentes.views; //00016623 Exporta el paquete views para que se pueda acceder a él
    opens org.example.comprasinteligentes.views to javafx.fxml; //00016623 Abre el paquete views para permitir el acceso desde archivos FXML
    exports org.example.comprasinteligentes.controllers; //00016623 Exporta el paquete controllers para que otros archivos puedan acceder
    opens org.example.comprasinteligentes.controllers to javafx.fxml; //00016623 Abre el paquete controllers para permitir el acceso desde archivos FXML

}