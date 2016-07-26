package com.hgs.game.tank;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.hgs.game.Blood;
import com.hgs.game.Collision;
import com.hgs.game.Control;
import com.hgs.game.Point;
import com.hgs.game.Speed;
import com.hgs.game.bullet.BigBullet;
import com.hgs.game.bullet.Bullet;
import com.hgs.game.bullet.Explode;
import com.hgs.game.resource.Direction;
import com.hgs.game.resource.R;
import com.hgs.game.util.ControlStudio;
import com.hgs.game.util.GameData;
import com.hgs.game.util.Images;
import com.hgs.game.wall.BaseWall;

public class MyTank implements Speed, Collision, Blood, Control {
	protected int INIT_TANK_BLOOD = 1000;// ��ʼ̹��Ѫ��
	protected int curBlood = 1000;// ��ǰ��Ѫ��
	private boolean isLive = true;
	private Point tankLocation;// ̹�˵�����λ��
	private int oX, oY;
	private int SPEED = 6;// ̹���ٶ�
	private int SKEW_SPEED = 4;
	protected int TANK_WIDTH = 38, TANK_HEIGHT = 38;
	private boolean isUp, isDown, isLeft, isRight;
	private Image image;
	private Direction curDirection = Direction.U;// ��ʼ����ǰTankΪ������
	protected Map<Direction, Image> imgMaps = new HashMap<>();// ���̹�˷������Ӧ��ͼƬ
	private List<BaseWall> wallList = new ArrayList<>();
	private List<MyTank> tankList = new ArrayList<>();
	private boolean haveFire;// �����жϰ�һ�·��ӵ���ֻ��һ���ӵ�
	/**
	 * �ڵ�����
	 */
	private static final int BULLET = 0;
	private static final int BIG_BULLET = 1;
	private int curBulletType = 0;// ��ǰ���ڵ�����

	public MyTank() {
		initTank();
	}

	// ��ʼ��̹�ˣ�һЩ�����ͼƬ

	private void initTank() {
		image = Images.getImage(R.Tank.MyTank.IMG_MYTANK_U);// ��ʼʹ�õ�ͼƬΪ���Ϸ���
		imgMaps.put(Direction.U, Images.getImage(R.Tank.MyTank.IMG_MYTANK_U));
		imgMaps.put(Direction.D, Images.getImage(R.Tank.MyTank.IMG_MYTANK_D));
		imgMaps.put(Direction.L, Images.getImage(R.Tank.MyTank.IMG_MYTANK_L));
		imgMaps.put(Direction.R, Images.getImage(R.Tank.MyTank.IMG_MYTANK_R));
		imgMaps.put(Direction.LU, Images.getImage(R.Tank.MyTank.IMG_MYTANK_LU));
		imgMaps.put(Direction.LD, Images.getImage(R.Tank.MyTank.IMG_MYTANK_LD));
		imgMaps.put(Direction.RU, Images.getImage(R.Tank.MyTank.IMG_MYTANK_RU));
		imgMaps.put(Direction.RD, Images.getImage(R.Tank.MyTank.IMG_MYTANK_RD));
	}

	/**
	 * ����Ϊ̹�����ð˸������ͼƬ
	 */
	public void reDecorateTank(Map<Direction, Image> imgMaps) {
		image = imgMaps.get(Direction.U);// ��ʼʹ�õ�ͼƬΪ���Ϸ���
		this.imgMaps = imgMaps;
	}

	/****************** ̹�˵�λ�ô��� ****************/

	/**
	 * ��ʼ��̹������λ��
	 * 
	 * @param point
	 */
	public void setInitLocation(Point point) {
		tankLocation = point;
		oX = point.getX();
		oY = point.getY();
		// oldTankLocation = point;
	}

	/**
	 * �ı�̹�˵�λ��
	 * 
	 * @param point
	 */
	public void changeTankLocation(Point point) {
		tankLocation = point;
	}

	/**
	 * ��������x������
	 * 
	 * @param x
	 */
	public void changeTankLocationX(int x) {
		tankLocation.setX(x);// ��������X����
	}

