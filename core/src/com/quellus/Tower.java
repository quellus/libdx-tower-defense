package com.quellus;

import java.lang.Math;
import java.util.ArrayList;

import com.quellus.Entity;

public class Tower extends Entity {

	int damage = 1;
	int cooldown = 5;
	int currentCooldown = 0;

	public Tower() {
		locationX = 6;
		locationY = 9;
	}

	public void attack(ArrayList<Enemy> enemies) {
		if (currentCooldown > 0) {
			currentCooldown--;
		}
		if (currentCooldown == 0 && enemies.size() >= 1) {
			Enemy enemy = enemies.get(0);
			enemy.attack(damage);
			currentCooldown = cooldown;
		}
	}

	public void rotate(ArrayList<Enemy> enemies) {
		if (enemies.size() >= 1) {
			Enemy enemy = enemies.get(0);
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

}

