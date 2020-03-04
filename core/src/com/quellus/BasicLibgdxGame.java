package com.quellus;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.quellus.Enemy;
import com.quellus.Tower;

public class BasicLibgdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture enemyImage;
	Texture mapImage;
	Texture towerImage;
	ArrayList<Enemy> enemies;
	Tower towerObj;
	int screenSizeY;

	private int[][] map = {
		{0,14},
		{10,14},
		{10,11},
		{4,11},
		{4,8},
		{12,8},
		{12,5},
		{7,5},
		{7,0}
	};

	private float textureScale;
	private float locationScale;
	private float locationOffset;
	
	@Override
	public void create() {
		screenSizeY = Gdx.graphics.getHeight();
		textureScale = screenSizeY / 256f;
		locationScale = screenSizeY / 16f;
		locationOffset = 25;
		System.out.println(locationScale);
		batch = new SpriteBatch();
		mapImage = new Texture("basic-map.png");
		enemyImage = new Texture("enemy.png");
		towerImage = new Texture("basic-tower.png");
		enemies = new ArrayList<Enemy>();
		enemies.add(new Enemy(map));
		towerObj = new Tower();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(mapImage, 0, 0, 256 * textureScale, 256 * textureScale);
		moveAndDrawEnemies();
		rotateAndDrawTowers();
		batch.end();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}

	public int getRotation() {
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			return 0;
		} else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			return 180;
		} else if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			return 90;
		} else if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
			return 270;
		}
		return -1;
	}

	public void moveAndDrawEnemies() {
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemyObj = enemies.get(i);
			if (enemyObj.getHealth() > 0) {
				Sprite enemySprite = new Sprite(enemyImage, 16, 16);
				enemySprite.setScale(textureScale);
				enemySprite.setPosition(enemyObj.getLocationX() * locationScale + locationOffset, enemyObj.getLocationY() *  locationScale + locationOffset);
				enemySprite.setRotation(enemyObj.getRotation());
				enemyObj.move();
				enemySprite.draw(batch);
			}
		}
	}

	public void rotateAndDrawTowers() {
		towerObj.rotate(enemies);
		towerObj.attack(enemies);
		Sprite towerSprite = new Sprite(towerImage, 16, 16);
		towerSprite.setScale(textureScale);
		towerSprite.setPosition(towerObj.getLocationX() * locationScale + locationOffset, towerObj.getLocationY() *  locationScale + locationOffset);
		towerSprite.setRotation(towerObj.getRotation());
		towerSprite.draw(batch);
	}

}

