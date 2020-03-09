package com.quellus.libgdxgame;

import java.util.ArrayList;

import java.io.*;

import com.quellus.libgdxgame.Enemy;
import com.quellus.libgdxgame.Tower;
import com.quellus.libgdxgame.GameLogic;
import com.quellus.libgdxgame.Game;
import com.quellus.libgdxgame.Coordinate;

public class Game {
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Tower> towers = new ArrayList<Tower>();

	private Coordinate<Integer>[] map;

	public Game() {
		parseMap("basic-map.txt");
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
	
	public Coordinate<Integer>[] getMap() {
		return map;
	}

	private boolean parseMap(String filename) {
		File file;
		BufferedReader reader;
		try {
			file = new File(filename);
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			System.out.println("map file not found");
			return false;
		}

		ArrayList<Coordinate<Integer>> coordsList = readAndParseMapFile(reader);

		coordsList = waypointsToTiles(coordsList);
		if (coordsList == null) {
			System.out.println("waypointsToTiles failed");
			return false;
		}

		Coordinate<Integer>[] map = new Coordinate[coordsList.size()];
		this.map = coordsList.toArray(map);
		return true;
	}

	private ArrayList<Coordinate<Integer>> readAndParseMapFile(BufferedReader reader) {
		ArrayList<Coordinate<Integer>> coordsList = new ArrayList<Coordinate<Integer>>();

		try {
			String line = reader.readLine();

			while (line != null) {
				String[] coords = line.split(" ");
				if (coords.length < 2) {
					System.out.println("The map file is bad");
					return null;
				}
				Coordinate<Integer> coord = new Coordinate<Integer>(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));

				coordsList.add(coord);

				line = reader.readLine();
			}
		} catch (IOException e) {
			System.out.println("idk something broke I guess");
			return null;
		}

		return coordsList;

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
		System.out.println(mapStr);
	}

}

