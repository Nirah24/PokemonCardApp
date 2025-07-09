package ph.edu.dlsu.lbycpei.pokemoncardapp.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import ph.edu.dlsu.lbycpei.pokemoncardapp.config.AppConfig;
import ph.edu.dlsu.lbycpei.pokemoncardapp.model.Pokemon;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.scene.paint.LinearGradient;


import java.util.Objects;

/**
 * PokemonCardView - A JavaFX component that creates a visual Pokemon trading card.
 *
 * This class generates a complete Pokemon card UI with:
 * - Pokémon number and name
 * - Pokémon image (GIF) or placeholder
 * - Type information with color coding
 * - Basic stats (weight, height, power level)
 * - Battle stats with visual progress bars (attack, defense, stamina)
 *
 * The card automatically styles itself based on the Pokemon's type and includes
 * proper error handling for missing images.
 *
 */
public class PokemonCardView {

    /**
     * The main container for the Pokemon card.
     * This VBox holds all the card components in a vertical layout.
     * Think of this as a tray where you arrange your food
     */
    private final VBox card;

    /**
     * Creates a new Pokemon card view for the specified Pokemon.
     *
     * This constructor builds the entire card layout including:
     * - Card styling based on Pokemon type
     * - All visual components (image, labels, stats)
     * - Proper spacing and alignment
     *
     * @param pokemon The Pokemon object containing all the data to display
     * @throws NullPointerException if pokemon is null
     */
    public PokemonCardView(Pokemon pokemon) {
        // Initialize the main card container
        card = new VBox(15); // 15px spacing between child elements
        card.getStyleClass().add("pokemon-card"); // CSS class for styling
        card.setAlignment(Pos.CENTER); // Center all content
        card.setPadding(new Insets(25)); // 25px padding on all sides
        card.setMaxWidth(450); // Maximum card width (May vary accdg. to resolution)
        card.setMaxHeight(680); // Maximum card height (May vary accdg. to resolution)

        // Set dynamic background color based on Pokemon type
        // This creates a rounded card with drop shadow effect
        card.setStyle("-fx-background-color: " + pokemon.getTypeBackground() +
                "; -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);");

        // Create all card components in order from top to bottom
        Label pokemonNumber = createPokemonNumber(pokemon.getInstanceId());
        ImageView pokemonImage = createPokemonImage(pokemon);
        Label nameLabel = new Label(pokemon.getName());
        nameLabel.getStyleClass().add("pokemon-name");
        StackPane typeBox = createTypeBox(pokemon);
        VBox basicInfo = createBasicInfo(pokemon);
        VBox statsBox = createStatsBox(pokemon);

        // Add all components to the card with separators for visual organization
        card.getChildren().addAll(
                pokemonNumber,
                pokemonImage,
                new Separator(), // Visual divider
                nameLabel,
                new Separator(),
                basicInfo,
                typeBox,
                new Separator(),
                statsBox,
                new Separator()
        );
    }

    /**
     * Creates the Pokemon type display box.
     *
     * This method handles both single-type and dual-type Pokemon:
     * - Single type: Shows one colored rectangle with type name
     * - Dual type: Shows two colored rectangles side by side with both type names
     *
     * The colors are automatically determined by the Pokemon's type(s).
     *
     * @param pokemon The Pokemon whose type(s) to display
     * @return A StackPane containing the type display elements
     */

    // TO DO  private StackPane createTypeBox(Pokemon pokemon)
    private StackPane createTypeBox(Pokemon pokemon) {
        StackPane pane = new StackPane();
        HBox typeContainer = new HBox(2); // Spacing between rectangles
        typeContainer.setAlignment(Pos.CENTER);

        String[] types = pokemon.getTypes();
        for (String type : types) {
            Rectangle typeRect = new Rectangle(200, 50);
            typeRect.setArcWidth(5);
            typeRect.setArcHeight(5);

            // Add gradient fill
            typeRect.setFill(new LinearGradient(
                    0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                    new Stop(0, getColorForType(type.trim().toUpperCase()).brighter()),
                    new Stop(1, getColorForType(type.trim().toUpperCase()))
            ));

            Label typeLabel = new Label(type.toUpperCase());
            typeLabel.setTextFill(Color.WHITE);
            typeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

            StackPane typeBox = new StackPane(typeRect, typeLabel);
            typeBox.setEffect(new DropShadow(5, 2, 2, Color.rgb(0, 0, 0, 0.3)));

            // Hover animation
            typeBox.setOnMouseEntered(e -> {
                typeBox.setScaleX(1.05);
                typeBox.setScaleY(1.05);
            });
            typeBox.setOnMouseExited(e -> {
                typeBox.setScaleX(1);
                typeBox.setScaleY(1);
            });

            typeContainer.getChildren().add(typeBox);
        }

        pane.getChildren().add(typeContainer);
        pane.setAlignment(Pos.CENTER);
        return pane;
    }


