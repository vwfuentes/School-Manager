package com.schoolmanager.controller;

import com.schoolmanager.model.Database;
import com.schoolmanager.model.Student;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class EditStudentModalController {
    private Student student;

    @FXML private TextField nombreField;
    @FXML private TextField apellidoMaternoField;
    @FXML private TextField apellidoPaternoField;
    @FXML private TextField edadField;
    @FXML private TextField cursoField;
    @FXML private TextField celularField;
    @FXML private TextField nombrePadreField;
    @FXML private TextField apellidoPadreField;
    @FXML private TextField nombreMadreField;
    @FXML private TextField apellidoMadreField;
    @FXML private TextField celularPadreField;
    @FXML private TextField celularMadreField;
    @FXML private Button saveButton;

    public void setStudent(Student s) {
        this.student = s;
        nombreField.setText(s.getNombre());
        apellidoMaternoField.setText(s.getApellidoMaterno());
        apellidoPaternoField.setText(s.getApellidoPaterno());
        edadField.setText(String.valueOf(s.getEdad()));
        cursoField.setText(s.getCurso());
        celularField.setText(s.getCelular());
        nombrePadreField.setText(s.getNombrePadre());
        apellidoPadreField.setText(s.getApellidoPadre());
        nombreMadreField.setText(s.getNombreMadre());
        apellidoMadreField.setText(s.getApellidoMadre());
        celularPadreField.setText(s.getCelularPadre());
        celularMadreField.setText(s.getCelularMadre());
    }

    @FXML
    private void handleSave() {
        student.setNombre(nombreField.getText());
        student.setApellidoMaterno(apellidoMaternoField.getText());
        student.setApellidoPaterno(apellidoPaternoField.getText());
        student.setEdad(Integer.parseInt(edadField.getText()));
        student.setCurso(cursoField.getText());
        student.setCelular(celularField.getText());
        student.setNombrePadre(nombrePadreField.getText());
        student.setApellidoPadre(apellidoPadreField.getText());
        student.setNombreMadre(nombreMadreField.getText());
        student.setApellidoMadre(apellidoMadreField.getText());
        student.setCelularPadre(celularPadreField.getText());
        student.setCelularMadre(celularMadreField.getText());
        try {
            Database.updateStudent(student);
            ((Stage) saveButton.getScene().getWindow()).close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error actualizando estudiante: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }
}
