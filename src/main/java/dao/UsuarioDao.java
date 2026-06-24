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
    }
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
            System.out.println("Error login: " + e.getMessage());
        }

        return null;
    }
    public List<Usuario> listar() {

        List<Usuario> lista = new ArrayList<>();

        String sql = "SELECT * FROM usuarios";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error listar: " + e.getMessage());
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