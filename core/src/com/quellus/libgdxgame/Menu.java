package com.quellus.libgdxgame;

import com.quellus.libgdxgame.GameLogic;
import com.quellus.libgdxgame.MenuItemType;
import com.quellus.libgdxgame.entities.Entity;
import com.quellus.libgdxgame.entities.MenuItem;
import com.quellus.libgdxgame.entities.towers.Tower;
import com.quellus.libgdxgame.entities.towers.TowerEnum;
import com.quellus.libgdxgame.entities.towers.TowerFactory;

public class Menu {
  private Tower selectedTower = null;
  private GameLogic gameLogic;

  private Tower[] towerArsenal = {
            TowerFactory.newTower(TowerEnum.TURRET, 17, 14),
            TowerFactory.newTower(TowerEnum.LAUNCHER, 19, 14)
          };
  private MenuItem[] menuButtons = {new MenuItem(25, 1, MenuItemType.PAUSE)};

  public Menu(GameLogic gameLogic) {
    this.gameLogic = gameLogic;
  }

  public boolean select(float x, float y) {
    for (int i = 0; i < towerArsenal.length; i++) {
      Tower item = towerArsenal[i];
      if (item.getLocationX() == x && item.getLocationY() == y) {
        if (gameLogic.canBuyTower(item)) {
          selectedTower = TowerFactory.newTower(item.getType(), x, y);
          return true;
        }
      }
    }
    for (int i = 0; i < menuButtons.length; i++) {
      MenuItem item = menuButtons[i];
      if (item.getLocationX() == x && item.getLocationY() == y) {
        // TODO pause
        gameLogic.togglePause();
      }

    }
    return false;
  }

  public void drag(float x, float y) {
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
    return towerArsenal;
  }

  public MenuItem[] getMenuButtons() {
    return menuButtons;
  }

}

