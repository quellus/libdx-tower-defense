package com.quellus.libgdxgame.entities.projectiles;

import com.quellus.libgdxgame.entities.Entity;
import com.quellus.libgdxgame.entities.Enemy;
import com.quellus.libgdxgame.entities.projectiles.Projectile;
import com.quellus.libgdxgame.entities.towers.Tower;

public class Explosive extends Projectile {
  private float explosionRadius;
  private Enemy target;
  private float targetX;
  private float targetY;

  public Explosive(Tower tower, Enemy target, int damage, float explosionRadius) {
    super(tower, damage);
    this.moveSpeed = 0.05f;
    this.target = target;
    this.explosionRadius = explosionRadius;
    targetX = target.getLocationX();
    targetY = target.getLocationY();
  }

  public float getExplosionRadius() {
    return explosionRadius;
  }

  public void move() {
    float dx = targetX - locationX;
    float dy = targetY - locationY;

    float distance = (float) Math.sqrt((dx * dx) + (dy * dy));

    float distancePerUpdate = moveSpeed;
    float updatesToReachTarget = distance / distancePerUpdate;

    float dxEveryFrame = dx / updatesToReachTarget;
    float dyEveryFrame = dy / updatesToReachTarget;

    locationX += dxEveryFrame;
    locationY += dyEveryFrame;
  }

  public boolean isCollisionWithTarget() {
    if (Math.abs(locationX - targetX) <= moveSpeed && Math.abs(locationY - targetY) <= moveSpeed) {
      return true;
    }
    return false;
  }
}
