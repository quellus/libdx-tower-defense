package com.quellus.libgdxgame;

import java.util.ArrayList;

import com.quellus.libgdxgame.GameLogic;
import com.quellus.libgdxgame.entities.Enemy;
import com.quellus.libgdxgame.entities.towers.Tower;
import com.quellus.libgdxgame.entities.projectiles.Projectile;


public class Game {
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Tower> towers = new ArrayList<Tower>();
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

	private Map map;

	public Game(String filename) {
		map = new Map(filename);
	}

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

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	public void addProjectile(Projectile projectile) {
		projectiles.add(projectile);
	}

	public void removeProjectile(Projectile projectile) {
		projectiles.remove(projectile);
	}
	
	public Coordinate<Integer>[] getMap() {
		return map.getMapTiles();
	}

}

