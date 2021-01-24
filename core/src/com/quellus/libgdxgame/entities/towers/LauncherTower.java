package com.quellus.libgdxgame.entities.towers;

import com.quellus.libgdxgame.entities.towers.Tower;
import com.quellus.libgdxgame.entities.towers.TowerEnum;
import com.quellus.libgdxgame.entities.Enemy;
import com.quellus.libgdxgame.entities.projectiles.Explosive;
import com.quellus.libgdxgame.entities.projectiles.Projectile;

public class LauncherTower extends Tower {
	private float explosionRadius = 2.5f;

	public LauncherTower(float x, float y) {
		super(x, y, 3.2f);
		type = TowerEnum.LAUNCHER;
		cooldown = 75;
		damage = 5;
	}

	protected Projectile spawnProjectile(Enemy enemy) {
			return new Explosive(this, enemy, damage, explosionRadius);
	}

}
