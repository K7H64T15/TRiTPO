package interf;

import java.io.IOException;

import game.Defines;
import game.FieldMonster;
import game.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MiddleBattle implements Defines {

  protected Player player;
  protected FieldMonster monsterEcounter;
  protected int exitFlag;

  public MiddleBattle(GameWindow gameWindow) {
    player = gameWindow.middle.map.player;
    exitFlag = 0;
    monsterEcounter = new FieldMonster(player, gameWindow.difficulty);
  }

  void drawBattle(GameWindow gameWindow) {
    gameWindow.middle.middlePanel.getChildren().clear();

    if ((exitFlag == 1) || (monsterEcounter.getStat(MONSTER_STAT_HP) <= 0)) {
      player.addExperience(25 * gameWindow.difficulty);
      player.addScore(monsterEcounter.getStat(MONSTER_STAT_MAXIMUM_HP));
      gameWindow.left.refresh(gameWindow);
      gameWindow.middle.middlePanel.getChildren().clear();
      gameWindow.middle.middlePanel.getChildren().add(gameWindow.middle.mapCanvas);
      gameWindow.middle.moveFlag = true;
      return;
    }
    if (exitFlag == -1) {
      gameWindow.left.refresh(gameWindow);
      EndScreen end = new EndScreen(gameWindow, 0);
      try {
        gameWindow.middle.replay.saveEnd(false);
      } catch (IOException inputError) {
        inputError.printStackTrace();
      }
      return;
    }
    VBox battleBox = new VBox();
    VBox playerDeffenceBox = new VBox();
    VBox playerAttackBox = new VBox();
    VBox monsterStatBox = new VBox();

    Button playerBlock = new Button();
    playerBlock.setText("Блок");
    playerBlock.setTextFill(Color.RED);
    playerBlock.setPrefSize(BATTLE_BUTTON_SIZE_X, BATTLE_ELEMENTS_SIZE_Y);
    playerBlock.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        monsterEcounter.setDeffence(BATTLE_BLOCK);
        drawBattle(gameWindow);
        return;
      }
    });
    Button playerDodge = new Button();
    playerDodge.setText("Уклонение");
    playerDodge.setTextFill(Color.GREEN);
    playerDodge.setPrefSize(BATTLE_BUTTON_SIZE_X, BATTLE_ELEMENTS_SIZE_Y);
    playerDodge.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        monsterEcounter.setDeffence(BATTLE_DODGE);
        drawBattle(gameWindow);
        return;
      }
    });
    Button playerCounterstrike = new Button();
    playerCounterstrike.setText("Контратака");
    playerCounterstrike.setTextFill(Color.BLUE);
    playerCounterstrike.setPrefSize(BATTLE_BUTTON_SIZE_X, BATTLE_ELEMENTS_SIZE_Y);
    playerCounterstrike.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        monsterEcounter.setDeffence(BATTLE_COUNTERSTRIKE);
        drawBattle(gameWindow);
        return;
      }
    });

    TextField playerDef = new TextField();
    playerDef.setPrefSize(BATTLE_BUTTON_SIZE_X, BATTLE_ELEMENTS_SIZE_Y);
    String playerDeffenseText;
    switch (monsterEcounter.getStat(BATTLE_PLAYER_DEF)) {
      case BATTLE_BLOCK: {
        playerDeffenseText = "Блок";
        break;
      }
      case BATTLE_DODGE: {
        playerDeffenseText = "Уклонение";
        break;
      }
      case BATTLE_COUNTERSTRIKE: {
        playerDeffenseText = "Контратака";
        break;
      }
      default:
        playerDeffenseText = "";
    }
    playerDef.setText(playerDeffenseText);
    playerDef.setDisable(true);
    playerDeffenceBox.getChildren().addAll(playerDef, playerBlock, playerDodge, playerCounterstrike);
    playerDeffenceBox.setPadding(new Insets(ELEMENTS_INSETS_TOP, ELEMENTS_INSETS_RIGHT,
        ELEMENTS_INSETS_BOTTOM, ELEMENTS_INSETS_LEFT));
    playerDeffenceBox.setSpacing(ELEMENT_LARGE_SPACING);
    playerDeffenceBox.setStyle("-fx-background-color: #336699;");

    Button playerStrongAttack = new Button();
    playerStrongAttack.setText("Силовая атака");
    playerStrongAttack.setTextFill(Color.RED);
    playerStrongAttack.setPrefSize(BATTLE_BUTTON_SIZE_X, BATTLE_ELEMENTS_SIZE_Y);
    playerStrongAttack.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        monsterEcounter.setAttack(BATTLE_STRONG_ATTACK);
        battle(player, monsterEcounter);
        drawBattle(gameWindow);
        return;
      }
    });
    Button playerQuickAttack = new Button();
    playerQuickAttack.setText("Быстрая атака");
    playerQuickAttack.setTextFill(Color.GREEN);
    playerQuickAttack.setPrefSize(BATTLE_BUTTON_SIZE_X, BATTLE_ELEMENTS_SIZE_Y);
    playerQuickAttack.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        monsterEcounter.setAttack(BATTLE_QUICK_ATTACK);
        battle(player, monsterEcounter);
        drawBattle(gameWindow);
        return;
      }
    });
    Button playerFint = new Button();
    playerFint.setText("Финт");
    playerFint.setTextFill(Color.BLUE);
    playerFint.setPrefSize(BATTLE_BUTTON_SIZE_X, BATTLE_ELEMENTS_SIZE_Y);
    playerFint.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        monsterEcounter.setAttack(BATTLE_FINT);
        battle(player, monsterEcounter);
        drawBattle(gameWindow);
        return;
      }
    });
    TextField playerAtk = new TextField();
    playerAtk.setPrefSize(BATTLE_BUTTON_SIZE_X, BATTLE_ELEMENTS_SIZE_Y);
    String playerAttackText;
    switch (monsterEcounter.getStat(BATTLE_PLAYER_ATK)) {
      case BATTLE_STRONG_ATTACK: {
        playerAttackText = "Силовая атака";
        break;
      }
      case BATTLE_QUICK_ATTACK: {
        playerAttackText = "Быстрая атака";
        break;
      }
      case BATTLE_FINT: {
        playerAttackText = "Финт";
        break;
      }
      default:
        playerAttackText = "";
    }
    playerAtk.setText(playerAttackText);
    playerAtk.setDisable(true);
    playerAttackBox.getChildren().addAll(playerAtk, playerStrongAttack, playerQuickAttack, playerFint);
    playerAttackBox.setPadding(new Insets(ELEMENTS_INSETS_TOP, ELEMENTS_INSETS_RIGHT,
        ELEMENTS_INSETS_BOTTOM, ELEMENTS_INSETS_LEFT));
    playerAttackBox.setSpacing(ELEMENT_LARGE_SPACING);
    playerAttackBox.setStyle("-fx-background-color: #336699;");

    Label monsterNameLabel = new Label(monsterEcounter.getInfo());
    monsterNameLabel.setPrefSize(BATTLE_BUTTON_SIZE_X, BATTLE_ELEMENTS_SIZE_Y);
    Label monsterHPLabel = new Label("HP");
    monsterHPLabel.setPrefSize(BATTLE_LABEL_SMALL_SIZE_X, BATTLE_ELEMENTS_SIZE_Y);
    Label monsterAtkLabel = new Label("Atk");
    monsterAtkLabel.setPrefSize(BATTLE_LABEL_SMALL_SIZE_X, BATTLE_ELEMENTS_SIZE_Y);
    TextField monsterHPText = new TextField();
    TextField monsterAtkText = new TextField();
    // HP
    String currentHP = Integer.toString(monsterEcounter.getStat(MONSTER_STAT_HP));
    String maximumHP = Integer.toString(monsterEcounter.getStat(MONSTER_STAT_MAXIMUM_HP));
    monsterHPText.insertText(0, maximumHP);
    monsterHPText.insertText(0, "/");
    monsterHPText.insertText(0, currentHP);
    monsterHPText.setPrefSize(BATTLE_LABEL_MEDIUM_SIZE_X, BATTLE_ELEMENTS_SIZE_Y);
    monsterHPText.setDisable(true);
    // MP
    String Atk = Integer.toString(monsterEcounter.getStat(MONSTER_STAT_ATTACK));
    monsterAtkText.insertText(0, Atk);
    monsterAtkText.setPrefSize(BATTLE_LABEL_MEDIUM_SIZE_X, BATTLE_ELEMENTS_SIZE_Y);
    monsterAtkText.setDisable(true);

    HBox statHP = new HBox();
    HBox statATK = new HBox();
    statHP.getChildren().addAll(monsterHPLabel, monsterHPText);
    statATK.getChildren().addAll(monsterAtkLabel, monsterAtkText);

    TextField monsterDef = new TextField();
    monsterDef.setPrefSize(BATTLE_BUTTON_SIZE_X, BATTLE_ELEMENTS_SIZE_Y);
    String monsterDeffenseText;
    switch (monsterEcounter.getStat(BATTLE_MONSTER_DEF)) {
      case BATTLE_BLOCK: {
        monsterDeffenseText = "Блок";
        break;
      }
      case BATTLE_DODGE: {
        monsterDeffenseText = "Уклонение";
        break;
      }
      case BATTLE_COUNTERSTRIKE: {
        monsterDeffenseText = "Контратака";
        break;
      }
      default:
        monsterDeffenseText = "";
    }
    monsterDef.setText(monsterDeffenseText);
    monsterDef.setDisable(true);

    monsterStatBox.getChildren().addAll(monsterNameLabel, statHP, statATK, monsterDef);
    monsterStatBox.setPadding(new Insets(ELEMENTS_INSETS_TOP, ELEMENTS_INSETS_RIGHT,
        ELEMENTS_INSETS_BOTTOM, ELEMENTS_INSETS_LEFT));
    monsterStatBox.setSpacing(ELEMENT_LARGE_SPACING);
    monsterStatBox.setStyle("-fx-background-color: #336699;");

    Label battleImageLow = new Label();
    battleImageLow.setPrefSize(BATTLE_LOW_IMAGE_SIZE_X, BATTLE_LOW_IMAGE_SIZE_Y);
    battleImageLow.setDisable(true);

    HBox battleButtons = new HBox();
    battleButtons.getChildren().addAll(playerAttackBox, playerDeffenceBox, battleImageLow, monsterStatBox);

    Label battleImage = new Label();
    battleImage.setPrefSize(MIDDLE_PANEL_SIZE_X, BATTLE_IMAGE_SIZE_Y);
    battleImage.setDisable(true);

    battleBox.getChildren().addAll(battleImage, battleButtons);

    gameWindow.middle.middlePanel.getChildren().clear();
    gameWindow.middle.middlePanel.getChildren().add(battleBox);
    gameWindow.left.refresh(gameWindow);
    return;
  }

  void battle(Player player, FieldMonster monsterEcounter) {

    monsterEcounter.battleMonster(player);
    if (monsterEcounter.endFlag == 1) {
      exitFlag = monsterEcounter.endFlag;
      return;
    }
    monsterEcounter.battlePlayer(player);
    if (monsterEcounter.endFlag == -1) {
      exitFlag = monsterEcounter.endFlag;
      return;
    }
  }
}