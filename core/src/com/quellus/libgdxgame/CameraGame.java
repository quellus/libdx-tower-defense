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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
	private Texture bulletTexture;
	private Texture enemyTexture;
	private Texture explosiveTexture;
	private Texture menuBackgroundTexture;
	private Texture mapTexture;
	private Texture pathTexture;
	private Texture towerTurretTexture;
	private Texture towerLauncherTexture;

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Viewport viewport;
	private BitmapFont font;

	private Menu menu;
	private Game game;
	private GameLogic gameLogic;
	private Input input;

	private float mapSize = 256f;
	private float viewSize = 28 * 16;
	private int textureSize = 16;

	@Override
	public void create() {
		loadTextures();

		game = new Game("basic-map.txt");
		gameLogic = new GameLogic(game);
		menu = new Menu(gameLogic);
		input = new Input(menu);

		font = new BitmapFont();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		viewport = new ScalingViewport(Scaling.fit, viewSize, mapSize, camera);
		viewport.apply();

		batch = new SpriteBatch();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(viewport.getCamera().combined);

		batch.begin();

		if (Gdx.input.isTouched(0)) {
			Ray ray = viewport.getPickRay(Gdx.input.getX(), Gdx.input.getY());
			int x = (int) (ray.origin.x / 16f);
			int y = (int) (ray.origin.y / 16f);
			input.handleInput(x, y);
		} else {
			Ray ray = viewport.getPickRay(Gdx.input.getX(), Gdx.input.getY());
			int x = (int) (ray.origin.x / 16f);
			int y = (int) (ray.origin.y / 16f);
			input.noInput(x, y);
		}

		gameLogic.update();


		drawMapTiles();
		drawMapPath();
		drawMenu();
		drawEnemies();
		drawTowers();
		drawProjectiles();

		font.draw(batch, "" + game.getCurrency(), 300, 20);

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
		bulletTexture = new Texture("bullet.png");
		mapTexture = new Texture("map-tile.png");
		enemyTexture = new Texture("enemy.png");
		explosiveTexture = new Texture("explosive.png");
		menuBackgroundTexture = new Texture("menu-background.png");
		pathTexture = new Texture("map-path.png");
		towerTurretTexture = new Texture("basic-tower.png");
		towerLauncherTexture = new Texture("launcher-tower.png");
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

	private void drawMenu() {
		for (int x = 0; x < 12; x++) {
			for (int y = 0; y < 16; y++) {
				batch.draw(menuBackgroundTexture, mapSize + x * textureSize, y * textureSize, textureSize, textureSize);
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
			font.draw(batch, "" + item.getPrice(), item.getLocationX() * textureSize, item.getLocationY() * textureSize);
		}
	}

	private void drawEnemies() {
		ArrayList<Enemy> enemies = game.getEnemies();
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemyObj = enemies.get(i);
			drawEntity(enemyTexture, enemyObj);
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
		Sprite bulletSprite = new Sprite(bulletTexture, 16, 16);
		for (int i = 0; i < projectiles.size(); i++) {
			Projectile projectile = projectiles.get(i);
			drawProjectile(projectile);
			bulletSprite.setPosition(projectile.getLocationX() * textureSize , projectile.getLocationY() *  textureSize);
			bulletSprite.setRotation(projectile.getRotation());
			bulletSprite.draw(batch);
		}
	}

	private void drawTower(Tower towerObj) {
		Texture towerTexture = null;
		switch(towerObj.getType()) {
			case TURRET:
				towerTexture = towerTurretTexture;
				break;
			case LAUNCHER:
				towerTexture = towerLauncherTexture;
				break;
		}

		drawEntity(towerTexture, towerObj);
	}

	private void drawProjectile(Projectile projectile) {
		Texture projectileTexture = null;
		if (projectile instanceof Bullet) {
			projectileTexture = bulletTexture;
		} else if (projectile instanceof Explosive) {
			projectileTexture = explosiveTexture;
		} else {
			return;
		}

		drawEntity(projectileTexture, projectile);
	}


	private void drawEntity(Texture texture, Entity entity) {
		Sprite projectileSprite = new Sprite(texture, textureSize, textureSize);
		projectileSprite.setPosition(entity.getLocationX() * textureSize, entity.getLocationY() * textureSize);
		projectileSprite.setRotation(entity.getRotation());
		projectileSprite.draw(batch);
	}

}

