package interf;

import game.Defines;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StatsPanel implements Defines {

  protected VBox statsPanel;
  protected TextField statSTRText = new TextField();
  protected TextField statAGIText = new TextField();
  protected TextField statENDText = new TextField();
  protected TextField statINTText = new TextField();
  protected TextField statWISText = new TextField();
  protected TextField statLUCText = new TextField();

  public StatsPanel(GameWindow gameWindow) {

    Label statSTRLabel = new Label("STR");
    statSTRLabel.setPrefSize(25, 25);
    Label statAGILabel = new Label("AGI");
    statAGILabel.setPrefSize(25, 25);
    Label statENDLabel = new Label("END");
    statAGILabel.setPrefSize(25, 25);
    Label statINTLabel = new Label("INT");
    statINTLabel.setPrefSize(25, 25);
    Label statWISLabel = new Label("WIS");
    statAGILabel.setPrefSize(25, 25);
    Label statLUCLabel = new Label("LUC");
    statAGILabel.setPrefSize(25, 25);

    // HP
    String strength = Integer.toString(gameWindow.middle.map.player.getStat(PLAYER_STAT_STRENGHT));
    statSTRText.insertText(0, strength);
    statSTRText.setPrefSize(STAT_SMALL_TEXT_SIZE_X, LEFT_PANEL_ELEMENTS_SIZE_Y);
    statSTRText.setDisable(true);

    HBox statSTR = new HBox();
    HBox statAGI = new HBox();
    HBox statEND = new HBox();
    HBox statINT = new HBox();
    HBox statWIS = new HBox();
    HBox statLUC = new HBox();

    statSTR.getChildren().addAll(statSTRLabel, statSTRText);
    statAGI.getChildren().addAll(statAGILabel, statAGIText);
    statEND.getChildren().addAll(statENDLabel, statENDText);
    statINT.getChildren().addAll(statINTLabel, statINTText);
    statWIS.getChildren().addAll(statWISLabel, statWISText);
    statLUC.getChildren().addAll(statLUCLabel, statLUCText);

    VBox stats = new VBox();
    stats.getChildren().addAll(statSTR, statAGI, statEND, statINT, statWIS, statLUC);
    stats.setPadding(new Insets(ELEMENTS_INSETS_TOP, ELEMENTS_INSETS_RIGHT, ELEMENTS_INSETS_BOTTOM,
        ELEMENTS_INSETS_LEFT));
    stats.setSpacing(ELEMENT_SMALL_SPACING);
    stats.setStyle("-fx-background-color: #336699;");
  }

  public void refresh(GameWindow gameWindow) {
    //
  }
}