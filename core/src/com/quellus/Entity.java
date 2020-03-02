package com.quellus;

public abstract class Entity {
	final public int UP = 0;
	final public int DOWN = 180;
	final public int LEFT = 90;
	final public int RIGHT = 270;

	private int locationX = 0;
	private int locationY = 0;
	private int rotation = 0;
	private int moveSpeed = 1;

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public int getLocationX() {
		return locationX;
	}

	public void setLocationX(int locationX) {
		this.locationX = locationX;
	}

	public int getLocationY() {
		return locationY;
	}

	public void setLocationY(int locationY) {
		this.locationY = locationY;
	}

	public int getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(int moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	public void move() {
		if (getRotation() == UP) {
			setLocationY(getLocationY() + getMoveSpeed());
		} else if (getRotation() == DOWN) {
			setLocationY(getLocationY() - getMoveSpeed());
		} else if (getRotation() == RIGHT) {
			setLocationX(getLocationX() + getMoveSpeed());
		} else if (getRotation() == LEFT) {
			setLocationX(getLocationX() - getMoveSpeed());
		}
	}

}

