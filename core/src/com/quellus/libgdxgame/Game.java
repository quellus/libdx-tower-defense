package com.quellus.libgdxgame;

import java.util.ArrayList;

import com.quellus.libgdxgame.Enemy;
import com.quellus.libgdxgame.Tower;
import com.quellus.libgdxgame.GameLogic;
import com.quellus.libgdxgame.Game;

public class Game {
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Tower> towers = new ArrayList<Tower>();

	private int[][] map = {
		{0,14},
		{10,14},
		{10,11},
		{4,11},
		{4,8},
		{12,8},
		{12,5},
		{7,5},
		{7,0}
	};

	public ArrayList<Enemy> getEnemies()  {
		return enemies;
	}

	public void addEnemy(Enemy enemy) {
		enemies.add(enemy);
	}

	public void removeEnemy(Enemy enemy) {
		enemies.remove(enemy);
	}

	public ArrayList<Tower> getTowers() {
		return towers;
	}

	public void addTower(Tower tower) {
		towers.add(tower);
	}
	
	public int[][] getMap() {
		return map;
	}

}

