package com.quellus.libgdxgame.entities;

import java.lang.Math;

import com.quellus.libgdxgame.entities.Entity;
import com.quellus.libgdxgame.Coordinate;

public class Enemy extends Entity {
	final public int UP = 0;
	final public int DOWN = 180;
	final public int LEFT = 90;
	final public int RIGHT = 270;

	private Coordinate<Integer>[] map;

	private int lastWaypoint = 0;
	
	private int health = 20;
	private boolean isDead = false;

	public Enemy(Coordinate<Integer>[] map) {
		this.map = map;
		moveSpeed = 0.05f;
		locationX = map[0].getX();
		locationY = map[0].getY();
		setRotation(LEFT);
	}

	public void move() {
		if (map.length == lastWaypoint + 1 && Math.abs(locationX - map[lastWaypoint].getX()) <= moveSpeed && Math.abs(locationY - map[lastWaypoint].getY()) <= moveSpeed) {
			isDead = true;
			return;
		}

		if (map.length > lastWaypoint + 1 && Math.abs(locationX - map[lastWaypoint].getX()) <= moveSpeed && Math.abs(locationY - map[lastWaypoint].getY()) <= moveSpeed) {
			locationX = map[lastWaypoint].getX();
			locationY = map[lastWaypoint].getY();
			lastWaypoint++;
		}

		int targetX = map[lastWaypoint].getX();
		int targetY = map[lastWaypoint].getY();
		
		moveAndRotateTowards(targetX, targetY);
	}

	public boolean isDead() {
		return isDead;
	}

	public float getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(int moveSpeed) {
		this.moveSpeed = moveSpeed;
	}
	
	public int getHealth() {
		return health;
	}

	public void attack(int damage) {
		health -= damage;
		if (health <= 0) {
			isDead = true;
		}
			
	}

	private void moveAndRotateTowards(int targetX, int targetY) {
		if (locationX == targetX) {
			if (locationY < targetY) {
				rotation = UP;
				locationY += moveSpeed;
			} else if (locationY > targetY) {
				rotation = DOWN;
				locationY -= moveSpeed;
			}
		} else if (locationY == targetY) {
			if (locationX < targetX) {
				rotation = RIGHT;
				locationX += moveSpeed;
			} else if (locationX > targetX) {
				rotation = LEFT;
				locationX -= moveSpeed;
			}
		}
	}

}

