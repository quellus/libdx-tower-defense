package com.quellus.libgdxgame;

import com.badlogic.gdx.Gdx;

import com.quellus.libgdxgame.Menu;
import com.quellus.libgdxgame.entities.towers.TurretTower;
import com.quellus.libgdxgame.entities.towers.TowerFactory;

public class Input {

  private Menu menu;
  private boolean isHeld = false;

  public Input(Menu menu) {
    this.menu = menu;
  }

  public void handleInput(int x, int y) {
    if (!isHeld) {
      isHeld = menu.held(x, y);
      isHeld = true;
    } else {
      menu.drag(x, y);
    }

  }

  public void noInput(int x, int y) {
    menu.unheld();
    isHeld = false;
  }
}
