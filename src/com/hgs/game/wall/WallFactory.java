package com.hgs.game.wall;

import java.util.List;

public class WallFactory extends AbstractWallFactory {

	/**
	 * 创建基础墙
	 */
	@Override
	public BaseWall createBaseWall() {
		return new BaseWall();
	}

	/**
	 * 创建草地
	 */
	@Override
	public Grassland createGrassland() {
		return new Grassland();
	}

	/**
	 * 创建草地
	 */
	@Override
	public River createRiver() {
		return new River();
	}

	/**
	 * 创建砖墙
	 */
	@Override
	public BrickWall createBrickWall() {
		return new BrickWall();
	}
	/**
	 * 创造铁块
	 */
	@Override
	public Iron createIron() {
		return new Iron();
	}

	/**
	 * 田字墙的创造
	 */
	@Override
	public TianBrickWall createTianBrickWall() {
		return new TianBrickWall();
	}

	/**
	 * 造家
	 */
	@Override
	public Home createHome() {
		return new Home();
	}
}
