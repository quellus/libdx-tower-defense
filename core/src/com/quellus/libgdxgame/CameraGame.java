package com.quellus.libgdxgame;

import java.util.ArrayList;
import java.io.File;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Scaling;

import com.quellus.libgdxgame.GameLogic;
import com.quellus.libgdxgame.Game;
import com.quellus.libgdxgame.Menu;
import com.quellus.libgdxgame.entities.Enemy;
import com.quellus.libgdxgame.entities.Entity;
import com.quellus.libgdxgame.entities.towers.Tower;
import com.quellus.libgdxgame.entities.projectiles.Projectile;
import com.quellus.libgdxgame.entities.projectiles.Bullet;
import com.quellus.libgdxgame.entities.projectiles.Explosive;

public class CameraGame extends ApplicationAdapter {
	private Texture mapTexture;
	private Texture pathTexture;
	private Texture enemyTexture;

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Viewport viewport;

	private Menu menu;
	private Game game;
	private GameLogic gameLogic;

	private float mapSize = 256f;
	private int textureSize = 16;

	@Override
	public void create() {
		loadTextures();

		menu = new Menu();
		game = new Game("basic-map.txt");
		gameLogic = new GameLogic(game);

		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		viewport = new ScalingViewport(Scaling.fit, mapSize, mapSize, camera);
		viewport.apply();

		batch = new SpriteBatch();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(viewport.getCamera().combined);

		batch.begin();

		gameLogic.update();

		drawMapTiles();
		drawMapPath();

		drawEnemies();

		if (Gdx.input.isTouched(0)) {
			Ray ray = viewport.getPickRay(Gdx.input.getX(), Gdx.input.getY());
			int x = (int) (ray.origin.x / 16f);
			int y = (int) (ray.origin.y / 16f);
			Gdx.app.log("Test", "Mouse click at (" + x + "," + y + ")");

			Sprite sprite = new Sprite(pathTexture, 16, 16);
			sprite.setPosition(x * 16, y * 16);
			sprite.draw(batch);
		}
		batch.end();
		camera.update();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	private void loadTextures() {
		mapTexture = new Texture("map-tile.png");
		pathTexture = new Texture("map-path.png");
		enemyTexture = new Texture("enemy.png");
	}

	private void drawMapTiles() {
		for (int x = 0; x < 16; x++) {
			for (int y = 0; y < 16; y++) {
				Sprite sprite = new Sprite(mapTexture, textureSize, textureSize);
				sprite.setPosition(x * textureSize, y * textureSize);
				sprite.draw(batch);
			}
		}
	}

	private void drawMapPath() {
		Coordinate<Integer>[] map = game.getMap();
		for (int i = 0; i < map.length; i++) {
			int x = map[i].getX();
			int y = map[i].getY();
			Sprite sprite = new Sprite(pathTexture, textureSize, textureSize);
			sprite.setPosition(x * textureSize, y * textureSize);
			sprite.draw(batch);
		}
	}

	private void drawEnemies() {
		ArrayList<Enemy> enemies = game.getEnemies();
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemyObj = enemies.get(i);
			drawEntity(enemyTexture, enemyObj);
		}
	}

	private void drawEntity(Texture texture, Entity entity) {
		Sprite projectileSprite = new Sprite(texture, textureSize, textureSize);
		projectileSprite.setPosition(entity.getLocationX() * textureSize, entity.getLocationY() * textureSize);
		projectileSprite.setRotation(entity.getRotation());
		projectileSprite.draw(batch);
	}

}

