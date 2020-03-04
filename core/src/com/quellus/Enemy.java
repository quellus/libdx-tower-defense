package com.quellus;

import java.lang.Math;

import com.quellus.Entity;

public class Enemy extends Entity {
	final public int UP = 0;
	final public int DOWN = 180;
	final public int LEFT = 90;
	final public int RIGHT = 270;

	private int[][] map;

	private int lastWaypoint = 0;
	
	private int health = 100;

	public Enemy(int[][] map) {
		this.map = map;
		moveSpeed = 0.05f;
		locationX = map[0][0];
		locationY = map[0][1];
		setRotation(LEFT);
	}

	public void move() {
		if (map.length >= lastWaypoint + 1) {
			if (map.length > lastWaypoint + 1 && Math.abs(locationX - map[lastWaypoint][0]) <= moveSpeed && Math.abs(locationY - map[lastWaypoint][1]) <= moveSpeed) {
				locationX = map[lastWaypoint][0];
				locationY = map[lastWaypoint][1];
				lastWaypoint++;
				System.out.println("EnemyLocation" + locationX + ", " + locationY);
			}

			int targetX = map[lastWaypoint][0];
			int targetY = map[lastWaypoint][1];
			
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
		if (health > 0) {
			health -= damage;
		} else {
			System.out.println("This enemy is heaking DEAD!");
		}
		
	}

}

