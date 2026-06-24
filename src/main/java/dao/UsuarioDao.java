package dao;

import modelo.Usuario;
import modelo.Usuario.Rol;
import util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

    private final Connection conn;

    public UsuarioDao() {
        this.conn = Conexion.getInstancia().getConnection();
        crearAdminSiNoExiste(); // 👈 crea admin automático
    }

    // ================= LOGIN =================
    public Usuario autenticar(String usuario, String password) {

        String sql = "SELECT id, nombre, usuario, password, rol, creado_en "
                + "FROM usuarios WHERE usuario = ? AND password = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error en login: " + e.getMessage());
        }

        return null;
    }

    // ================= INSERTAR =================
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

    // ================= LISTAR =================
    public List<Usuario> listar() {

        List<Usuario> lista = new ArrayList<>();

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

    // ================= MAPEAR =================
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

    // ================= ADMIN AUTOMÁTICO =================
    private void crearAdminSiNoExiste() {

        String sql = "SELECT COUNT(*) FROM usuarios WHERE usuario = 'admin'";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next() && rs.getInt(1) == 0) {

                String insert = "INSERT INTO usuarios (nombre, usuario, password, rol) "
                        + "VALUES ('Administrador', 'admin', '1234', 'ADMIN'::rol_enum)";

                st.executeUpdate(insert);

                System.out.println("✔ Usuario admin creado (admin / 1234)");
            }

        } catch (SQLException e) {
            System.out.println("Error creando admin: " + e.getMessage());
        }
    }
}