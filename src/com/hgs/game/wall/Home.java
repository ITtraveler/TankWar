package com.hgs.game.wall;

import java.awt.Graphics2D;

import com.hgs.game.Blood;
import com.hgs.game.Location;
import com.hgs.game.Point;
import com.hgs.game.util.Images;

public class Home extends BrickWall implements Blood {

	private int curBlood = 100;
	private final int widht = 80;
	private final int height = 80;

	public Home() {
		setWallWidthAndHeight(widht, height);
		setBlood(curBlood);
	}


	@Override
	public void draw(Graphics2D g) {
		g.drawImage(Images.home, getWallLocation().getX(), getWallLocation()
				.getY(), widht, height, null);
	}
	
}
