package game;

import java.util.Random;

public class Field implements Defines {

  protected boolean enabled;
  protected boolean current;
  protected boolean visible;
  protected boolean exit;
  protected boolean visited;
  protected int status;

  Field() {
    enabled = false;
    current = false;
    visible = false;
    exit = false;
    visited = false;
    status = 0;
  }

  void setRandField() {
    visible = true; //for test only
    enabled = true;
    Random rand1 = new Random();
    status = rand1.nextInt(FIELD_ECOUNTER_CHANCE);
    if (status > 3) {// 4 for test only
      status = 0;
    }
  }

  public int getStatus() {
    return status;
  }

  public boolean getExit() {
    return exit;
  }

  public boolean getCurrent() {
    return current;
  }

  public boolean getVisible() {
    return visible;
  }

  public boolean getEnabled() {
    return enabled;
  }

  public boolean getVisited() {
    return visited;
  }

  void setVisible() {
    visible = true;
  }

  void setVisited() {
    visited = true;
  }

  void setInvisible() {
    visible = false;
  }
}