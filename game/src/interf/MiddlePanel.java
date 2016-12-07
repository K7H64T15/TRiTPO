package interf;

import java.io.FileNotFoundException;
import java.io.IOException;
import game.Defines;
import game.FieldEvent;
import game.FieldTrap;
import game.FieldTreasure;
import game.Map;
import game.Replay;
import game.WaitThread;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MiddlePanel implements Defines, Runnable {

  protected FlowPane middlePanel;
  protected Map map;
  protected Canvas mapCanvas;
  protected GraphicsContext graphContext;
  protected boolean moveFlag;
  protected boolean replayFlag;
  protected Replay replay;
  protected GameWindow gameWindow;
  protected Thread middleThread;
  protected int botMovesNumber;

  public MiddlePanel(GameWindow gameWindowTmp) {
    botMovesNumber = 0;
    gameWindow = gameWindowTmp;
    replayFlag = false;
    middlePanel = new FlowPane(MIDDLE_PANEL_SIZE_X, MIDDLE_PANEL_SIZE_Y);
    mapCanvas = new Canvas(MIDDLE_PANEL_SIZE_X, MIDDLE_PANEL_SIZE_Y);
    map = new Map(gameWindow.difficulty);
    graphContext = mapCanvas.getGraphicsContext2D();
    drawMap();
    middlePanel.getChildren().add(mapCanvas);
    moveFlag = true;
    try {
      replay = new Replay("temp_replay", false);
    } catch (FileNotFoundException noFile) {
      noFile.printStackTrace();
    }
    try {
      replay.saveMap(map);
    } catch (IOException inputError) {
      inputError.printStackTrace();
    }
    middleThread = new Thread(this);
    middleThread.start();
  }

  public MiddlePanel(GameWindow gameWindow, boolean replayFlagActivate) {
    botMovesNumber = 0;
    replayFlag = false;
    middlePanel = new FlowPane(MIDDLE_PANEL_SIZE_X, MIDDLE_PANEL_SIZE_Y);
    mapCanvas = new Canvas(MIDDLE_PANEL_SIZE_X, MIDDLE_PANEL_SIZE_Y);
    graphContext = mapCanvas.getGraphicsContext2D();
    middlePanel.getChildren().add(mapCanvas);
    moveFlag = true;
    replayFlag = replayFlagActivate;

    if (replayFlagActivate == true) {
      try {
        replay = new Replay("temp_replay", false);
      } catch (FileNotFoundException noFile) {
        noFile.printStackTrace();
      }

      loadMap(gameWindow);
      drawMap();

      gameWindow.mainScene.setOnMouseClicked(new EventHandler<MouseEvent>() {
        public void handle(MouseEvent drawEvent) {
          if (replayFlag == true) {
            replayMove(gameWindow);
            gameWindow.left.refresh(gameWindow);
            drawMap();

            WaitThread waitThread = new WaitThread();
            try {
              waitThread.waitThread.join();
            } catch (InterruptedException sleepError) {
              sleepError.printStackTrace();
            }
          }
          if (moveFlag == false) {
            replayFlag = false;
            return;
          }
        }
      });
    }
  }
  
  public void loadMap(GameWindow gameWindow) {
    map = new Map(gameWindow.difficulty, replay);
  }

  public void run() {
    gameWindow.mainScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
      public void handle(KeyEvent moveEvent) {
        // 1 phase
        if ((moveEvent.getCode() == KeyCode.UP) && (moveFlag == true)) {
          saveMove(MOVE_UP);
        }
        if ((moveEvent.getCode() == KeyCode.RIGHT) && (moveFlag == true)) {
          saveMove(MOVE_RIGHT);
        }
        if ((moveEvent.getCode() == KeyCode.DOWN) && (moveFlag == true)) {
          saveMove(MOVE_DOWN);
        }
        if ((moveEvent.getCode() == KeyCode.LEFT) && (moveFlag == true)) {
          saveMove(MOVE_LEFT);
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
            try {
              replay.saveEnd(true);
            } catch (IOException inputError) {
              inputError.printStackTrace();
            }
            break;
          }
          case MAP_EVENT_BATTLE: {
            middlePanel.getChildren().remove(mapCanvas);
            moveFlag = false;

            MiddleBattle middleBattle = new MiddleBattle(gameWindow);
            middleBattle.drawBattle(gameWindow);
            gameWindow.left.refresh(gameWindow);
            saveAction();
            break;
          }
          case MAP_EVENT_TRAP: {
            middlePanel.getChildren().remove(mapCanvas);
            moveFlag = false;
            drawTrap(gameWindow);
            saveAction();
            break;
          }
          case MAP_EVENT_TREASURE: {
            middlePanel.getChildren().remove(mapCanvas);
            moveFlag = false;
            drawTreasure(gameWindow);
            saveAction();
            break;
          }
          case MAP_EVENT_EVENT: {
            middlePanel.getChildren().remove(mapCanvas);
            moveFlag = false;
            drawEvent(gameWindow);
            saveAction();
            break;
          }
        }
        if (map.player.getStat(PLAYER_STAT_CURRENT_HP) <= 0) {
          moveFlag = false;
          EndScreen end = new EndScreen(gameWindow, 0);// fail
          try {
            replay.saveEnd(false);
          } catch (IOException inputError) {
            inputError.printStackTrace();
          }
        }
        map.clearField();
        gameWindow.left.refresh(gameWindow);
        drawMap();
      }
    });
  }

  void replayMove(GameWindow gameWindow) {
    // 1 phase
    botMovesNumber++;
    switch (replay.loadAction()) {
      case 'U': {// move up
        map.changePlayerPos(MOVE_UP);
        break;
      }
      case 'R': {// move up
        map.changePlayerPos(MOVE_RIGHT);
        break;
      }
      case 'L': {// move up
        map.changePlayerPos(MOVE_LEFT);
        break;
      }
      case 'D': {// move up
        map.changePlayerPos(MOVE_DOWN);
        break;
      }
      case 'W': {// win
        gameWindow.left.refresh(gameWindow);
        EndScreen end = new EndScreen(gameWindow, 1);
        moveFlag = false;
        return;
      }
      case 'F': {// fail
        gameWindow.left.refresh(gameWindow);
        EndScreen end = new EndScreen(gameWindow, 0);
        moveFlag = false;
        return;
      }
    }
    // 2 phase
    switch (map.startEvent()) {
      case MAP_EVENT_BATTLE: {
        map.player.getDamage(BOT_MONSTER_DAMAGE);
        map.player.addExperience(BOT_EXPERIENCE_GAIN);
        break;
      }
      case MAP_EVENT_TRAP: {
        map.player.getDamage(BOT_TRAP_DAMAGE);
        break;
      }
      case MAP_EVENT_TREASURE: {
        map.player.addScore(BOT_SCORE_GAIN);
        break;
      }
      case MAP_EVENT_EVENT: {
        map.player.addScore(BOT_SCORE_GAIN);
        break;
      }
    }
    map.clearField();
  }

  void autoPlay(GameWindow gameWindow) {
    int maximumMoves = 0;
    while (map.player.getStat(PLAYER_STAT_CURRENT_HP) >= 0) {
      // 1 phase
      boolean alreadyMoved = false;
      maximumMoves++;
      if ((map.fields[map.playerY][map.playerX + 1].getEnabled() == true)
          && (map.fields[map.playerY][map.playerX + 1].getVisited() == false)) {
        if (alreadyMoved == false) {
          saveMove(MOVE_RIGHT);
          alreadyMoved = true;
        }
      }
      if ((map.fields[map.playerY + 1][map.playerX].getEnabled() == true)
          && (map.fields[map.playerY + 1][map.playerX].getVisited() == false)) {
        if (alreadyMoved == false) {
          saveMove(MOVE_DOWN);
          alreadyMoved = true;
        }
      }
      if (map.fields[map.playerY][map.playerX - 1].getEnabled() == true) {
        if (alreadyMoved == false) {
          saveMove(MOVE_LEFT);
          alreadyMoved = true;
        }
      }
      if (map.fields[map.playerY - 1][map.playerX].getEnabled() == true) {
        if (alreadyMoved == false) {
          saveMove(MOVE_UP);
          alreadyMoved = true;
        }
      }
      // 2 phase
      switch (map.startEvent()) {
        case MAP_EVENT_EXIT: {
          EndScreen end = new EndScreen(gameWindow, 1);
          try {
            replay.saveEnd(true);
          } catch (IOException inputError) {
            inputError.printStackTrace();
          }
          return;
        }
        case MAP_EVENT_BATTLE: {
          map.player.getDamage(BOT_MONSTER_DAMAGE);
          map.player.addExperience(BOT_EXPERIENCE_GAIN);
          saveAction();
          break;
        }
        case MAP_EVENT_TRAP: {
          map.player.getDamage(BOT_TRAP_DAMAGE);
          saveAction();
          break;
        }
        case MAP_EVENT_TREASURE: {
          map.player.addScore(BOT_SCORE_GAIN);
          saveAction();
          break;
        }
        case MAP_EVENT_EVENT: {
          map.player.addScore(BOT_SCORE_GAIN);
          saveAction();
          break;
        }
      }
      if (map.player.getStat(PLAYER_STAT_CURRENT_HP) <= 0) {
        EndScreen end = new EndScreen(gameWindow, 0);// fail
        try {
          replay.saveEnd(false);
        } catch (IOException inputError) {
          inputError.printStackTrace();
        }
        return;
      }
      botMovesNumber++;
      if (maximumMoves >= BOT_MAXIMUM_MOVES) {
        map.player.addScore(-BOT_MAXIMUM_MOVES);
        EndScreen end = new EndScreen(gameWindow, 0);// fail
        try {
          replay.saveEnd(false);
        } catch (IOException inputError) {
          inputError.printStackTrace();
        }
        return;
      }
    }

    EndScreen end = new EndScreen(gameWindow, 0);
    return;
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

  void saveMove(int move) {
    if (map.changePlayerPos(move)) {
      try {
        replay.saveMove(move);
      } catch (IOException inputError) {
        inputError.printStackTrace();
      }
    }
  }

  void saveAction() {
    int tempHP = map.player.getStat(PLAYER_STAT_CURRENT_HP);
    int tempScore = map.player.getStat(PLAYER_STAT_SCORE);
    try {
      replay.saveAction(tempHP, tempScore);
    } catch (IOException inputError) {
      inputError.printStackTrace();
    }
  }
}