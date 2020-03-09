package com.quellus.libgdxgame;

import java.util.ArrayList;

import com.quellus.libgdxgame.Game;
import com.quellus.libgdxgame.Entity;
import com.quellus.libgdxgame.Enemy;
import com.quellus.libgdxgame.Tower;

public class GameLogic {
	private Game game;
	private int  enemySpawnRate = 30;
	private int enemySpawn = enemySpawnRate;

	public GameLogic(Game game) {
		this.game = game;
		Coordinate<Integer>[] map = game.getMap();
		game.addEnemy(new Enemy(map));
	}
	
	public void update() {
		spawnEnemy();
		moveEnemies();
		rotateTowers();
	}

	public void spawnTowerAt(int x, int y) {
		// TODO check if location is path
	
		if (isPathAt(x, y)) {
			System.out.println("There's path there!");
			return;
		}


		if (isTowerAt(x, y)) {
			System.out.println("Tower already exists!");
			return;
		}
		game.addTower(new Tower(x, y));
	}

	private boolean isPathAt(int x, int y) {
		Coordinate<Integer>[] map = game.getMap();
		for (int i = 0; i < map.length; i++) {
			Coordinate<Integer> coord = map[i];
			if (coord.getX() == x && coord.getY() == y) {
				return true;
			}
		}
		return false;
	}

	private boolean isTowerAt(int x, int y) {
		ArrayList<Tower> towers = game.getTowers();
		for (int i = 0; i < towers.size(); i++) {
			Tower tower = towers.get(i);
			if (tower.getLocationX() == x && tower.getLocationY() == y) {
				return true;
			}
		}

		return false;

	}

	private void moveEnemies() {
		ArrayList<Enemy> enemies = game.getEnemies();
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemyObj = enemies.get(i);
			if (!enemyObj.isDead()) {
				enemyObj.move();
			}	else {
				game.removeEnemy(enemyObj);
			}
		}
	}

	private void rotateTowers() {
		ArrayList<Enemy> enemies = game.getEnemies();
		if (enemies.size() > 0) {
			ArrayList<Tower> towers = game.getTowers();
			for (int i = 0; i < towers.size(); i++) {
				Tower towerObj = towers.get(i);
				Enemy closestEnemy = getClosestEnemy(towerObj);
				towerObj.rotate(closestEnemy);
				towerObj.attack(closestEnemy);
			}
		}
	}

	private void spawnEnemy() {
		if (enemySpawn == 0) {
			game.addEnemy(new Enemy(game.getMap()));
			enemySpawn = enemySpawnRate;
		} else {
			enemySpawn--;
		}
	}

	private Enemy getClosestEnemy(Tower tower) {
		Enemy closestEnemy = null;
		float closestDist = Float.MAX_VALUE;
		ArrayList<Enemy> enemies = game.getEnemies();
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
			if (closestEnemy == null) {
				closestEnemy = enemy;
				closestDist = getEuclideanDistance(enemy, tower);
			} else {
				float enemyDist = getEuclideanDistance(enemy, tower);
				if (enemyDist < closestDist) {
					closestEnemy = enemy;
					closestDist = enemyDist;
				}
			}
		}
		return closestEnemy;
	}

	private float getEuclideanDistance(Entity entity1, Entity entity2) {
		float xDiff = entity1.getLocationX() - entity2.getLocationX();
		float yDiff = entity1.getLocationY() - entity2.getLocationY();

		double a = Math.pow( (double) xDiff, 2);
		double b = Math.pow( (double) yDiff, 2);

		float c = (float) Math.sqrt(a + b);
		return c;
	}

}

