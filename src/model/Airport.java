package model;

public class Airport {

  private int airportID;
  private String name;

  public Airport(){}
  public Airport(int airportID, String name){
    this.airportID = airportID;
    this.name = name;
  }

  public int getAirportID() {
    return airportID;
  }

  public void setAirportID(int airportID) {
    this.airportID = airportID;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
