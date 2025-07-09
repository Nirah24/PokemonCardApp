package ph.edu.dlsu.lbycpei.pokemoncardapp;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ph.edu.dlsu.lbycpei.pokemoncardapp.controller.PokemonController;
import ph.edu.dlsu.lbycpei.pokemoncardapp.config.AppConfig;
import ph.edu.dlsu.lbycpei.pokemoncardapp.utils.BackgroundMusicManager;

import java.util.Objects;

public class PokemonApp extends Application {

    private BackgroundMusicManager musicManager;
    // Variables to store initial click position
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) {
        // Initialize music manager
        musicManager = BackgroundMusicManager.getInstance();
        // Load background music (place your music files in src/main/resources/audio/)
        musicManager.loadMusic("pokemon_theme", "/audio/pokemon_theme.mp3");
        primaryStage.initStyle(StageStyle.UNDECORATED);

        PokemonController controller = new PokemonController();
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double screenHeight = screenBounds.getHeight();
        Scene scene = new Scene(controller.getView(), 1024, screenHeight - 80);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(AppConfig.CSS_PATH)).toExternalForm());

        // Make the window draggable
        scene.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        scene.setOnMouseDragged((MouseEvent event) -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });


        // Start playing default music
        musicManager.playMusic("pokemon_theme");

        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);

        primaryStage.setOnCloseRequest(e -> {
            musicManager.dispose(); // Cleanup resources
        });

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}