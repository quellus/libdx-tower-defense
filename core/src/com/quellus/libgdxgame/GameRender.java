package com.quellus.libgdxgame;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;

import com.quellus.libgdxgame.entities.MenuItem;
import com.quellus.libgdxgame.entities.Enemy;
import com.quellus.libgdxgame.entities.Entity;
import com.quellus.libgdxgame.entities.towers.Tower;
import com.quellus.libgdxgame.entities.projectiles.*;


public class GameRender {
  private int textureSize;
  private float mapSize;

  private Texture bulletTexture;
  private Texture enemyTexture;
  private Texture explosiveTexture;
  private Texture menuBackgroundTexture;
  private Texture mapTexture;
  private Texture pathTexture;
  private Texture towerTurretTexture;
  private Texture towerLauncherTexture;
  private Texture pauseTexture;
  private Texture playTexture;

  private BitmapFont font;

  private Game game;
  private Menu menu;

  public GameRender(int textureSize, float mapSize, Game game, Menu menu) {
    loadTextures();
    this.textureSize = textureSize;
    this.mapSize = mapSize;
    this.game = game;
    this.menu = menu;
    font = new BitmapFont();
  }

  public void render(SpriteBatch batch) {
    drawMapTiles(batch);
    drawMapPath(batch);
    drawMenu(batch);
    drawEnemies(batch);
    drawTowers(batch);
    drawProjectiles(batch);
    font.draw(batch, "" + game.getCurrency(), 300, 20);
    font.draw(batch, "" + game.getLives(), 300, 40);
  }

  private void loadTextures() {
    bulletTexture = new Texture("bullet.png");
    mapTexture = new Texture("map-tile.png");
    enemyTexture = new Texture("enemy.png");
    explosiveTexture = new Texture("explosive.png");
    menuBackgroundTexture = new Texture("menu-background.png");
    pathTexture = new Texture("map-path.png");
    towerTurretTexture = new Texture("basic-tower.png");
    towerLauncherTexture = new Texture("launcher-tower.png");
    pauseTexture = new Texture("pause.png");
    playTexture = new Texture("play.png");
  }

  private void drawMapTiles(SpriteBatch batch) {
    for (int x = 0; x < 16; x++) {
      for (int y = 0; y < 16; y++) {
        Sprite sprite = new Sprite(mapTexture, textureSize, textureSize);
        sprite.setPosition(x * textureSize, y * textureSize);
        sprite.draw(batch);
      }
    }
  }

  private void drawMapPath(SpriteBatch batch) {
    Coordinate<Integer>[] map = game.getMap();
    for (int i = 0; i < map.length; i++) {
      int x = map[i].getX();
      int y = map[i].getY();
      Sprite sprite = new Sprite(pathTexture, textureSize, textureSize);
      sprite.setPosition(x * textureSize, y * textureSize);
      sprite.draw(batch);
    }
  }

  private void drawMenu(SpriteBatch batch) {
    for (int x = 0; x < 12; x++) {
      for (int y = 0; y < 16; y++) {
        batch.draw(menuBackgroundTexture, mapSize + x * textureSize, y * textureSize, textureSize, textureSize);
      }
    }
    Tower tower = menu.getSelectedTower();
    if (tower != null) {
      drawTower(batch, tower);
    }
    Tower[] menuItems = menu.getMenuItems();
    for (int i = 0; i < menuItems.length; i++) {
      Tower item = menuItems[i];
      drawTower(batch, item);
      font.draw(batch, "" + item.getPrice(), item.getLocationX() * textureSize, item.getLocationY() * textureSize);
    }
    MenuItem[] menuButtons = menu.getMenuButtons();
    for (int i = 0; i < menuButtons.length; i++) {
      MenuItem item = menuButtons[i];
      drawMenuButton(batch, item);
    }
  }

  private void drawEnemies(SpriteBatch batch) {
    ArrayList<Enemy> enemies = game.getEnemies();
    for (int i = 0; i < enemies.size(); i++) {
      Enemy enemyObj = enemies.get(i);
      drawEntity(batch, enemyTexture, enemyObj);
    }
  }

  private void drawTowers(SpriteBatch batch) {
    ArrayList<Tower> towers = game.getTowers();
    for (int i = 0; i < towers.size(); i++) {
      Tower towerObj = towers.get(i);
      drawTower(batch, towerObj);
    }
  }

  private void drawProjectiles(SpriteBatch batch) {
    ArrayList<Projectile> projectiles = game.getProjectiles();
    Sprite bulletSprite = new Sprite(bulletTexture, 16, 16);
    for (int i = 0; i < projectiles.size(); i++) {
      Projectile projectile = projectiles.get(i);
      drawProjectile(batch, projectile);
      bulletSprite.setPosition(projectile.getLocationX() * textureSize , projectile.getLocationY() *  textureSize);
      bulletSprite.setRotation(projectile.getRotation());
      bulletSprite.draw(batch);
    }
  }

  private void drawTower(SpriteBatch batch, Tower towerObj) {
    Texture towerTexture = null;
    switch(towerObj.getType()) {
      case TURRET:
        towerTexture = towerTurretTexture;
        break;
      case LAUNCHER:
        towerTexture = towerLauncherTexture;
        break;
    }

    drawEntity(batch, towerTexture, towerObj);
  }

  private void drawMenuButton(SpriteBatch batch, MenuItem menuButton) {
    Texture buttonTexture = null;
    switch(menuButton.getType()) {
      case PAUSE:
        buttonTexture = pauseTexture;
        if (game.getIsPaused()) {
          buttonTexture = playTexture;
        }
        break;
    }

    drawEntity(batch, buttonTexture, menuButton);
  }

  private void drawProjectile(SpriteBatch batch, Projectile projectile) {
    Texture projectileTexture = null;
    if (projectile instanceof Bullet) {
      projectileTexture = bulletTexture;
    } else if (projectile instanceof Explosive) {
      projectileTexture = explosiveTexture;
    } else {
      return;
    }

    drawEntity(batch, projectileTexture, projectile);
  }


  private void drawEntity(SpriteBatch batch, Texture texture, Entity entity) {
    Sprite projectileSprite = new Sprite(texture, textureSize, textureSize);
    projectileSprite.setPosition(entity.getLocationX() * textureSize, entity.getLocationY() * textureSize);
    projectileSprite.setRotation(entity.getRotation());
    projectileSprite.draw(batch);
  }
}
