package com.hgs.game.wall;

import java.util.List;

public class WallFactory extends AbstractWallFactory {

	/**
	 * ��������ǽ
	 */
	@Override
	public BaseWall createBaseWall() {
		return new BaseWall();
	}

	/**
	 * �����ݵ�
	 */
	@Override
	public Grassland createGrassland() {
		return new Grassland();
	}

	/**
	 * �����ݵ�
	 */
	@Override
	public River createRiver() {
		return new River();
	}

	/**
	 * ����שǽ
	 */
	@Override
	public BrickWall createBrickWall() {
		return new BrickWall();
	}
	/**
	 * ��������
	 */
	@Override
	public Iron createIron() {
		return new Iron();
	}

	/**
	 * ����ǽ�Ĵ���
	 */
	@Override
	public TianBrickWall createTianBrickWall() {
		return new TianBrickWall();
	}

	/**
	 * ���
	 */
	@Override
	public Home createHome() {
		return new Home();
	}
}
