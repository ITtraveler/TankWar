package com.hgs.game.wall;

import java.awt.Graphics2D;

import com.hgs.game.Point;

public class FixMan {
	/**
	 * @param baseWall
	 *            Ҫ��װ��ǽ�壬��������ǽ���ݵء�����ȵ�,����װǽ��
	 * 
	 * @param g
	 *            Graphic
	 * @param x
	 *            Ҫ��ͼ�����Ͻ�x
	 * @param y
	 *            Ҫ��ͼ�����Ͻ�y
	 * @param width
	 *            ��
	 * @param height
	 *            ��
	 */
	public static BaseWall fixWall(BaseWall baseWall, Graphics2D g, int x,
			int y, int width, int height) {
		baseWall.setWallLocation(new Point(x, y));
		baseWall.setWallWidthAndHeight(width, height);
		baseWall.draw(g);
		return baseWall;
	}

	/**
	 * רҵ��װBaseWall
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
	 * רҵ��װGrassland
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
	 * רҵ�Ӻ���
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
	 * רҵ��ש
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
