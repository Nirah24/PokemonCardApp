package ph.edu.dlsu.lbycpei.pokemoncardapp.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class RemovePanel {

    private final VBox panel;
    private final Button removeBtn;
    private final TextField removeField;

    public RemovePanel() {
        // Remove a pokemon
        Label removeLabel = new Label("Remove Pokemon:");
        removeLabel.getStyleClass().add("section-label");
        removeField = new TextField();
        removeField.setPromptText("Enter Pokemon name to remove...");
        removeField.getStyleClass().add("input-field");
        removeBtn = new Button("Remove Pokemon");
        removeBtn.getStyleClass().add("action-button");
        VBox.setVgrow(removeBtn, Priority.ALWAYS);
        removeBtn.setMaxWidth(Double.MAX_VALUE);

        panel = new VBox(20);
        panel.getChildren().addAll(removeLabel, removeField, removeBtn);
    }

    public Button getRemoveBtn() {
        return removeBtn;
    }

    public TextField getRemoveField() {
        return removeField;
    }

    public VBox getPanel() { return panel; }
}
