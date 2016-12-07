package game;

public interface Defines {
  // interf consts
  final int WINDOW_SIZE_X = 1070;
  final int WINDOW_SIZE_Y = 490;
  final int ELEMENTS_INSETS_TOP = 10;
  final int ELEMENTS_INSETS_RIGHT = 5;
  final int ELEMENTS_INSETS_BOTTOM = 10;
  final int ELEMENTS_INSETS_LEFT = 5;
  final int ELEMENT_SMALL_SPACING = 5;
  final int ELEMENT_LARGE_SPACING = 10;

  // main menu consts
  final int MENU_BUTTON_SIZE_X = 120;
  final int MENU_BUTTON_SIZE_Y = 30;

  // options panel consts
  final int OPTIONS_ELEMENTS_SIZE_Y = 25;
  final int OPTIONS_DIFFICULTY_BUTTON_SIZE_X = 25;
  final int OPTIONS_LABEL_SIZE_X = 60;
  final int OPTIONS_TEXT_SIZE_X = 25;
  final int OPTIONS_BUTTON_SIZE_X = 130;

  // left panel consts
  final int LEFT_PANEL_ELEMENTS_SIZE_Y = 25;
  final int STAT_LABEL_SMALL_SIZE_X = 25;
  final int STAT_LABEL_LARGE_SIZE_X = 50;//130
  final int STAT_SMALL_TEXT_SIZE_X = 60;
  final int STAT_MEDIUM_TEXT_SIZE_X = 100;
  final int STAT_LARGE_TEXT_SIZE_X = 130;

  // right panel consts
  final int RIGHT_BUTTON_SIZE_X = 130;
  final int RIGHT_BUTTON_SIZE_Y = 25;

  // middle panel consts
  final int MIDDLE_PANEL_SIZE_X = 800;
  final int MIDDLE_PANEL_SIZE_Y = 500;
  final int MAP_FIELD_SIZE = 20;
  final int BATTLE_BUTTON_SIZE_X = 130;
  final int BATTLE_LABEL_SMALL_SIZE_X = 25;
  final int BATTLE_LABEL_MEDIUM_SIZE_X = 60;
  final int BATTLE_ELEMENTS_SIZE_Y = 25;
  final int BATTLE_IMAGE_SIZE_Y = 350;
  final int BATTLE_LOW_IMAGE_SIZE_X = 350;
  final int BATTLE_LOW_IMAGE_SIZE_Y = 125;
  final int MAP_EVENT_EXIT = -1;
  final int MAP_EVENT_BATTLE = 1;
  final int MAP_EVENT_TRAP = 2;
  final int MAP_EVENT_TREASURE = 3;
  final int MAP_EVENT_EVENT = 4;

  // map consts
  final int MOVE_UP = 0;
  final int MOVE_RIGHT = 1;
  final int MOVE_DOWN = 2;
  final int MOVE_LEFT = 3;
  final int MAP_BASE_SIZE_X = 40;
  final int MAP_BASE_SIZE_Y = 25;
  final int MAP_PLAYER_START_POS_X = 1;
  final int MAP_PLAYER_START_POS_Y = 1;
  final int MAP_BASE_PATH_LENGTH = 5;
  final int MAP_BORDER_PATH_LENGTH = 20;
  final int MAP_BASE_PATH_NUM = 40;
  final int FIELD_ECOUNTER_CHANCE = 15;

  // battle consts
  final int BATTLE_BLOCK = 0;
  final int BATTLE_DODGE = 1;
  final int BATTLE_COUNTERSTRIKE = 2;
  final int BATTLE_STRONG_ATTACK = 0;
  final int BATTLE_QUICK_ATTACK = 1;
  final int BATTLE_FINT = 2;
  final int MONSTER_BASE_HP_MUL = 15;
  final int MONSTER_BASE_ATTACK = 1;
  final int PLAYER_BASE_ATTACK_MODIFER = 4;
  final int BATTLE_DAMAGE_MUL = 2;
  final int BATTLE_PLAYER_DEF = 0;
  final int BATTLE_PLAYER_ATK = 1;
  final int BATTLE_MONSTER_DEF = 2;
  final int BATTLE_MONSTER_ATK = 3;

  // event consts
  final int EVENT_ELEMENTS_SIZE_X = 800;
  final int EVENT_IMAGE_SIZE_Y = 200;
  final int EVENT_TEXT_SIZE_Y = 150;
  final int EVENT_BUTTON_SIZE_Y = 50;

  // treasure consts
  final int TREASURE_BASE_SCORE = 10;
  final int TREASURE_BASE_SCORE_MUL = 10;

  // trap consts
  final int TRAP_BASE_DAMAGE = 5;
  final int TRAP_BASE_DAMAGE_MUL = 5;

  // monster stats consts
  final int MONSTER_STAT_HP = 4;
  final int MONSTER_STAT_MAXIMUM_HP = 5;
  final int MONSTER_STAT_ATTACK = 6;

  // player attributes consts
  final int PLAYER_BASE_HP = 30;
  final int PLAYER_HP_STR_MUL = 10;
  final int PLAYER_HP_LEVEL_MUL = 5;
  final int PLAYER_MP_INT_MUL = 10;
  final int PLAYER_BASE_LEVEL_XP = 100;

  // player stats consts
  final int PLAYER_STAT_LEVEL = 0;
  final int PLAYER_STAT_EXPERIENCE = 1;
  final int PLAYER_STAT_ATTRIBUTE_POINTS = 2;
  final int PLAYER_STAT_MAXIMUM_HP = 3;
  final int PLAYER_STAT_CURRENT_HP = 4;
  final int PLAYER_STAT_MAXIMUM_MP = 5;
  final int PLAYER_STAT_CURRENT_MP = 6;
  final int PLAYER_STAT_STRENGHT = 7;
  final int PLAYER_STAT_AGILITY = 8;
  final int PLAYER_STAT_INTELLEGENCE = 9;
  final int PLAYER_STAT_SCORE = 10;
  final int ATTRIBUTE_STRENGHT = 7;
  final int ATTRIBUTE_AGILITY = 8;
  final int ATTRIBUTE_INTELLEGENCE = 9;

  // bot consts
  final int BOT_TRAP_DAMAGE = 5;
  final int BOT_MONSTER_DAMAGE = 10;
  final int BOT_EXPERIENCE_GAIN = 50;
  final int BOT_SCORE_GAIN = 100;
  final int BOT_MAXIMUM_MOVES = 500;

  // difficulty consts
  final int DIFFICULTY_MODIFER = 1;
  final int DIFFICULTY_MAXIMUM = 30;
  final int DIFFICULTY_MINIMUM = 1;
}