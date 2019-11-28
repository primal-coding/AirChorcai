package model;

public class Customer {

  private int customerID;
  private String email;
  private String pwd;
  private String fname;
  private String lname;
  private int ticketID = 0;
  private String uname;

  public Customer(){}
  public Customer(int customerID, String email, String pwd, String fname, String lname) {
    this.customerID = customerID;
    this.email = email;
    this.pwd = pwd;
    this.fname = fname;
    this.lname = lname;
  }

  public String getUname() {
    return uname;
  }

  public void setUname(String uname) {
    this.uname = uname;
  }

  public int getCustomerID() {
    return customerID;
  }

  public void setCustomerID(int customerID) {
    this.customerID = customerID;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public String getFname() {
    return fname;
  }

  public void setFname(String fname) {
    this.fname = fname;
  }

  public String getLname() {
    return lname;
  }

  public void setLname(String lname) {
    this.lname = lname;
  }

  public int getTicketID() {
    return ticketID;
  }

  public void setTicketID(int ticketID) {
    this.ticketID = ticketID;
  }
}
