package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Conexion {

    private static Connection conexion;

    private static final String URL = "jdbc:postgresql://localhost:5432/clinica_db";
    private static final String USER = "postgres";
    private static final String PASS = "12345";

    private Conexion() {

    }

    public static Connection getConexion() {

        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(URL, USER, PASS);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return conexion;
    }
}