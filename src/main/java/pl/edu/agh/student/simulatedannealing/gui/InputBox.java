package pl.edu.agh.student.simulatedannealing.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pingwin on 06.01.17.
 */
public class InputBox extends Stage {
    private VBox mainLayout = new VBox();
    private Map<String, String> inputs = new HashMap<>();
    private List<String> parameterNames;

    public InputBox(List<String> parameterNames) {
        this.parameterNames = parameterNames;
        this.initModality(Modality.APPLICATION_MODAL);
        mainLayout.setPadding(new Insets(10));
        mainLayout.setAlignment(Pos.CENTER);
        for (String parameterName : parameterNames) {
            HBox valuePair = new HBox(10);
            valuePair.setAlignment(Pos.CENTER);
            valuePair.getChildren().add(new Label(parameterName));
            TextField textField = new TextField();
            textField.setPromptText(parameterName);

            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.isEmpty() && newValueIsNumber(newValue)) {
                    inputs.put(parameterName, newValue);
                }
            });
            valuePair.getChildren().add(textField);
            mainLayout.getChildren().add(valuePair);
        }
        Button submit = new Button("Submit");
        submit.setOnAction(event -> {
            if (stateIsValid()) {
                this.close();
            }
        });
        mainLayout.getChildren().add(submit);
        this.initStyle(StageStyle.UNDECORATED);
        this.setScene(new Scene(mainLayout));
    }

    private boolean newValueIsNumber(String newValue) {
        try {
            Double.valueOf(newValue);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    private boolean stateIsValid() {
        return getInputs() != null;
    }

    public Map<String, String> getInputs() {
        if (inputs.values().size() != parameterNames.size()) {
            return null;
        }
        for (Object value : inputs.values()) {
            if (value == null) {
                return null;
            }
        }
        return inputs;
    }
}
