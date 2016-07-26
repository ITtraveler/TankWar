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
	protected int INIT_TANK_BLOOD = 1000;// 初始坦克血量
	protected int curBlood = 1000;// 当前的血量
	private boolean isLive = true;
	private Point tankLocation;// 坦克的坐标位置
	private int oX, oY;
	private int SPEED = 6;// 坦克速度
	private int SKEW_SPEED = 4;
	protected int TANK_WIDTH = 38, TANK_HEIGHT = 38;
	private boolean isUp, isDown, isLeft, isRight;
	private Image image;
	private Direction curDirection = Direction.U;// 初始化当前Tank为朝向方向
	protected Map<Direction, Image> imgMaps = new HashMap<>();// 存放坦克方向与对应的图片
	private List<BaseWall> wallList = new ArrayList<>();
	private List<MyTank> tankList = new ArrayList<>();
	private boolean haveFire;// 用于判断按一下发子弹，只发一个子弹
	/**
	 * 炮弹类型
	 */
	private static final int BULLET = 0;
	private static final int BIG_BULLET = 1;
	private int curBulletType = 0;// 当前的炮弹类型

	public MyTank() {
		initTank();
	}

	// 初始化坦克，一些所需的图片

	private void initTank() {
		image = Images.getImage(R.Tank.MyTank.IMG_MYTANK_U);// 初始使用的图片为朝上方向
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
	 * 重新为坦克设置八个方向的图片
	 */
	public void reDecorateTank(Map<Direction, Image> imgMaps) {
		image = imgMaps.get(Direction.U);// 初始使用的图片为朝上方向
		this.imgMaps = imgMaps;
	}

	/****************** 坦克的位置处理 ****************/

	/**
	 * 初始化坦克坐标位置
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
	 * 改变坦克的位置
	 * 
	 * @param point
	 */
	public void changeTankLocation(Point point) {
		tankLocation = point;
	}

	/**
	 * 重新设置x的坐标
	 * 
	 * @param x
	 */
	public void changeTankLocationX(int x) {
		tankLocation.setX(x);// 重新设置X坐标
	}

	/**
	 * 重新设置y的坐标
	 * 
	 * @param y
	 */
	public void changeTankLocationY(int y) {
		tankLocation.setY(y);// 重新设置X坐标
	}

	/**
	 * 得到坦克当前坐标
	 * 
	 * @return
	 */
	public Point getTankLocation() {
		return tankLocation;
	}

	/**
	 * 改变当前的方向
	 * 
	 * @param direction
	 */
	public void changeDirection(Direction direction) {
		curDirection = direction;
	}

	/**
	 * 得到当前的方向
	 * 
	 * @return
	 */
	public Direction getDirction() {
		return curDirection;
	}

	/********** 速度处理 **********/
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

	/***************** 坦克血量的处理 ******************/
	/**
	 * 初始化血量
	 * 
	 * @param blood
	 */
	public void setInitBlood(int blood) {
		this.curBlood = blood;

	}

	/**
	 * 
	 * 得到当前血量
	 * 
	 * @return
	 */

	@Override
	public int getBlood() {
		// TODO Auto-generated method stub
		return curBlood;
	}

	/**
	 * 血量减少处理
	 * 
	 * @param hurtValue
	 *            伤害值
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
	 * 血量增加，用于补血治疗
	 * 
	 * @param treatValue
	 *            治疗值
	 */
	@Override
	public void increaseBlood(int treatValue) {
		// 进行血量控制，使其不大于初始值
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

	/***************** 坦克键盘事件的操作 *******************/

	/**
	 * tank键盘事件
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
			if (!haveFire) {// 使每次按只发出一个子弹
				print("fire.......");
				fire();
				haveFire = true;
			}
			break;
		case KeyEvent.VK_F:
			if (!haveFire && GameData.score >= BigBullet.DEC_SCORE) {// 使每次按只发出一个子弹
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
	 * 键盘按钮释放事件
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
		if (isLeft && !isRight && !isUp && !isDown) {// 左滑
			curDirection = Direction.L;
			changeTankLocationX(tankLocation.getX() - SPEED);
		} else if (!isLeft && isRight && !isUp && !isDown) {// 右滑
			curDirection = Direction.R;
			changeTankLocationX(tankLocation.getX() + SPEED);

		} else if (!isLeft && !isRight && isUp && !isDown) {// 上滑
			curDirection = Direction.U;
			changeTankLocationY(tankLocation.getY() - SPEED);

		} else if (!isLeft && !isRight && !isUp && isDown) {// 下滑
			curDirection = Direction.D;
			changeTankLocationY(tankLocation.getY() + SPEED);

		} else if (isLeft && !isRight && isUp && !isDown) {// 左滑+上滑

			curDirection = Direction.LU;
			changeTankLocationX(tankLocation.getX() - SKEW_SPEED);
			changeTankLocationY(tankLocation.getY() - SKEW_SPEED);

		} else if (isLeft && !isRight && !isUp && isDown) {// 左滑+下滑

			curDirection = Direction.LD;
			changeTankLocationX(tankLocation.getX() - SKEW_SPEED);
			changeTankLocationY(tankLocation.getY() + SKEW_SPEED);

		} else if (!isLeft && isRight && isUp && !isDown) {// 右滑+上滑
			curDirection = Direction.RU;

			changeTankLocationX(tankLocation.getX() + SKEW_SPEED);
			changeTankLocationY(tankLocation.getY() - SKEW_SPEED);

		} else if (!isLeft && isRight && !isUp && isDown) {// 右滑+下滑
			curDirection = Direction.RD;
			changeTankLocationX(tankLocation.getX() + SKEW_SPEED);
			changeTankLocationY(tankLocation.getY() + SKEW_SPEED);
		}

	}

	/**
	 * 如果产生碰撞的情况，退到原来的位置。
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

	/************* 坦克的绘制过程，包括处理键盘事件的改变，对图形绘制的更新 *****************/

	/**
	 * 绘制出坦克
	 * 
	 * @param g2d
	 */
	public void draw(Graphics2D g2d) {
		// 检查碰撞

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

	/************** 坦克对墙体和其他坦克的处理 *******************/

	/**
	 * 将所有的墙都一次性添加进来，不过将会覆盖掉之前add过的所有的墙。
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

	// 坦克的
	public void putAllTanks(List<MyTank> tankList) {
		this.tankList = tankList;
	}

	public void addTank(MyTank tank) {
		tankList.add(tank);
	}

	public void removeTank(MyTank tank) {
		tankList.remove(tank);
	}

	/* 不直接传入Collision的原因，是为了让不同的碰撞可以有不同的处理，不然所有碰撞处理都一样了 */
	private List<Collision> cList = new ArrayList<>();

	public void addCollisionObject(Collision collision) {
		cList.add(collision);
	}

	/**
	 * 超级添加碰撞体,添加在链表末尾，其实所有实现collision接口的都能添加进来
	 * 
	 * @param cList
	 */
	public <T> void putAllCollisionObject(List<T> cList) {
		// this.cList = cList;
		for (T collision : cList)
			addCollisionObject((Collision) collision);
	}

	/**
	 * 碰撞检测
	 */

	@Override
	public boolean checkCollision(Graphics2D g2d) {
		checkWall();
		checkTank();
		for (Collision c : cList) {
			if (this.getRect().intersects(c.getRect())) {// 如果坦克的位置在墙上且如果墙不可以通过
				back();
				cList.clear();// 清空
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
	 * 墙体碰撞檢測
	 */
	protected void checkWall() {
		// 对于墙体
		for (BaseWall wall : wallList) {
			if (this.getRect().intersects(wall.getRect()) && !wall.isCanPass()) {// 如果坦克的位置在墙上且如果墙不可以通过
				back();
				// print("wall");
			}
		}
	}

	/**
	 * tank碰撞之間檢測
	 */
	protected void checkTank() {
		// 对于坦克
		for (MyTank tank : tankList) {
			if (this.getRect().intersects(tank.getRect())) {// 如果坦克的位置在墙上且如果墙不可以通过
				back();

				// print("tank");
			}
		}
	}

	/**
	 * 敌方子弹打我，如果继承此类需要注意重新此方法
	 */
	protected void checkBullet(Graphics2D g2d) {
		Vector<Bullet> tBullets = new Vector<>();
		// tBullets = ControlStudio.getEnemyBullets();
		for (Bullet bullet : ControlStudio.getEnemyBullets()) {
			tBullets.add(bullet);
		}
		// 自己被子弹击中处理
		for (Bullet bullet : tBullets) {
			// 确定此 Rectangle 是否与指定的 Rectangle 相交。
			if (this.getRect().intersects(bullet.getRect())) {
				// back();
				bullet.setLiveToFalse();// 使子弹消失,这一步可以不要
				print("collision");
				// ControlStudio.removeMyTank(this);
				// tBullets.remove(bullet);// 会报异常，因为因为循环还没结束，就移除此子弹当然就报异常了
				ControlStudio.removeEnemyBullets(bullet);
				decreaseBlood(bullet.getPower());// 使受伤，血量减少
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

	// 发子弹
	public void fire() {
		// 处理子弹在前头发射
		int x = tankLocation.getX();
		int y = tankLocation.getY();
		Point point = adjustFireHead(curDirection, x, y);// 得到正确的子弹发射口
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
	 * 改变子弹的类型
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
	 * 调整子弹发出的位置，使子弹在坦克的头部
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
