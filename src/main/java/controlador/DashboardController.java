package controlador;

import dao.ProductoDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import modelo.Producto;
import modelo.Usuario;

public class DashboardController {
    private Usuario usuario;
    @FXML private TextField txtNombre;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtStock;
    @FXML private TextField txtCategoria;
    @FXML private TableView<Producto> tablaProductos;
    @FXML private TableColumn<Producto, Integer> colId;
    @FXML private TableColumn<Producto, String> colNombre;
    @FXML private TableColumn<Producto, Double> colPrecio;
    @FXML private TableColumn<Producto, Integer> colStock;
    @FXML private TableColumn<Producto, String> colCategoria;

    private final ProductoDao productoDAO = new ProductoDao();
    private final ObservableList<Producto> lista = FXCollections.observableArrayList();
    @FXML
    public void initialize() {
        configurarColumnas();
        cargarProductos();
    }
    public void inicializar(Usuario usuario) {
        this.usuario = usuario;

        System.out.println("Bienvenido: " + usuario.getNombre());
    }
    private void configurarColumnas() {

        colId.setCellValueFactory(c ->
                new javafx.beans.property.SimpleIntegerProperty(c.getValue().getId()).asObject());

        colNombre.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getNombre()));

        colPrecio.setCellValueFactory(c ->
                new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getPrecio()));

        colStock.setCellValueFactory(c ->
                new javafx.beans.property.SimpleIntegerProperty(c.getValue().getStock()).asObject());

        colCategoria.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getCategoria()));
    }
    private void cargarProductos() {
        lista.clear();
        lista.addAll(productoDAO.listar());
        tablaProductos.setItems(lista);
    }
    @FXML
    public void agregarProducto() {

        Producto p = new Producto(
                txtNombre.getText(),
                Double.parseDouble(txtPrecio.getText()),
                Integer.parseInt(txtStock.getText()),
                txtCategoria.getText()
        );

        if (productoDAO.insertar(p)) {
            mostrarMensaje("Producto agregado");
            cargarProductos();
            limpiar();
        } else {
            mostrarError("Error al agregar");
        }
    }
    @FXML
    public void actualizarProducto() {

        Producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarError("Selecciona un producto");
            return;
        }

        seleccionado.setNombre(txtNombre.getText());
        seleccionado.setPrecio(Double.parseDouble(txtPrecio.getText()));
        seleccionado.setStock(Integer.parseInt(txtStock.getText()));
        seleccionado.setCategoria(txtCategoria.getText());

        if (productoDAO.actualizar(seleccionado)) {
            mostrarMensaje("Producto actualizado");
            cargarProductos();
            limpiar();
        } else {
            mostrarError("Error al actualizar");
        }
    }
    @FXML
    public void eliminarProducto() {

        Producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarError("Selecciona un producto");
            return;
        }

        if (productoDAO.eliminar(seleccionado.getId())) {
            mostrarMensaje("Producto eliminado");
            cargarProductos();
            limpiar();
        } else {
            mostrarError("Error al eliminar");
        }
    }
    @FXML
    public void seleccionarProducto() {

        Producto p = tablaProductos.getSelectionModel().getSelectedItem();

        if (p != null) {
            txtNombre.setText(p.getNombre());
            txtPrecio.setText(String.valueOf(p.getPrecio()));
            txtStock.setText(String.valueOf(p.getStock()));
            txtCategoria.setText(p.getCategoria());
        }
    }
    private void limpiar() {
        txtNombre.clear();
        txtPrecio.clear();
        txtStock.clear();
        txtCategoria.clear();
    }
    private void mostrarMensaje(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText(msg);
        a.show();
    }

    private void mostrarError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(msg);
        a.show();
    }
}