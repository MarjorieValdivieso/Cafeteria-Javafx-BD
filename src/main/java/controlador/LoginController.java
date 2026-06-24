package controlador;

import dao.UsuarioDao;
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

public class LoginController {

    @FXML private TextField campoUsuario;
    @FXML private ComboBox<String> comboRol;
    @FXML private PasswordField campoPassword;
    @FXML private Label etiquetaError;

    private final UsuarioDao usuarioDAO = new UsuarioDao();
    private List<Usuario> usuarios;
    @FXML
    public void initialize() {

        comboRol.getItems().setAll("ADMIN", "CAJERO", "BARISTA");
        etiquetaError.setVisible(false);
    }
    @FXML
    public void handleLogin(javafx.event.ActionEvent event) {

        String usuario = campoUsuario.getText();
        String rol = comboRol.getValue();
        String password = campoPassword.getText();

        if (usuario == null || rol == null || password.isEmpty()) {
            mostrarError("Completa usuario, rol y contraseña.");
            return;
        }

        Usuario u = usuarioDAO.autenticar(usuario, password);

        if (u != null) {

            if (!u.getRol().name().equals(rol)) {
                mostrarError("El rol no coincide con el usuario.");
                return;
            }

            abrirDashboard(event, u);

        } else {
            mostrarError("Usuario o contraseña incorrectos.");
            campoPassword.clear();
        }
    }
    private void abrirDashboard(javafx.event.ActionEvent event, Usuario usuario) {

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/vistas/dashboard.fxml")
            );

            Parent root = loader.load();

            DashboardController ctrl = loader.getController();
            ctrl.inicializar(usuario);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1100, 700));
            stage.setTitle("Cafetería - " + usuario.getNombre());
            stage.show();

        } catch (IOException e) {
            mostrarError("Error al abrir dashboard: " + e.getMessage());
        }
    }
    private void mostrarError(String msg) {
        etiquetaError.setText(msg);
        etiquetaError.setVisible(true);
    }
}