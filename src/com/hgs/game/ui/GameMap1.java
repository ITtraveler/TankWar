package com.hgs.game.ui;

import java.util.List;
import java.util.Random;

import com.hgs.game.Point;
import com.hgs.game.resource.R.Home;
import com.hgs.game.util.ControlStudio;
import com.hgs.game.wall.AbstractWallFactory;
import com.hgs.game.wall.BaseWall;
import com.hgs.game.wall.TianBrickWall;
import com.hgs.game.wall.WallFactory;

public class GameMap1 {
	public static final int GRASSLAND = 1;
	public static final int RIVER = 2;
	public static final int IRON = 3;
	public static final int TIAN_TRICK = 4;
	public static final int HOME = -2;
	private final int width = 880, height = 600;// 窗口的長寬
	public int[][] map = new int[][] { // 22x15的格子.為固定格子數，不變
			{ -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0,
					-1, -1 },
			{ -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, 0, 4, 4, 4, 0, 0, 0, 0,
					-1, -1 },
			{ 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 0, 0, 0, 0, 3, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0 },
			{ 2, 0, 0, 0, 0, 1, 0, 0, 2, 2, 0, 0, 1, 0, 0, 4, 0, 0, 0, 0, 0, 2 },
			{ 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 4, 0, 3, 3, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0 },
			{ 0, 4, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 4, 4, 0, 0, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, -2, -2, -2, -2, 0, 0, 0, 0, 0, 0, 0,
					0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, -1, -1, -2, -1, -1, -2, 0, 0, 0, 0, 0, 0, 0,
					0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, -1, -1, -2, -1, -1, -2, 0, 0, 0, 0, 0, 0, 0,
					0, 0 } };

	public int[][] noInit = { { 12, 9 }, { 12, 10 }, { 12, 11 }, { 12, 12 },
			{ 13, 9 }, { 13, 12 }, { 14, 9 }, { 14, 12 } };

	public GameMap1() {
		initMap();
	}

	public void randMap() {
		Random rand = new Random();
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 22; j++) {
				if (map[i][j] != -1) {
					 map[i][j] = rand.nextInt(17);// rand.nextInt(20);
				}
			}
		}
		// 老窝围成砖块
		for (int i = 0; i < noInit.length; i++) {
			int x = noInit[i][0];
			int y = noInit[i][1];
			map[x][y] = 4;
		}
	}

	// 初始化游戏墙体数据
	public void initMap() {
		ControlStudio.clearAll();
		AbstractWallFactory wallFactory = new WallFactory();
		// 初始化家 固定位置
		BaseWall home = wallFactory.createHome();
		home.setWallLocation(new Point(400, 520));
		ControlStudio.addWall(home);
		ControlStudio.home = home;
		// 初始化墙体
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 22; j++) {
				int value = map[i][j];
				if (value != 0) {
					int x = j * 40;
					int y = i * 40;
					BaseWall wall = null;
					switch (value) {
					case GameMap1.GRASSLAND:
						wall = wallFactory.createGrassland();
						break;
					case GameMap1.RIVER:
						wall = wallFactory.createRiver();
						break;
					case GameMap1.IRON:
						wall = wallFactory.createIron();
						break;
					case GameMap1.TIAN_TRICK:// 田字砖块
						TianBrickWall tian = wallFactory.createTianBrickWall();
						tian.setLocation(new Point(x, y));
						List<BaseWall> tList = tian.createTianBrickWall();
						for (BaseWall tWall : tList) {
							ControlStudio.addWall(tWall);
						}
						break;
					}
					if (wall != null) {
						wall.setWallLocation(new Point(x, y));
						ControlStudio.addWall(wall);
					}
				}
			}
		}
	}

}
