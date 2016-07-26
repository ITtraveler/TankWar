package com.hgs.game.tank;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import com.hgs.game.resource.Direction;
import com.hgs.game.resource.R;
import com.hgs.game.util.Images;

public class EnemyTank3 extends EnemyTank2{
	private int blood = 300;
	private boolean haveChange = false;
	public EnemyTank3(){
		initEnemyTank3();
		setSpeed(5);
		setSkewSpeed(4);
		setInitBlood(blood);
	}
	
	private void initEnemyTank3(){
		Map<Direction, Image> imgMaps = new HashMap<>();// 存放坦克方向与对应的图片
		imgMaps.put(Direction.U, Images.getImage(R.Tank.EnemyTank.E_T_3_U));
		imgMaps.put(Direction.D, Images.getImage(R.Tank.EnemyTank.E_T_3_D));
		imgMaps.put(Direction.L, Images.getImage(R.Tank.EnemyTank.E_T_3_L));
		imgMaps.put(Direction.R, Images.getImage(R.Tank.EnemyTank.E_T_3_R));
		imgMaps.put(Direction.LU, Images.getImage(R.Tank.EnemyTank.E_T_3_LU));
		imgMaps.put(Direction.LD, Images.getImage(R.Tank.EnemyTank.E_T_3_LD));
		imgMaps.put(Direction.RU, Images.getImage(R.Tank.EnemyTank.E_T_3_RU));
		imgMaps.put(Direction.RD, Images.getImage(R.Tank.EnemyTank.E_T_3_RD));
		reDecorateTank(imgMaps);
	}
	
	@Override
	public boolean checkCollision(Graphics2D g2d) {
		super.checkCollision(g2d);
		if(!haveChange && curBlood == 200){//血量变成200,且没有改变过则把敌方坦克3变成敌方坦克2的外观
			initEnemyTank2();
			haveChange = true;
		}
		return true;
	}
}
