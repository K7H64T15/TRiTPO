package interf;

import game.Defines;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainMenu implements Defines {

  protected VBox mainMenu;

  MainMenu(GameWindow gameWindow, int startFlag) {
    mainMenu = new VBox();

    if (startFlag == 0) {
      Button continueButton = new Button();
      continueButton.setText("Продолжить");
      continueButton.setPrefSize(MENU_BUTTON_SIZE_X, MENU_BUTTON_SIZE_Y);
      mainMenu.getChildren().add(continueButton);
      continueButton.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
          gameWindow.mainScene.getChildren().clear();
          gameWindow.mainScene.getChildren().add(gameWindow.left.leftPanel);
          gameWindow.mainScene.getChildren().add(gameWindow.middle.middlePanel);
          gameWindow.mainScene.getChildren().add(gameWindow.right.rightPanel);
        }
      });
    }

    Button newGameButton = new Button();
    newGameButton.setText("Новая игра");
    newGameButton.setPrefSize(MENU_BUTTON_SIZE_X, MENU_BUTTON_SIZE_Y);
    newGameButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        gameWindow.autoPlayFlag = false;
        gameWindow.mainMenu = new MainMenu(gameWindow, 0);
        gameWindow.middle = new MiddlePanel(gameWindow);
        gameWindow.right = new RightPanel(gameWindow);
        gameWindow.left = new LeftPanel(gameWindow);
        gameWindow.mainScene.getChildren().clear();
        gameWindow.mainScene.getChildren().add(gameWindow.left.leftPanel);
        gameWindow.mainScene.getChildren().add(gameWindow.middle.middlePanel);
        gameWindow.mainScene.getChildren().add(gameWindow.right.rightPanel);
      }
    });
    Button loadButton = new Button();
    loadButton.setText("Загрузить");
    loadButton.setPrefSize(MENU_BUTTON_SIZE_X, MENU_BUTTON_SIZE_Y);

    Button optionsButton = new Button();
    optionsButton.setText("Настройки");
    optionsButton.setPrefSize(MENU_BUTTON_SIZE_X, MENU_BUTTON_SIZE_Y);
    optionsButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        gameWindow.options = new OptionsPanel(gameWindow, 0);
        gameWindow.mainScene.getChildren().clear();
        gameWindow.mainScene.getChildren().add(gameWindow.options.optionsPane);
      }
    });

    Button exitButton = new Button();
    exitButton.setText("Выйти");
    exitButton.setPrefSize(MENU_BUTTON_SIZE_X, MENU_BUTTON_SIZE_Y);
    exitButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        System.exit(0);
      }
    });

    mainMenu.getChildren().addAll(newGameButton, loadButton, optionsButton,
        exitButton);
    mainMenu.setPadding(new Insets(ELEMENTS_INSETS_TOP, ELEMENTS_INSETS_RIGHT,
        ELEMENTS_INSETS_BOTTOM, ELEMENTS_INSETS_LEFT));
    mainMenu.setSpacing(ELEMENT_LARGE_SPACING);
    mainMenu.setStyle("-fx-background-color: #336699;");
  }

}