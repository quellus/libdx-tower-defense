package com.quellus.libgdxgame;

import com.quellus.libgdxgame.GameLogic;
import com.quellus.libgdxgame.entities.Entity;
import com.quellus.libgdxgame.entities.towers.Tower;
import com.quellus.libgdxgame.entities.towers.TowerEnum;
import com.quellus.libgdxgame.entities.towers.TowerFactory;

public class Menu {
  private Tower selectedTower = null;
  private GameLogic gameLogic;

  private Tower[] menuItems = {TowerFactory.newTower(TowerEnum.TURRET, 17, 14), TowerFactory.newTower(TowerEnum.LAUNCHER, 19, 14)};

  public Menu(GameLogic gameLogic) {
    this.gameLogic = gameLogic;
  }

  public boolean select(int x, int y) {
    for (int i = 0; i < menuItems.length; i++) {
      Tower item = menuItems[i];
      if (item.getLocationX() == x && item.getLocationY() == y && gameLogic.canBuyTower(item)) {
        selectedTower = TowerFactory.newTower(item.getType(), x, y);
        return true;
      }
    }
    return false;
  }

  public void drag(int x, int y) {
    if (selectedTower != null) {
      selectedTower.setLocationX(x);
      selectedTower.setLocationY(y);
    }
  }

  public void deselect() {
    if (selectedTower != null && selectedTower.getLocationX() < 16) {
      if (gameLogic.spawnTower(TowerFactory.newTower(selectedTower.getType(), (int) selectedTower.getLocationX(), (int) selectedTower.getLocationY()))) {
        gameLogic.buyTower(selectedTower);
      }
    }
    selectedTower = null;
  }

  public Tower getSelectedTower() {
    return selectedTower;
  }

  public Tower[] getMenuItems() {
    return menuItems;
  }

}

