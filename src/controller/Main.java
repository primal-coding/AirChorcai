package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.DBClass;

import java.io.IOException;

public class Main extends Application {

    private Stage  primaryStage;
    private DBClass dbClass= DBClass.getInstance(); // in order to be able to close the DB on exit

    @Override
    public void start(Stage primaryStage) throws Exception{

        this.primaryStage = primaryStage;
        mainWindow();

    }

    public void mainWindow(){

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("../view/MainWindowView.fxml"));
            AnchorPane pane = loader.load();

            MainWindowController mainWindowController = loader.getController();
            mainWindowController.setMain(this);
            Scene scene = new Scene(pane);

            primaryStage.setTitle("AirCorcaigh Booking Flights");
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setOnCloseRequest( event -> {  // in order to close the DB
                dbClass.closeDB();                      // in case the application gets closed
                mainWindowController.handleExit();      // from the close button of the title bar
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeTitle(String title){
        primaryStage.setTitle(title);
    }




    public static void main(String[] args) {
        launch(args);
    }
}
