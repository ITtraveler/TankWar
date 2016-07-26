package com.hgs.game.wall;

import java.util.ArrayList;
import java.util.List;

import com.hgs.game.Point;
/**
 * 田字砖墙
 * @author hgs
 *
 */
public class TianBrickWall {
	private Point wLocation;
	public void setLocation(Point wLocation){
		this.wLocation = wLocation;
	}
	
	/**
	 * 相當於由四個BrickWall組成
	 * @return
	 */
	public List<BaseWall> createTianBrickWall(){
		List<BaseWall> wallList = new ArrayList<>();
		for(int i = 0;i < 2;i++)
			for(int j = 0;j < 2;j++){
				int x = wLocation.getX()+i*20;
				int y = wLocation.getY()+j*20;
				wallList.add(new BrickWall(new Point(x,y)));
		}
		return wallList;
	}
}
