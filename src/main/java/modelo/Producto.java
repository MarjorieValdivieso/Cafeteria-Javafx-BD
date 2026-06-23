package modelo;

public class Producto {

    private int id;
    private String nombre;
    private double precio;
    private int stock;
    private String categoria;
    private String creado_en;

    public Producto() {}
    public Producto(int id, String nombre, double precio, int stock, String categoria, String creado_en) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
        this.creado_en = creado_en;
    }
    public Producto(String nombre, double precio, int stock, String categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getCreado_en() { return creado_en; }
    public void setCreado_en(String creado_en) { this.creado_en = creado_en; }

    @Override
    public String toString() {
        return nombre + " - " + categoria + " - $" + precio;
    }
}