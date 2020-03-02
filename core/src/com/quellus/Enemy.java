package com.quellus;

public class Enemy extends Entity {

	int startX = 1000;
	
	public Enemy() {
		setRotation(LEFT);
		setMoveSpeed(2);
	}

	public Enemy(int locationX, int locationY) {
		startX = locationX;
		setLocationX(locationX);
		setLocationY(locationY);
		setRotation(LEFT);
		setMoveSpeed(2);
	}

	@Override
	public void move() {
		if (getLocationX() <= 0) {
			setLocationX(startX);
		} else {
			super.move();
		}
	}
		
}

