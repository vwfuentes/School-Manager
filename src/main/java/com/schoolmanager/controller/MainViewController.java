package com.schoolmanager.controller;

import com.schoolmanager.model.Database;
import com.schoolmanager.model.Student;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainViewController {
    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, String> nombreCol;
    @FXML private TableColumn<Student, String> apellidoMaternoCol;
    @FXML private TableColumn<Student, String> apellidoPaternoCol;
    @FXML private TableColumn<Student, Integer> edadCol;
    @FXML private TableColumn<Student, String> cursoCol;
    @FXML private TableColumn<Student, String> celularCol;
    @FXML private ProgressIndicator loadingIndicator;
    @FXML private Button uploadButton;
    @FXML private Button addButton;

    private ObservableList<Student> students = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellidoMaternoCol.setCellValueFactory(new PropertyValueFactory<>("apellidoMaterno"));
        apellidoPaternoCol.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
        edadCol.setCellValueFactory(new PropertyValueFactory<>("edad"));
        cursoCol.setCellValueFactory(new PropertyValueFactory<>("curso"));
        celularCol.setCellValueFactory(new PropertyValueFactory<>("celular"));

        loadingIndicator.setVisible(false);
        studentTable.setItems(students);
        studentTable.setRowFactory(tv -> {
            TableRow<Student> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    showEditModal(row.getItem());
                }
            });
            return row;
        });
        try {
            Database.init();
            students.addAll(Database.getAllStudents());
        } catch (SQLException e) {
            showError("Error inicializando la base de datos: " + e.getMessage());
        }
    }

    @FXML
    private void handleUpload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona archivo CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showOpenDialog(uploadButton.getScene().getWindow());
        if (file != null) {
            loadingIndicator.setVisible(true);
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    List<Student> loadedStudents = readCsv(file);
                    Database.insertStudents(loadedStudents);
                    Platform.runLater(() -> {
                        students.clear();
                        try {
                            students.addAll(Database.getAllStudents());
                        } catch (SQLException e) {
                            showError("Error cargando estudiantes: " + e.getMessage());
                        }
                        loadingIndicator.setVisible(false);
                    });
                    return null;
                }
            };
            new Thread(task).start();
        }
    }

    @FXML
    private void handleAddStudent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/schoolmanager/view/EditStudentModal.fxml"));
            Parent root = loader.load();
            EditStudentModalController controller = loader.getController();
            Student newStudent = new Student();
            controller.setStudent(newStudent);
            controller.setNewStudentMode(true); // Indica que es un nuevo estudiante
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Agregar Estudiante");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            students.clear();
            students.addAll(Database.getAllStudents());
        } catch (Exception e) {
            showError("Error mostrando modal: " + e.getMessage());
        }
    }

    private List<Student> readCsv(File file) throws IOException {
        List<Student> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; }
                String[] parts = line.split(",");
                if (parts.length < 13) continue;
                Student s = new Student();
                s.setNombre(parts[0]);
                s.setApellidoMaterno(parts[1]);
                s.setApellidoPaterno(parts[2]);
                s.setEdad(Integer.parseInt(parts[3]));
                s.setCurso(parts[4]);
                s.setCelular(parts[5]);
                s.setNombrePadre(parts[6]);
                s.setApellidoPadre(parts[7]);
                s.setNombreMadre(parts[8]);
                s.setApellidoMadre(parts[9]);
                s.setCelularPadre(parts[10]);
                s.setCelularMadre(parts[11]);
                list.add(s);
            }
        }
        return list;
    }

    private void showEditModal(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/schoolmanager/view/EditStudentModal.fxml"));
            Parent root = loader.load();
            EditStudentModalController controller = loader.getController();
            controller.setStudent(student);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Editar Estudiante");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            students.clear();
            students.addAll(Database.getAllStudents());
        } catch (Exception e) {
            showError("Error mostrando modal: " + e.getMessage());
        }
    }

    private void showError(String msg) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
            alert.showAndWait();
        });
    }
}
