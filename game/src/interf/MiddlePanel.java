package interf;

import game.Defines;
import game.FieldEvent;
import game.FieldTrap;
import game.FieldTreasure;
import game.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MiddlePanel implements Defines, Runnable {

  protected FlowPane middlePanel;
  protected Map map;
  protected Canvas mapCanvas;
  protected GraphicsContext graphContext;
  protected boolean moveFlag;
  protected GameWindow gameWindow;
  protected Thread middleThread;

  public MiddlePanel(GameWindow gameWindowTmp) {
    gameWindow = gameWindowTmp;
    middlePanel = new FlowPane(MIDDLE_PANEL_SIZE_X, MIDDLE_PANEL_SIZE_Y);
    mapCanvas = new Canvas(MIDDLE_PANEL_SIZE_X, MIDDLE_PANEL_SIZE_Y);
    map = new Map(gameWindow.difficulty);
    graphContext = mapCanvas.getGraphicsContext2D();
    drawMap();
    middlePanel.getChildren().add(mapCanvas);
    moveFlag = true;

    middleThread = new Thread(this);
    middleThread.start();
  }

  public MiddlePanel(GameWindow gameWindow, boolean replayFlagActivate) {
    middlePanel = new FlowPane(MIDDLE_PANEL_SIZE_X, MIDDLE_PANEL_SIZE_Y);
    mapCanvas = new Canvas(MIDDLE_PANEL_SIZE_X, MIDDLE_PANEL_SIZE_Y);
    graphContext = mapCanvas.getGraphicsContext2D();
    middlePanel.getChildren().add(mapCanvas);
    moveFlag = true;
  }

  public void run() {
    gameWindow.mainScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
      public void handle(KeyEvent moveEvent) {
        // 1 phase
        if ((moveEvent.getCode() == KeyCode.UP) && (moveFlag == true)) {
          movePlayer(MOVE_UP);
        }
        if ((moveEvent.getCode() == KeyCode.RIGHT) && (moveFlag == true)) {
          movePlayer(MOVE_RIGHT);
        }
        if ((moveEvent.getCode() == KeyCode.DOWN) && (moveFlag == true)) {
          movePlayer(MOVE_DOWN);
        }
        if ((moveEvent.getCode() == KeyCode.LEFT) && (moveFlag == true)) {
          movePlayer(MOVE_LEFT);
        }
        drawMap();
        if (gameWindow.autoPlayFlag == true) {
          moveFlag = false;
          return;
        }
        // 2 phase
        switch (map.startEvent()) {
          case MAP_EVENT_EXIT: {
            moveFlag = false;
            EndScreen end = new EndScreen(gameWindow, 1);
            break;
          }
          case MAP_EVENT_BATTLE: {
            middlePanel.getChildren().remove(mapCanvas);
            moveFlag = false;

            MiddleBattle middleBattle = new MiddleBattle(gameWindow);
            middleBattle.drawBattle(gameWindow);
            gameWindow.left.refresh(gameWindow);
            break;
          }
          case MAP_EVENT_TRAP: {
            middlePanel.getChildren().remove(mapCanvas);
            moveFlag = false;
            drawTrap(gameWindow);
            break;
          }
          case MAP_EVENT_TREASURE: {
            middlePanel.getChildren().remove(mapCanvas);
            moveFlag = false;
            drawTreasure(gameWindow);
            break;
          }
          case MAP_EVENT_EVENT: {
            middlePanel.getChildren().remove(mapCanvas);
            moveFlag = false;
            drawEvent(gameWindow);
            break;
          }
        }
        if (map.player.getStat(PLAYER_STAT_CURRENT_HP) <= 0) {
          moveFlag = false;
          EndScreen end = new EndScreen(gameWindow, 0);// fail
        }
        map.clearField();
        gameWindow.left.refresh(gameWindow);
        drawMap();
      }
    });
  }

  void drawMap() {

    graphContext.setFill(Color.BLACK);// drawing map borders
    graphContext.fillRect(0, 0, MIDDLE_PANEL_SIZE_X, MIDDLE_PANEL_SIZE_Y);

    graphContext.setFill(Color.GRAY);// drawing invisible fields
    graphContext.fillRect(MAP_FIELD_SIZE, MAP_FIELD_SIZE, MIDDLE_PANEL_SIZE_X - 2 * MAP_FIELD_SIZE,
        MIDDLE_PANEL_SIZE_Y - 2 * MAP_FIELD_SIZE);

    for (int i = 1; i < map.mapY - 1; i++) {// drawing map borders
      for (int j = 1; j < map.mapX - 1; j++) {
        if (map.fields[i][j].getVisible() == true) {// drawing visible fields
          if (map.fields[i][j].getEnabled() == false) {
            graphContext.setFill(Color.BLACK);// drawing disabled fields
            graphContext.fillRect(j * MAP_FIELD_SIZE, i * MAP_FIELD_SIZE, MAP_FIELD_SIZE,
                MAP_FIELD_SIZE);
          } else {
            if (map.fields[i][j].getCurrent() == true) {
              graphContext.setFill(Color.CYAN);// drawing hero location
              graphContext.fillRect(j * MAP_FIELD_SIZE, i * MAP_FIELD_SIZE, MAP_FIELD_SIZE,
                  MAP_FIELD_SIZE);
            } else {
              switch (map.fields[i][j].getStatus()) {
                case MAP_EVENT_BATTLE: {
                  graphContext.setFill(Color.GREEN);// drawing monster encounters
                  graphContext.fillRect(j * MAP_FIELD_SIZE, i * MAP_FIELD_SIZE, MAP_FIELD_SIZE,
                      MAP_FIELD_SIZE);
                  break;
                }
                case MAP_EVENT_TRAP: {
                  graphContext.setFill(Color.RED);// drawing trap encounters
                  graphContext.fillRect(j * MAP_FIELD_SIZE, i * MAP_FIELD_SIZE, MAP_FIELD_SIZE,
                      MAP_FIELD_SIZE);
                  break;
                }
                case MAP_EVENT_TREASURE: {
                  graphContext.setFill(Color.YELLOW);// drawing treasure encounters
                  graphContext.fillRect(j * MAP_FIELD_SIZE, i * MAP_FIELD_SIZE, MAP_FIELD_SIZE,
                      MAP_FIELD_SIZE);
                  break;
                }
                case MAP_EVENT_EVENT: {
                  graphContext.setFill(Color.BLUE);// drawing event encounters
                  graphContext.fillRect(j * MAP_FIELD_SIZE, i * MAP_FIELD_SIZE, MAP_FIELD_SIZE,
                      MAP_FIELD_SIZE);
                  break;
                }
                default: {
                  graphContext.setFill(Color.WHITE);// drawing empty fields
                  graphContext.fillRect(j * MAP_FIELD_SIZE, i * MAP_FIELD_SIZE, MAP_FIELD_SIZE,
                      MAP_FIELD_SIZE);
                  break;
                }
              }
              if (map.fields[i][j].getExit() == true) {
                graphContext.setFill(Color.BROWN);// drawing exit field
                graphContext.fillRect(j * MAP_FIELD_SIZE, i * MAP_FIELD_SIZE, MAP_FIELD_SIZE,
                    MAP_FIELD_SIZE);
              }
            }
          }
        }
      }
    }
  }

  void drawTrap(GameWindow gameWindow) {
    FieldTrap trapEncounter = new FieldTrap(map.player, gameWindow.difficulty);

    VBox trapBox = new VBox();

    Label trapImage = new Label();
    trapImage.setPrefSize(EVENT_ELEMENTS_SIZE_X, EVENT_IMAGE_SIZE_Y);
    trapImage.setDisable(true);

    TextField trapText = new TextField();
    trapText.setText("Вы попались в ловушку");
    trapText.setPrefSize(EVENT_ELEMENTS_SIZE_X, EVENT_TEXT_SIZE_Y);
    trapText.setDisable(true);

    Button decisionButton = new Button();
    decisionButton.setPrefSize(EVENT_ELEMENTS_SIZE_X, EVENT_BUTTON_SIZE_Y);
    String score = Integer.toString(trapEncounter.damage);
    decisionButton.setText("Получить " + score + " урона");
    decisionButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        map.player.getDamage(trapEncounter.damage);
        gameWindow.left.refresh(gameWindow);
        middlePanel.getChildren().clear();
        middlePanel.getChildren().add(mapCanvas);
        moveFlag = true;

        return;
      }
    });

    trapBox.getChildren().addAll(trapImage, trapText, decisionButton);

    middlePanel.getChildren().addAll(trapBox);
  }

  void drawTreasure(GameWindow gameWindow) {

    FieldTreasure treasureEncounter = new FieldTreasure(gameWindow.difficulty);

    VBox treasureBox = new VBox();

    Label treasureImage = new Label();
    treasureImage.setPrefSize(EVENT_ELEMENTS_SIZE_X, EVENT_IMAGE_SIZE_Y);
    treasureImage.setDisable(true);

    TextField treasureText = new TextField();
    treasureText.setText("Вы нашли сокровища");
    treasureText.setPrefSize(EVENT_ELEMENTS_SIZE_X, EVENT_TEXT_SIZE_Y);
    treasureText.setDisable(true);

    Button decisionButton = new Button();
    decisionButton.setPrefSize(EVENT_ELEMENTS_SIZE_X, EVENT_BUTTON_SIZE_Y);
    String score = Integer.toString(treasureEncounter.score);
    decisionButton.setText("Получить " + score + " очков");
    decisionButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        map.player.addScore(treasureEncounter.score);
        gameWindow.left.refresh(gameWindow);
        middlePanel.getChildren().clear();
        middlePanel.getChildren().add(mapCanvas);
        moveFlag = true;

        return;
      }
    });

    treasureBox.getChildren().addAll(treasureImage, treasureText, decisionButton);

    middlePanel.getChildren().addAll(treasureBox);
  }

  void drawEvent(GameWindow gameWindow) {
    FieldEvent eventEncounter = new FieldEvent(map.player);
    VBox eventBox = new VBox();
    VBox decisionBox = new VBox();

    Label eventImage = new Label();
    eventImage.setPrefSize(EVENT_ELEMENTS_SIZE_X, EVENT_IMAGE_SIZE_Y);
    eventImage.setDisable(true);

    TextField eventText = new TextField();
    eventText.setPrefSize(EVENT_ELEMENTS_SIZE_X, EVENT_TEXT_SIZE_Y);
    eventText.setDisable(true);

    for (int i = 0; i < eventEncounter.decisionNumber; i++) {
      Button decisionButton = new Button();
      decisionButton.setPrefSize(EVENT_ELEMENTS_SIZE_X, EVENT_BUTTON_SIZE_Y);
      decisionBox.getChildren().add(decisionButton);
    }

    eventBox.getChildren().addAll(eventImage, eventText, decisionBox);
    middlePanel.getChildren().addAll(eventBox);

  }

  void movePlayer(int move) {
      map.changePlayerPos(move);
  }

}