package tomsikr_seminarka;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent parent = FXMLLoader.load(getClass().getResource("dashboard2.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.setMinHeight(480);
        stage.setMinWidth(640);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {

            }
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
