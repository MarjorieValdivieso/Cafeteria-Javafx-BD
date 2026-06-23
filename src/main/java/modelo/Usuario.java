package modelo;

public class Usuario {

    private int id;
    private String nombre;
    private String usuario;
    private String password;
    private Rol rol;
    private String creado_en; // opcional

    public enum Rol {
        ADMIN,
        CAJERO,
        BARISTA
    }

    public Usuario() {}

    public Usuario(int id, String nombre, String usuario, String password, Rol rol, String creado_en) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.password = password;
        this.rol = rol;
        this.creado_en = creado_en;
    }

    public Usuario(String nombre, String usuario, String password, Rol rol) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.password = password;
        this.rol = rol;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public String getCreado_en() { return creado_en; }
    public void setCreado_en(String creado_en) { this.creado_en = creado_en; }

    @Override
    public String toString() {
        return nombre + " (" + rol + ")";
    }
}