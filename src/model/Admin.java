package model;

import org.sqlite.core.DB;

public class Admin {

  private int adminID;
  private String uname;
  private String lname;


  public Admin(){}
  public Admin(int adminID, String uname, String pwd){
    this.adminID = adminID;
    this.uname = uname;
    this.lname = lname;
  }




  public int getAdminID() {
    return adminID;
  }

  public void setAdminID(int adminID) {
    this.adminID = adminID;
  }

  public String getUname() {
    return uname;
  }

  public void setUname(String uname) {
    this.uname = uname;
  }

  public String getLname() {
    return lname;
  }

  public void setLname(String lname) {
    this.lname = lname;
  }
}
