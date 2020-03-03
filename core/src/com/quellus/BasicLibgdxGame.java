package com.quellus;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.quellus.Player;
import com.quellus.Enemy;

public class BasicLibgdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture enemyImage;
	Texture mapImage;
	ArrayList<Enemy> enemies;

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

	private float textureScale = 4.21874f;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		mapImage = new Texture("basic-map.png");
		enemyImage = new Texture("enemy.png");
		enemies = new ArrayList<Enemy>();
		enemies.add(new Enemy(map));
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(mapImage, 0, 0, 256 * textureScale, 256 * textureScale);
		moveAndDrawEnemies();
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
			Sprite enemySprite = new Sprite(enemyImage, 16, 16);
			enemySprite.setCenter(4, 4);
			enemySprite.setScale(textureScale);
			enemySprite.setPosition(enemyObj.getLocationX() * 67.5f, enemyObj.getLocationY() *  67.5f);
			enemySprite.setRotation(enemyObj.getRotation());
			enemyObj.move();
			enemySprite.draw(batch);
		}
	}
}

