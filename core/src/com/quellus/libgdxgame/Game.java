package com.quellus.libgdxgame;

import java.util.ArrayList;

import java.io.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.files.FileHandle;

import com.quellus.libgdxgame.GameLogic;
import com.quellus.libgdxgame.entities.Enemy;
import com.quellus.libgdxgame.entities.towers.Tower;
import com.quellus.libgdxgame.entities.projectiles.Projectile;


public class Game {
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Tower> towers = new ArrayList<Tower>();
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

	private Coordinate<Integer>[] map;

	public Game(String filename) {
		FileHandle mapFile = Gdx.files.internal(filename);
		parseMap(mapFile);
	}

	public ArrayList<Enemy> getEnemies()  {
		return enemies;
	}

	public void addEnemy(Enemy enemy) {
		enemies.add(enemy);
	}

	public void removeEnemy(Enemy enemy) {
		enemies.remove(enemy);
	}

	public ArrayList<Tower> getTowers() {
		return towers;
	}

	public void addTower(Tower tower) {
		towers.add(tower);
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	public void addProjectile(Projectile projectile) {
		projectiles.add(projectile);
	}

	public void removeProjectile(Projectile projectile) {
		projectiles.remove(projectile);
	}
	
	public Coordinate<Integer>[] getMap() {
		return map;
	}

	private boolean parseMap(FileHandle file) {
		String fileString = file.readString();
		String[] lines = fileString.split("\\r?\\n");
		ArrayList<Coordinate<Integer>> coordList = new ArrayList<Coordinate<Integer>>();
		for (int i = 0; i < lines.length; i++) {
			String[] coords = lines[i].split(" ");
			if (coords.length != 2) {
				Gdx.app.error("TowerDefense","Failed to parse map file");
				return false;
			}
			Coordinate<Integer> coord = new Coordinate<Integer>(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
			coordList.add(coord);
		}

		coordList = waypointsToTiles(coordList);
		if (coordList == null) {
			Gdx.app.error("TowerDefense", "waypointsToTiles failed");
			return false;
		}

		Coordinate<Integer>[] map = new Coordinate[coordList.size()];
		this.map = coordList.toArray(map);
		debugMap();
		return true;
	}

	private ArrayList<Coordinate<Integer>> waypointsToTiles(ArrayList<Coordinate<Integer>> map) {
		ArrayList<Coordinate<Integer>> newMap = new ArrayList<Coordinate<Integer>>();
		for (int i = 0; i < map.size(); i++) {
			Coordinate<Integer> currCoords = map.get(i);
			Coordinate<Integer> nextPointCoords = null;

			int x = currCoords.getX();
			int y = currCoords.getY();

			if (i + 1 < map.size()) {
				nextPointCoords = map.get(i + 1);
			} else {
				newMap.add(new Coordinate<Integer>(x, y));
				return newMap;
			}

			int pointX = nextPointCoords.getX();
			int pointY = nextPointCoords.getY();

			while (pointX != x || pointY != y) {
				newMap.add(new Coordinate<Integer>(x, y));
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
		}
		return newMap;
	}

	private void debugMap() {
		String mapStr = "";
		for (int i = 0; i < map.length; i++) {
			mapStr += ", " + map[i].toString();
		}
		Gdx.app.log("TowerDefense", mapStr);
	}

}