	/**
	 * ��������y������
	 * 
	 * @param y
	 */
	public void changeTankLocationY(int y) {
		tankLocation.setY(y);// ��������X����
	}

	/**
	 * �õ�̹�˵�ǰ����
	 * 
	 * @return
	 */
	public Point getTankLocation() {
		return tankLocation;
	}

	/**
	 * �ı䵱ǰ�ķ���
	 * 
	 * @param direction
	 */
	public void changeDirection(Direction direction) {
		curDirection = direction;
	}

	/**
	 * �õ���ǰ�ķ���
	 * 
	 * @return
	 */
	public Direction getDirction() {
		return curDirection;
	}

	/********** �ٶȴ��� **********/
	@Override
	public void setSpeed(int speed) {
		this.SPEED = speed;
	}

	@Override
	public int getSpeed() {
		return SPEED;
	}

	@Override
	public void setSkewSpeed(int skewSpeed) {
		this.SKEW_SPEED = skewSpeed;
	}

	@Override
	public int getSkewSpeed() {
		return SKEW_SPEED;
	}

	/***************** ̹��Ѫ���Ĵ��� ******************/
	/**
	 * ��ʼ��Ѫ��
	 * 
	 * @param blood
	 */
	public void setInitBlood(int blood) {
		this.curBlood = blood;

	}

	/**
	 * 
	 * �õ���ǰѪ��
	 * 
	 * @return
	 */

	@Override
	public int getBlood() {
		// TODO Auto-generated method stub
		return curBlood;
	}

	/**
	 * Ѫ�����ٴ���
	 * 
	 * @param hurtValue
	 *            �˺�ֵ
	 */
	@Override
	public void decreaseBlood(int hurtValue) {
		if (curBlood > 100) {
			curBlood -= hurtValue;
		} else {
			System.out.println("dead...Game Over!");

			isLive = false;
		}
	}

	/**
	 * Ѫ�����ӣ����ڲ�Ѫ����
	 * 
	 * @param treatValue
	 *            ����ֵ
	 */
	@Override
	public void increaseBlood(int treatValue) {
		// ����Ѫ�����ƣ�ʹ�䲻���ڳ�ʼֵ
		if (curBlood < (INIT_TANK_BLOOD - treatValue)) {
			curBlood += treatValue;
		} else {
			curBlood = INIT_TANK_BLOOD;
		}
	}

	@Override
	public boolean isLive() {
		// TODO Auto-generated method stub
		return isLive;
	}

	@Override
	public void setLive(boolean live) {
		this.isLive = live;
	}

	/***************** ̹�˼����¼��Ĳ��� *******************/

	/**
	 * tank�����¼�
	 * 
	 * @param keyEvent
	 */
	@Override
	public void keyPressed(KeyEvent keyEvent) {
		int key = keyEvent.getKeyCode();
		switch (key) {
		case KeyEvent.VK_UP:
			isUp = true;
			break;
		case KeyEvent.VK_DOWN:
			isDown = true;
			break;
		case KeyEvent.VK_LEFT:
			isLeft = true;
			break;
		case KeyEvent.VK_RIGHT:
			isRight = true;
			break;
		case KeyEvent.VK_D:
			if (!haveFire) {// ʹÿ�ΰ�ֻ����һ���ӵ�
				print("fire.......");
				fire();
				haveFire = true;
			}
			break;
		case KeyEvent.VK_F:
			if (!haveFire && GameData.score >= BigBullet.DEC_SCORE) {// ʹÿ�ΰ�ֻ����һ���ӵ�
				print("Bigfire.......");
				changeBullet(BIG_BULLET);
				fire();
				changeBullet(BULLET);
				GameData.score -= BigBullet.DEC_SCORE;
				haveFire = true;
			}
			break;
		}
	}

