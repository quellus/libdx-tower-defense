package com.quellus.libgdxgame;

import java.util.ArrayList;
import java.io.File;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.quellus.libgdxgame.GameLogic;
import com.quellus.libgdxgame.Game;
import com.quellus.libgdxgame.Menu;
import com.quellus.libgdxgame.entities.Enemy;
import com.quellus.libgdxgame.entities.Entity;
import com.quellus.libgdxgame.entities.towers.Tower;
import com.quellus.libgdxgame.entities.projectiles.Projectile;

public class BasicLibgdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture menuBackgroundImage;
	Texture enemyImage;
	Texture bulletImage;
	Texture towerTurretImage;
	Texture towerLauncherImage;
	Texture mapTileImage;
	Texture mapPathImage;
	Menu menu;
	Game game;
	GameLogic gameLogic;
	Input input;
	int screenSizeY;
	int screenSizeX;

	private float mapSize = 256f;
	private float textureSize = 16f;
	private float textureScale;
	private float scaledTextureSize;
	private float locationScale;
	private float locationOffset;

	@Override
	public void create() {
		screenSizeY = Gdx.graphics.getHeight();
		screenSizeX = Gdx.graphics.getWidth();
		textureScale = screenSizeY / mapSize;
		scaledTextureSize = textureSize * textureScale;
		locationScale = screenSizeY / textureSize;
		locationOffset = 6f / textureSize; //TODO change this it is one pixel off on android
		FileHandle mapFile = Gdx.files.internal("basic-map.txt");
		menu = new Menu();
		game = new Game("basic-map.txt");
		gameLogic = new GameLogic(game);
		input = new Input(screenSizeX, screenSizeY, locationScale, gameLogic, menu);
		batch = new SpriteBatch();
		menuBackgroundImage = new Texture("menu-background.png");
		enemyImage = new Texture("enemy.png");
		bulletImage = new Texture("bullet.png");
		towerTurretImage = new Texture("basic-tower.png");
		towerLauncherImage = new Texture("launcher-tower.png");
		mapTileImage = new Texture("map-tile.png");
		mapPathImage = new Texture("map-path.png");
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		input.handleInput();
		gameLogic.update();
		drawMapTiles();
		drawMapPath();
		drawMenu();
		drawEnemies();
		drawTowers();
		drawProjectiles();
		batch.end();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}



	private void drawMapTiles() {
		for (int x = 0; x < 16; x++) {
			for (int y = 0; y < 16; y++) {
				batch.draw(mapTileImage, x * locationScale, y * locationScale, scaledTextureSize, scaledTextureSize);
			}
		}
	}

	private void drawMapPath() {
		Coordinate<Integer>[] map = game.getMap();
		for (int i = 0; i < map.length; i++) {
			int x = map[i].getX();
			int y = map[i].getY();
			batch.draw(mapPathImage, x * locationScale, y * locationScale, scaledTextureSize, scaledTextureSize);
		}
	}

	private void drawMenu() {
		for (int x = 0; x < 12; x++) {
			for (int y = 0; y < 16; y++) {
				batch.draw(menuBackgroundImage, x * locationScale + screenSizeY, y * locationScale, scaledTextureSize, scaledTextureSize);
			}
		}
		Tower tower = menu.getHeldTower();
		if (tower != null) {
			drawTower(tower);
		}
		Tower[] menuItems = menu.getMenuItems();
		for (int i = 0; i < menuItems.length; i++) {
			Tower item = menuItems[i];
			drawTower(item);
		}
	}

	private void drawEnemies() {
		ArrayList<Enemy> enemies = game.getEnemies();
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemyObj = enemies.get(i);
			Sprite enemySprite = new Sprite(enemyImage, 16, 16);
			enemySprite.setScale(textureScale);
			enemySprite.setRotation(enemyObj.getRotation());
			enemySprite.setPosition((enemyObj.getLocationX() + locationOffset) * locationScale, (enemyObj.getLocationY() + locationOffset) *  locationScale);
			enemySprite.draw(batch);
		}
	}

	private void drawTowers() {
		ArrayList<Tower> towers = game.getTowers();
		for (int i = 0; i < towers.size(); i++) {
			Tower towerObj = towers.get(i);
			drawTower(towerObj);
		}
	}

	private void drawProjectiles() {
		ArrayList<Projectile> projectiles = game.getProjectiles();
		Sprite bulletSprite = new Sprite(bulletImage, 16, 16);
		bulletSprite.setScale(textureScale);
		for (int i = 0; i < projectiles.size(); i++) {
			Projectile projectile = projectiles.get(i);
			bulletSprite.setPosition((projectile.getLocationX() + locationOffset) * locationScale , (projectile.getLocationY() + locationOffset) *  locationScale);
			bulletSprite.setRotation(projectile.getRotation());
			bulletSprite.draw(batch);
		}
	}

	private void drawTower(Tower towerObj) {
		Texture towerImage = null;
		switch(towerObj.getType()) {
			case TURRET:
				towerImage = towerTurretImage;
				break;
			case LAUNCHER:
				towerImage = towerLauncherImage;
				break;
		}

		Sprite towerSprite = new Sprite(towerImage, 16, 16);
		towerSprite.setScale(textureScale);
		towerSprite.setPosition((towerObj.getLocationX() + locationOffset) * locationScale , (towerObj.getLocationY() + locationOffset) *  locationScale);
		towerSprite.setRotation(towerObj.getRotation());
		towerSprite.draw(batch);
	}

}

