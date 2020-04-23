package com.quellus.libgdxgame;

import com.quellus.libgdxgame.GameLogic;
import com.quellus.libgdxgame.entities.Entity;
import com.quellus.libgdxgame.entities.towers.Tower;
import com.quellus.libgdxgame.entities.towers.TowerEnum;
import com.quellus.libgdxgame.entities.towers.TowerFactory;

public class Menu {
  private Tower heldTower = null;
  private GameLogic gameLogic;

  private Tower[] menuItems = {TowerFactory.newTower(TowerEnum.TURRET, 17, 14), TowerFactory.newTower(TowerEnum.LAUNCHER, 19, 14)};
  
  public Menu(GameLogic gameLogic) {
    this.gameLogic = gameLogic;
  }

  public boolean held(int x, int y) {
    for (int i = 0; i < menuItems.length; i++) {
      Tower item = menuItems[i];
      if (item.getLocationX() == x && item.getLocationY() == y && gameLogic.canBuyTower(item)) {
        heldTower = TowerFactory.newTower(item.getType(), x, y);
        return true;
      }
    }
    return false;
  }

  public void drag(int x, int y) {
    if (heldTower != null) {
      heldTower.setLocationX(x);
      heldTower.setLocationY(y);
    }
  }

  public void unheld() {
    if (heldTower != null && heldTower.getLocationX() < 16) {
      gameLogic.buyTower(heldTower);
      gameLogic.spawnTower(TowerFactory.newTower(heldTower.getType(), (int) heldTower.getLocationX(), (int) heldTower.getLocationY()));
    }
    heldTower = null;
  }

  public Tower getHeldTower() {
    return heldTower;
  }

  public Tower[] getMenuItems() {
    return menuItems;
  }

}
