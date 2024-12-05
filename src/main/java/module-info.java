module com.example.conexionbbddodoo_res_country_state_restapi {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.conexionbbddodoo_res_country_state_restapi to javafx.fxml;
    exports com.example.conexionbbddodoo_res_country_state_restapi;
}