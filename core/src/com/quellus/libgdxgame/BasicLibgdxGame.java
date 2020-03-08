package com.quellus.libgdxgame;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.quellus.libgdxgame.GameLogic;
import com.quellus.libgdxgame.Game;

public class BasicLibgdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture enemyImage;
	Texture towerImage;
	Texture mapTileImage;
	Texture mapPathImage;
	Game game = new Game();
	GameLogic gameLogic = new GameLogic(game);
	int screenSizeY;

	private float textureScale;
	private float locationScale;
	private float locationOffset;
	
	@Override
	public void create() {
		screenSizeY = Gdx.graphics.getHeight();
		textureScale = screenSizeY / 256f;
		locationScale = screenSizeY / 16f;
		locationOffset = 6f / 16f; //TODO change this it is one pixel off on android
		batch = new SpriteBatch();
		enemyImage = new Texture("enemy.png");
		towerImage = new Texture("basic-tower.png");
		mapTileImage = new Texture("map-tile.png");
		mapPathImage = new Texture("map-path.png");
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		handleInput();
		gameLogic.update();
		drawMapTiles();
		drawMapPath();
		drawEnemies();
		drawTowers();
		batch.end();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}

	public void drawMapTiles() {
		for (int x = 0; x < 16; x++) {
			for (int y = 0; y < 16; y++) {
				batch.draw(mapTileImage, x * locationScale, y * locationScale, 16 * textureScale, 16 * textureScale);
			}
		}
	}

	public void drawMapPath() {
		Coordinate<Integer>[] map = game.getMap();
		for (int i = 0; i < map.length; i++) {
			Coordinate<Integer> currCoords = map[i];
			Coordinate<Integer> nextPointCoords = null;

			if (i + 1 < map.length) {
				nextPointCoords = map[i + 1];
			} else {
				return;
			}

			int pointX = nextPointCoords.getX();
			int pointY = nextPointCoords.getY();
			int x = currCoords.getX();
			int y = currCoords.getY();

			while (pointX != x || pointY != y) {
				batch.draw(mapPathImage, x * locationScale, y * locationScale, 16 * textureScale, 16 * textureScale);
				if (pointX == x) {
					if (pointY > y) {
						y++;
					} else {
						y--;
					}
				} else {
					if (pointX > x) {
						x++;
					} else {
						x--;
					}
				}
			}
			batch.draw(mapPathImage, x * locationScale, y * locationScale, 16 * textureScale, 16 * textureScale);
		}
	}

	public void drawEnemies() {
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

	public void drawTowers() {
		ArrayList<Tower> towers = game.getTowers();
		Sprite towerSprite = new Sprite(towerImage, 16, 16);
		towerSprite.setScale(textureScale);
		for (int i = 0; i < towers.size(); i++) {
			Tower towerObj = towers.get(i);
			towerSprite.setPosition((towerObj.getLocationX() + locationOffset) * locationScale , (towerObj.getLocationY() + locationOffset) *  locationScale);
			towerSprite.setRotation(towerObj.getRotation());
			towerSprite.draw(batch);
		}
	}

	public void handleInput() {
		if (Gdx.input.justTouched()) {
			int x = Gdx.input.getX();
			int y = screenSizeY - Gdx.input.getY();
			x = inputToMapCoords(x);
			y = inputToMapCoords(y);
			gameLogic.spawnTowerAt(x, y);
		}
	}

	public int inputToMapCoords(int coord) {
		coord = (int) (coord / locationScale);
		return coord;
	}

}

