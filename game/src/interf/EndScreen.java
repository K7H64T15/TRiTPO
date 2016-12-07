package interf;

import game.Defines;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import scala.ScalaSort;

public class EndScreen implements Defines {

  public EndScreen(GameWindow gameWindow, int end) {
    gameWindow.middle.moveFlag = false;
    drawEnd(gameWindow, end);
  }

  void drawEnd(GameWindow gameWindow, int end) {
    gameWindow.middle.middlePanel.getChildren().clear();
    VBox endBox = new VBox();

    Label endImage = new Label();
    endImage.setPrefSize(EVENT_ELEMENTS_SIZE_X, EVENT_IMAGE_SIZE_Y);
    endImage.setDisable(true);

    String score = Integer.toString(gameWindow.middle.map.player.getStat(PLAYER_STAT_SCORE));

    TextField endText = new TextField();

    if (end == 1) {
      endText.insertText(0, "Вы победили!\n" + "\nНабрано очков: " + score);
    } else {
      endText.insertText(0, "Вы проиграли...\n" + "\nНабрано очков: " + score);
    }

    endText.setPrefSize(EVENT_ELEMENTS_SIZE_X, EVENT_TEXT_SIZE_Y);
    endText.setDisable(true);

    ReplayInfo replayInfo = new ReplayInfo(gameWindow, true);
    ScalaSort statInfo = new ScalaSort();

    int movesNumber = statInfo.movesNumber(replayInfo.movesArray);
    int frequentMove[] = statInfo.frequentMove(replayInfo.movesArray);
    String notation[] = new String[movesNumber];
    
    Button decisionButton = new Button();
    decisionButton.setPrefSize(EVENT_ELEMENTS_SIZE_X, EVENT_BUTTON_SIZE_Y);
    decisionButton.setText("Выйти в меню");
    decisionButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        gameWindow.middle.moveFlag = false;
        gameWindow.middle.replayFlag = false;
        gameWindow.mainScene.getChildren().clear();
        gameWindow.mainScene.getChildren().add(gameWindow.startMenu.mainMenu);
        for (int i = 0; i < movesNumber; i++) {
          notation[i] = statInfo.matchMoves(statInfo.cutArray(replayInfo.movesArray)[i]);
          System.out.print(i);
          System.out.println(notation[i]);
        }
      }
    });  

    String transfer;
    VBox endStat = new VBox();

    HBox movesNumberBox = new HBox();
    Label movesNumberLabel = new Label("Ходов: ");
    movesNumberLabel.setPrefSize(STAT_LABEL_LARGE_SIZE_X, LEFT_PANEL_ELEMENTS_SIZE_Y);
    TextField movesNumberText = new TextField();
    movesNumberText.setPrefSize(STAT_SMALL_TEXT_SIZE_X, LEFT_PANEL_ELEMENTS_SIZE_Y);
    transfer = Integer.toString(movesNumber);
    movesNumberText.insertText(0, transfer);
    movesNumberText.setDisable(true);
    movesNumberBox.getChildren().addAll(movesNumberLabel, movesNumberText);

    HBox movesUpBox = new HBox();
    Label movesUpLabel = new Label("Вверх");
    movesUpLabel.setPrefSize(STAT_LABEL_LARGE_SIZE_X, LEFT_PANEL_ELEMENTS_SIZE_Y);
    TextField movesUpText = new TextField();
    movesUpText.setPrefSize(STAT_SMALL_TEXT_SIZE_X, LEFT_PANEL_ELEMENTS_SIZE_Y);
    transfer = Integer.toString(frequentMove[0]);
    movesUpText.insertText(0, transfer);
    movesUpText.setDisable(true);
    movesUpBox.getChildren().addAll(movesUpLabel, movesUpText);

    HBox movesRightBox = new HBox();
    Label movesRightLabel = new Label("Вправо");
    movesRightLabel.setPrefSize(STAT_LABEL_LARGE_SIZE_X, LEFT_PANEL_ELEMENTS_SIZE_Y);
    TextField movesRightText = new TextField();
    movesRightText.setPrefSize(STAT_SMALL_TEXT_SIZE_X, LEFT_PANEL_ELEMENTS_SIZE_Y);
    transfer = Integer.toString(frequentMove[1]);
    movesRightText.insertText(0, transfer);
    movesRightText.setDisable(true);
    movesRightBox.getChildren().addAll(movesRightLabel, movesRightText);

    HBox movesDownBox = new HBox();
    Label movesDownLabel = new Label("Вниз");
    movesDownLabel.setPrefSize(STAT_LABEL_LARGE_SIZE_X, LEFT_PANEL_ELEMENTS_SIZE_Y);
    TextField movesDownText = new TextField();
    movesDownText.setPrefSize(STAT_SMALL_TEXT_SIZE_X, LEFT_PANEL_ELEMENTS_SIZE_Y);
    transfer = Integer.toString(frequentMove[2]);
    movesDownText.insertText(0, transfer);
    movesDownText.setDisable(true);
    movesDownBox.getChildren().addAll(movesDownLabel, movesDownText);

    HBox movesLeftBox = new HBox();
    Label movesLeftLabel = new Label("Влево");
    movesLeftLabel.setPrefSize(STAT_LABEL_LARGE_SIZE_X, LEFT_PANEL_ELEMENTS_SIZE_Y);
    TextField movesLeftText = new TextField();
    movesLeftText.setPrefSize(STAT_SMALL_TEXT_SIZE_X, LEFT_PANEL_ELEMENTS_SIZE_Y);
    transfer = Integer.toString(frequentMove[3]);
    movesLeftText.insertText(0, transfer);
    movesLeftText.setDisable(true);
    movesLeftBox.getChildren().addAll(movesLeftLabel, movesLeftText);

    endStat.getChildren().addAll(movesNumberBox, movesUpBox, movesRightBox, movesDownBox,
        movesLeftBox);
    endStat.setPadding(new Insets(ELEMENTS_INSETS_TOP, ELEMENTS_INSETS_RIGHT * 3,
        ELEMENTS_INSETS_BOTTOM, ELEMENTS_INSETS_LEFT * 3));
    endStat.setSpacing(ELEMENT_SMALL_SPACING);
    endStat.setStyle("-fx-background-color: #336699;");

    //gameWindow.left.leftPanel.getChildren().clear();
    //gameWindow.left.leftPanel.getChildren().add(endStat);
    endBox.getChildren().addAll(endImage, endText, decisionButton);
    gameWindow.middle.middlePanel.getChildren().addAll(endBox);
  }
}
