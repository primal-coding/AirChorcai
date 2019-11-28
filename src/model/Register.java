package model;

import controller.MainWindowController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Register {

  private MainWindowController mainWC;
  private DBClass db = DBClass.getInstance();
  Customer customerUD = new Customer();

  public Register(MainWindowController mainWC){
    this.mainWC = mainWC;
  }

  public void start(boolean updateDetails){
    Customer customer = new Customer();
    Stage stage = new Stage();
    GridPane gp = new GridPane();
    gp.setHgap(5);
    gp.setVgap(5);
    gp.setAlignment(Pos.CENTER);

    Label lemail = new Label("Email");
    Label luname = new Label("Username");
    Label lpwd = new Label("Password");
    Label lfname = new Label("First name");
    Label llname = new Label("Last name");

    TextField temail = new TextField();
    TextField tuname = new TextField();
    PasswordField ppwd = new PasswordField();
    TextField tfname = new TextField();
    TextField tlname = new TextField();
    if (updateDetails){
      temail.setText(customerUD.getEmail());
      tuname.setText(customerUD.getUname());
      tfname.setText(customerUD.getFname());
      tlname.setText(customerUD.getLname());
    }

    Button btnRegister = new Button("Register");
    if (updateDetails) btnRegister.setText("Update Details");
    btnRegister.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        int cid = 0;
        boolean duplicate = false;
        ArrayList<Customer> customers = db.getCustomers();
        for(Customer ct : customers){
          if (tuname.getText().equals(ct.getUname())) duplicate = true;
        }
        if (duplicate && !updateDetails){
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
          alert.setHeaderText("Username already taken");
          alert.setContentText("Please choose another Username");
          alert.show();
        } else {
          customer.setEmail(temail.getText());
          customer.setUname(tuname.getText());
          customer.setPwd(ppwd.getText());
          customer.setFname(tfname.getText());
          customer.setLname(tlname.getText());
          customer.setTicketID(0);
          if (updateDetails) {
            customer.setPwd(customerUD.getPwd());       // otherwise pwd = "" and no further login for the existing customer
            customer.setCustomerID(customerUD.getCustomerID());
            cid = customerUD.getCustomerID();
            db.updateCustomerDetails(customer);
          }
          else cid = db.regCustomer(customer);
          mainWC.setUname(tuname.getText());
          mainWC.regCustomer(customer);
          mainWC.setCustomer(cid);
          stage.close();
        }
      }
    });

    gp.add(lemail,0,0);
    gp.add(luname,0,1);
    gp.add(lpwd,0,2);
    gp.add(lfname,0,3);
    gp.add(llname,0,4);
    gp.add(temail,1,0);
    gp.add(tuname,1,1);
    gp.add(ppwd,1,2);
    gp.add(tfname,1,3);
    gp.add(tlname,1,4);
    gp.add(btnRegister,0,5,2,1);

    Scene scene = new Scene(gp, 350, 450);
    stage.setScene(scene);
    if (updateDetails) stage.setTitle("Update Details");
    else stage.setTitle("Register");
    stage.show();
  }
  public void setCustomerUD(Customer customer){ this.customerUD = customer; }
}
