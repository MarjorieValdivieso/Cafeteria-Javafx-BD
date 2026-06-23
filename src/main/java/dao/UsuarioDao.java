package dao;

import modelo.Usuario;
import modelo.Usuario.Rol;
import util.Conexion;

import java.sql.*;

public class UsuarioDao {

    private final Connection conn;

    public UsuarioDao() {
        this.conn = Conexion.getInstancia().getConnection();
    }
    public Usuario autenticar(String usuario, String password) {

        String sql = "SELECT id, nombre, usuario, password, rol FROM usuarios "
                + "WHERE usuario = ? AND password = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println(" Error en login: " + e.getMessage());
        }

        return null;
    }
    public boolean insertar(Usuario u) {

        String sql = "INSERT INTO usuarios (nombre, usuario, password, rol) "
                + "VALUES (?, ?, ?, ?::rol_enum)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getUsuario());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getRol().name());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar: " + e.getMessage());
            return false;
        }
    }
    public java.util.List<Usuario> listar() {

        java.util.List<Usuario> lista = new java.util.ArrayList<>();

        String sql = "SELECT * FROM usuarios";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }

        return lista;
    }

    private Usuario mapear(ResultSet rs) throws SQLException {

        return new Usuario(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("usuario"),
                rs.getString("password"),
                Rol.valueOf(rs.getString("rol")),
                rs.getString("creado_en")
        );
    }
}