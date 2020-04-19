package com.quellus.libgdxgame.entities.towers;

import com.quellus.libgdxgame.entities.towers.Tower;
import com.quellus.libgdxgame.entities.towers.TowerEnum;
import com.quellus.libgdxgame.entities.Enemy;
import com.quellus.libgdxgame.entities.projectiles.Bullet;

public class TurretTower extends Tower {

	public TurretTower(int x, int y) {
		super(x, y, 3.2f);
		type = TowerEnum.TURRET;
	}

	public Bullet attack(Enemy enemy) {
		boolean canAttack = currentCooldown == 0;
		updateCooldown();
		if (canAttack) {
			return new Bullet(this, enemy, damage);
		}
		return null;
	}

}
