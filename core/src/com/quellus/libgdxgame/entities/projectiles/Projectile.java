package com.quellus.libgdxgame.entities.projectiles;

import java.lang.Math;

import com.quellus.libgdxgame.entities.Entity;
import com.quellus.libgdxgame.entities.Enemy;
import com.quellus.libgdxgame.entities.towers.Tower;

public class Projectile extends Entity {
    private boolean targetDead = false;
    private Enemy target;
    private int damage;

    public Projectile(Tower tower, Enemy target, int damage) {
        moveSpeed = 0.1f;
        this.target = target;
        this.damage = damage;
        locationX = tower.getLocationX();
        locationY = tower.getLocationY();
    }

    public int getDamage() {
        return damage;
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
