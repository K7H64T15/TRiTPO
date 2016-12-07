package interf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import game.Defines;
import game.Map;
import game.Replay;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainMenu implements Defines {

  protected VBox mainMenu;
  protected ReplayInfo sortInfo;
  
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

    Button replayButton = new Button();
    replayButton.setText("Повтор");
    replayButton.setPrefSize(MENU_BUTTON_SIZE_X, MENU_BUTTON_SIZE_Y);
    replayButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        gameWindow.mainMenu = new MainMenu(gameWindow, 0);
        gameWindow.middle = new MiddlePanel(gameWindow, true);
        gameWindow.right = new RightPanel(gameWindow);
        gameWindow.left = new LeftPanel(gameWindow);
        gameWindow.mainScene.getChildren().clear();
        gameWindow.mainScene.getChildren().add(gameWindow.left.leftPanel);
        gameWindow.mainScene.getChildren().add(gameWindow.middle.middlePanel);
        gameWindow.mainScene.getChildren().add(gameWindow.right.rightPanel);
      }
    });

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

    Button replayGenButton = new Button();
    replayGenButton.setText("Replay Gen");
    replayGenButton.setPrefSize(MENU_BUTTON_SIZE_X, MENU_BUTTON_SIZE_Y);
    replayGenButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        int i;
        for (i = 1; i <= 20; i++) {

          String replayName = "replay_";
          gameWindow.middle = new MiddlePanel(gameWindow);
          gameWindow.autoPlayFlag = true;
          gameWindow.middle.moveFlag = false;

          String replayNumber = Integer.toString(i);
          replayName += replayNumber;
          Replay replay = null;

          try {
            replay = new Replay(replayName, true);
          } catch (FileNotFoundException noFile) {
            noFile.printStackTrace();
          }
          try {
            replay.saveMap(gameWindow.middle.map);
          } catch (IOException inputError) {
            inputError.printStackTrace();
          }
          gameWindow.middle.replay = replay;
          gameWindow.middle.autoPlay(gameWindow);
        }
        sortInfo = new ReplayInfo(gameWindow);
      }
    });
    Button replaySortButton = new Button();
    replaySortButton.setText("Replay Sort");
    replaySortButton.setPrefSize(MENU_BUTTON_SIZE_X, MENU_BUTTON_SIZE_Y);
    replaySortButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {        
        replayGenerator(gameWindow);
      }
    });
    mainMenu.getChildren().addAll(newGameButton, loadButton, replayButton, optionsButton,
        exitButton, replayGenButton, replaySortButton);
    mainMenu.setPadding(new Insets(ELEMENTS_INSETS_TOP, ELEMENTS_INSETS_RIGHT,
        ELEMENTS_INSETS_BOTTOM, ELEMENTS_INSETS_LEFT));
    mainMenu.setSpacing(ELEMENT_LARGE_SPACING);
    mainMenu.setStyle("-fx-background-color: #336699;");
  }

  void replayGenerator(GameWindow gameWindow) {
    VBox replayBox = new VBox();

    Button replayLowMove = new Button();
    replayLowMove.setText("ходы -");
    replayLowMove.setPrefSize(MENU_BUTTON_SIZE_X, MENU_BUTTON_SIZE_Y);
    replayLowMove.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        sortInfo.javaSortBubble();
        sortInfo.output();
        try {
          copyReplay(sortInfo.getReplay(false));
        } catch (IOException noFile) {
          noFile.printStackTrace();
        }
      }
    });
    Button replayHighMove = new Button();
    replayHighMove.setText("ходы +");
    replayHighMove.setPrefSize(MENU_BUTTON_SIZE_X, MENU_BUTTON_SIZE_Y);
    replayHighMove.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        sortInfo.scalaSortBubble();
        sortInfo.output();
        try {
          copyReplay(sortInfo.getReplay(true));
        } catch (IOException noFile) {
          noFile.printStackTrace();
        }
      }
    });
    Button replayLowScore = new Button();
    replayLowScore.setText("счёт -");
    replayLowScore.setPrefSize(MENU_BUTTON_SIZE_X, MENU_BUTTON_SIZE_Y);
    replayLowScore.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        sortInfo.javaSortSelection();
        sortInfo.output();
        try {
          copyReplay(sortInfo.getReplay(false));
        } catch (IOException noFile) {
          noFile.printStackTrace();
        }
      }
    });
    Button replayHighScore = new Button();
    replayHighScore.setText("счёт +");
    replayHighScore.setPrefSize(MENU_BUTTON_SIZE_X, MENU_BUTTON_SIZE_Y);
    replayHighScore.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        sortInfo.scalaSortQuick();
        sortInfo.output();
        try {
          copyReplay(sortInfo.getReplay(true));
        } catch (IOException noFile) {
          noFile.printStackTrace();
        }
      }
    });

    replayBox.getChildren().addAll(replayLowMove, replayHighMove, replayLowScore, replayHighScore);
    replayBox.setPadding(new Insets(ELEMENTS_INSETS_TOP, ELEMENTS_INSETS_RIGHT,
        ELEMENTS_INSETS_BOTTOM, ELEMENTS_INSETS_LEFT));
    replayBox.setSpacing(ELEMENT_LARGE_SPACING);
    replayBox.setStyle("-fx-background-color: #226622;");

    gameWindow.mainScene.getChildren().add(replayBox);

  }

  void copyReplay(String name) throws IOException {
    String filePath = "src/replays/temp_replay";
    FileInputStream fileInput = new FileInputStream(name);
    FileOutputStream fileOutput = new FileOutputStream(filePath);
    int symb = 'X';
    while (true) {
      symb = fileInput.read();
      fileOutput.write(symb);
      if (symb == 'F') {
        fileInput.close();
        fileOutput.close();
        return;
      }
      if (symb == 'W') {
        fileInput.close();
        fileOutput.close();
        return;
      }
    }
  }
}