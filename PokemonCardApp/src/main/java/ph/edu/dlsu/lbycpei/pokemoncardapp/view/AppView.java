package ph.edu.dlsu.lbycpei.pokemoncardapp.view;

import javafx.geometry.Rectangle2D;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.Screen;
import ph.edu.dlsu.lbycpei.pokemoncardapp.model.Pokemon;

public class AppView {
    private BorderPane root;
    private StackPane cardPanel;
    private MenuPanel menuPanel;

    private double screenHeight;

    public AppView() {
        initializeView();
        initializeScreenDimensions();
    }

    private void initializeScreenDimensions(){
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        screenHeight = screenBounds.getHeight();
    }

    private void initializeView() {
        root = new BorderPane();
        root.getStyleClass().add("root");

        // Create menu side panel
        menuPanel = new MenuPanel();
        root.setLeft(menuPanel.getPanel());

        // Create card panel at the center
        initializeCardPanel();
        root.setCenter(cardPanel);
    }

    private void initializeCardPanel() {
        // Create card display panel
        cardPanel = new StackPane();
        cardPanel.getStyleClass().add("card-panel");
        cardPanel.setPrefSize(800, screenHeight-80);
        initializeWelcomeMessage();
    }

    private void initializeWelcomeMessage() {
        // Initial welcome message
        Label welcomeLabel = new Label("Welcome to LBYCPEI Pokémon Card Collection!");
        welcomeLabel.getStyleClass().add("welcome-label");
        cardPanel.getChildren().add(welcomeLabel);
    }

    public void displayPokemonCard(Pokemon pokemon) {
        cardPanel.getChildren().clear(); // clear prior prompt message

        if (pokemon == null) {
            Label noDataLabel = new Label("No Pokemon data available");
            noDataLabel.getStyleClass().add("no-data-label");
            cardPanel.getChildren().add(noDataLabel);
            return;
        }

        PokemonCardView pokemonCardView = new PokemonCardView(pokemon);
        cardPanel.getChildren().add(pokemonCardView.getCard());
    }

    // Shows app prompt messages
    public void showMessage(String message) {
        cardPanel.getChildren().clear();
        Label messageLabel = new Label(message);
        messageLabel.getStyleClass().add("message-label");
        cardPanel.getChildren().add(messageLabel);
    }

    // Getters for controller access
    public BorderPane getRoot() { return root; }
    public Button getViewRandomBtn() { return  menuPanel.getViewRandomBtn(); }
    public Button getSlideshowBtn() { return menuPanel.getSlideshowBtn(); }
    public Button getQuitBtn() { return menuPanel.getQuitBtn(); }
    public Button getSearchBtn() { return menuPanel.getSearchBtn(); }
    public Button getRemoveBtn() { return menuPanel.getRemoveBtn(); }
    public TextField getSearchField() { return menuPanel.getSearchField(); }
    public TextField getRemoveField() { return menuPanel.getRemoveField(); }
}
