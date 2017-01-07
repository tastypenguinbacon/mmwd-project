package pl.edu.agh.student.simulatedannealing.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * Created by pingwin on 06.01.17.
 */
public class PizzaOrDeliverer extends Stage {
    private Boolean isPizzaSelected = null;

    public PizzaOrDeliverer() {
        VBox vertical = new VBox();
        Label label = new Label("Do you want to add a pizza or pizza deliverer");
        HBox horizontal = new HBox(10);
        Button pizza = new Button("Pizza");
        pizza.setOnAction(event -> {
            isPizzaSelected = true;
            this.close();
        });
        Button deliverer = new Button("Deliverer");
        deliverer.setOnAction(event -> {
            isPizzaSelected = false;
            this.close();
        });
        vertical.setPadding(new Insets(10));
        horizontal.setPadding(new Insets(10));
        horizontal.getChildren().addAll(pizza, deliverer);
        vertical.getChildren().addAll(label, horizontal);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setScene(new Scene(vertical));
    }

    public Boolean isPizzaSelected() {
        return isPizzaSelected;
    }
}
