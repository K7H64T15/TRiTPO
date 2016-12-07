package interf;

import game.Defines;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class RightPanel implements Defines {

  protected VBox rightPanel;

  public RightPanel(GameWindow gameWindow) {

    VBox menu = new VBox();
    VBox menuMenu = new VBox();

    Button menuButton = new Button();
    menuButton.setText("Меню");
    menuButton.setPrefSize(RIGHT_BUTTON_SIZE_X, RIGHT_BUTTON_SIZE_Y);
    menuButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        gameWindow.middle.moveFlag = false;
        gameWindow.middle.replayFlag = false;
        rightPanel.getChildren().remove(menu);
        rightPanel.getChildren().add(menuMenu);
      }
    });
    Button playerButton = new Button();
    playerButton.setText("Персонаж");
    playerButton.setPrefSize(RIGHT_BUTTON_SIZE_X, RIGHT_BUTTON_SIZE_Y);
    Button itemsButton = new Button();
    itemsButton.setText("Инвентарь");
    itemsButton.setPrefSize(RIGHT_BUTTON_SIZE_X, RIGHT_BUTTON_SIZE_Y);

    Button autoButton = new Button();
    autoButton.setText("Авто");
    autoButton.setPrefSize(RIGHT_BUTTON_SIZE_X, RIGHT_BUTTON_SIZE_Y);
    autoButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        if (gameWindow.autoPlayFlag == true) {
          gameWindow.autoPlayFlag = false;
          gameWindow.middle.moveFlag = true;
        } else {
          if (gameWindow.middle.replayFlag != true) {
            gameWindow.autoPlayFlag = true;
            gameWindow.middle.moveFlag = false;
            gameWindow.middle.autoPlay(gameWindow);
          }
        }
      }
    });

    menu.getChildren().addAll(menuButton, playerButton, itemsButton, autoButton);
    menu.setPadding(new Insets(ELEMENTS_INSETS_TOP, ELEMENTS_INSETS_RIGHT, ELEMENTS_INSETS_BOTTOM,
        ELEMENTS_INSETS_LEFT));
    menu.setSpacing(ELEMENT_LARGE_SPACING);
    menu.setStyle("-fx-background-color: #336699;");

    rightPanel = new VBox();
    rightPanel.getChildren().add(menu);

    Button backButton = new Button();
    backButton.setText("Назад");
    backButton.setPrefSize(RIGHT_BUTTON_SIZE_X, RIGHT_BUTTON_SIZE_Y);
    backButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        gameWindow.middle.moveFlag = true;
        gameWindow.middle.replayFlag = true;
        rightPanel.getChildren().remove(menuMenu);
        rightPanel.getChildren().add(menu);
      }
    });
    Button saveButton = new Button();
    saveButton.setText("Сохранить");
    saveButton.setPrefSize(RIGHT_BUTTON_SIZE_X, RIGHT_BUTTON_SIZE_Y);
    Button loadButton = new Button();
    loadButton.setText("Загрузить");
    loadButton.setPrefSize(RIGHT_BUTTON_SIZE_X, RIGHT_BUTTON_SIZE_Y);
    Button optionsButton = new Button();
    optionsButton.setText("Настройки");
    optionsButton.setPrefSize(RIGHT_BUTTON_SIZE_X, RIGHT_BUTTON_SIZE_Y);
    optionsButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        gameWindow.options = new OptionsPanel(gameWindow, 1);
        gameWindow.mainScene.getChildren().clear();
        gameWindow.mainScene.getChildren().add(gameWindow.options.optionsPane);
      }
    });
    Button menuExitButton = new Button();
    menuExitButton.setText("Выйти в меню");
    menuExitButton.setPrefSize(RIGHT_BUTTON_SIZE_X, RIGHT_BUTTON_SIZE_Y);
    menuExitButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        gameWindow.mainScene.getChildren().clear();
        gameWindow.mainScene.getChildren().add(gameWindow.mainMenu.mainMenu);
      }
    });
    Button ExitButton = new Button();
    ExitButton.setText("Выйти");
    ExitButton.setPrefSize(RIGHT_BUTTON_SIZE_X, RIGHT_BUTTON_SIZE_Y);
    ExitButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        System.exit(0);
      }
    });

    menuMenu.getChildren().addAll(backButton, saveButton, loadButton, optionsButton, menuExitButton,
        ExitButton);
    menuMenu.setPadding(new Insets(ELEMENTS_INSETS_TOP, ELEMENTS_INSETS_RIGHT,
        ELEMENTS_INSETS_BOTTOM, ELEMENTS_INSETS_LEFT));
    menuMenu.setSpacing(ELEMENT_LARGE_SPACING);
    menuMenu.setStyle("-fx-background-color: #336699;");
  }
}