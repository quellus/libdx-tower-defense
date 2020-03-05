package com.quellus;

import java.util.ArrayList;

import com.quellus.Game;
import com.quellus.Enemy;
import com.quellus.Tower;

public class GameLogic {
	private Game game;

	public GameLogic(Game game) {
		this.game = game;
		//TODO this is temporary
		game.addTower(new Tower());
		int[][] map = game.getMap();
		game.addEnemy(new Enemy(map));
	}
	
	public void update() {
		//spawnEnemies();
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

}

