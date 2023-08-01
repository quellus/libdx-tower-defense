package com.quellus.libgdxgame;

public class Waves {
  private int numEnemies = 3;
  private float enemyHealthFactor = 1;

  public void next() {
    ++numEnemies;
    enemyHealthFactor = (float)Math.pow(1.2f, numEnemies) - 0.728f;
  }

  public int getNumEnemies() {
    return numEnemies;
  }

  public float getEnemyHealthFactor() {
    return enemyHealthFactor;
  }
}

