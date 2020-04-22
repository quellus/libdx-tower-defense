package com.quellus.libgdxgame;

import com.badlogic.gdx.Gdx;

import com.quellus.libgdxgame.GameLogic;
import com.quellus.libgdxgame.Menu;
import com.quellus.libgdxgame.entities.towers.TurretTower;
import com.quellus.libgdxgame.entities.towers.TowerFactory;

public class Input {

  private GameLogic gameLogic;
  private Menu menu;
  private boolean isHeld = false;

  public Input(GameLogic gameLogic, Menu menu) {
    this.gameLogic = gameLogic;
    this.menu = menu;
  }

  public void handleInput(int x, int y) {
    if (!isHeld) {
      menu.held(x, y);
    } else {
      menu.drag(x, y);
    }

    isHeld = true;
  }

  public void noInput(int x, int y) {
    if (isHeld) {
      if (x < 16 && menu.getHeldTower() != null) {
        gameLogic.spawnTower(TowerFactory.newTower(menu.getHeldTower().getType(), x, y));
      }
    }
    menu.unheld();
    isHeld = false;
  }
}
