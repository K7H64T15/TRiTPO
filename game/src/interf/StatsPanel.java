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
  protected TextField statAGIext = new TextField();
  protected TextField statINTText = new TextField();

  public StatsPanel(GameWindow gameWindow) {

    Label statSTRLabel = new Label("STR");
    statSTRLabel.setPrefSize(25, 25);
    Label statAGILabel = new Label("AGI");
    statAGILabel.setPrefSize(25, 25);
    Label statINTLabel = new Label("INT");
    statINTLabel.setPrefSize(25, 25);

    // HP
    String strength = Integer.toString(gameWindow.middle.map.player.getStat(PLAYER_STAT_STRENGHT));
    statSTRText.insertText(0, strength);
    statSTRText.setPrefSize(STAT_SMALL_TEXT_SIZE_X, LEFT_PANEL_ELEMENTS_SIZE_Y);
    statSTRText.setDisable(true);

    HBox statSTR = new HBox();
    HBox statAGI = new HBox();
    HBox statINT = new HBox();
    statSTR.getChildren().addAll(statSTRLabel, statSTRText);
    statAGI.getChildren().addAll(statAGILabel, statAGIext);
    statINT.getChildren().addAll(statINTLabel, statINTText);

    VBox stats = new VBox();
    stats.getChildren().addAll(statINT, statSTR, statAGI);
    stats.setPadding(new Insets(ELEMENTS_INSETS_TOP, ELEMENTS_INSETS_RIGHT, ELEMENTS_INSETS_BOTTOM,
        ELEMENTS_INSETS_LEFT));
    stats.setSpacing(ELEMENT_SMALL_SPACING);
    stats.setStyle("-fx-background-color: #336699;");
  }

  public void refresh(GameWindow gameWindow) {
    //
  }
}