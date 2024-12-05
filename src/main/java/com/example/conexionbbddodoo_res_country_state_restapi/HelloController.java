package com.example.conexionbbddodoo_res_country_state_restapi;

import DAO.StateDAO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelos.State;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class HelloController {
    @FXML
    public TableView<State> tbDatos;
    @FXML
    public TableColumn<State, String> tcName;
    @FXML
    private TableColumn<State, Integer> tcCreateUid;
    @FXML
    private TableColumn<State, java.sql.Timestamp> tcWriteDate;
    @FXML
    private TableColumn<State, Integer> tcWriteUid;
    @FXML
    private TableColumn<State, String> tcCode;
    @FXML
    private TableColumn<State, Integer> tcCountryId;
    @FXML
    private TableColumn <State, Integer> tcId;
    @FXML
    private TableColumn<State, java.sql.Timestamp> tcCreateDate;

    @FXML
    private Button btnLupa;
    @FXML
    private TextField txtNombre;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnAnadir;
    @FXML
    private Button btnModificar;


    public void initialize() throws SQLException {

        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcCountryId.setCellValueFactory(new PropertyValueFactory<>("country_id"));
        tcCreateUid.setCellValueFactory(new PropertyValueFactory<>("create_uid"));
        tcWriteUid.setCellValueFactory(new PropertyValueFactory<>("write_uid"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        tcCreateDate.setCellValueFactory(new PropertyValueFactory<>("create_date"));
        tcWriteDate.setCellValueFactory(new PropertyValueFactory<>("write_date"));

        //Configuracion del evento de doble clic en la tabla para abrir la ventana de edición para los registros
        tbDatos.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Doble clic
                abrirVentanaEdicion();
            }
        });

    }

    @FXML
    public void onbBtnObtenBancosMal(ActionEvent actionEvent) {

        try {
            List<modelos.State> estados = StateDAO.obtenerEstados();
            ObservableList<State> datos = FXCollections.observableArrayList(estados);
            tbDatos.setItems(datos);
        } catch (SQLException e) {
            System.err.println("Error al obtener los Estados: " + e.getMessage());
        }

    }

    //Boton para realizar busquedas por nombre
    @FXML
    public void onBuscar(ActionEvent event) {
        String nombreBuscar = txtNombre.getText().toLowerCase();

        try {
            List<State> estados = StateDAO.obtenerEstados();
            List<State> estadosFiltrados = new ArrayList<>();

            //Filtrar la lista en base al nombre
            for (State estado : estados) {
                if (estado.getName().toLowerCase().contains(nombreBuscar)) {
                    estadosFiltrados.add(estado);
                }
            }

            //Actualizar la tabla con los resultados filtrados
            ObservableList<State> datos = FXCollections.observableArrayList(estadosFiltrados);
            tbDatos.setItems(datos);

        } catch (SQLException e) {
            System.err.println("Error al filtrar los Estados: " + e.getMessage());
        }
    }


    @FXML
    private void onBtnAnadirClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("nueva-ventana.fxml"));
            Parent root = loader.load();

            //Obtener el próximo ID
            int nextId = StateDAO.obtenerProximoId();

            //Pasar ID a NuevaVentana
            NuevaVentana controlador = loader.getController();
            controlador.setNextId(nextId);

            //Abrir la ventana
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void onBtnEliminarClick() {
        //Seleccionar el registro de la tabla
        State selectedState = tbDatos.getSelectionModel().getSelectedItem();

        if (selectedState != null) {
            //Mostrar una alerta de confirmación
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText("¿Está seguro de que desea eliminar este registro?");
            alert.setContentText("Esta acción no se puede deshacer.");

            //Esperar la respuesta del usuario
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    //Eliminar el registro de la base de datos
                    StateDAO.eliminarEstado(selectedState.getId());

                    //Actualizar la tabla después de eliminar
                    tbDatos.getItems().remove(selectedState);

                    System.out.println("Registro eliminado correctamente.");
                }
            });
        } else {
            //Mostrar una alerta si no se ha seleccionado ningún registro
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ningún registro seleccionado");
            alert.setHeaderText("Por favor, seleccione un registro para eliminar.");
            alert.showAndWait();
        }
    }

    @FXML
    private void onBtnModificarClick() {
        abrirVentanaEdicion();
    }

    private void abrirVentanaEdicion() {
        //Obtener el estado seleccionado
        State selectedState = tbDatos.getSelectionModel().getSelectedItem();

        if (selectedState != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("nueva-ventana.fxml"));
                Parent root = loader.load();

                //Pasar los datos del estado seleccionado a la ventana de edición
                NuevaVentana controlador = loader.getController();
                controlador.setEstado(selectedState);

                //Mostrar la ventana
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //Mostrar una alerta si no hay un registro seleccionado
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ningún registro seleccionado");
            alert.setHeaderText("Por favor, seleccione un registro para modificar.");
            alert.showAndWait();
        }
    }



    @FXML
    public void onbBtnObtenBancosBien(ActionEvent actionEvent) {

        Task<Void> tarea = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try{
                    List<modelos.State> estados = StateDAO.obtenerEstados();
                    ObservableList<modelos.State> datos = FXCollections.observableArrayList(estados);

                    String url = "http://localhost:8080/?pgsql=db&username=odoo&db=odoo&ns=public&select=res_country_state";
                    java.awt.Desktop.getDesktop().browse(new java.net.URI(url));

                    Platform.runLater(() -> {
                        tbDatos.setItems(datos);
                    });


                } catch (SQLException e) {
                    System.err.println("Error de SQL al consultar: " + e.getMessage());
                    Platform.runLater(() -> {

                    });
                }
                return null;
            }
        };

        Thread hilo = new Thread(tarea);
        hilo.start();
    }

    public Button getBtnLupa() {
        return btnLupa;
    }

    public void setBtnLupa(Button btnLupa) {
        this.btnLupa = btnLupa;
    }
}