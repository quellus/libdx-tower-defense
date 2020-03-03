package com.quellus;

import java.lang.Math;

public class Enemy {
	final public int UP = 0;
	final public int DOWN = 180;
	final public int LEFT = 90;
	final public int RIGHT = 270;

	private float locationX = 0;
	private float locationY = 0;
	private int rotation = 0;
	private float moveSpeed = 0.1f;
	private int[][] map;

	private int lastWaypoint = 0;

	public Enemy(int[][] map) {
		this.map = map;
		locationX = map[0][0];
		locationY = map[0][1];
		System.out.println("Enemy starts at" + locationX + ", " + locationY);
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

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public float getLocationX() {
		return locationX;
	}

	public void setLocationX(int locationX) {
		this.locationX = locationX;
	}

	public float getLocationY() {
		return locationY;
	}

	public void setLocationY(int locationY) {
		this.locationY = locationY;
	}

	public float getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(int moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

}

