package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static final String CALC = "calculator.fxml";
    private Stage stage;
    private Scene scene;

    @Override
    public void start(Stage stage){
        try{
            Parent root = FXMLLoader.load(getClass().getResource(CALC));
            scene = new Scene(root);
        }catch (IOException e){
            e.printStackTrace();
        }
        this.stage = stage;
        run();
        CalcLogic c = new CalcLogic();
    }

    private void run() {
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setTitle("Calculator");
        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}
