package game;

public class Player implements Defines {

  protected int level;
  protected int experience;
  protected int attributePoints;
  protected int score;

  protected int maximumHP;
  protected int currentHP;
  protected int maximumMP;
  protected int currentMP;

  protected int strength;
  protected int agility;
  protected int intellegence;

  Player() {
    level = 1;
    experience = 0;

    strength = 1;
    agility = 1;
    intellegence = 1;

    attributePoints = 0;

    maximumHP = currentHP = PLAYER_BASE_HP + strength * PLAYER_HP_STR_MUL;
    maximumMP = currentMP = intellegence * PLAYER_MP_INT_MUL;
  }

  public void addExperience(int newExperience) {
    experience += newExperience;
    while (experience >= level * PLAYER_BASE_LEVEL_XP) {
      experience -= level * PLAYER_BASE_LEVEL_XP;
      level++;
      if (level % 3 == 0) {
        attributePoints++;
      }
      maximumHP =
          currentHP = PLAYER_BASE_HP + level * PLAYER_HP_LEVEL_MUL + strength * PLAYER_HP_STR_MUL;
      maximumMP = currentMP = intellegence * PLAYER_MP_INT_MUL;
      score += level * PLAYER_BASE_LEVEL_XP;
    }
  }

  public void addAttribute(int attribute, int value) {
    if (attributePoints < value) {
      return;
    }
    switch (attribute) {
      case ATTRIBUTE_STRENGHT: {
        strength += value;
        attributePoints -= value;
        break;
      }
      case ATTRIBUTE_AGILITY: {
        agility += value;
        attributePoints -= value;
        break;
      }
      case ATTRIBUTE_INTELLEGENCE: {
        intellegence += value;
        attributePoints -= value;
        break;
      }
    }
  }

  public void getDamage(int damage) {
    currentHP -= damage;
  }

  public int getStat(int stat) {
    switch (stat) {
      case PLAYER_STAT_LEVEL:
        return level;
      case PLAYER_STAT_EXPERIENCE:
        return experience;
      case PLAYER_STAT_ATTRIBUTE_POINTS:
        return attributePoints;
      case PLAYER_STAT_MAXIMUM_HP:
        return maximumHP;
      case PLAYER_STAT_CURRENT_HP:
        return currentHP;
      case PLAYER_STAT_MAXIMUM_MP:
        return maximumMP;
      case PLAYER_STAT_CURRENT_MP:
        return currentMP;
      case PLAYER_STAT_STRENGHT:
        return strength;
      case PLAYER_STAT_AGILITY:
        return agility;
      case PLAYER_STAT_INTELLEGENCE:
        return intellegence;
      case PLAYER_STAT_SCORE:
        return score;
    }
    return -1;
  }

  public void addScore(int newScore) {
    score += newScore;
  }
}