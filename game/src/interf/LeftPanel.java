package interf;

import game.Defines;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LeftPanel implements Defines {

  protected VBox leftPanel;
  protected TextField statHPText = new TextField();
  protected TextField statMPText = new TextField();
  protected TextField statLvlText = new TextField();
  protected TextField statScoreText = new TextField();

  public LeftPanel(GameWindow gameWindow) {

    Label statHPLabel = new Label("HP");
    statHPLabel.setPrefSize(STAT_LABEL_SMALL_SIZE_X, LEFT_PANEL_ELEMENTS_SIZE_Y);
    Label statMPLabel = new Label("MP");
    statMPLabel.setPrefSize(STAT_LABEL_SMALL_SIZE_X, LEFT_PANEL_ELEMENTS_SIZE_Y);
    Label statLvlLabel = new Label("Lvl");
    statLvlLabel.setPrefSize(STAT_LABEL_SMALL_SIZE_X, LEFT_PANEL_ELEMENTS_SIZE_Y);
    Label statScoreLabel = new Label("Очки");
    statScoreLabel.setPrefSize(STAT_LABEL_LARGE_SIZE_X, LEFT_PANEL_ELEMENTS_SIZE_Y);

    // HP
    String cururrentHP =
        Integer.toString(gameWindow.middle.map.player.getStat(PLAYER_STAT_CURRENT_HP));
    String maximumHP =
        Integer.toString(gameWindow.middle.map.player.getStat(PLAYER_STAT_MAXIMUM_HP));
    statHPText.insertText(0, maximumHP);
    statHPText.insertText(0, "/");
    statHPText.insertText(0, cururrentHP);
    statHPText.setPrefSize(STAT_SMALL_TEXT_SIZE_X, LEFT_PANEL_ELEMENTS_SIZE_Y);
    statHPText.setDisable(true);

    // MP
    String cururrentMP =
        Integer.toString(gameWindow.middle.map.player.getStat(PLAYER_STAT_CURRENT_MP));
    String maximumMP =
        Integer.toString(gameWindow.middle.map.player.getStat(PLAYER_STAT_MAXIMUM_MP));
    statMPText.insertText(0, maximumMP);
    statMPText.insertText(0, "/");
    statMPText.insertText(0, cururrentMP);
    statMPText.setPrefSize(STAT_SMALL_TEXT_SIZE_X, LEFT_PANEL_ELEMENTS_SIZE_Y);
    statMPText.setDisable(true);

    // level
    String level = Integer.toString(gameWindow.middle.map.player.getStat(PLAYER_STAT_LEVEL));
    String exp = Integer.toString(gameWindow.middle.map.player.getStat(PLAYER_STAT_EXPERIENCE));
    String nextExp =
        Integer.toString(gameWindow.middle.map.player.getStat(PLAYER_STAT_LEVEL) * 100);
    statLvlText.insertText(0, ")");
    statLvlText.insertText(0, nextExp);
    statLvlText.insertText(0, "/");
    statLvlText.insertText(0, exp);
    statLvlText.insertText(0, " (");
    statLvlText.insertText(0, level);
    statLvlText.setPrefSize(STAT_MEDIUM_TEXT_SIZE_X, LEFT_PANEL_ELEMENTS_SIZE_Y);
    statLvlText.setDisable(true);

    // Score
    String score = Integer.toString(gameWindow.middle.map.player.getStat(PLAYER_STAT_SCORE));
    statScoreText.insertText(0, score);
    statScoreText.setPrefSize(STAT_LARGE_TEXT_SIZE_X, LEFT_PANEL_ELEMENTS_SIZE_Y);
    statScoreText.setDisable(true);

    HBox statHP = new HBox();
    HBox statMP = new HBox();
    HBox statLvl = new HBox();
    statHP.getChildren().addAll(statHPLabel, statHPText);
    statMP.getChildren().addAll(statMPLabel, statMPText);
    statLvl.getChildren().addAll(statLvlLabel, statLvlText);

    VBox stats = new VBox();
    stats.getChildren().addAll(statLvl, statHP, statMP, statScoreLabel, statScoreText);
    stats.setPadding(new Insets(ELEMENTS_INSETS_TOP, ELEMENTS_INSETS_RIGHT, ELEMENTS_INSETS_BOTTOM,
        ELEMENTS_INSETS_LEFT));
    stats.setSpacing(ELEMENT_SMALL_SPACING);
    stats.setStyle("-fx-background-color: #336699;");

    leftPanel = new VBox();
    leftPanel.getChildren().addAll(stats);
  }

  public void refresh(GameWindow gameWindow) {

    statHPText.clear();
    statMPText.clear();
    statLvlText.clear();
    statScoreText.clear();

    // HP
    String cururrentHP =
        Integer.toString(gameWindow.middle.map.player.getStat(PLAYER_STAT_CURRENT_HP));
    String maximumHP =
        Integer.toString(gameWindow.middle.map.player.getStat(PLAYER_STAT_MAXIMUM_HP));
    statHPText.insertText(0, maximumHP);
    statHPText.insertText(0, "/");
    statHPText.insertText(0, cururrentHP);
    statHPText.setPrefSize(STAT_SMALL_TEXT_SIZE_X, LEFT_PANEL_ELEMENTS_SIZE_Y);
    statHPText.setDisable(true);

    // MP
    String cururrentMP =
        Integer.toString(gameWindow.middle.map.player.getStat(PLAYER_STAT_CURRENT_MP));
    String maximumMP =
        Integer.toString(gameWindow.middle.map.player.getStat(PLAYER_STAT_MAXIMUM_MP));
    statMPText.insertText(0, maximumMP);
    statMPText.insertText(0, "/");
    statMPText.insertText(0, cururrentMP);
    statMPText.setPrefSize(STAT_SMALL_TEXT_SIZE_X, LEFT_PANEL_ELEMENTS_SIZE_Y);
    statMPText.setDisable(true);

    // level
    String level = Integer.toString(gameWindow.middle.map.player.getStat(PLAYER_STAT_LEVEL));
    String exp = Integer.toString(gameWindow.middle.map.player.getStat(PLAYER_STAT_EXPERIENCE));
    String nextExp =
        Integer.toString(gameWindow.middle.map.player.getStat(PLAYER_STAT_LEVEL) * 100);
    statLvlText.insertText(0, ")");
    statLvlText.insertText(0, nextExp);
    statLvlText.insertText(0, "/");
    statLvlText.insertText(0, exp);
    statLvlText.insertText(0, " (");
    statLvlText.insertText(0, level);
    statLvlText.setPrefSize(STAT_MEDIUM_TEXT_SIZE_X, LEFT_PANEL_ELEMENTS_SIZE_Y);
    statLvlText.setDisable(true);

    // Score
    String score = Integer.toString(gameWindow.middle.map.player.getStat(PLAYER_STAT_SCORE));
    statScoreText.insertText(0, score);
    statScoreText.setPrefSize(STAT_LARGE_TEXT_SIZE_X, LEFT_PANEL_ELEMENTS_SIZE_Y);
    statScoreText.setDisable(true);
  }
}