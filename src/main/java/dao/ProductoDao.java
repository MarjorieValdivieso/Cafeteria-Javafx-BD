package dao;
import modelo.Producto;
import util.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDao {

    private final Connection conn;

    public ProductoDao() {
        this.conn = Conexion.getInstancia().getConnection();
    }
    public boolean insertar(Producto p) {
        String sql = "INSERT INTO productos (nombre, precio, stock, categoria) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecio());
            ps.setInt(3, p.getStock());
            ps.setString(4, p.getCategoria());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error insertar producto: " + e.getMessage());
            return false;
        }
    }
    public List<Producto> listar() {

        List<Producto> lista = new ArrayList<>();

        String sql = "SELECT * FROM productos ORDER BY id";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error listar productos: " + e.getMessage());
        }
        return lista;
    }
    public boolean actualizar(Producto p) {

        String sql = "UPDATE productos SET nombre=?, precio=?, stock=?, categoria=? WHERE id=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecio());
            ps.setInt(3, p.getStock());
            ps.setString(4, p.getCategoria());
            ps.setInt(5, p.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(" Error actualizar producto: " + e.getMessage());
            return false;
        }
    }
    public boolean eliminar(int id) {
        String sql = "DELETE FROM productos WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error eliminar producto: " + e.getMessage());
            return false;}
    }
    public Producto buscarPorId(int id) {
        String sql = "SELECT * FROM productos WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }}
        } catch (SQLException e) {
            System.out.println("Error buscar producto: " + e.getMessage());}
        return null;
    }
    private Producto mapear(ResultSet rs) throws SQLException {
        return new Producto(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getDouble("precio"),
                rs.getInt("stock"),
                rs.getString("categoria"),
                rs.getString("creado_en")
        );
    }
}