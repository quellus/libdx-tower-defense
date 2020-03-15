package com.quellus.libgdxgame.entities.towers;

import com.quellus.libgdxgame.entities.towers.Tower;
import com.quellus.libgdxgame.entities.towers.TowerEnum;
import com.quellus.libgdxgame.entities.Enemy;
import com.quellus.libgdxgame.entities.projectiles.Projectile;

public class LauncherTower extends Tower {

	public LauncherTower(int x, int y) {
		super(x, y);
		type = TowerEnum.LAUNCHER;
	}

	public Projectile attack(Enemy enemy) {
		boolean canAttack = currentCooldown == 0;
		updateCooldown();
		if (canAttack) {
			return new Projectile(this, enemy, damage);
		}
		return null;
	}

}
