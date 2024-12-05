package com.example.conexionbbddodoo_res_country_state_restapi;

import DAO.StateDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelos.State;

public class NuevaVentana {

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtCountryId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtCode;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;

    private State estadoActual; //Guarda el estado actual si se está editando

    //Configura el próximo ID en caso de que sea un nuevo registro
    public void setNextId(int nextId) {
        txtId.setText(String.valueOf(nextId));
        txtId.setDisable(true); //Desactiva el campo de ID en modo de creación
    }

    //Configura el estado para editar el registro
    public void setEstado(State estado) {
        this.estadoActual = estado;
        txtId.setText(String.valueOf(estado.getId()));
        txtCountryId.setText(String.valueOf(estado.getCountry_id()));
        txtName.setText(estado.getName());
        txtCode.setText(estado.getCode());
        txtId.setDisable(true); //Desactiva el campo de ID en modo de edición
    }

    @FXML
    private void onBtnAceptarClick() {
        try {
            int id = Integer.parseInt(txtId.getText());
            int countryId = Integer.parseInt(txtCountryId.getText());
            String name = txtName.getText();
            String code = txtCode.getText();

            if (name.isEmpty() || code.isEmpty()) {
                mostrarAlerta("Campos vacíos", "Por favor, completa todos los campos.");
                return;
            }

            if (estadoActual == null) {
                //Modo de creación
                State nuevoEstado = new State(id, countryId, name, code);
                StateDAO.insertarEstado(nuevoEstado);
                mostrarAlerta("Registro creado", "El estado ha sido creado exitosamente.");
            } else {
                //Modo de edición
                estadoActual.setCountry_id(countryId);
                estadoActual.setName(name);
                estadoActual.setCode(code);
                StateDAO.actualizarEstado(estadoActual);
                mostrarAlerta("Registro actualizado", "El estado ha sido actualizado exitosamente.");
            }

            cerrarVentana();
        } catch (NumberFormatException e) {
            mostrarAlerta("Error de formato", "Por favor, verifica los campos numéricos.");
        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un error al procesar la solicitud: " + e.getMessage());
        }
    }

    @FXML
    private void onBtnCancelarClick() {
        cerrarVentana();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
}
