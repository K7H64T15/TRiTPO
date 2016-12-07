package game;

import java.util.Random;

public class FieldTreasure implements Defines {

  public int score;
  public int decisionNumber;

  public FieldTreasure(int difficulty) {
    Random rand1 = new Random();
    score = rand1.nextInt(TREASURE_BASE_SCORE_MUL) + TREASURE_BASE_SCORE * difficulty;
    decisionNumber = 1;
  }
}