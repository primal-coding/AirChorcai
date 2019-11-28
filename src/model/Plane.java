package model;

public class Plane {

  private int planeID;
  private String model;
  private int depA;
  private int arrA;

  public Plane(){}
  public Plane (String model, int depA, int arrA){
    this.model = model;
    this.depA = depA;
    this.arrA = arrA;
  }
  public String toString(){           // to be used in comboboxes
    String str = planeID + "_ " + model;
    return str;
  }

  public int getPlaneID() {
    return planeID;
  }
  public void setPlaneID(int planeID) {
    this.planeID = planeID;
  }
  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
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

}

