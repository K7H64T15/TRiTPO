package interf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import game.Defines;
import game.Map;
import game.Replay;
import scala.ScalaSort;

public class ReplayInfo implements Defines {
  private String filePath;
  private FileInputStream fileInput;

  protected int replaysNumber;
  protected int movesNumber[];
  protected int scoreNumber[];
  protected int replayNames[];
  protected int movesArray[];

  public ReplayInfo(GameWindow gameWindow) {
    replaysNumber = 0;
    filePath = "src/replays/replay_";
    String fileName = filePath + Integer.toString(1);

    try {// counting replays
      while ((fileInput = new FileInputStream(fileName)) != null) {
        replaysNumber++;
        fileName = filePath + Integer.toString(replaysNumber + 1);
      }
    } catch (FileNotFoundException noFile) {
      // always noFile exception on last+1 file
      // noFile.printStackTrace();
    }

    movesNumber = new int[replaysNumber];
    scoreNumber = new int[replaysNumber];
    replayNames = new int[replaysNumber];
    fileName = filePath + Integer.toString(1);

    for (int i = 0; i < replaysNumber; i++) {
      gameWindow.middle = new MiddlePanel(gameWindow);

      try {
        gameWindow.middle.replay = new Replay(fileName, false);
      } catch (FileNotFoundException noFile) {
        // noFile.printStackTrace();
      }

      gameWindow.middle.loadMap(gameWindow);
      gameWindow.autoPlayFlag = true;
      gameWindow.middle.moveFlag = false;
      gameWindow.middle.autoPlay(gameWindow);

      movesNumber[i] = gameWindow.middle.botMovesNumber;
      scoreNumber[i] = gameWindow.middle.map.player.getStat(PLAYER_STAT_SCORE);
      replayNames[i] = i + 1;// using for sorted output

      fileName = filePath + Integer.toString(replaysNumber);
    }
    // saving result
  }

  public ReplayInfo(GameWindow gameWindow, boolean replayType) {
    filePath = "src/replays/replay_";

    if (replayType == true) { // temp_replay
      String fileName = "src/replays/temp_replay";
      int i = 0;
      movesArray = new int[BOT_MAXIMUM_MOVES];
      try {
        fileInput = new FileInputStream(fileName);        
        try {
          Replay replay = new Replay("temp_replay", false);
          Map tempMap = new Map(gameWindow.difficulty, replay);
          replay.loadAction();
          replay.loadAction();
          for (i = 0; i< BOT_MAXIMUM_MOVES;i++) {
            movesArray[i] = replay.loadAction();
            //System.out.println(movesArray[i]);
          }
        } catch (FileNotFoundException noFile) {
          noFile.printStackTrace();
        }
      } catch (FileNotFoundException noFile) {
        // noFile.printStackTrace();
      }
    }
  }

  public void output() {
    int i = 0;
    javaSortBubble();
    for (i = 0; i < replaysNumber; i++) {
      System.out.println(replayNames[i]);
    }
    System.out.println('\n');

    scalaSortQuick();
    for (i = 0; i < replaysNumber; i++) {
      System.out.println(replayNames[i]);
    }
  }

  public String getReplay(boolean quality) {
    if (quality == true) {
      filePath = "src/replays/replay_" + Integer.toString(replayNames[replaysNumber - 1]);
    } else {
      filePath = "src/replays/replay_" + Integer.toString(replayNames[0]);
    }
    return filePath;
  }

  public void javaSortBubble() {
    for (int i = movesNumber.length - 1; i > 0; i--) {
      for (int j = 0; j < i; j++) {
        if (movesNumber[j] > movesNumber[j + 1]) {
          // sorting array
          int tmp = movesNumber[j];
          movesNumber[j] = movesNumber[j + 1];
          movesNumber[j + 1] = tmp;
          // sorting output
          tmp = replayNames[j];
          replayNames[j] = replayNames[j + 1];
          replayNames[j + 1] = tmp;
        }
      }
    }
  }

  public void javaSortSelection() {
    for (int i = 0; i < scoreNumber.length; i++) {
      int min = scoreNumber[i];
      int min_i = i;
      for (int j = i + 1; j < scoreNumber.length; j++) {
        if (scoreNumber[j] < min) {
          min = scoreNumber[j];
          min_i = j;
        }
      }
      if (i != min_i) {
        // sorting array
        int tmp = scoreNumber[i];
        scoreNumber[i] = scoreNumber[min_i];
        scoreNumber[min_i] = tmp;
        // sorting output
        tmp = replayNames[i];
        replayNames[i] = replayNames[min_i];
        replayNames[min_i] = tmp;
      }
    }
  }

  public void scalaSortBubble() {
    ScalaSort scalaSort = new ScalaSort();
    scalaSort.movesSort(movesNumber, replayNames, replaysNumber);
  }

  public void scalaSortQuick() {
    ScalaSort scalaSort = new ScalaSort();
    scalaSort.scoreSort(scoreNumber, replayNames);
  }

}
