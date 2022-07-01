package open.sourceproject.httppacket;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainFX extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MainFX.fxml"));
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("HttpPacket v0.2");
        stage.getIcons().add(new Image("images/http.png"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
