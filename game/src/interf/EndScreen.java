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
    
    Button decisionButton = new Button();
    decisionButton.setPrefSize(EVENT_ELEMENTS_SIZE_X, EVENT_BUTTON_SIZE_Y);
    decisionButton.setText("Выйти в меню");
    decisionButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        gameWindow.middle.moveFlag = false;
        gameWindow.mainScene.getChildren().clear();
        gameWindow.mainScene.getChildren().add(gameWindow.startMenu.mainMenu);
      }
    });  

    endBox.getChildren().addAll(endImage, endText, decisionButton);
    gameWindow.middle.middlePanel.getChildren().addAll(endBox);
  }
}
