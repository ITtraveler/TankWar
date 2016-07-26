package com.hgs.game.wall;

import java.util.List;

public abstract class AbstractWallFactory {

	public abstract BaseWall createBaseWall();

	public abstract Grassland createGrassland();

	public abstract River createRiver();

	public abstract Iron createIron();

	public abstract BrickWall createBrickWall();

	public abstract TianBrickWall createTianBrickWall();

	public abstract Home createHome();
}
