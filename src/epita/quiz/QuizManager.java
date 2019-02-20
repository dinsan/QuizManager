package epita.quiz;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class QuizManager extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        QuizUI UI = new QuizUI();

        primaryStage.setTitle("Quiz Manager");

        // Create the registration form grid pane
        GridPane gridPane = UI.createQuizManagerFormPane();
        // Add UI controls to the registration form grid pane
        UI.addUIControlsQuizManger(gridPane);
        // Create a scene with registration form grid pane as the root node
        Scene scene = new Scene(gridPane, 800, 700);
        // Set the scene in primary stage
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
