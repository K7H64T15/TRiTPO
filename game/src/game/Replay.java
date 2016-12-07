package game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Replay implements Defines {

  private String filePath;
  private Map map;
  private boolean generated;
  public FileInputStream fileInput;
  public FileOutputStream fileOutput;


  public Replay(String fileName, boolean generatedTmp) throws FileNotFoundException {
    generated = generatedTmp;
    filePath = "src/replays/" + fileName;
    if (generated == true) {
      fileOutput = new FileOutputStream(filePath);
    } else {
      fileInput = new FileInputStream(filePath);
    }

  }

  public void saveMap(Map map) throws IOException {
    if (generated == false) {
      fileOutput = new FileOutputStream(filePath);
    }
    for (int i = 0; i < map.mapY; i++) {
      for (int j = 0; j < map.mapX; j++) {
        if (map.fields[i][j].enabled == false) {
          fileOutput.write('X');// disabled field
        } else {
          if (map.fields[i][j].exit == true) {
            fileOutput.write('!');// exit field
          } else {
            switch (map.fields[i][j].status) {
              case MAP_EVENT_BATTLE: {
                fileOutput.write('M');// monster field
                break;
              }
              case MAP_EVENT_TRAP: {
                fileOutput.write('T');// trap field
                break;
              }
              case MAP_EVENT_TREASURE: {
                fileOutput.write('S');// treasure field
                break;
              }
              case MAP_EVENT_EVENT: {
                fileOutput.write('E');// event field
                break;
              }
              default: {
                fileOutput.write(' ');// empty field
                break;
              }
            }
          }
        }
      }
      fileOutput.write('\n');
    }
    fileOutput.write('\n');
  }

  public void saveMove(int move) throws IOException {
    switch (move) {
      case MOVE_UP: {
        fileOutput.write('U');
        break;
      }
      case MOVE_RIGHT: {
        fileOutput.write('R');
        break;
      }
      case MOVE_DOWN: {
        fileOutput.write('D');
        break;
      }
      case MOVE_LEFT: {
        fileOutput.write('L');
        break;
      }
    }
  }

  public void saveAction(int currentHP, int score) throws IOException {
    // fileOutput.write(currentHP);
    // fileOutput.write(score);
  }

  public void saveEnd(boolean end) throws IOException {
    if (end) {
      fileOutput.write('W');
    } else {
      fileOutput.write('F');
    }
  }

  public int loadAction() {
    try {
      return fileInput.read();
    } catch (IOException readError) {
      readError.printStackTrace();
    }
    return 0;
  }
}