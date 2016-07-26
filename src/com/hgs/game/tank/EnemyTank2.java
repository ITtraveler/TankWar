package com.hgs.game.tank;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import com.hgs.game.resource.Direction;
import com.hgs.game.resource.R;
import com.hgs.game.util.Images;

public class EnemyTank2 extends EnemyTank{
	private int blood = 200;
	private boolean haveChange = false;
	public EnemyTank2(){
		initEnemyTank2();
		setInitBlood(blood);
	}
	
	protected void initEnemyTank2(){
		Map<Direction, Image> imgMaps = new HashMap<>();// 存放坦克方向与对应的图片
		imgMaps.put(Direction.U, Images.getImage(R.Tank.EnemyTank.E_T_2_U));
		imgMaps.put(Direction.D, Images.getImage(R.Tank.EnemyTank.E_T_2_D));
		imgMaps.put(Direction.L, Images.getImage(R.Tank.EnemyTank.E_T_2_L));
		imgMaps.put(Direction.R, Images.getImage(R.Tank.EnemyTank.E_T_2_R));
		imgMaps.put(Direction.LU, Images.getImage(R.Tank.EnemyTank.E_T_2_LU));
		imgMaps.put(Direction.LD, Images.getImage(R.Tank.EnemyTank.E_T_2_LD));
		imgMaps.put(Direction.RU, Images.getImage(R.Tank.EnemyTank.E_T_2_RU));
		imgMaps.put(Direction.RD, Images.getImage(R.Tank.EnemyTank.E_T_2_RD));
		reDecorateTank(imgMaps);
	}
	
	@Override
	public boolean checkCollision(Graphics2D g2d) {
		super.checkCollision(g2d);
		if(!haveChange && curBlood  == 100){//血量变成100,且没有改变过则把敌方坦克2变成敌方坦克1的外观
			initEnemyTank();
			haveChange = true;
		}
		return true;
	}
}
