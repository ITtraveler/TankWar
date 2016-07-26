package com.hgs.game.wall;

import java.awt.Graphics2D;

import com.hgs.game.Point;

public class FixMan {
	/**
	 * @param baseWall
	 *            要安装的墙体，包括基础墙、草地、铁块等等,万能装墙工
	 * 
	 * @param g
	 *            Graphic
	 * @param x
	 *            要画图形左上角x
	 * @param y
	 *            要画图形左上角y
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 */
	public static BaseWall fixWall(BaseWall baseWall, Graphics2D g, int x,
			int y, int width, int height) {
		baseWall.setWallLocation(new Point(x, y));
		baseWall.setWallWidthAndHeight(width, height);
		baseWall.draw(g);
		return baseWall;
	}

	/**
	 * 专业安装BaseWall
	 * 
	 * @param g
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	public static BaseWall fixBaseWall(Graphics2D g, int x, int y, int width,
			int height) {
		BaseWall baseWall = new BaseWall();
		baseWall.setWallLocation(new Point(x, y));
		baseWall.setWallWidthAndHeight(width, height);
		baseWall.draw(g);
		return baseWall;
	}

	/**
	 * 专业安装Grassland
	 * 
	 * @param g
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	public static Grassland fixGrassland(Graphics2D g, int x, int y) {
		Grassland grassland = new Grassland();
		grassland.setWallLocation(new Point(x, y));
		grassland.draw(g);
		return grassland;
	}

	/**
	 * 专业加河流
	 * 
	 * @param g
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	public static River fixRiver(Graphics2D g, int x, int y) {
		River river = new River();
		river.setWallLocation(new Point(x, y));
		river.draw(g);
		return river;
	}

	/**
	 * 专业砌砖
	 * 
	 * @param g
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	public static BrickWall fixBrickWall(Graphics2D g, int x, int y) {
		BrickWall brickWall = new BrickWall();
		brickWall.setWallLocation(new Point(x, y));
		brickWall.draw(g);
		return brickWall;
	}
}
