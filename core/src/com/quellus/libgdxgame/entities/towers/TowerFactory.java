package com.quellus.libgdxgame.entities.towers;

import com.quellus.libgdxgame.entities.towers.TowerEnum;

public class TowerFactory {

  public static Tower newTower(TowerEnum type, float x, float y) {
    switch(type) {
      case TURRET:
        return new TurretTower(x, y);
      case LAUNCHER:
        return new LauncherTower(x, y);
      default:
        return null;
    }
  }

}
