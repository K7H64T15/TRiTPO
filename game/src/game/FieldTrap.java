package game;

import java.util.Random;

public class FieldTrap implements Defines {

  public int damage;
  public int decisionNumber;

  public FieldTrap(Player player, int difficulty) {
    Random rand1 = new Random();
    damage = rand1.nextInt(player.getStat(0) * TRAP_BASE_DAMAGE_MUL) + TRAP_BASE_DAMAGE * difficulty;
    decisionNumber = 1;
  }
}
