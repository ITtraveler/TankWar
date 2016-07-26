package com.hgs.game.wall;

import java.util.ArrayList;
import java.util.List;

import com.hgs.game.Point;
/**
 * ����שǽ
 * @author hgs
 *
 */
public class TianBrickWall {
	private Point wLocation;
	public void setLocation(Point wLocation){
		this.wLocation = wLocation;
	}
	
	/**
	 * �ஔ����Ă�BrickWall�M��
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
