package pl.edu.agh.student.simulatedannealing.gui.plot;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by pingwin on 06.01.17.
 */
public class InfoBox extends Stage {
    private VBox vBox;
    private VBox innerVBox;
    public InfoBox() {
        vBox = new VBox(3);
        innerVBox = new VBox(3);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().add(innerVBox);
        this.setScene(new Scene(vBox));
    }

    public InfoBox add(String value, String key) {
        HBox hBox = new HBox(20);
        Label left = new Label(value);
        Label right = new Label(key);
        hBox.setAlignment(Pos.BASELINE_LEFT);
        hBox.getChildren().addAll(left, right);
        innerVBox.getChildren().add(hBox);
        return this;
    }

    public InfoBox addButton(Button button) {
        vBox.getChildren().add(button);
        return this;
    }
}
