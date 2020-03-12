package com.quellus.libgdxgame.entities.towers;

import com.quellus.libgdxgame.entities.towers.Tower;
import com.quellus.libgdxgame.entities.Enemy;
import com.quellus.libgdxgame.entities.projectiles.Projectile;

public class TurretTower extends Tower {

	public TurretTower(int x, int y) {
      super(x, y);
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
