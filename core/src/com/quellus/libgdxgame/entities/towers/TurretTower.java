package com.quellus.libgdxgame.entities.towers;

import com.quellus.libgdxgame.entities.towers.Tower;
import com.quellus.libgdxgame.entities.towers.TowerEnum;
import com.quellus.libgdxgame.entities.Enemy;
import com.quellus.libgdxgame.entities.projectiles.Bullet;
import com.quellus.libgdxgame.entities.projectiles.Projectile;

public class TurretTower extends Tower {

	public TurretTower(int x, int y) {
		super(x, y, 3.2f);
		cooldown = 10;
		type = TowerEnum.TURRET;
	}

	protected Projectile spawnProjectile(Enemy enemy) {
			return new Bullet(this, enemy, damage);
	}

}
