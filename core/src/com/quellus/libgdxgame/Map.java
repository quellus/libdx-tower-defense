package com.quellus.libgdxgame;

import java.util.ArrayList;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application;

public class Map {

	private Coordinate<Integer>[] mapTiles;

	public Map (String filename) {
		FileHandle mapFile = Gdx.files.internal(filename);
		parseMap(mapFile);
		debugMap(mapTiles);
	}

	public Coordinate<Integer>[] getMapTiles() {
			return mapTiles;
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
		mapTiles = coordList.toArray(map);
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

	private void debugMap(Coordinate<Integer>[] map) {
		String mapStr = "";
		for (int i = 0; i < map.length; i++) {
			mapStr += ", " + map[i].toString();
		}
		Gdx.app.log("TowerDefense", mapStr);
	}

}
