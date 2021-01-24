package com.quellus.libgdxgame;

public class Waves {
  private int numEnemies = 3;
  private int enemyHealthFactor = 1;

  public void next() {
    ++numEnemies;
  }

  public int getNumEnemies() {
    return numEnemies;
  }
}