	/**
	 * ���̰�ť�ͷ��¼�
	 * 
	 * @param keyEvent
	 */
	@Override
	public void keyReleased(KeyEvent keyEvent) {
		int key = keyEvent.getKeyCode();
		switch (key) {
		case KeyEvent.VK_UP:
			isUp = false;
			break;
		case KeyEvent.VK_DOWN:
			isDown = false;
			break;
		case KeyEvent.VK_LEFT:
			isLeft = false;
			break;
		case KeyEvent.VK_RIGHT:
			isRight = false;
			break;
		case KeyEvent.VK_D:
			haveFire = false;
			break;
		case KeyEvent.VK_F:
			haveFire = false;
			break;
		}
	}

	@Override
	public void move() {
		oX = tankLocation.getX();
		oY = tankLocation.getY();
		if (isLeft && !isRight && !isUp && !isDown) {// ��
			curDirection = Direction.L;
			changeTankLocationX(tankLocation.getX() - SPEED);
		} else if (!isLeft && isRight && !isUp && !isDown) {// �һ�
			curDirection = Direction.R;
			changeTankLocationX(tankLocation.getX() + SPEED);

		} else if (!isLeft && !isRight && isUp && !isDown) {// �ϻ�
			curDirection = Direction.U;
			changeTankLocationY(tankLocation.getY() - SPEED);

		} else if (!isLeft && !isRight && !isUp && isDown) {// �»�
			curDirection = Direction.D;
			changeTankLocationY(tankLocation.getY() + SPEED);

		} else if (isLeft && !isRight && isUp && !isDown) {// ��+�ϻ�

			curDirection = Direction.LU;
			changeTankLocationX(tankLocation.getX() - SKEW_SPEED);
			changeTankLocationY(tankLocation.getY() - SKEW_SPEED);

		} else if (isLeft && !isRight && !isUp && isDown) {// ��+�»�

			curDirection = Direction.LD;
			changeTankLocationX(tankLocation.getX() - SKEW_SPEED);
			changeTankLocationY(tankLocation.getY() + SKEW_SPEED);

		} else if (!isLeft && isRight && isUp && !isDown) {// �һ�+�ϻ�
			curDirection = Direction.RU;

			changeTankLocationX(tankLocation.getX() + SKEW_SPEED);
			changeTankLocationY(tankLocation.getY() - SKEW_SPEED);

		} else if (!isLeft && isRight && !isUp && isDown) {// �һ�+�»�
			curDirection = Direction.RD;
			changeTankLocationX(tankLocation.getX() + SKEW_SPEED);
			changeTankLocationY(tankLocation.getY() + SKEW_SPEED);
		}

	}

	/**
	 * ���������ײ��������˵�ԭ����λ�á�
	 */
	public void back() {
		switch (curDirection) {
		case U:
			changeTankLocationY(tankLocation.getY() + SPEED);
			break;
		case L:
			changeTankLocationX(tankLocation.getX() + SPEED);
			break;
		case R:
			changeTankLocationX(tankLocation.getX() - SPEED);
			break;
		case D:
			changeTankLocationY(tankLocation.getY() - SPEED);
			break;
		case LU:
			changeTankLocationX(tankLocation.getX() + SKEW_SPEED);
			changeTankLocationY(tankLocation.getY() + SKEW_SPEED);
			break;
		case LD:
			changeTankLocationX(tankLocation.getX() + SKEW_SPEED);
			changeTankLocationY(tankLocation.getY() - SKEW_SPEED);
			break;
		case RU:
			changeTankLocationX(tankLocation.getX() - SKEW_SPEED);
			changeTankLocationY(tankLocation.getY() + SKEW_SPEED);
			break;
		case RD:
			changeTankLocationX(tankLocation.getX() - SKEW_SPEED);
			changeTankLocationY(tankLocation.getY() - SKEW_SPEED);
			break;
		case STOP:
			break;
		}

		// tankLocation.setX(oX);
		// tankLocation.setY(oY);
	}

	/************* ̹�˵Ļ��ƹ��̣�������������¼��ĸı䣬��ͼ�λ��Ƶĸ��� *****************/

