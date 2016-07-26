package com.hgs.game.wall;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Vector;

import javax.xml.transform.OutputKeys;

import com.hgs.game.Blood;
import com.hgs.game.Collision;
import com.hgs.game.Point;
import com.hgs.game.bullet.Bullet;
import com.hgs.game.util.ControlStudio;

public class BaseWall implements Blood, Collision {
	private int blood = -1;// 默认情况下为-1，即无限血量
	private boolean canPass = false;// tank能否穿墙
	public static int INFINITE = -1;// 无限的血量
	private Point wLocation;// 墙的位置
	protected int width = 40, height = 40;// 默认墙的长宽。
	private boolean canRemove = false;
	private boolean isLife = true;
	public BaseWall() {
		
	}

	/************ 墙的位置、长宽 **************/
	/**
	 * 
	 * @param wLocation
	 *            墙的设置位置(为左上角位置)。
	 */
	public BaseWall(Point wLocation) {
		this.wLocation = wLocation;
	}

	/**
	 * 墙的设置位置(为左上角位置)。
	 *
	 * @param wLocation
	 */
	public void setWallLocation(Point wLocation) {
		this.wLocation = wLocation;
	}

	/**
	 * 设置墙的宽 高
	 * 
	 * @param width
	 * @param height
	 */
	public void setWallWidthAndHeight(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * 得到墙的左上角位置
	 * 
	 * @return
	 */
	public Point getWallLocation() {
		return wLocation;
	}

	/**
	 * 检查坐标点point 是否在墙的范围内，在的化返回true，否则false
	 * 
	 * @param point
	 *            这里为了理解方便，point当成坦克位置.
	 * @return
	 */
	public boolean isInRange(Point point) {
		int x = point.getX();// 坦克的位置
		int y = point.getY();
		int wX = wLocation.getX();
		int wY = wLocation.getY();
		// 判断point是否在砖块内。
		if (x <= (wX + width) && x >= wX && y >= wY && y <= wY + height) {
			return true;
		}
		return false;
	}

	/*********** 血量 ***********/
	/**
	 * 设置墙的血量
	 * 
	 * @param bloodValue
	 */
	public void setBlood(int bloodValue) {
		this.blood = bloodValue;
	}

	@Override
	public int getBlood() {
		return blood;
	}

	/**
	 * 当受到攻击时，血量减少处理
	 * 
	 * @param hurtValue
	 *            伤害值
	 */
	@Override
	public void decreaseBlood(int hurtValue) {
		if (blood != INFINITE && hurtValue < blood) {// 墙不为无限血，且伤害低于当前血量
			blood -= hurtValue;
		} else if (blood != INFINITE && hurtValue >= blood) {// 墙不为无限血，且伤害高于当前血量
			blood = 0;
			// 此处调用墙体消失
			isLife = false;
			//System.out.println("墙被打坏了，该消失了。。。");
		}
		// 为无限血将不做任何操作
	}

	@Override
	public void increaseBlood(int treatValue) {
	}

	@Override
	public boolean isLive() {
		return isLife;
	}

	@Override
	public void setLive(boolean live) {
		this.isLife = live;
	}

	/**
	 * 设置能否穿墙
	 * 
	 * @param pass
	 */
	public void canPass(Boolean pass) {
		this.canPass = pass;
	}

	public boolean isCanPass() {
		return canPass;
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.yellow);
		g.fillRect(wLocation.getX(), wLocation.getY(), width, height);
		g.draw3DRect(wLocation.getX(), wLocation.getY(), width, height, true);
	}

	@Override
	public Rectangle getRect() {
		return new Rectangle(wLocation.getX(), wLocation.getY(), width, height);
	}

	/**
	 * 对于墙体我们可以忽略此方法，除非让这墙也有动作效果时再去重写此方法。 例如此墙有生命值，每次子弹打击就要血量减少,或者到墙时子弹消失。
	 */
	@Override
	public boolean checkCollision(Graphics2D g2d) {
		Vector<Bullet> tBullets = new Vector<>();
		// tBullets = ControlStudio.getEnemyBullets();
		// 加入我的坦克子弹
		for (Bullet bullet : ControlStudio.getMyBullets()) {
			tBullets.add(bullet);
		}
		// 加入敌方坦克的子弹
		for (Bullet bullet : ControlStudio.getEnemyBullets()) {

			tBullets.add(bullet);
		}
		for (Bullet bullet : tBullets) {
			if (this.getRect().intersects(bullet.getRect())) {
				bullet.setLiveToFalse();// 使子弹消失
				
				ControlStudio.removeMyBullets(bullet);
				ControlStudio.removeEnemyBullets(bullet);
				decreaseBlood(bullet.getPower());
				if(canRemove){//能够移除此砖块
					ControlStudio.removeWall(this);
				}
			}
		}
		return false;
	}
	
	public void setCanRemove(Boolean canRemove){
		this.canRemove = canRemove;
	}
}
