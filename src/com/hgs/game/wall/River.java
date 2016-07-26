package com.hgs.game.wall;

import java.awt.Color;
import java.awt.Graphics2D;

import com.hgs.game.Point;
import com.hgs.game.resource.R;
import com.hgs.game.util.Images;

public class River extends Grassland {
	private int rX;
	private int rY;
	private final int rWidth = 40,rHeight = 40;
	public River() {
		super();
		canPass(false);
	}

	public River(Point rLocation) {
		super(rLocation);
		canPass(false);
	}

	@Override
	public void draw(Graphics2D g) {
		rX = getWallLocation().getX();
		rY = getWallLocation().getY();
		g.drawImage(Images.river, rX, rY,rWidth,rHeight,null);
		
	}
}
