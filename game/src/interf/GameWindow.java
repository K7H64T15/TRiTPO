package interf;

import game.Defines;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;

public class GameWindow extends Application implements Defines {

  protected FlowPane mainScene = new FlowPane();
  protected MainMenu startMenu = new MainMenu(this, 1);
  protected MainMenu mainMenu;
  protected MiddlePanel middle;
  protected RightPanel right;
  protected LeftPanel left;
  protected OptionsPanel options;
  protected boolean autoPlayFlag = false;
  protected int difficulty = 2;

  public static void main(String[] args) {
    launch(args);
  }

  public void start(Stage primaryStage) {
    primaryStage.setTitle("game");
    primaryStage.setResizable(false);
    primaryStage.setFullScreen(false);
    mainScene.getChildren().add(startMenu.mainMenu);
    mainScene.setStyle("-fx-background-color: BLACK;");
    primaryStage.setScene(new Scene(mainScene, WINDOW_SIZE_X, WINDOW_SIZE_Y));
    primaryStage.show();
  }
}