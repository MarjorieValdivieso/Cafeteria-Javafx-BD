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

public class LoginController {

    @FXML private ComboBox<String> comboUsuarios;
    @FXML private PasswordField campoPassword;
    @FXML private Label etiquetaError;

    private final UsuarioDao usuarioDAO = new UsuarioDao();

    private List<Usuario> usuarios;

    @FXML
    public void initialize() {
        cargarUsuarios();
    }

    private void cargarUsuarios() {
        usuarios = usuarioDAO.listar();

        comboUsuarios.setItems(
                FXCollections.observableArrayList(
                        usuarios.stream()
                                .map(Usuario::getUsuario)
                                .toList()
                )
        );
    }
    @FXML
    public void handleLogin(javafx.event.ActionEvent event) {

        String usuarioSeleccionado = comboUsuarios.getValue();
        String password = campoPassword.getText();

        if (usuarioSeleccionado == null || password.isEmpty()) {
            mostrarError("Selecciona usuario y contraseña.");
            return;
        }

        Usuario u = usuarioDAO.autenticar(usuarioSeleccionado, password);

        if (u != null) {
            abrirDashboard(event, u);
        } else {
            mostrarError("Contraseña incorrecta.");
            campoPassword.clear();
        }
    }
    private void abrirDashboard(javafx.event.ActionEvent event, Usuario usuario) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/dashboard.fxml"));
            Parent root = loader.load();

            DashboardController ctrl = loader.getController();
            ctrl.inicializar(usuario);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1100, 700));
            stage.setTitle("Cafetería - " + usuario.getNombre());
            stage.show();

        } catch (IOException e) {
            mostrarError("Error al abrir sistema.");
        }
    }
    private void mostrarError(String msg) {
        etiquetaError.setText(msg);
        etiquetaError.setVisible(true);
    }
}