package com.quellus.libgdxgame;

import java.util.ArrayList;
import java.io.File;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Viewport vp;
	private Texture mapTexture;

	@Override
	public void create() {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		vp = new ScalingViewport(Scaling.fit, 256f, 256f, camera);
		vp.apply();
		batch = new SpriteBatch();
		mapTexture = new Texture("map-tile.png");
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(vp.getCamera().combined);
		batch.begin();
		drawMap();
		batch.end();
		camera.update();
	}

	@Override
	public void resize(int width, int height) {
		vp.update(width, height, true);
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	public void drawMap() {
		for (int x = 0; x < 16; x++) {
			for (int y = 0; y < 16; y++) {
				Sprite projectileSprite = new Sprite(mapTexture, 16, 16);
				projectileSprite.setPosition(x * 16, y * 16);
				projectileSprite.draw(batch);
			}
		}
	}
}

