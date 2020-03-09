package com.quellus.libgdxgame;

import java.lang.Math;
import java.util.ArrayList;

import com.quellus.libgdxgame.Entity;

public class Tower extends Entity {

	private int damage = 1;
	private int cooldown = 5;
	private int currentCooldown = 0;

	public Tower(int x, int y) {
		locationX = x;
		locationY = y;
	}

	public void updateCooldown() {
		if (currentCooldown > 0) {
			currentCooldown--;
		}
		if (currentCooldown == 0) {
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

	public int getDamage() {
		return damage;
	}

}

