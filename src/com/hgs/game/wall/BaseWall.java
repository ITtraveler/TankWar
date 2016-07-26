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
	private int blood = -1;// Ĭ�������Ϊ-1��������Ѫ��
	private boolean canPass = false;// tank�ܷ�ǽ
	public static int INFINITE = -1;// ���޵�Ѫ��
	private Point wLocation;// ǽ��λ��
	protected int width = 40, height = 40;// Ĭ��ǽ�ĳ���
	private boolean canRemove = false;
	private boolean isLife = true;
	public BaseWall() {
		
	}

	/************ ǽ��λ�á����� **************/
	/**
	 * 
	 * @param wLocation
	 *            ǽ������λ��(Ϊ���Ͻ�λ��)��
	 */
	public BaseWall(Point wLocation) {
		this.wLocation = wLocation;
	}

	/**
	 * ǽ������λ��(Ϊ���Ͻ�λ��)��
	 *
	 * @param wLocation
	 */
	public void setWallLocation(Point wLocation) {
		this.wLocation = wLocation;
	}

	/**
	 * ����ǽ�Ŀ� ��
	 * 
	 * @param width
	 * @param height
	 */
	public void setWallWidthAndHeight(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * �õ�ǽ�����Ͻ�λ��
	 * 
	 * @return
	 */
	public Point getWallLocation() {
		return wLocation;
	}

	/**
	 * ��������point �Ƿ���ǽ�ķ�Χ�ڣ��ڵĻ�����true������false
	 * 
	 * @param point
	 *            ����Ϊ����ⷽ�㣬point����̹��λ��.
	 * @return
	 */
	public boolean isInRange(Point point) {
		int x = point.getX();// ̹�˵�λ��
		int y = point.getY();
		int wX = wLocation.getX();
		int wY = wLocation.getY();
		// �ж�point�Ƿ���ש���ڡ�
		if (x <= (wX + width) && x >= wX && y >= wY && y <= wY + height) {
			return true;
		}
		return false;
	}

	/*********** Ѫ�� ***********/
	/**
	 * ����ǽ��Ѫ��
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
	 * ���ܵ�����ʱ��Ѫ�����ٴ���
	 * 
	 * @param hurtValue
	 *            �˺�ֵ
	 */
	@Override
	public void decreaseBlood(int hurtValue) {
		if (blood != INFINITE && hurtValue < blood) {// ǽ��Ϊ����Ѫ�����˺����ڵ�ǰѪ��
			blood -= hurtValue;
		} else if (blood != INFINITE && hurtValue >= blood) {// ǽ��Ϊ����Ѫ�����˺����ڵ�ǰѪ��
			blood = 0;
			// �˴�����ǽ����ʧ
			isLife = false;
			//System.out.println("ǽ�����ˣ�����ʧ�ˡ�����");
		}
		// Ϊ����Ѫ�������κβ���
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
	 * �����ܷ�ǽ
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
	 * ����ǽ�����ǿ��Ժ��Դ˷�������������ǽҲ�ж���Ч��ʱ��ȥ��д�˷����� �����ǽ������ֵ��ÿ���ӵ������ҪѪ������,���ߵ�ǽʱ�ӵ���ʧ��
	 */
	@Override
	public boolean checkCollision(Graphics2D g2d) {
		Vector<Bullet> tBullets = new Vector<>();
		// tBullets = ControlStudio.getEnemyBullets();
		// �����ҵ�̹���ӵ�
		for (Bullet bullet : ControlStudio.getMyBullets()) {
			tBullets.add(bullet);
		}
		// ����з�̹�˵��ӵ�
		for (Bullet bullet : ControlStudio.getEnemyBullets()) {

			tBullets.add(bullet);
		}
		for (Bullet bullet : tBullets) {
			if (this.getRect().intersects(bullet.getRect())) {
				bullet.setLiveToFalse();// ʹ�ӵ���ʧ
				
				ControlStudio.removeMyBullets(bullet);
				ControlStudio.removeEnemyBullets(bullet);
				decreaseBlood(bullet.getPower());
				if(canRemove){//�ܹ��Ƴ���ש��
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