    // ADDED
    private Color getColorForType(String type) {
        switch (type.toUpperCase()) {
            case "FIRE": return Color.ORANGERED;
            case "WATER": return Color.DODGERBLUE;
            case "ELECTRIC": return Color.GOLD;
            case "GRASS": return Color.LIMEGREEN;
            case "NORMAL": return Color.LIGHTGRAY;
            case "FIGHTING": return Color.DARKRED;
            case "PSYCHIC": return Color.MEDIUMVIOLETRED;
            case "FAIRY": return Color.HOTPINK;
            case "GROUND": return Color.SANDYBROWN;
            case "ICE": return Color.LIGHTBLUE;
            case "FLYING": return Color.SKYBLUE;
            case "POISON": return Color.MEDIUMPURPLE;
            case "BUG": return Color.YELLOWGREEN;
            case "ROCK": return Color.BURLYWOOD;
            case "GHOST": return Color.SLATEBLUE;
            case "STEEL": return Color.SILVER;
            case "DRAGON": return Color.DARKVIOLET;
            case "DARK": return Color.DIMGRAY;
            default: return Color.GRAY;
        }
    }


    /**
     * Creates the basic information section of the Pokemon card.
     *
     * This section displays:
     * - Pokemon weight in kilograms
     * - Pokemon height in meters
     * - Calculated power level (computed from stats)
     *
     * @param pokemon The Pokemon whose basic info to display
     * @return A VBox containing the formatted basic information labels
     */
    private VBox createBasicInfo(Pokemon pokemon) {
        VBox basicInfo = new VBox(10); // 10px spacing between labels
        basicInfo.setAlignment(Pos.CENTER);

        // Create formatted labels
        Label weightLabel = new Label("Weight: " + pokemon.getWeight() + " kg");
        Label heightLabel = new Label("Height: " + pokemon.getHeight() + " m");

        double avgPower = (pokemon.getAttack() + pokemon.getDefense() + pokemon.getStamina()) / 3.0;
        String formattedPower = String.format("%.2f", avgPower);
        Label powerLabel = new Label("Power Level: " + formattedPower);

        // Create a subtle drop shadow
        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(1.5);
        shadow.setOffsetY(1.5);
        shadow.setColor(Color.color(0, 0, 0, 0.3)); // semi-transparent black

        // Apply font and shadow styling
        Font boldFont = Font.font("Verdana", FontWeight.NORMAL, 20);

        weightLabel.setFont(boldFont);
        weightLabel.setEffect(shadow);
        weightLabel.setTextFill(Color.WHITE);

        heightLabel.setFont(boldFont);
        heightLabel.setEffect(shadow);
        heightLabel.setTextFill(Color.WHITE);

        powerLabel.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 20));
        powerLabel.setTextFill(Color.DARKMAGENTA);
        powerLabel.setEffect(shadow);

        basicInfo.getChildren().addAll(weightLabel, heightLabel, powerLabel);
        return basicInfo;
    }



    /**
     * Creates the Pokemon number label (e.g., "#001").
     *
     * The number is formatted with a leading zero and hash symbol.
     * This label is left-aligned within the card layout.
     *
     * @param id The Pokemon's instance ID number
     * @return A formatted Label displaying the Pokemon number
     */
    private Label createPokemonNumber(int id) {
        String formattedId = String.format("#%03d", id);
        Label pokemonNumber = new Label(formattedId);

        // Optional styling
        pokemonNumber.setFont(Font.font("Consolas", FontWeight.BOLD, 16));
        pokemonNumber.setTextFill(Color.DARKSLATEGRAY);

        return pokemonNumber;
    }


    /**
     * Creates and loads the Pokemon image display.
     *
     * This method attempts to load a Pokemon GIF image from resources.
     * If the image cannot be loaded, it creates a styled placeholder instead.
     *
     * The image loading process:
     * 1. Try to load Pokemon-specific GIF from resources
     * 2. If loading fails, create a colored placeholder
     * 3. Apply drop shadow effect for visual appeal
     *
     * @param pokemon The Pokemon whose image to load
     * @return An ImageView containing either the Pokemon image or a placeholder
     */
    private ImageView createPokemonImage(Pokemon pokemon) {
        ImageView imageView = new ImageView();

        try {
            // Create file name from Pokemon name
            String name = pokemon.getName().toLowerCase().replace(" ", "");
            String imagePath = Objects.requireNonNull(getClass().getResource(
                    "/images/" + name + ".gif")).toExternalForm();

            Image image = new Image(imagePath);

            if (image.isError()) {
                // Image failed to load, use placeholder
                imageView = createPlaceholderImage(pokemon);
            } else {
                imageView.setImage(image);
            }
        } catch (Exception e) {
            // Exception occurred during loading, use placeholder
            imageView = createPlaceholderImage(pokemon);
        }

        // Set preferred size and drop shadow
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);
        imageView.setEffect(new javafx.scene.effect.DropShadow(8, Color.BLACK));

        return imageView;
    }


    /**
     * Creates a placeholder image when the actual Pokemon image cannot be loaded.
     *
     * The placeholder is a colored circle that matches the Pokemon's type color,
     * with "No Image" text in the center. This provides a consistent visual
     * experience even when images are missing.
     *
     * @param pokemon The Pokemon for which to create a placeholder
     * @return An ImageView containing the generated placeholder image
     */
    private ImageView createPlaceholderImage(Pokemon pokemon) {
        ImageView placeholder = new ImageView();
        placeholder.setFitWidth(180);
        placeholder.setFitHeight(180);

        // Create a canvas to draw the placeholder
        Canvas canvas = new Canvas(180, 180);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Draw a colored circle background
        gc.setFill(Paint.valueOf(pokemon.getTypeBackground()));
        gc.fillOval(10, 10, 160, 160); // 10px margin from edges

        // Draw "No Image" text in white
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        String prompt = "No Image";
        gc.fillText(prompt, 45, 90); // Centered positioning

        // Convert the canvas drawing to an image
        javafx.scene.SnapshotParameters params = new javafx.scene.SnapshotParameters();
        params.setFill(Color.TRANSPARENT); // Transparent background
        Image placeholderImage = canvas.snapshot(params, null);

        placeholder.setImage(placeholderImage);
        return placeholder;
    }

    /**
     * Creates the statistics display section with visual progress bars.
     *
     * This section shows three key battle statistics:
     * - Attack (red bar)
     * - Defense (blue bar)
     * - Stamina (green bar)
     *
     * Each statistic is displayed with both numerical value and a colored
     * progress bar that visually represents the stat's strength.
     *
     * @param pokemon The Pokemon whose stats to display
     * @return A VBox containing all three stat bars
     */
    private VBox createStatsBox(Pokemon pokemon) {
        VBox statsBox = new VBox(12); // 12px spacing between stat bars
        statsBox.setAlignment(Pos.CENTER);

        VBox attackBox = createStatBar("Attack", pokemon.getAttack(), Color.CRIMSON);
        VBox defenseBox = createStatBar("Defense", pokemon.getDefense(), Color.STEELBLUE);
        VBox staminaBox = createStatBar("Stamina", pokemon.getStamina(), Color.FORESTGREEN);

        statsBox.getChildren().addAll(attackBox, defenseBox, staminaBox);
        return statsBox;
    }


    /**
     * Creates an individual statistic bar with label and visual progress indicator.
     *
     * Each stat bar consists of:
     * - A text label showing the stat name and numerical value
     * - A background bar (light gray)
     * - A colored progress bar showing the stat's relative strength
     *
     * The progress bar is proportional to the stat value, with a maximum of 100%.
     *
     * @param statName The name of the statistic (e.g., "ATTACK")
     * @param value The numerical value of the statistic (0.0 to 1.0)
     * @param color The color to use for the progress bar
     * @return A VBox containing the complete stat bar display
     */
    private VBox createStatBar(String statName, double value, Color color) {
        VBox statBox = new VBox(5);
        statBox.setAlignment(Pos.CENTER_LEFT); // Left-align everything

        Label statLabel = new Label(statName + ": " + String.format("%.2f", value).toUpperCase());
        statLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        statLabel.setTextFill(Color.WHITE);

        double maxWidth = 400;
        double barHeight = 40;

        // Background bar (gray full width)
        Rectangle bgBar = new Rectangle(maxWidth, barHeight);
        bgBar.setFill(Color.LIGHTGRAY);
        bgBar.setArcWidth(10);
        bgBar.setArcHeight(10);

        // Foreground bar (scaled to value)
        Rectangle fgBar = new Rectangle(value * maxWidth, barHeight);
        fgBar.setFill(color);
        fgBar.setArcWidth(10);
        fgBar.setArcHeight(10);

        // Stack both bars
        StackPane barStack = new StackPane();
        barStack.getChildren().addAll(bgBar, fgBar);
        barStack.setAlignment(Pos.CENTER_LEFT); // make fill animate from left

        statBox.getChildren().addAll(statLabel, barStack);
        return statBox;
    }



    /**
     * Gets the root card container.
     *
     * This method provides access to the main VBox that contains all
     * the Pokemon card components. Use this to add the card to your
     * scene graph or parent container.
     *
     * @return The VBox containing the complete Pokemon card
     */
    public VBox getCard() {
        return card;
    }
}