	/**
	 * ���Ƴ�̹��
	 * 
	 * @param g2d
	 */
	public void draw(Graphics2D g2d) {
		// �����ײ

		move();
		switch (curDirection) {
		case U:
			image = imgMaps.get(Direction.U);
			drawTank(g2d);
			break;
		case L:
			image = imgMaps.get(Direction.L);
			drawTank(g2d);
			break;
		case R:
			image = imgMaps.get(Direction.R);
			drawTank(g2d);
			break;
		case D:
			image = imgMaps.get(Direction.D);
			drawTank(g2d);
			break;
		case LU:
			image = imgMaps.get(Direction.LU);
			drawTank(g2d);
			break;
		case LD:
			image = imgMaps.get(Direction.LD);
			drawTank(g2d);
			break;
		case RU:
			image = imgMaps.get(Direction.RU);
			drawTank(g2d);
			break;
		case RD:
			image = imgMaps.get(Direction.RD);
			drawTank(g2d);
			break;
		case STOP:
			break;
		}
		checkCollision(g2d);
	}

	private void drawTank(Graphics2D g) {
		g.drawImage(image, tankLocation.getX(), tankLocation.getY(),TANK_WIDTH,TANK_HEIGHT, null);
	}

	/************** ̹�˶�ǽ�������̹�˵Ĵ��� *******************/

	/**
	 * �����е�ǽ��һ������ӽ������������Ḳ�ǵ�֮ǰadd�������е�ǽ��
	 * 
	 * @param wallList
	 */
	public void putAllWalls(List<BaseWall> wallList) {
		this.wallList = wallList;
	}

	public void addWall(BaseWall baseWall) {
		wallList.add(baseWall);
	}

	public void removeWall(BaseWall baseWall) {
		wallList.remove(baseWall);
	}

	// ̹�˵�
	public void putAllTanks(List<MyTank> tankList) {
		this.tankList = tankList;
	}

	public void addTank(MyTank tank) {
		tankList.add(tank);
	}

	public void removeTank(MyTank tank) {
		tankList.remove(tank);
	}

	/* ��ֱ�Ӵ���Collision��ԭ����Ϊ���ò�ͬ����ײ�����в�ͬ�Ĵ�����Ȼ������ײ����һ���� */
	private List<Collision> cList = new ArrayList<>();

	public void addCollisionObject(Collision collision) {
		cList.add(collision);
	}

	/**
	 * ���������ײ��,���������ĩβ����ʵ����ʵ��collision�ӿڵĶ�����ӽ���
	 * 
	 * @param cList
	 */
	public <T> void putAllCollisionObject(List<T> cList) {
		// this.cList = cList;
		for (T collision : cList)
			addCollisionObject((Collision) collision);
	}

	/**
	 * ��ײ���
	 */

	@Override
	public boolean checkCollision(Graphics2D g2d) {
		checkWall();
		checkTank();
		for (Collision c : cList) {
			if (this.getRect().intersects(c.getRect())) {// ���̹�˵�λ����ǽ�������ǽ������ͨ��
				back();
				cList.clear();// ���
				return true;
			}
		}
		checkBullet(g2d);
		checkEdge();
		return false;
	}

	public void checkEdge() {
		int width = 895;// 8
		int height = 660;// 6

		int x = tankLocation.getX();
		int y = tankLocation.getY();

		if (x < 0)
			changeTankLocationX(0);
		if (y < 0)
			changeTankLocationY(0);
		if (x + TANK_WIDTH > width)
			changeTankLocationX(width - TANK_WIDTH);
		if (y + TANK_HEIGHT > height)
			changeTankLocationY(height - TANK_HEIGHT);

	}

	/**
	 * ǽ����ײ�z�y
	 */
	protected void checkWall() {
		// ����ǽ��
		for (BaseWall wall : wallList) {
			if (this.getRect().intersects(wall.getRect()) && !wall.isCanPass()) {// ���̹�˵�λ����ǽ�������ǽ������ͨ��
				back();
				// print("wall");
			}
		}
	}

