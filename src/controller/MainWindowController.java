package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class MainWindowController {

  DBClass db = DBClass.getInstance();
  String uname;
  int cid = 0;          // customerID
  int logUser = -1;
  private Customer customer;
  private Main main;
  private int year;
  private int month;
  private int day;
  // Views
  @FXML Label label;
  @FXML GridPane gridAdmin;
  @FXML GridPane gridCustomer;
  @FXML TextField airName;
  @FXML Button btnP;
  @FXML Button btnN;
  @FXML Button btnBT;
  @FXML TextArea taC;
  @FXML CheckBox cbVF;
  @FXML CheckBox cbEL;
  @FXML CheckBox cbRT;
  @FXML TextField txtSN;
  @FXML TextField plModel;
  @FXML ComboBox plArr;
  @FXML ComboBox plDep;
  @FXML ComboBox flArr;
  @FXML ComboBox flDep;
  @FXML ComboBox flPlane;
  @FXML TextField flHour;
  @FXML TextField flMinute;
  @FXML DatePicker flDP;
  @FXML TextField flPrice;


  public void setMain(Main main){
    this.main = main;
    gridAdmin.setVisible(false);
    gridCustomer.setVisible(false);
  }


  public void handleRegister(){
    gridAdmin.setVisible(false);
    gridCustomer.setVisible(false);
    Register register = new Register(this);
    register.start(false);
  }
  public void handleExit(){
    db.closeDB();
    Platform.exit();
  }

  public void handleLogAdmin(){
    gridAdmin.setVisible(false);
    gridCustomer.setVisible(false);
    Login login = new Login(0,this);
    login.start();
  }
  public void handleLogCustomer(){
    gridAdmin.setVisible(false);
    gridCustomer.setVisible(false);
    main.changeTitle("AirCorcaigh Booking Flights - ");
    Login login = new Login(1, this);
    login.start();
  }
  public void setAdmin(){
    logUser = 0;
    main.changeTitle("AirCorcaigh Booking Flights - ADMIN");
    label.setText("Back Office Flight Management System - ADMIN panel.");
    gridAdmin.setVisible(true);

    ArrayList<Plane> planes = db.getPlane();
    ArrayList<Airport> airports = db.getAirport();

    for(Airport ap : airports){
      plArr.getItems().add(ap.getName());
      plDep.getItems().add(ap.getName());
      flDep.getItems().add(ap.getName());
      flArr.getItems().add(ap.getName());
    }
    for(Plane pl : planes){
      flPlane.getItems().add(pl.toString());
    }
    plArr.setVisibleRowCount(5);
    plDep.setVisibleRowCount(5);
    flDep.setVisibleRowCount(5);
    flPlane.setVisibleRowCount(5);
    flArr.setVisibleRowCount(5);
    plArr.getSelectionModel().selectFirst();
    plDep.getSelectionModel().selectFirst();
    flPlane.getSelectionModel().selectFirst();
    flDep.getSelectionModel().selectFirst();
    flArr.getSelectionModel().selectFirst();

    flHour.setText("17");
    flMinute.setText("59");
    flDP.setValue(LocalDate.now());
  }
  public void regCustomer(Customer customer){
    this.customer = customer;
  }
  public void setCustomer(int cid){
    customer = db.getCustomer(cid);
    logUser = 1;
    txtSN.setText("1");
    main.changeTitle("AirCorcaigh Booking Flights - " + uname);
    label.setText("Welcome " + uname + ", please select your flight.");
    gridCustomer.setVisible(true);
    selectFlight();
  }
  private void launchInvoice(Ticket tc, Customer customer){
    Invoice invoice = new Invoice(this, tc, customer);
  }

  public void setUname(String uname) { this.uname = uname; }


  public void bAirList(ActionEvent actionEvent) {
    ArrayList<Airport> airport = db.getAirport();
    Airport ap;
    var ref = new Object() {    // in order to use index in a lambda expression
      int index = 0;
    };
    int size = airport.size();
    Stage stage = new Stage();
    GridPane gp = new GridPane();
    gp.setHgap(5);
    gp.setVgap(5);
    gp.setAlignment(Pos.CENTER);
    Label lbl = new Label("List of Airports:");
    Button prev = new Button("Previous");
    Button next = new Button("Next");
    Button delete = new Button("Delete");
    TextArea ta = new TextArea();

    ta.setText("Airport ID: " + airport.get(ref.index).getAirportID() + "\n Name: " + airport.get(ref.index).getName());

    gp.add(lbl,0,0,4,1);
    gp.add(prev,0,1,1,1);
    gp.add(ta,1,1,2,1);
    gp.add(next,3,1,1,1);
    gp.add(delete,3,2,1,1);

    prev.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        if (ref.index == 0) ref.index = size;
        ref.index = ref.index - 1;
        ta.setText("Airport ID: " + airport.get(ref.index).getAirportID() + "\n Name: " + airport.get(ref.index).getName());
      }
    });

    next.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        if (ref.index == (size - 1)) ref.index = -1;
        ref.index = ref.index + 1;
        ta.setText("Airport ID: " + airport.get(ref.index).getAirportID() + "\n Name: " + airport.get(ref.index).getName());
      }
    });

    delete.setOnAction((event) -> {
      db.delAirport(airport.get(ref.index));
      stage.close();
    });

    Scene scene = new Scene(gp, 350, 250);
    stage.setScene(scene);
    stage.show();
  }
  public void bAirAdd(ActionEvent actionEvent) {
    if (airName.getText() != null && !airName.getText().equals("")){
      Airport airport = new Airport();
      airport.setName(airName.getText());
      db.addAirport(airport);
    }
    else throwAlert();
    airName.setText("");
  }

  public void bPlaneAdd(ActionEvent actionEvent){
    if (plModel.getText() != null && !plModel.getText().equals("")){
      Plane plane = new Plane();
      plane.setArrA(getPlaneArr());
      plane.setDepA(getPlaneDep());
      plane.setModel(plModel.getText());
      db.addPlane(plane);
    }
    else throwAlert();
    plModel.setText("");
  }
  public int getPlaneArr(){
    int res = 0;
    ArrayList<Airport> airports = db.getAirport();
    for (Airport ap : airports){
      if (ap.getName().equals(plArr.getValue())) res = ap.getAirportID();
    }
    return res;
  }
  public int getPlaneDep(){
    int res = 0;
    ArrayList<Airport> airports = db.getAirport();
    for (Airport ap : airports){
      if (ap.getName().equals(plDep.getValue())) res = ap.getAirportID();
    }
    return res;
  }

  public void bPlaneList (ActionEvent ae){
    ListPlanes listPlanes = new ListPlanes();
    listPlanes.show();
  }

  public void bFlightAdd(ActionEvent actionEvent){
    Flight flight = new Flight();
    flight.setPrice(Double.parseDouble(flPrice.getText()));
    flight.setMinute(Integer.parseInt(flMinute.getText()));
    flight.setHour(Integer.parseInt(flHour.getText()));
    flight.setYear(year);
    flight.setMonth(month);
    flight.setDay(day);
    flight.setArrA(getFlightArr());
    flight.setDepA(getFlightDep());
    flight.setPlaneID(getFlightPlane());
    db.addFlight(flight);
  }
  public int getFlightPlane(){
    int res = 0;
    String[] sPlane = flPlane.getValue().toString().split("_");
    res = Integer.parseInt(sPlane[0]);
    return res;
  }
  public int getFlightArr(){
    int res = 0;
    ArrayList<Airport> airports = db.getAirport();
    for (Airport ap : airports){
      if (ap.getName().equals(flArr.getValue())) res = ap.getAirportID();
    }
    return res;
  }
  public int getFlightDep(){
    int res = 0;
    ArrayList<Airport> airports = db.getAirport();
    for (Airport ap : airports){
      if (ap.getName().equals(flDep.getValue())) res = ap.getAirportID();
    }
    return res;
  }

  public void dpFlight(ActionEvent e){
    String[] sDate = flDP.getValue().toString().split("-");
    year = Integer.parseInt(sDate[0]);
    month = Integer.parseInt(sDate[1]);
    day = Integer.parseInt(sDate[2]);
  }
  public void bFlightList(ActionEvent ae){    // TODO
    ListFlights listFlights = new ListFlights();
    listFlights.show();
  }

  public void cbFlightPlane(ActionEvent ae){
  }
  public void cbPlaneDep(ActionEvent e){}
  public void cbPlaneArr(ActionEvent e){  }
  public void cbFlightDep(ActionEvent e){}
  public void cbFlightArr(ActionEvent e){}

  public void selectFlight(){
    var ref = new Object() {    // in order to use index in a lambda expression
      int index = 0;
    };
    Ticket tc = new Ticket();
    ArrayList<Flight> flights = db.getFlight();
    int size = flights.size();
    Flight fl = flights.get(ref.index);

    taC.setText("Flight #" + fl.getFlightID()
            + " - Departure: " + fl.getDepA()
            + "\nArrival: " + fl.getArrA()
            + "\nOn the " + fl.getDay() + " of the " + fl.getMonth() + " " +fl.getYear()
            + " For €" + fl.getPrice());

    btnBT.setOnAction(new EventHandler<ActionEvent>() {       // buy ticket button
      @Override
      public void handle(ActionEvent actionEvent) {
        tc.setFlightID(fl.getFlightID());
        tc.setCustomerID(customer.getCustomerID());
        tc.setPrice(fl.getPrice());
        tc.setSeat(Integer.parseInt(txtSN.getText()));
        if (cbEL.isSelected()) tc.setLuggage(1);
        else tc.setLuggage(0);
        if (cbVF.isSelected()) tc.setVip(1);
        else tc.setVip(0);
        if (cbRT.isSelected()) tc.setType(1);
        else tc.setType(0);

        launchInvoice(tc,customer);     // required as a new Invoice object can't be created in an event handler
      }
    });
    btnN.setOnAction(new EventHandler<ActionEvent>() {        // Next button
      @Override
      public void handle(ActionEvent actionEvent) {
        if (ref.index == (size - 1)) ref.index = -1;
        ref.index = ref.index + 1;
        taC.setText("Flight #" + flights.get(ref.index).getFlightID()
                + " - Departure: " + flights.get(ref.index).getDepA()
                + "\nArrival: " + flights.get(ref.index).getArrA()
                + "\nOn the " + flights.get(ref.index).getDay() + " of the " + flights.get(ref.index).getMonth() + " " +flights.get(ref.index).getYear()
                + " For €" + flights.get(ref.index).getPrice());
      }
    });
    btnP.setOnAction(new EventHandler<ActionEvent>() {        // Previous button
      @Override
      public void handle(ActionEvent actionEvent) {
        if (ref.index == 0) ref.index = size;
        ref.index = ref.index - 1;
        taC.setText("Flight #" + flights.get(ref.index).getFlightID()
                + " - Departure: " + flights.get(ref.index).getDepA()
                + "\nArrival: " + flights.get(ref.index).getArrA()
                + "\nOn the " + flights.get(ref.index).getDay() + " of the " + flights.get(ref.index).getMonth() + " " +flights.get(ref.index).getYear()
                + " For €" + flights.get(ref.index).getPrice());
      }
    });
  }
  // update customer details
  public void btUpdateDetails(ActionEvent e){
    Register register = new Register(this);
    register.setCustomerUD(customer);
    register.start(true);
  }

  public void throwAlert(){
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Invalid/Missing Field");
    alert.setHeaderText("Please try again");
    alert.setContentText("Required field missing or duplicate in a required field.");
    alert.show();
  }
}
