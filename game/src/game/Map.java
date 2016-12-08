package game;

import java.io.IOException;
import java.util.Random;

public class Map implements Defines {

  public Player player;
  public Field[][] fields;
  public int mapX;
  public int mapY;
  public int difficultyModifer;
  public int playerX, playerY;
  
  private int startX, startY;
  private int pathNum;
  private int pathLength;
  private int wayPrew;
  private int way;

  public Map(int diff) {
    mapX = MAP_BASE_SIZE_X;
    mapY = MAP_BASE_SIZE_Y;
    difficultyModifer = diff;
    playerX = startX = MAP_PLAYER_START_POS_X;
    playerY = startY = MAP_PLAYER_START_POS_Y;
    pathNum = MAP_BASE_PATH_NUM * difficultyModifer;
    pathLength = MAP_BASE_PATH_LENGTH;
    fields = new Field[mapY][mapX];
    player = new Player();
    for (int i = 0; i < mapY; i++) {
      fields[i] = new Field[mapX];
      for (int j = 0; j < mapX; j++) {
        fields[i][j] = new Field();
      }
    }
    createPath();
  }

  void createPath() {
    int x = startX;
    int y = startY;
    int exitFlag = 1;
    way = 0;
    wayPrew = 0;
    for (int i = 0; i < pathNum; i++) {
      Random rand1 = new Random();
      pathLength = rand1.nextInt(MAP_BORDER_PATH_LENGTH) + MAP_BASE_PATH_LENGTH;
      int repeatFlag = 0;

      while (way == wayPrew) {
        Random rand2 = new Random();
        way = rand2.nextInt(4);
        if (repeatFlag % 50 == 0) {
          Random rand3 = new Random();
          pathLength = rand3.nextInt(10) + 3;
        }
        if ((way == MOVE_UP) && ((y - pathLength - 1) <= 0)) {
          way = wayPrew;
        }
        if ((way == MOVE_RIGHT) && ((x + pathLength + 1) >= mapX)) {
          way = wayPrew;
        }
        if ((way == MOVE_DOWN) && ((y + pathLength + 1) >= mapY)) {
          way = wayPrew;
        }
        if ((way == MOVE_LEFT) && ((x - pathLength - 1) <= 0)) {
          way = wayPrew;
        }
        repeatFlag++;
        if (repeatFlag == 1000) {
          return;
        }
      }

      if (exitFlag != -1) {
        exitFlag = pathNum - i;
        Random rand4 = new Random();
        if ((rand4.nextInt(exitFlag) == 0) && (exitFlag < 10)) {
          fields[y][x].exit = true;
          exitFlag = -1;
        }
      }

      switch (way) {
        case MOVE_UP: {
          for (int j = 0; j < pathLength; j++) {
            fields[y--][x].setRandField();
            wayPrew = MOVE_UP;
          }
          break;
        }
        case MOVE_RIGHT: {
          for (int j = 0; j < pathLength; j++) {
            fields[y][x++].setRandField();
            wayPrew = MOVE_RIGHT;
          }
          break;
        }
        case MOVE_DOWN: {
          for (int j = 0; j < pathLength; j++) {
            fields[y++][x].setRandField();
            wayPrew = MOVE_DOWN;
          }
          break;
        }
        case MOVE_LEFT: {
          for (int j = 0; j < pathLength; j++) {
            fields[y][x--].setRandField();
            wayPrew = MOVE_LEFT;
          }
        }
      }
    }
  //start position settings
    fields[startY][startX].enabled = true;
    fields[startY][startX].current = true;
    fields[startY][startX].visible = true;
    fields[playerY][playerX].visited = true;
    fields[startY][startX].status = 0;
    setVisibleArea();
  }

  public boolean changePlayerPos(int direction) {
    boolean movedFlag = false;
    if ((direction < 0) || (direction > 3))
      return false;

    fields[playerY][playerX].current = false;
    switch (direction) {
      case MOVE_UP:
        if (((playerY - 1) >= 0) && (fields[playerY - 1][playerX].enabled == true)) {
          playerY--;
          movedFlag = true;
        }
        break;
      case MOVE_RIGHT:
        if (((playerX + 1) <= mapX) && (fields[playerY][playerX + 1].enabled == true)) {
          playerX++;
          movedFlag = true;
        }
        break;
      case MOVE_DOWN:
        if (((playerY + 1) <= mapY) && (fields[playerY + 1][playerX].enabled == true)) {
          playerY++;
          movedFlag = true;
        }
        break;
      case MOVE_LEFT:
        if (((playerX - 1) >= 0) && (fields[playerY][playerX - 1].enabled == true)) {
          playerX--;
          movedFlag = true;
        }
    }
    setVisibleArea();
    fields[playerY][playerX].current = true;
    fields[playerY][playerX].visited = true;

    return movedFlag;
  }

  public void clearField() {
    fields[playerY][playerX].status = 0;
  }

  void setVisibleArea() {
    fields[playerY - 1][playerX - 1].setVisible();
    fields[playerY - 1][playerX].setVisible();
    fields[playerY - 1][playerX + 1].setVisible();

    fields[playerY][playerX - 1].setVisible();
    fields[playerY][playerX].setVisible();
    fields[playerY][playerX + 1].setVisible();

    fields[playerY + 1][playerX - 1].setVisible();
    fields[playerY + 1][playerX].setVisible();
    fields[playerY + 1][playerX + 1].setVisible();
  }

  public int startEvent() {
    if (fields[playerY][playerX].getExit() == true) {
      return -1;
    }
    return fields[playerY][playerX].getStatus();
  }
}