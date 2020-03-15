package com.quellus.libgdxgame;

import com.badlogic.gdx.Gdx;

import com.quellus.libgdxgame.GameLogic;
import com.quellus.libgdxgame.Menu;
import com.quellus.libgdxgame.entities.towers.TurretTower;
import com.quellus.libgdxgame.entities.towers.TowerFactory;

public class Input {

  private int screenSizeX;
  private int screenSizeY;
  private float locationScale;
  private GameLogic gameLogic;
  private Menu menu;
  private boolean isHeld = false;

  public Input(int screenSizeX, int screenSizeY, float locationScale, GameLogic gameLogic, Menu menu) {
    this.screenSizeX = screenSizeX;
    this.screenSizeY = screenSizeY;
    this.locationScale = locationScale;
    this.gameLogic = gameLogic;
    this.menu = menu;
  }

  public void handleInput() {
    int x = Gdx.input.getX();
    int y = screenSizeY - Gdx.input.getY(); // input Y is backwards so convert it
    x = inputToMapCoords(x);
    y = inputToMapCoords(y);
    if (Gdx.input.isTouched(0)) {
      if (!isHeld) {
        menu.held(x, y);
      } else {
        menu.drag(x, y);
      }

      isHeld = true;
    } else {
      if (isHeld) {
        if (x < 16 && menu.getHeldTower() != null) {
          gameLogic.spawnTower(TowerFactory.newTower(menu.getHeldTower().getType(), x, y));
        }
      }
      menu.unheld();
      isHeld = false;
    }

  }

  private int inputToMapCoords(int coord) {
    coord = (int) (coord / locationScale);
    return coord;
  }
   
}
