package com.quellus;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.quellus.GameLogic;
import com.quellus.Game;

public class BasicLibgdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture enemyImage;
	Texture mapImage;
	Texture towerImage;
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
		locationOffset = 25;
		batch = new SpriteBatch();
		mapImage = new Texture("basic-map.png");
		enemyImage = new Texture("enemy.png");
		towerImage = new Texture("basic-tower.png");

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(mapImage, 0, 0, 256 * textureScale, 256 * textureScale);
		handleInput();
		gameLogic.update();
		drawEnemies();
		drawTowers();
		batch.end();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}

	public void drawEnemies() {
		ArrayList<Enemy> enemies = game.getEnemies();
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemyObj = enemies.get(i);
			Sprite enemySprite = new Sprite(enemyImage, 16, 16);
			enemySprite.setScale(textureScale);
			enemySprite.setPosition(enemyObj.getLocationX() * locationScale + locationOffset, enemyObj.getLocationY() *  locationScale + locationOffset);
			enemySprite.setRotation(enemyObj.getRotation());
			enemySprite.draw(batch);
		}
	}

	public void drawTowers() {
		ArrayList<Tower> towers = game.getTowers();
		Sprite towerSprite = new Sprite(towerImage, 16, 16);
		towerSprite.setScale(textureScale);
		for (int i = 0; i < towers.size(); i++) {
			Tower towerObj = towers.get(i);
			towerSprite.setPosition(towerObj.getLocationX() * locationScale + locationOffset, towerObj.getLocationY() *  locationScale + locationOffset);
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

