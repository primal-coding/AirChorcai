package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBClass {

  private static DBClass dbInstance;
  private Connection connection = null;
  private Statement statement = null;

  private DBClass(){
    start();
  }

  public static DBClass getInstance(){
    if (dbInstance == null)
      dbInstance = new DBClass();
    return dbInstance;
  }

  public void start(){
    try
    {
      // create a database connection
      connection = DriverManager.getConnection("jdbc:sqlite:src/model/AirChorcai.db");
      statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.
    }
    catch(SQLException e)
    {
      // if the error message is "out of memory",
      // it probably means no database file is found
      System.err.println(e.getMessage());
    }
  }

  public boolean isAdmin(String uname, String pwd){
    boolean res = false;
    try {
      ResultSet rs = statement.executeQuery("select * from Admin");
      while (rs.next()) {
        String u = rs.getString("Uname");
        String p = rs.getString("Pwd");
        if (u.equals(uname) && p.equals(pwd)) res = true;
      }
    }
    catch(SQLException e)
    {
      System.err.println(e.getMessage());
    }
    return res;
  }
  public int isCustomer(String uname, String pwd){
    int res = 0;
    try {
      ResultSet rs = statement.executeQuery("select * from Customer");
      while (rs.next()) {
        String u = rs.getString("Uname");
        String p = rs.getString("Pwd");
        if (u.equals(uname) && p.equals(pwd)) res = rs.getInt("CustomerID");
      }
    }
    catch(SQLException e)
    {
      System.err.println(e.getMessage());
    }
    return res;
  }
  public int regCustomer(Customer cst){
    int res = 0;
    try{
      statement.executeUpdate("insert into Customer (Email, Pwd, Fname, Lname, Ticket, Uname) values('"
              + cst.getEmail() + "', '"
              + cst.getPwd() + "', '"
              + cst.getFname() + "', '"
              + cst.getLname() + "', '"
              + cst.getTicketID() + "', '"
              + cst.getUname()
              + "')");
      ResultSet rs = statement.executeQuery("select * from Customer where Uname = '" + cst.getUname()+"'");
      while (rs.next()) {
        res = rs.getInt("CustomerID");
      }
    }
    catch(SQLException e)
    {
      System.err.println(e.getMessage());
    }
    return res;
  }
  public void updateCustomerDetails(Customer customer){
    try{
      statement.executeUpdate("update Customer set Ticket = '" + customer.getTicketID()
              + "', Fname = '" + customer.getFname()
              + "', Email = '" + customer.getEmail()
              + "', Pwd = '" + customer.getPwd()
              + "', Lname = '" + customer.getLname()
              + "' where CustomerID = '" + customer.getCustomerID() + "'");

    }
    catch(SQLException e)
    {
      System.err.println(e.getMessage());
    }
  }
  public void updateCustomerTicket(int customerID, int ticketID){
    try{
      statement.executeUpdate("update Customer set Ticket = '" + ticketID + "' where CustomerID = '"
      + customerID + "'");
    }
    catch(SQLException e)
    {
      System.err.println(e.getMessage());
    }
  }
  public ArrayList<Customer> getCustomers(){
    ArrayList customer = new ArrayList();
    try{
      ResultSet rs = statement.executeQuery("select * from Customer");
      while(rs.next())
      {
        Customer ct = new Customer();
        ct.setCustomerID(rs.getInt("CustomerID"));
        ct.setEmail(rs.getString("Email"));
        ct.setTicketID(rs.getInt("Ticket"));
        ct.setFname(rs.getString("Fname"));
        ct.setLname(rs.getString("Lname"));
        ct.setUname(rs.getString("Uname"));
        ct.setPwd(rs.getString("Pwd"));
        customer.add(ct);
      }
    }
    catch(SQLException e)
    {
      System.err.println(e.getMessage());
    }
    return customer;
  }
  public Customer getCustomer(int cid){
    Customer customer = new Customer();
    try {
      ResultSet rs = statement.executeQuery("select * from Customer where CustomerID = '" + cid + "'");
      while (rs.next()) {
        customer.setCustomerID(rs.getInt("CustomerID"));
        customer.setEmail(rs.getString("Email"));
        customer.setTicketID(rs.getInt("Ticket"));
        customer.setFname(rs.getString("Fname"));
        customer.setLname(rs.getString("Lname"));
        customer.setUname(rs.getString("Uname"));
        customer.setPwd(rs.getString("Pwd"));
      }
    }
    catch(SQLException e)
    {
      System.err.println(e.getMessage());
    }
    return customer;
  }
  // each row insert automatically increments the primary key
  public void addAirport(Airport airport){
    try{
      statement.executeUpdate("insert into Airport (Name) values('"
              + airport.getName()
              + "')");
    }
    catch(SQLException e)
    {
      System.err.println(e.getMessage());
    }
  }

  public void addPlane(Plane plane){
    try {
      statement.executeUpdate("insert into Plane (Model, DepAirport, ArrAirport) values('"
              + plane.getModel() + "', '"
              + plane.getDepA() + "', '"
              + plane.getArrA()
              + "')");
    }
    catch(SQLException e)
    {
      System.err.println(e.getMessage());
    }
  }
  public void addFlight(Flight flight){
    try{
      statement.executeUpdate("insert into Flight (Plane, DepAirport, ArrAirport, Year, Month, Day, Hour, Minute, Price) values('"
              + flight.getPlaneID() + "', '"
              + flight.getDepA() + "', '"
              + flight.getArrA() + "', '"
              + flight.getYear() + "', '"
              + flight.getMonth() + "', '"
              + flight.getDay() + "', '"
              + flight.getHour() + "', '"
              + flight.getMinute() + "', '"
              + flight.getPrice()
              + "')");
    }
    catch(SQLException e)
    {
      System.err.println(e.getMessage());
    }
  }
  public int addTicket(Ticket ticket){
    int res = 0;
    try{
      statement.executeUpdate("insert into Ticket (Flight, Customer, Price, Type, Luggage, VIPFacility, Seat) values('"
              + ticket.getFlightID() + "', '"
              + ticket.getCustomerID() + "', '"
              + ticket.getPrice() + "', '"
              + ticket.getType() + "', '"
              + ticket.getLuggage() + "', '"
              + ticket.getVip() + "', '"
              + ticket.getSeat()
              + "')");
      ResultSet rs = statement.executeQuery("select * from Ticket where Flight = '" + ticket.getFlightID()
              + "'and Customer = '" + ticket.getCustomerID() + "'");
      while (rs.next()) {
        res = rs.getInt("TicketID");
      }
    }
    catch(SQLException e)
    {
      System.err.println(e.getMessage());
    }
    return res;
  }

  public ArrayList getPlane(){
    ArrayList plane = new ArrayList();
    try{
      ResultSet rs = statement.executeQuery("select * from Plane");
      while(rs.next())
      {
        Plane pl = new Plane();
        pl.setPlaneID(rs.getInt("PlaneID"));
        pl.setModel(rs.getString("Model"));
        pl.setDepA(rs.getInt("DepAirport"));
        pl.setArrA(rs.getInt("ArrAirport"));
        plane.add(pl);
      }
    }
    catch(SQLException e)
    {
      System.err.println(e.getMessage());
    }
    return plane;
  }
  public ArrayList<Airport> getAirport(){
    ArrayList<Airport> airport = new ArrayList();
    try{
      ResultSet rs = statement.executeQuery("select * from Airport");
      while(rs.next())
      {
        Airport ap = new Airport();   // has to be in the loop to prevent shallow copy
        ap.setAirportID(rs.getInt("AirportID"));
        ap.setName(rs.getString("Name"));
        airport.add(ap);
      }
    }
    catch(SQLException e)
    {
      System.err.println(e.getMessage());
    }
    return airport;
  }
  public ArrayList<Flight> getFlight(){
    ArrayList<Flight> flight = new ArrayList();
    try{
      ResultSet rs = statement.executeQuery("select * from Flight");
      while(rs.next())
      {
        Flight fl = new Flight();
        fl.setFlightID(rs.getInt("FlightID"));
        fl.setPlaneID(rs.getInt("Plane"));
        fl.setDepA(rs.getInt("DepAirport"));
        fl.setArrA(rs.getInt("ArrAirport"));
        fl.setYear(rs.getInt("Year"));
        fl.setMonth(rs.getInt("Month"));
        fl.setDay(rs.getInt("Day"));
        fl.setHour(rs.getInt("Hour"));
        fl.setMinute(rs.getInt("Minute"));
        fl.setPrice(rs.getDouble("Price"));
        flight.add(fl);
      }
    }
    catch(SQLException e)
    {
      System.err.println(e.getMessage());
    }
    return flight;
  }
  public ArrayList<Ticket> getTicket(){
    ArrayList ticket = new ArrayList();
    try{
      ResultSet rs = statement.executeQuery("select * from Ticket");
      while(rs.next())
      {
        Ticket tk = new Ticket();
        tk.setTicketID(rs.getInt("TicketID"));
        tk.setFlightID(rs.getInt("Flight"));
        tk.setCustomerID(rs.getInt("Customer"));
        tk.setPrice(rs.getDouble("Price"));
        tk.setType(rs.getInt("Type"));
        tk.setLuggage(rs.getInt("Luggage"));
        tk.setVip(rs.getInt("VIPFacility"));
        tk.setSeat(rs.getInt("Seat"));
        ticket.add(tk);
      }
    }
    catch(SQLException e)
    {
      System.err.println(e.getMessage());
    }
    return ticket;
  }
  public void delAirport(Airport airport){
    try{
      statement.executeUpdate("delete from Airport where AirportID = " + airport.getAirportID());
    }
    catch(SQLException e)
    {
      System.err.println(e.getMessage());
    }
  }
  public void delPlane(Plane plane){
    try{
      statement.executeUpdate("delete from Plane where PlaneID = " + plane.getPlaneID());
    }
    catch(SQLException e)
    {
      System.err.println(e.getMessage());
    }
  }
  public void delFlight(Flight flight){
    try{
      statement.executeUpdate("delete from Flight where FlightID = " + flight.getFlightID());
    }
    catch(SQLException e)
    {
      System.err.println(e.getMessage());
    }
  }

  public void closeDB(){
    try
    {
      if(connection != null)
        connection.close();
    }
    catch(SQLException e)
    {
      // connection close failed.
      System.err.println(e.getMessage());
    }
  }

//      statement.executeUpdate("drop table if exists person");
//      statement.executeUpdate("create table person (id integer, name string)");
//      statement.executeUpdate("insert into person values(1, 'leo')");
//      statement.executeUpdate("insert into person values(2, 'yui')");

}
