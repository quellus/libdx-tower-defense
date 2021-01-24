package com.quellus.libgdxgame.entities;

import com.quellus.libgdxgame.entities.Entity;
import com.quellus.libgdxgame.MenuItemType;

public class MenuItem extends Entity {
  private MenuItemType type;

  public MenuItem(float x, float y, MenuItemType type) {
    this.type = type;
    locationX = x;
    locationY = y;
  }

  public MenuItemType getType() {
    return type;
  }
}

