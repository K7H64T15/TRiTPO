package game;

import java.util.Random;

public class FieldMonster implements Defines {

  protected String name;
  protected int currentHP;
  protected int maximumHP;
  protected int attack;
  protected int playerDeffence;
  protected int playerAttack;
  protected int monsterDeffence;
  protected int monsterAttack;
  
  public int endFlag;

  public FieldMonster(Player player, int difficulty) {

    endFlag = 0;
    playerDeffence = 0;
    playerAttack = 0;
    monsterDeffence = 0;
    monsterAttack = 0;

    name = "Monster";

    Random rand1 = new Random();
    Random rand2 = new Random();
    maximumHP = currentHP = (player.level + rand1.nextInt(2) + difficulty) * MONSTER_BASE_HP_MUL;
    attack = player.level + rand2.nextInt(2) + MONSTER_BASE_ATTACK * difficulty;
  }

  public void setDeffence(int set) {
    playerDeffence = set;
  }

  public void setAttack(int set) {
    playerAttack = set;
  }

  public void battlePlayer(Player player) {
    Random rand1 = new Random();
    Random rand2 = new Random();
    monsterAttack = rand1.nextInt(3);
    monsterDeffence = rand2.nextInt(3);

    if (player.currentHP <= 0) {
      return;
    }

    switch (playerAttack) {
      case BATTLE_STRONG_ATTACK: {// STR
        if (monsterDeffence == BATTLE_BLOCK) {
          currentHP -= (player.strength + PLAYER_BASE_ATTACK_MODIFER) * BATTLE_DAMAGE_MUL;
        }
        if (monsterDeffence == BATTLE_DODGE) {
          currentHP -= (player.strength + PLAYER_BASE_ATTACK_MODIFER) / BATTLE_DAMAGE_MUL;
        }
        if (monsterDeffence == BATTLE_COUNTERSTRIKE) {
          currentHP -= (player.strength + PLAYER_BASE_ATTACK_MODIFER);
        }
      }
      case BATTLE_QUICK_ATTACK: {// AGI
        if (monsterDeffence == BATTLE_BLOCK) {
          currentHP -= (player.agility + PLAYER_BASE_ATTACK_MODIFER) / BATTLE_DAMAGE_MUL;
        }
        if (monsterDeffence == BATTLE_DODGE) {
          currentHP -= (player.agility + PLAYER_BASE_ATTACK_MODIFER) * BATTLE_DAMAGE_MUL;
        }
        if (monsterDeffence == BATTLE_COUNTERSTRIKE) {
          currentHP -= (player.agility + PLAYER_BASE_ATTACK_MODIFER);
        }
      }
      case BATTLE_FINT: {// INT
        if (monsterDeffence == BATTLE_BLOCK) {
          currentHP -= (player.intellegence + PLAYER_BASE_ATTACK_MODIFER);
        }
        if (monsterDeffence == BATTLE_DODGE) {
          currentHP -= (player.intellegence + PLAYER_BASE_ATTACK_MODIFER) / BATTLE_DAMAGE_MUL;
        }
        if (monsterDeffence == BATTLE_COUNTERSTRIKE) {
          currentHP -= (player.intellegence + PLAYER_BASE_ATTACK_MODIFER) * BATTLE_DAMAGE_MUL;
        }
      }
    }
    if (currentHP <= 0) {
      endFlag = 1;
    }
  }

  public void battleMonster(Player player) {
    Random rand1 = new Random();
    Random rand2 = new Random();
    monsterAttack = rand1.nextInt(3);
    monsterDeffence = rand2.nextInt(3);

    if (currentHP <= 0) {
      return;
    }

    switch (monsterAttack) {
      case BATTLE_STRONG_ATTACK: {// STR
        if (playerDeffence == BATTLE_BLOCK) {
          player.currentHP -= attack * BATTLE_DAMAGE_MUL;
        }
        if (playerDeffence == BATTLE_DODGE) {
          player.currentHP -= attack / BATTLE_DAMAGE_MUL;
        }
        if (playerDeffence == BATTLE_COUNTERSTRIKE) {
          player.currentHP -= attack;
        }
      }
      case BATTLE_QUICK_ATTACK: {// AGI
        if (playerDeffence == BATTLE_BLOCK) {
          player.currentHP -= attack / BATTLE_DAMAGE_MUL;
        }
        if (playerDeffence == BATTLE_DODGE) {
          player.currentHP -= attack * BATTLE_DAMAGE_MUL;
        }
        if (playerDeffence == BATTLE_COUNTERSTRIKE) {
          player.currentHP -= attack;
        }
      }
      case BATTLE_FINT: {// INT
        if (playerDeffence == BATTLE_BLOCK) {
          player.currentHP -= attack;
        }
        if (playerDeffence == BATTLE_DODGE) {
          player.currentHP -= attack / BATTLE_DAMAGE_MUL;
        }
        if (playerDeffence == BATTLE_COUNTERSTRIKE) {
          player.currentHP -= attack * BATTLE_DAMAGE_MUL;
        }
      }
    }
    if (player.currentHP <= 0) {
      endFlag = -1;
    }
  }

  public int getStat(int stat) {
    switch (stat) {
      case BATTLE_PLAYER_DEF:
        return playerDeffence;
      case BATTLE_PLAYER_ATK:
        return playerAttack;
      case BATTLE_MONSTER_DEF:
        return monsterDeffence;
      case BATTLE_MONSTER_ATK:
        return monsterAttack;
      case MONSTER_STAT_HP:
        return currentHP;
      case MONSTER_STAT_MAXIMUM_HP:
        return maximumHP;
      case MONSTER_STAT_ATTACK:
        return attack;
    }
    return 0;
  }

  public String getInfo() {
    return name;
  }
}