	/**
	 * tank��ײ֮�g�z�y
	 */
	protected void checkTank() {
		// ����̹��
		for (MyTank tank : tankList) {
			if (this.getRect().intersects(tank.getRect())) {// ���̹�˵�λ����ǽ�������ǽ������ͨ��
				back();

				// print("tank");
			}
		}
	}

	/**
	 * �з��ӵ����ң�����̳д�����Ҫע�����´˷���
	 */
	protected void checkBullet(Graphics2D g2d) {
		Vector<Bullet> tBullets = new Vector<>();
		// tBullets = ControlStudio.getEnemyBullets();
		for (Bullet bullet : ControlStudio.getEnemyBullets()) {
			tBullets.add(bullet);
		}
		// �Լ����ӵ����д���
		for (Bullet bullet : tBullets) {
			// ȷ���� Rectangle �Ƿ���ָ���� Rectangle �ཻ��
			if (this.getRect().intersects(bullet.getRect())) {
				// back();
				bullet.setLiveToFalse();// ʹ�ӵ���ʧ,��һ�����Բ�Ҫ
				print("collision");
				// ControlStudio.removeMyTank(this);
				// tBullets.remove(bullet);// �ᱨ�쳣����Ϊ��Ϊѭ����û���������Ƴ����ӵ���Ȼ�ͱ��쳣��
				ControlStudio.removeEnemyBullets(bullet);
				decreaseBlood(bullet.getPower());// ʹ���ˣ�Ѫ������
				GameData.MyTankBlood = curBlood;
				Point point = bullet.getLocation();
				int x = point.getX();
				int y = point.getY();
				new Explode().draw(g2d,x,y);
			}
		}

	}

	@Override
	public Rectangle getRect() {
		return new Rectangle(tankLocation.getX(), tankLocation.getY(),
				TANK_HEIGHT, TANK_WIDTH);
	}

	// ���ӵ�
	public void fire() {
		// �����ӵ���ǰͷ����
		int x = tankLocation.getX();
		int y = tankLocation.getY();
		Point point = adjustFireHead(curDirection, x, y);// �õ���ȷ���ӵ������
		if (curBulletType == BULLET) {
			bullet(point);
		} else if (curBulletType == BIG_BULLET) {
			bigBullet(point);
		}
		System.out.println("ok fire.");
	}

	private void bullet(Point point) {
		ControlStudio.addMyBullets(new Bullet(point, curDirection));
	}

	private void bigBullet(Point point) {
		List<Bullet> bs = new BigBullet(point, curDirection).fire();
		for (Bullet b : bs)
			ControlStudio.addMyBullets(b);
	}

	/**
	 * �ı��ӵ�������
	 * 
	 * @param BULLET_TYPE
	 */
	public void changeBullet(int BULLET_TYPE) {
		switch (BULLET_TYPE) {
		case BULLET:
			curBulletType = BULLET;
			break;
		case BIG_BULLET:
			curBulletType = BIG_BULLET;
			break;
		}
	}

	/**
	 * �����ӵ�������λ�ã�ʹ�ӵ���̹�˵�ͷ��
	 * 
	 * @param curDirection
	 * @param x
	 * @param y
	 * @return
	 */
	protected Point adjustFireHead(Direction curDirection, int x, int y) {

		switch (curDirection) {
		case U:
			x += TANK_WIDTH / 2 - 4;
			y -= 10;
			break;
		case L:
			x -= 10;
			y += TANK_HEIGHT / 2 - 5;
			break;
		case R:
			x += TANK_WIDTH + 5;
			y += TANK_HEIGHT / 2 - 5;
			break;
		case D:
			x += TANK_WIDTH / 2 - 5;
			y += TANK_HEIGHT;
			break;
		case LU:
			
			break;
		case LD:
			y += TANK_HEIGHT - 10;
			break;
		case RU:
			x += TANK_WIDTH - 10;

			break;
		case RD:
			x += TANK_WIDTH;
			y += TANK_HEIGHT;
			break;
		default:
			break;
		}
		return new Point(x, y);
	}

	private void print(String s) {
		System.out.println(s);
	}

}
