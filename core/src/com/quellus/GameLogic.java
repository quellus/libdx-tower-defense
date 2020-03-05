package com.quellus;

import java.util.ArrayList;

import com.quellus.Game;
import com.quellus.Enemy;
import com.quellus.Tower;

public class GameLogic {
	private Game game;
	private int  enemySpawnRate = 30;
	private int enemySpawn = enemySpawnRate;

	public GameLogic(Game game) {
		this.game = game;
		//TODO this is temporary
		game.addTower(new Tower());
		int[][] map = game.getMap();
		game.addEnemy(new Enemy(map));
	}
	
	public void update() {
		spawnEnemy();
		moveEnemies();
		rotateTowers();
	}

	private void moveEnemies() {
		ArrayList<Enemy> enemies = game.getEnemies();
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemyObj = enemies.get(i);
			if (enemyObj.getHealth() > 0) {
				enemyObj.move();
			}
		}
	}

	private void rotateTowers() {
		ArrayList<Enemy> enemies = game.getEnemies();
		ArrayList<Tower> towers = game.getTowers();
		for (int i = 0; i < towers.size(); i++) {
			Tower towerObj = towers.get(i);
			towerObj.rotate(enemies);
			towerObj.attack(enemies);
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

}

