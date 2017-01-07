package pl.edu.agh.student.simulatedannealing.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;

/**
 * Created by pingwin on 05.01.17.
 */
public class MenuItem extends Button {
    public MenuItem() {
        this.setPadding(new Insets(10));
        this.setMinSize(200, 20);
    }
}
