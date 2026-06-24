package controlador;

import dao.UsuarioDao;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import modelo.Usuario;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class LoginController {

    @FXML private ComboBox<String> comboUsuarios;
    @FXML private ComboBox<String> comboRol;
    @FXML private PasswordField campoPassword;
    @FXML private Label etiquetaError;

    private final UsuarioDao usuarioDAO = new UsuarioDao();
    private List<Usuario> usuarios;

    // ================= INIT =================
    @FXML
    public void initialize() {
        etiquetaError.setVisible(false);
        cargarRoles();
        cargarUsuarios();
    }

    // ================= ROLES =================
    private void cargarRoles() {
        comboRol.getItems().addAll("ADMIN", "CAJERO", "BARISTA");
    }

    // ================= USUARIOS =================
    private void cargarUsuarios() {

        usuarios = usuarioDAO.listar();

        comboUsuarios.setItems(
                FXCollections.observableArrayList(
                        usuarios.stream()
                                .map(Usuario::getUsuario)
                                .collect(Collectors.toList())
                )
        );
    }

    // ================= LOGIN =================
    @FXML
    public void handleLogin(javafx.event.ActionEvent event) {

        etiquetaError.setVisible(false);

        String usuarioSeleccionado = comboUsuarios.getValue();
        String rolSeleccionado = comboRol.getValue();
        String password = campoPassword.getText();

        if (usuarioSeleccionado == null || rolSeleccionado == null || password.isEmpty()) {
            mostrarError("Completa usuario, rol y contraseña.");
            return;
        }

        Usuario u = usuarioDAO.autenticar(usuarioSeleccionado, password);

        if (u != null) {

            if (!u.getRol().name().equals(rolSeleccionado)) {
                mostrarError("El rol no coincide con el usuario.");
                return;
            }

            abrirDashboard(event, u);

        } else {
            mostrarError("Usuario o contraseña incorrectos.");
            campoPassword.clear();
        }
    }

    // ================= DASHBOARD =================
    private void abrirDashboard(javafx.event.ActionEvent event, Usuario usuario) {

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/vista/dashboard.fxml")
            );

            Parent root = loader.load();

            DashboardController ctrl = loader.getController();
            ctrl.inicializar(usuario);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1100, 700));
            stage.setTitle("Cafetería - " + usuario.getNombre());
            stage.show();

        } catch (IOException e) {
            mostrarError("Error al abrir sistema: " + e.getMessage());
        }
    }

    // ================= ERROR =================
    private void mostrarError(String msg) {
        etiquetaError.setText(msg);
        etiquetaError.setVisible(true);
    }
}