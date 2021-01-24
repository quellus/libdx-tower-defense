package com.quellus.libgdxgame;

import com.badlogic.gdx.Gdx;

import com.quellus.libgdxgame.Menu;
import com.quellus.libgdxgame.entities.towers.TurretTower;
import com.quellus.libgdxgame.entities.towers.TowerFactory;

public class Input {

  private Menu menu;
  private boolean isSelected = false;

  public Input(Menu menu) {
    this.menu = menu;
  }

  public void handleInput(float x, float y) {
    if (!isSelected) {
      isSelected = menu.select(x, y);
      isSelected = true;
    } else {
      menu.drag(x, y);
    }
  }

  public void noInput(int x, int y) {
    menu.deselect();
    isSelected = false;
  }
}
