package com.quellus.libgdxgame;

import java.util.ArrayList;

import com.quellus.libgdxgame.Game;
import com.quellus.libgdxgame.entities.towers.Tower;
import com.quellus.libgdxgame.entities.towers.TowerEnum;
import com.quellus.libgdxgame.entities.towers.TowerFactory;
import com.quellus.libgdxgame.entities.projectiles.Bullet;
import com.quellus.libgdxgame.entities.projectiles.Explosive;
import com.quellus.libgdxgame.entities.projectiles.Projectile;
import com.quellus.libgdxgame.entities.Enemy;
import com.quellus.libgdxgame.entities.Entity;

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
		rotateAndAttackTowers();
		moveAndAttackProjectiles();
	}

	public void spawnTower(Tower tower) {
		if (isPathAt((int) tower.getLocationX(), (int) tower.getLocationY())) {
			System.out.println("There's path there!");
			return;
		}


		if (isTowerAt((int) tower.getLocationX(), (int) tower.getLocationY())) {
			System.out.println("Tower already exists!");
			return;
		}

		game.addTower(tower);
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

	private void rotateAndAttackTowers() {
		ArrayList<Enemy> enemies = game.getEnemies();
		if (enemies.size() > 0) {
			ArrayList<Tower> towers = game.getTowers();
			for (int i = 0; i < towers.size(); i++) {
				Tower towerObj = towers.get(i);
				Enemy closestEnemy = getClosestEnemyInRadius(towerObj);
				if (closestEnemy != null) {
					towerObj.rotate(closestEnemy);
					Projectile projectile = towerObj.attack(closestEnemy);
					if (projectile != null) {
						game.addProjectile(projectile);
					}
				} else {
					towerObj.updateCooldown();
				}
			}
		}
	}

	private void moveAndAttackProjectiles() {
		ArrayList<Projectile> projectiles = game.getProjectiles();
		if (projectiles.size() > 0) {
			for (int i = 0; i < projectiles.size(); i++) {
				Projectile projectile = projectiles.get(i);
				if (projectile instanceof Bullet) {
					moveAndAttackBullet((Bullet) projectile);
				} else if (projectile instanceof Explosive) {
					moveAndAttackExplosive((Explosive) projectile);
				}
			}
		}
	}

	private void moveAndAttackBullet(Bullet bullet) {
		if (bullet.isTargetDead()) {
			game.removeProjectile(bullet);
		} else {
			if (bullet.isCollisionWithTarget()) {
					bullet.getTarget().attack(bullet.getDamage());
				game.removeProjectile(bullet);
			} else {
				bullet.move();
			}
		}
	}

	private void moveAndAttackExplosive(Explosive explosive) {
		if (explosive.isCollisionWithTarget()) {
			ArrayList<Enemy> enemies = game.getEnemies();
			for (int i = 0; i < enemies.size(); i++) {
				Enemy enemy = enemies.get(i);
				float distance = getEuclideanDistance(explosive, enemy);
				if (distance <= explosive.getExplosionRadius()) {
					enemy.attack(explosive.getDamage());
				}
			}
			game.removeProjectile(explosive);
		} else {
			explosive.move();
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

	private Enemy getClosestEnemyInRadius(Tower tower) {
		Enemy closestEnemy = null;
		float closestDist = Float.MAX_VALUE;
		ArrayList<Enemy> enemies = game.getEnemies();
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
			float enemyDist = getEuclideanDistance(enemy, tower);
			if (enemyDist <= tower.getRange()) {
				if (closestEnemy == null) {
					closestEnemy = enemy;
					closestDist = enemyDist;
				} else {
					if (enemyDist < closestDist) {
						closestEnemy = enemy;
						closestDist = enemyDist;
					}
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

