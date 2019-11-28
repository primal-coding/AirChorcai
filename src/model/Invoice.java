package model;

import controller.MainWindowController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Invoice {

  private MainWindowController mainWC;
  private Customer customer;
  private Ticket ticket;
  private Flight flight;
  private Airport depAir;
  private Airport arrAir;
  private DBClass db = DBClass.getInstance();

  public Invoice(MainWindowController mainWC, Ticket ticket, Customer customer){
    this.customer = customer;
    this.ticket = ticket;
    this.mainWC = mainWC;
    ArrayList<Flight> flights = db.getFlight();
    ArrayList<Airport> airports = db.getAirport();

    for (Flight fl : flights){
      if (fl.getFlightID() == ticket.getFlightID())
        flight = fl;
    }
    for (Airport airP : airports){
      if (airP.getAirportID() == flight.getDepA())
        depAir = airP;
      if (airP.getAirportID() == flight.getArrA())
        arrAir = airP;
    }
    this.ticket.setTicketID(db.addTicket(this.ticket));   // register ticket in DB and get ticketID back
    this.customer.setTicketID(ticket.getTicketID());
    db.updateCustomerTicket(this.customer.getCustomerID(), this.customer.getTicketID());

    start();
  }

  public void start(){
    Stage stage = new Stage();
    GridPane gp = new GridPane();
    gp.setHgap(5);
    gp.setVgap(5);
    gp.setAlignment(Pos.CENTER);

    Label l0 = new Label("");
    Label l1 = new Label("Dear " + customer.getFname());
    Label l2 = new Label("");
    Label l3 = new Label("Your flight from " + depAir.getName() + " to " + arrAir.getName());
    Label l4 = new Label("ID# " + flight.getFlightID() + " the " + flight.getDay()
    + "-" + flight.getMonth() + "-" + flight.getYear());

    Label l5 = new Label("Has been booked!");
    Label l6 = new Label("");
    Label l7 = new Label("Your ticket number is: " + customer.getTicketID());
    Label l8 = new Label("");
    Label l9 = new Label("Thank you for your booking.");

    gp.add(l0,3,0,1,1);
    gp.add(l1,1,1,2,1);
    gp.add(l2,1,2,2,1);
    gp.add(l3,1,3,2,1);
    gp.add(l4,1,4,2,1);
    gp.add(l5,1,5,2,1);
    gp.add(l6,1,6,2,1);
    gp.add(l7,1,7,2,1);
    gp.add(l8,1,8,2,1);
    gp.add(l9,1,9,2,1);

    Scene scene = new Scene(gp, 350, 450);
    stage.setScene(scene);
    stage.setTitle("Invoice");
    stage.show();
  }
}
