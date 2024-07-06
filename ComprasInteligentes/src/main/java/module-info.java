module org.example.comprasinteligentes {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.example.comprasinteligentes.clases to javafx.base;
    opens org.example.comprasinteligentes to javafx.fxml;
    exports org.example.comprasinteligentes.views;
    opens org.example.comprasinteligentes.views to javafx.fxml;
    exports org.example.comprasinteligentes.controllers;
    opens org.example.comprasinteligentes.controllers to javafx.fxml;
}