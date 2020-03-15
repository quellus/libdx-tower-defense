package com.quellus.libgdxgame;

import com.quellus.libgdxgame.entities.Entity;
import com.quellus.libgdxgame.entities.towers.Tower;
import com.quellus.libgdxgame.entities.towers.TowerEnum;
import com.quellus.libgdxgame.entities.towers.TowerFactory;

public class Menu {
  private Tower heldTower = null;

  private Tower[] menuItems = {TowerFactory.newTower(TowerEnum.TURRET, 17, 14), TowerFactory.newTower(TowerEnum.LAUNCHER, 19, 14)};

  public void held(int x, int y) {
    for (int i = 0; i < menuItems.length; i++) {
      Tower item = menuItems[i];
      if (item.getLocationX() == x && item.getLocationY() == y) {
        heldTower = TowerFactory.newTower(item.getType(), x, y);
      }
    }
  }

  public void drag(int x, int y) {
    if (heldTower != null) {
      heldTower.setLocationX(x);
      heldTower.setLocationY(y);
    }
  }

  public void unheld() {
    heldTower = null;
  }

  public Tower getHeldTower() {
    return heldTower;
  }

  public Tower[] getMenuItems() {
    return menuItems;
  }

}
