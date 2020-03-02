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
	Texture playerImage;
	Texture enemyImage;
	Player playerObj = new Player();
	ArrayList<Enemy> enemies;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		playerImage = new Texture("player.png");
		enemyImage = new Texture("enemy.png");
		enemies = new ArrayList<Enemy>();
		enemies.add(new Enemy(1080, 100));
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		moveAndDrawPlayer();
		moveAndDrawEnemies();
		batch.end();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		playerImage.dispose();
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

	public void moveAndDrawPlayer() {
		Sprite playerSprite = new Sprite(playerImage, 16, 16);
		playerSprite.setPosition(playerObj.getLocationX(), playerObj.getLocationY());
		int rotation = getRotation();
		if (rotation != -1) {
			playerObj.setRotation(rotation);
		}
		playerSprite.setRotation(playerObj.getRotation());
		playerObj.move();
		playerSprite.draw(batch);
	}

	public void moveAndDrawEnemies() {
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemyObj = enemies.get(i);
			Sprite enemySprite = new Sprite(enemyImage, 16, 16);
			enemySprite.setPosition(enemyObj.getLocationX(), enemyObj.getLocationY());
			enemySprite.setRotation(enemyObj.getRotation());
			enemyObj.move();
			enemySprite.draw(batch);
		}
	}
}

