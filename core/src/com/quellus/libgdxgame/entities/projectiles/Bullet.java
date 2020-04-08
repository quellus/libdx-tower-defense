package com.quellus.libgdxgame.entities.projectiles;

import com.quellus.libgdxgame.entities.projectiles.Projectile;
import com.quellus.libgdxgame.entities.Entity;
import com.quellus.libgdxgame.entities.Enemy;
import com.quellus.libgdxgame.entities.towers.Tower;

public class Bullet extends Projectile {
  protected Enemy target;

  public Bullet(Tower tower, Enemy target, int damage) {
    super(tower, damage);
    this.target = target;
    moveSpeed = 0.3f;
  }

  public void move() {
    targetDead = target.isDead();

    float dx = target.getLocationX() - locationX ;
    float dy = target.getLocationY() - locationY;

    float distance = (float) Math.sqrt((dx * dx) + (dy * dy));

    float distancePerUpdate = moveSpeed;
    float updatesToReachTarget = distance / distancePerUpdate;

    float dxEveryFrame = dx / updatesToReachTarget;
    float dyEveryFrame = dy / updatesToReachTarget;

    locationX += dxEveryFrame;
    locationY += dyEveryFrame;
  }

  public Enemy getTarget() {
    return target;
  }

  public boolean isTargetDead() {
    return targetDead;
  }

  public boolean isCollisionWithTarget() {
    if (Math.abs(locationX - target.getLocationX()) <= moveSpeed && Math.abs(locationY - target.getLocationY()) <= moveSpeed) {
      return true;
    }
    return false;
  }
}
