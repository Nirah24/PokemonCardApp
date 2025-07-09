package ph.edu.dlsu.lbycpei.pokemoncardapp.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import ph.edu.dlsu.lbycpei.pokemoncardapp.config.AppConfig;

import java.util.Objects;

public class MenuPanel {

    private final VBox panel;
    private final SearchPanel searchPanel;
    private final RemovePanel removePanel;
    private final Button viewRandomBtn;
    private final Button slideshowBtn;
    private final Button quitBtn;

    public MenuPanel() {
        panel = new VBox(20);
        panel.getStyleClass().add("menu-panel");
        panel.setPadding(new Insets(30));
        panel.setPrefWidth(380);
        panel.setAlignment(Pos.CENTER);

        Label titleLabel = createTitleLabel();
        searchPanel = new SearchPanel();
        removePanel = new RemovePanel();
        viewRandomBtn = createViewRandomBtn();
        slideshowBtn = createSlideshowBtn();
        ImageView logo = createLogo();
        ImageView ball = createBall();
        VBox.setMargin(ball, new Insets(-50, 10, 10, 10)); // Adjust ball up
        quitBtn = createQuitButton();

        panel.getChildren().addAll(
                titleLabel,
                new Separator(), // Search Section
                searchPanel.getPanel(),
                new Separator(), // Remove Section
                removePanel.getPanel(),
                new Separator(),
                viewRandomBtn, // Random Section
                slideshowBtn,  // Slideshow Section
                new Separator(), // Quit Section
                logo,
                ball,
                quitBtn
        );
    }

    private Label createTitleLabel() {
        Label titleLabel = new Label("LBYCPEI Pokémon App");
        titleLabel.getStyleClass().add("title-label");
        return titleLabel;
    }

    private Button createViewRandomBtn() {
        Button viewRandomBtn = new Button("View Random Pokemon");
        viewRandomBtn.getStyleClass().add("primary-button");
        fillParentWidth(viewRandomBtn);
        return viewRandomBtn;
    }

    private Button createSlideshowBtn() {
        Button slideshowBtn = new Button("Start Slideshow");
        slideshowBtn.getStyleClass().add("primary-button");
        fillParentWidth(slideshowBtn);
        return slideshowBtn;
    }

    private void fillParentWidth(Button button) {
        VBox.setVgrow(button, Priority.ALWAYS);
        button.setMaxWidth(Double.MAX_VALUE);
    }

    private ImageView createLogo() {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(AppConfig.LOGO_PATH)));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(240);
        imageView.setFitHeight(80);
        return imageView;
    }

    private ImageView createBall() {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(AppConfig.POKEBALL_PATH)));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);
        return imageView;
    }

    private Button createQuitButton() {
        Button quitBtn = new Button("Exit");
        quitBtn.getStyleClass().add("quit-button");
        fillParentWidth(quitBtn);
        return quitBtn;
    }

    public VBox getPanel() {
        return panel;
    }

    public Button getQuitBtn() {
        return quitBtn;
    }

    public Button getSlideshowBtn() {
        return slideshowBtn;
    }

    public Button getViewRandomBtn() {
        return viewRandomBtn;
    }

    public Button getSearchBtn(){
        return searchPanel.getSearchBtn();
    }

    public Button getRemoveBtn(){
        return removePanel.getRemoveBtn();
    }

    public TextField getSearchField() {
        return searchPanel.getSearchField();
    }

    public TextField getRemoveField() {
        return removePanel.getRemoveField();
    }
}
