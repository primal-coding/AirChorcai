package model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ListPlanes {

  DBClass db = DBClass.getInstance();

  public void show(){
    ArrayList<Plane> plane = db.getPlane();
    var ref = new Object() {    // in order to use index in a lambda expression
      int index = 0;
    };
    int size = plane.size();
    Stage stage = new Stage();
    GridPane gp = new GridPane();
    gp.setHgap(5);
    gp.setVgap(5);
    gp.setAlignment(Pos.CENTER);
    Label lbl = new Label("List of Planes:");
    Button prev = new Button("Previous");
    Button next = new Button("Next");
    Button delete = new Button("Delete");
    TextArea ta = new TextArea();

    ta.setText("Plane ID: " + plane.get(ref.index).getPlaneID()
            + "\n Model: " + plane.get(ref.index).getModel()
            + "\n Departure: " + plane.get(ref.index).getDepA()
            + "\n Arrival: " + plane.get(ref.index).getArrA());

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
        ta.setText("Plane ID: " + plane.get(ref.index).getPlaneID()
                + "\n Model: " + plane.get(ref.index).getModel()
                + "\n Departure: " + plane.get(ref.index).getDepA()
                + "\n Arrival: " + plane.get(ref.index).getArrA());
      }
    });

    next.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        if (ref.index == (size - 1)) ref.index = -1;
        ref.index = ref.index + 1;
        ta.setText("Plane ID: " + plane.get(ref.index).getPlaneID()
                + "\n Model: " + plane.get(ref.index).getModel()
                + "\n Departure: " + plane.get(ref.index).getDepA()
                + "\n Arrival: " + plane.get(ref.index).getArrA());
      }
    });

    delete.setOnAction((event) -> {
      db.delPlane(plane.get(ref.index));
      stage.close();
    });

    Scene scene = new Scene(gp, 350, 250);
    stage.setScene(scene);
    stage.show();
  }
}
