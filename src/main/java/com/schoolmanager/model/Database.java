package com.schoolmanager.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static final String DB_URL = "jdbc:sqlite:school.db";

    public static void init() throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "CREATE TABLE IF NOT EXISTS students (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT," +
                    "apellidoMaterno TEXT," +
                    "apellidoPaterno TEXT," +
                    "edad INTEGER," +
                    "curso TEXT," +
                    "celular TEXT," +
                    "nombrePadre TEXT," +
                    "apellidoPadre TEXT," +
                    "nombreMadre TEXT," +
                    "apellidoMadre TEXT," +
                    "celularPadre TEXT," +
                    "celularMadre TEXT)";
            conn.createStatement().execute(sql);
        }
    }

    public static void insertStudents(List<Student> students) throws SQLException {
        String sql = "INSERT INTO students (nombre, apellidoMaterno, apellidoPaterno, edad, curso, celular, nombrePadre, apellidoPadre, nombreMadre, apellidoMadre, celularPadre, celularMadre) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (Student s : students) {
                stmt.setString(1, s.getNombre());
                stmt.setString(2, s.getApellidoMaterno());
                stmt.setString(3, s.getApellidoPaterno());
                stmt.setInt(4, s.getEdad());
                stmt.setString(5, s.getCurso());
                stmt.setString(6, s.getCelular());
                stmt.setString(7, s.getNombrePadre());
                stmt.setString(8, s.getApellidoPadre());
                stmt.setString(9, s.getNombreMadre());
                stmt.setString(10, s.getApellidoMadre());
                stmt.setString(11, s.getCelularPadre());
                stmt.setString(12, s.getCelularMadre());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    public static List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM students")) {
            while (rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt("id"));
                s.setNombre(rs.getString("nombre"));
                s.setApellidoMaterno(rs.getString("apellidoMaterno"));
                s.setApellidoPaterno(rs.getString("apellidoPaterno"));
                s.setEdad(rs.getInt("edad"));
                s.setCurso(rs.getString("curso"));
                s.setCelular(rs.getString("celular"));
                s.setNombrePadre(rs.getString("nombrePadre"));
                s.setApellidoPadre(rs.getString("apellidoPadre"));
                s.setNombreMadre(rs.getString("nombreMadre"));
                s.setApellidoMadre(rs.getString("apellidoMadre"));
                s.setCelularPadre(rs.getString("celularPadre"));
                s.setCelularMadre(rs.getString("celularMadre"));
                students.add(s);
            }
        }
        return students;
    }

    public static void updateStudent(Student s) throws SQLException {
        String sql = "UPDATE students SET nombre=?, apellidoMaterno=?, apellidoPaterno=?, edad=?, curso=?, celular=?, nombrePadre=?, apellidoPadre=?, nombreMadre=?, apellidoMadre=?, celularPadre=?, celularMadre=? WHERE id=?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, s.getNombre());
            stmt.setString(2, s.getApellidoMaterno());
            stmt.setString(3, s.getApellidoPaterno());
            stmt.setInt(4, s.getEdad());
            stmt.setString(5, s.getCurso());
            stmt.setString(6, s.getCelular());
            stmt.setString(7, s.getNombrePadre());
            stmt.setString(8, s.getApellidoPadre());
            stmt.setString(9, s.getNombreMadre());
            stmt.setString(10, s.getApellidoMadre());
            stmt.setString(11, s.getCelularPadre());
            stmt.setString(12, s.getCelularMadre());
            stmt.setInt(13, s.getId());
            stmt.executeUpdate();
        }
    }
}
