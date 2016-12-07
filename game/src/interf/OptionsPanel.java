package interf;

import game.Defines;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class OptionsPanel implements Defines {

  protected StackPane optionsPane = new StackPane();

  OptionsPanel(GameWindow gameWindow, int returnBackFlag) {

    VBox optionsBox = new VBox();
    HBox difficultyBox = new HBox();

    Label difficultyLabel = new Label("Difficulty");
    difficultyLabel.setPrefSize(OPTIONS_LABEL_SIZE_X, OPTIONS_ELEMENTS_SIZE_Y);

    TextField difficultyText = new TextField();
    String difficultyCoff = Integer.toString(gameWindow.difficulty);
    difficultyText.insertText(0, difficultyCoff);
    difficultyText.setPrefSize(OPTIONS_TEXT_SIZE_X, OPTIONS_ELEMENTS_SIZE_Y);
    difficultyText.setDisable(true);

    Button difficultyIncreaseButton = new Button();
    difficultyIncreaseButton.setText("+");
    difficultyIncreaseButton.setPrefSize(OPTIONS_DIFFICULTY_BUTTON_SIZE_X, OPTIONS_ELEMENTS_SIZE_Y);
    difficultyIncreaseButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        if (gameWindow.difficulty < DIFFICULTY_MAXIMUM) {
          gameWindow.difficulty += DIFFICULTY_MODIFER;
          String difficultyCoff = Integer.toString(gameWindow.difficulty);
          difficultyText.clear();
          difficultyText.insertText(0, difficultyCoff);
        }
      }
    });
    Button difficultyDecreaseButton = new Button();
    difficultyDecreaseButton.setText("-");
    difficultyDecreaseButton.setPrefSize(OPTIONS_DIFFICULTY_BUTTON_SIZE_X, OPTIONS_ELEMENTS_SIZE_Y);
    difficultyDecreaseButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        if (gameWindow.difficulty > DIFFICULTY_MINIMUM) {
          gameWindow.difficulty -= DIFFICULTY_MODIFER;
          String difficultyCoff = Integer.toString(gameWindow.difficulty);
          difficultyText.clear();
          difficultyText.insertText(0, difficultyCoff);
        }
      }
    });

    difficultyBox.getChildren().addAll(difficultyLabel, difficultyText, difficultyDecreaseButton,
        difficultyIncreaseButton);
    difficultyBox.setPadding(new Insets(ELEMENTS_INSETS_TOP, ELEMENTS_INSETS_RIGHT,
        ELEMENTS_INSETS_BOTTOM, ELEMENTS_INSETS_LEFT));
    difficultyBox.setSpacing(ELEMENT_SMALL_SPACING);
    difficultyBox.setStyle("-fx-background-color: #336699;");

    Button backButton = new Button();
    backButton.setText("Назад");
    backButton.setPrefSize(OPTIONS_BUTTON_SIZE_X, OPTIONS_ELEMENTS_SIZE_Y);
    backButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        if (returnBackFlag == 0) {
          gameWindow.mainScene.getChildren().clear();
          gameWindow.mainScene.getChildren().add(gameWindow.startMenu.mainMenu);
        } else {
          gameWindow.mainScene.getChildren().clear();
          gameWindow.mainScene.getChildren().add(gameWindow.left.leftPanel);
          gameWindow.mainScene.getChildren().add(gameWindow.middle.middlePanel);
          gameWindow.mainScene.getChildren().add(gameWindow.right.rightPanel);
        }
      }
    });
    optionsBox.getChildren().addAll(difficultyBox, backButton);
    optionsBox.setPadding(new Insets(ELEMENTS_INSETS_TOP, ELEMENTS_INSETS_RIGHT,
        ELEMENTS_INSETS_BOTTOM, ELEMENTS_INSETS_LEFT));
    optionsBox.setSpacing(ELEMENT_SMALL_SPACING);
    optionsBox.setStyle("-fx-background-color: #336699;");
    optionsPane.setStyle("-fx-background-color: PURPLE;");
    optionsPane.getChildren().add(optionsBox);

  }
}