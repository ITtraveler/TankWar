package com.hgs.game.util;

import java.awt.Image;
import java.awt.Toolkit;

import com.hgs.game.resource.R;

public class Images {
	public static Toolkit tool = Toolkit.getDefaultToolkit();

	public static Image getImage(String imgPath) {
		// Image image = null;
		// image = ImageIO.read(new File(imgPath));
		Image image = tool.getImage(Images.class.getClassLoader().getResource(imgPath));
		return image;
	}
	public static Image grassland = Images.getImage(R.Wall.GRASSLAND);
	public static Image brickWall = Images.getImage(R.Wall.BRICKWALL);
	public static Image iron = Images.getImage(R.Wall.IRON);
	public static Image river = Images.getImage(R.Wall.RIVAER);
	public static Image home = Images.getImage(R.Home.HOME);
}
