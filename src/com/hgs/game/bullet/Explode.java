package com.hgs.game.bullet;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class Explode {
	//static Image image = Images.getImage(R.Explodes.EXPLODE);
	public void draw(Graphics2D g2d,int x,int y) {
		g2d.setColor(Color.RED);
		Shape shape = new Ellipse2D.Double(x,y,20,20);
		g2d.fill(shape);
	}
}
