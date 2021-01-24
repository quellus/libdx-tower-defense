package com.quellus.libgdxgame.entities;

public abstract class Entity {
	final public int UP = 0;
	final public int DOWN = 180;
	final public int LEFT = 90;
	final public int RIGHT = 270;

	protected float locationX = 0;
	protected float locationY = 0;
	protected int rotation = 0;
	protected float moveSpeed = 1;

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public float getLocationX() {
		return locationX;
	}

	public void setLocationX(float locationX) {
		this.locationX = locationX;
	}

	public float getLocationY() {
		return locationY;
	}

	public void setLocationY(float locationY) {
		this.locationY = locationY;
	}

	public float getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(float moveSpeed) {
		this.moveSpeed = moveSpeed;
	}
}

