package ua.student.archquiz.computerarchitecturequiz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX; // Імпорт BootstrapFX

import java.io.IOException;

public class QuizApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(QuizApplication.class.getResource("/quiz-view.fxml"));

        // Збільшуємо розмір вікна для зручності (800x600)
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);


        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        stage.setTitle("Екзамен: Архітектура Комп'ютера");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}