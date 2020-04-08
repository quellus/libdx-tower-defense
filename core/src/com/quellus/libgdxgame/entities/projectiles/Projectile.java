package com.quellus.libgdxgame.entities.projectiles;

import java.lang.Math;

import com.quellus.libgdxgame.entities.Entity;
import com.quellus.libgdxgame.entities.Enemy;
import com.quellus.libgdxgame.entities.towers.Tower;

public abstract class Projectile extends Entity {
  protected boolean targetDead = false;
  protected int damage;

  public Projectile(Tower tower, int damage) {
    moveSpeed = 0.1f;
    this.damage = damage;
    locationX = tower.getLocationX();
    locationY = tower.getLocationY();
  }

  public int getDamage() {
    return damage;
  }

  public abstract void move();
  public abstract boolean isCollisionWithTarget();

}
