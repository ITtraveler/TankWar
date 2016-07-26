package com.hgs.game.tank;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import com.hgs.game.Point;
import com.hgs.game.bullet.Bullet;
import com.hgs.game.resource.Direction;
import com.hgs.game.resource.R;
import com.hgs.game.util.ControlStudio;
import com.hgs.game.util.Images;

public class EnemyTank extends MyTank implements Runnable {
	public final int value = 50;// ��ֵ
	private Point tankLocation;
	private int speed, skewSpeed;
	private final int U = 0, D = 1, L = 2, Right = 3, LU = 4, RU = 5, LD = 6,
			RD = 7, STOP = 8;
	private Random randrom = new Random();
	private int blood = 100;

	public EnemyTank() {
		setSpeed(5);
		setSkewSpeed(4);
		
		speed = getSpeed();
		skewSpeed = getSkewSpeed();
		initEnemyTank();
		setInitBlood(blood);
	}

	/**
	 * ��ʼ��̹�˵�ͼƬ
	 */
	protected void initEnemyTank() {
		Map<Direction, Image> imgMaps = new HashMap<>();// ���̹�˷������Ӧ��ͼƬ
		imgMaps.put(Direction.U, Images.getImage(R.Tank.EnemyTank.E_T_1_U));
		imgMaps.put(Direction.D, Images.getImage(R.Tank.EnemyTank.E_T_1_D));
		imgMaps.put(Direction.L, Images.getImage(R.Tank.EnemyTank.E_T_1_L));
		imgMaps.put(Direction.R, Images.getImage(R.Tank.EnemyTank.E_T_1_R));
		imgMaps.put(Direction.LU, Images.getImage(R.Tank.EnemyTank.E_T_1_LU));
		imgMaps.put(Direction.LD, Images.getImage(R.Tank.EnemyTank.E_T_1_LD));
		imgMaps.put(Direction.RU, Images.getImage(R.Tank.EnemyTank.E_T_1_RU));
		imgMaps.put(Direction.RD, Images.getImage(R.Tank.EnemyTank.E_T_1_RD));
		reDecorateTank(imgMaps);
	}

	@Override
	public void move() {
		tankLocation = getTankLocation();
		int x = tankLocation.getX();
		int y = tankLocation.getY();
		switch (getDirction()) {
		case U:
			changeTankLocationY(y - speed);
			break;
		case L:
			changeTankLocationX(x - speed);
			break;
		case R:
			changeTankLocationX(x + speed);
			break;
		case D:
			changeTankLocationY(y + speed);
			break;
		case LU:
			changeTankLocationX(x - skewSpeed);
			changeTankLocationY(y - skewSpeed);
			break;
		case LD:
			changeTankLocationX(x - skewSpeed);
			changeTankLocationY(y + skewSpeed);
			break;
		case RU:
			changeTankLocationX(x + skewSpeed);
			changeTankLocationY(y - skewSpeed);
			break;
		case RD:
			changeTankLocationX(x + skewSpeed);
			changeTankLocationY(y + skewSpeed);
			break;
		case STOP:
			back();
		}
		if (randomTime() < 70) {
			fire();
		}

	}

	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		super.draw(g2d);
		checkCollision(g2d);
	}

	@Override
	public void fire() {
		int x = tankLocation.getX();
		int y = tankLocation.getY();
		Point point = adjustFireHead(getDirction(), x, y);
		ControlStudio.addEnemyBullets(new Bullet(point, getDirction()));
	}

	@Override
	public boolean checkCollision(Graphics2D g2d) {
		checkWall();
		checkTank();
		checkEdge();
		Vector<Bullet> tBullets = new Vector<>();
		// tBullets = ControlStudio.getEnemyBullets();
		for (Bullet bullet : ControlStudio.getMyBullets()) {
			tBullets.add(bullet);
		}
		for (Bullet bullet : tBullets) {
			if (this.getRect().intersects(bullet.getRect())) {
				curBlood -= bullet.getPower();
				// back();
				bullet.setLiveToFalse();// ʹ�ӵ���ʧ
				ControlStudio.removeMyBullets(bullet);// ������ͬʱ�������쳣,�������
				if (curBlood <= 0) {
					ControlStudio.removeEnemyTank(this);
					this.setLive(false);
				}
			}
		}

		return true;
	}

	/**
	 * �����������
	 */
	private void randomDirection() {
		int directionValue = randrom.nextInt(8);// 0~7����������
		switch (directionValue) {
		case U:
			// ControlStudio.removeEnemyTank(this);
			changeDirection(Direction.U);
			break;
		case D:
			changeDirection(Direction.D);
			break;
		case L:
			changeDirection(Direction.L);
			break;
		case Right:
			changeDirection(Direction.R);
			break;
		case LU:
			changeDirection(Direction.LU);
			break;
		case RU:
			changeDirection(Direction.RU);
			break;
		case LD:
			changeDirection(Direction.LD);
			break;
		case RD:
			changeDirection(Direction.RD);
			break;
		}
	}

	/**
	 * ÿ���з�̹�˽�ӵ���Լ�����������
	 */
	@Override
	public void run() {
		while (isLive()) {
			try {
				randomDirection();
				Thread.sleep(randomTime());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * �������0~2���ʱ��
	 * 
	 * @return
	 */
	private long randomTime() {
		int time = randrom.nextInt(2000);
		return time;
	}

}
