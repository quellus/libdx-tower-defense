package com.quellus.libgdxgame.entities.towers;

import java.lang.Math;
import java.util.ArrayList;

import com.quellus.libgdxgame.entities.Entity;
import com.quellus.libgdxgame.entities.Enemy;
import com.quellus.libgdxgame.entities.towers.TowerEnum;
import com.quellus.libgdxgame.entities.projectiles.Projectile;

public abstract class Tower extends Entity {

	protected int damage = 1;
	protected float range;
	protected int cooldown = 10;
	protected int currentCooldown = 0;
	protected TowerEnum type = null;

	public Tower(int x, int y, float range) {
		locationX = x;
		locationY = y;
		this.range = range;
	}

	public abstract Projectile attack(Enemy enemy);

	public float getRange() {
		return range;
	}

	public TowerEnum getType() {
		return type;
	}

	public int getDamage() {
		return damage;
	}

	public void updateCooldown() {
		if (currentCooldown >= 0) {
			currentCooldown--;
		} else if (currentCooldown < 0) {
			currentCooldown = cooldown;
		}
	}

	public void rotate(Enemy enemy) {
		double diffX = locationX - enemy.getLocationX();
		double diffY = locationY - enemy.getLocationY();
		double value;
		if (diffX >= 0 && diffY >= 0) {
			rotation = 90;
			value = (double) (Math.abs(diffY) / Math.abs(diffX));
		}	else if (diffX <= 0 && diffY <= 0) {
			rotation = 270;
			value = (double) (Math.abs(diffY) / Math.abs(diffX));
		} else if (diffX >= 0 && diffY <= 0) {
			rotation = 0;
			value = (double) (Math.abs(diffX) / Math.abs(diffY));
		} else  {
			rotation = 180;
			value = (double) (Math.abs(diffX) / Math.abs(diffY));
		}
		double angleRadians = Math.atan(value);
		double angleDegrees = Math.toDegrees(angleRadians);
		rotation += (int) Math.floor(angleDegrees);
	}

}

