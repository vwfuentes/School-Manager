package com.schoolmanager.controller;

import com.schoolmanager.model.Database;
import com.schoolmanager.model.Student;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class EditStudentModalController {
    private Student student;
    private boolean isNewStudent = false;

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
        if (s.getNombre() != null) nombreField.setText(s.getNombre());
        if (s.getApellidoMaterno() != null) apellidoMaternoField.setText(s.getApellidoMaterno());
        if (s.getApellidoPaterno() != null) apellidoPaternoField.setText(s.getApellidoPaterno());
        if (s.getEdad() != 0) edadField.setText(String.valueOf(s.getEdad()));
        if (s.getCurso() != null) cursoField.setText(s.getCurso());
        if (s.getCelular() != null) celularField.setText(s.getCelular());
        if (s.getNombrePadre() != null) nombrePadreField.setText(s.getNombrePadre());
        if (s.getApellidoPadre() != null) apellidoPadreField.setText(s.getApellidoPadre());
        if (s.getNombreMadre() != null) nombreMadreField.setText(s.getNombreMadre());
        if (s.getApellidoMadre() != null) apellidoMadreField.setText(s.getApellidoMadre());
        if (s.getCelularPadre() != null) celularPadreField.setText(s.getCelularPadre());
        if (s.getCelularMadre() != null) celularMadreField.setText(s.getCelularMadre());
    }

    public void setNewStudentMode(boolean isNew) {
        this.isNewStudent = isNew;
    }

    @FXML
    private void handleSave() {
        student.setNombre(nombreField.getText());
        student.setApellidoMaterno(apellidoMaternoField.getText());
        student.setApellidoPaterno(apellidoPaternoField.getText());
        try {
            student.setEdad(Integer.parseInt(edadField.getText()));
        } catch (NumberFormatException e) {
            showError("Edad inválida");
            return;
        }
        student.setCurso(cursoField.getText());
        student.setCelular(celularField.getText());
        student.setNombrePadre(nombrePadreField.getText());
        student.setApellidoPadre(apellidoPadreField.getText());
        student.setNombreMadre(nombreMadreField.getText());
        student.setApellidoMadre(apellidoMadreField.getText());
        student.setCelularPadre(celularPadreField.getText());
        student.setCelularMadre(celularMadreField.getText());
        try {
            if (isNewStudent) {
                Database.insertStudents(java.util.Collections.singletonList(student));
            } else {
                Database.updateStudent(student);
            }
            ((Stage) saveButton.getScene().getWindow()).close();
        } catch (Exception e) {
            showError("Error guardando estudiante: " + e.getMessage());
        }
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.showAndWait();
    }
}
