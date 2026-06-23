package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static Conexion instancia;
    private Connection conexion;

    private final String URL = "jdbc:postgresql://localhost:5432/Cafeteria_db";
    private final String USER = "postgres";
    private final String PASS = "12345";

    private Conexion() {
        try {
            conexion = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("✔ Conexión exitosa");
        } catch (SQLException e) {
            System.out.println(" Error de conexión: " + e.getMessage());
        }
    }

    public static Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    public Connection getConnection() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(URL, USER, PASS);
            }
        } catch (SQLException e) {
            System.out.println("Error reconectando: " + e.getMessage());
        }
        return conexion;
    }
}