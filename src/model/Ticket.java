package model;

public class Ticket {

  private int ticketID;
  private int flightID;
  private int customerID;
  private double price;
  private int type;
  private int luggage;
  private int vip;
  private int seat;

  public Ticket(){}
  public Ticket(int flightID, int customerID, double price, int type, int luggage, int vip, int seat) {
    this.flightID = flightID;
    this.customerID = customerID;
    this.price = price;
    this.type = type;
    this.luggage = luggage;
    this.vip = vip;
    this.seat = seat;
  }

  public int getTicketID() {
    return ticketID;
  }

  public void setTicketID(int ticketID) {
    this.ticketID = ticketID;
  }

  public int getFlightID() {
    return flightID;
  }

  public void setFlightID(int flightID) {
    this.flightID = flightID;
  }

  public int getCustomerID() {
    return customerID;
  }

  public void setCustomerID(int customerID) {
    this.customerID = customerID;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getLuggage() {
    return luggage;
  }

  public void setLuggage(int luggage) {
    this.luggage = luggage;
  }

  public int getVip() {
    return vip;
  }

  public void setVip(int vip) {
    this.vip = vip;
  }

  public int getSeat() {
    return seat;
  }

  public void setSeat(int seat) {
    this.seat = seat;
  }
}
