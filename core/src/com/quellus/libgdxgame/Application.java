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
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Scaling;

import com.quellus.libgdxgame.GameRender;
import com.quellus.libgdxgame.GameLogic;
import com.quellus.libgdxgame.Game;
import com.quellus.libgdxgame.Menu;
import com.quellus.libgdxgame.entities.Enemy;
import com.quellus.libgdxgame.entities.Entity;
import com.quellus.libgdxgame.entities.towers.Tower;
import com.quellus.libgdxgame.entities.projectiles.*;

public class Application extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Viewport viewport;

	private Menu menu;
	private Game game;
	private GameRender gameRender;
	private GameLogic gameLogic;
	private Input input;

	private int textureSize = 16;
	private float mapSize = 16 * textureSize;
	private float viewSize = 28 * textureSize; // map is 16 wide and menu is 12

	@Override
	public void create() {

		game = new Game("basic-map.txt");
		gameLogic = new GameLogic(game);
		menu = new Menu(gameLogic);
		input = new Input(menu);

		gameRender = new GameRender(textureSize, mapSize, game, menu);

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

		if (gameLogic.isWaveEnd()) {
			gameLogic.togglePause();
			gameLogic.nextWave();
		}
		
		gameLogic.update();
		gameRender.render(batch);

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

}

