
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/vistas/Login.fxml")
        );

        if (loader.getLocation() == null) {
            throw new RuntimeException(" No se encontró login.fxml");
        }

        Scene scene = new Scene(loader.load(), 400, 300);

        stage.setTitle("Cafetería - Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}