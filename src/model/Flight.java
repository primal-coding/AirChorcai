package model;

public class Flight {

  private int flightID;
  private int planeID;
  private int depA;
  private int arrA;
  private int year;
  private int month;
  private int day;
  private int hour;
  private int minute;
  private double price;

  public Flight(){}
  public Flight(int planeID, int depA, int arrA, int year, int month, int day, int hour, int minute) {
    this.planeID = planeID;
    this.depA = depA;
    this.arrA = arrA;
    this.year = year;
    this.month = month;
    this.day = day;
    this.hour = hour;
    this.minute = minute;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getFlightID() {
    return flightID;
  }

  public void setFlightID(int flightID) {
    this.flightID = flightID;
  }

  public int getPlaneID() {
    return planeID;
  }

  public void setPlaneID(int planeID) {
    this.planeID = planeID;
  }

  public int getDepA() {
    return depA;
  }

  public void setDepA(int depA) {
    this.depA = depA;
  }

  public int getArrA() {
    return arrA;
  }

  public void setArrA(int arrA) {
    this.arrA = arrA;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public int getMonth() {
    return month;
  }

  public void setMonth(int month) {
    this.month = month;
  }

  public int getDay() {
    return day;
  }

  public void setDay(int day) {
    this.day = day;
  }

  public int getHour() {
    return hour;
  }

  public void setHour(int hour) {
    this.hour = hour;
  }

  public int getMinute() {
    return minute;
  }

  public void setMinute(int minute) {
    this.minute = minute;
  }
}