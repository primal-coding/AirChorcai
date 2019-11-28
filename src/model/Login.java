package model;

import controller.MainWindowController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login {

  private int id = -1;
  private Customer customer;
  String uname;
  int cid = 0;
  private MainWindowController mainWC;
  private DBClass db = DBClass.getInstance();

  public Login(int id, MainWindowController mainWC){
    this.id = id;
    this.mainWC = mainWC;
  }

  public void start(){
    Stage stage = new Stage();
    if (id == 0) stage.setTitle("Admin Login");
    else stage.setTitle("Customer Login");

    VBox vbox = new VBox();
    vbox.setPadding(new Insets(5,5,5,5));
    vbox.setSpacing(5);
    vbox.setAlignment(Pos.CENTER);

    Label label = new Label("Enter Username and Password:");
    TextField textUser = new TextField();
    textUser.setPromptText("enter user name");
    PasswordField passField = new PasswordField();
    passField.setPromptText("enter password");

    Button btnLogin = new Button();
    btnLogin.setText("Login");

    btnLogin.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        if (id == 0 && db.isAdmin(textUser.getText(),passField.getText())){
          mainWC.setAdmin();
        }
        else {
          if (id == 1 && (db.isCustomer(textUser.getText(),passField.getText()) > 0)){
            cid = db.isCustomer(textUser.getText(),passField.getText());
            uname = textUser.getText();
            mainWC.setUname(uname);
            mainWC.setCustomer(cid);
          }
          else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Failed login");
            alert.setHeaderText("Attempt failed");
            alert.setContentText("Username and/or Password invalid.");
            alert.show();
          }
        }
        stage.close(); // return to main window
      }
    });

    vbox.getChildren().addAll(label,textUser,passField,btnLogin);
    Scene scene = new Scene(vbox, 250, 150);
    stage.setScene(scene);
    stage.show();
  }

  public String getUname(){ return uname; }
  public int getCID(){ return cid; }

}
