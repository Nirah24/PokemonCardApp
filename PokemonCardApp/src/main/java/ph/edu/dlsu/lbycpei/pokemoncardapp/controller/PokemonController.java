package ph.edu.dlsu.lbycpei.pokemoncardapp.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import javafx.application.Platform;
import ph.edu.dlsu.lbycpei.pokemoncardapp.model.PokemonModel;
import ph.edu.dlsu.lbycpei.pokemoncardapp.view.AppView;
import ph.edu.dlsu.lbycpei.pokemoncardapp.model.Pokemon;

import java.util.List;

public class PokemonController {
    private final PokemonModel model;
    private final AppView view;
    private Timeline slideshow;
    private int currentSlideIndex = 0;

    public PokemonController() {
        this.model = new PokemonModel();
        this.view = new AppView();
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        view.getViewRandomBtn().setOnAction(e -> handleViewRandom());
        view.getSlideshowBtn().setOnAction(e -> handleSlideshow());
        view.getSearchBtn().setOnAction(e -> handleSearch());
        view.getRemoveBtn().setOnAction(e -> handleRemove());
        view.getQuitBtn().setOnAction(e -> handleQuit());

        // Enter key support for text fields
        view.getSearchField().setOnAction(e -> handleSearch());
        view.getRemoveField().setOnAction(e -> handleRemove());
    }

    private void handleViewRandom() {
        Pokemon randomPokemon = model.getRandomPokemon();
        if (randomPokemon != null) {
            view.displayPokemonCard(randomPokemon);
            // Demonstrate polymorphism
            randomPokemon.displayInfo(); // Calls overridden method
        } else {
            view.showMessage("No Pokemon available in the database.");
        }
    }

    private void handleSlideshow() {
        List<Pokemon> allPokemon = model.getAllPokemon();
        if (allPokemon.isEmpty()) {
            view.showMessage("No Pokemon available for slideshow.");
            return;
        }

        if (slideshow != null && slideshow.getStatus() == Timeline.Status.RUNNING) {
            slideshow.stop();
            view.getSlideshowBtn().setText("Start Slideshow");
            return;
        }

        view.getSlideshowBtn().setText("Stop Slideshow");
        currentSlideIndex = 0;

        slideshow = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            if (currentSlideIndex < allPokemon.size()) {
                Pokemon pokemon = allPokemon.get(currentSlideIndex);
                view.displayPokemonCard(pokemon);
                currentSlideIndex++;
            } else {
                slideshow.stop();
                view.getSlideshowBtn().setText("Start Slideshow");
                view.showMessage("Slideshow completed!");
            }
        }));

        slideshow.setCycleCount(allPokemon.size());
        slideshow.play();

        // Show first Pokemon immediately
        view.displayPokemonCard(allPokemon.getFirst());
        currentSlideIndex = 1;
    }

    private void handleSearch() {
        String searchName = view.getSearchField().getText().trim();
        if (searchName.isEmpty()) {
            view.showMessage("Please enter a Pokemon name to search.");
            return;
        }

        Pokemon foundPokemon = model.searchPokemon(searchName);
        if (foundPokemon != null) {
            view.displayPokemonCard(foundPokemon);
            view.getSearchField().clear();
        } else {
            view.showMessage("Pokemon '" + searchName + "' not found in the database.");
        }
    }

    private void handleRemove() {
        String removeName = view.getRemoveField().getText().trim();
        if (removeName.isEmpty()) {
            view.showMessage("Please enter a Pokemon name to remove.");
            return;
        }

        boolean removed = model.removePokemon(removeName);
        if (removed) {
            view.showMessage("Pokemon '" + removeName + "' has been removed from the database.\n" +
                    "Remaining Pokemon: " + model.getPokemonCount());
            view.getRemoveField().clear();
        } else {
            view.showMessage("Pokemon '" + removeName + "' not found in the database.");
        }
    }

    private void handleQuit() {
        if (slideshow != null) {
            slideshow.stop();
        }
        Platform.exit();
    }

    public BorderPane getView() {
        return view.getRoot();
    }
}
