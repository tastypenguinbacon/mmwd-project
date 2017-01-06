package pl.edu.agh.student.simulatedannealing.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by pingwin on 06.01.17.
 */
public class ErrorDialog extends Stage {
    public ErrorDialog() {
        this.initModality(Modality.APPLICATION_MODAL);
        Label errorMessage = new Label("You have to provide all\n parameters necessary");
        VBox layout = new VBox(5);
        errorMessage.setPadding(new Insets(10, 10, 10, 10));
        Button ok = new Button("OK");
        ok.setOnAction(event -> this.close());
        ok.setPadding(new Insets(10, 10, 10, 10));
        layout.getChildren().add(errorMessage);
        layout.getChildren().add(ok);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10, 10, 10, 10));
        this.setScene(new Scene(layout));
        this.show();
    }
}
