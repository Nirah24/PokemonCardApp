package ph.edu.dlsu.lbycpei.pokemoncardapp.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class SearchPanel {

    private final VBox panel;
    private final TextField searchField;
    private final Button searchBtn;

    public SearchPanel() {
        // Search section
        Label searchLabel = new Label("Search Pokemon:");
        searchLabel.getStyleClass().add("section-label");
        searchField = new TextField();
        searchField.setPromptText("Enter Pokemon name...");
        searchField.getStyleClass().add("input-field");
        searchBtn = new Button("Search Pokemon");
        searchBtn.getStyleClass().add("action-button");
        VBox.setVgrow(searchBtn, Priority.ALWAYS);
        searchBtn.setMaxWidth(Double.MAX_VALUE);

        panel = new VBox(20);
        panel.getChildren().addAll(searchLabel, searchField, searchBtn);
    }

    public TextField getSearchField() {
        return searchField;
    }

    public Button getSearchBtn() {
        return searchBtn;
    }

    public VBox getPanel() {
        return panel;
    }
}
