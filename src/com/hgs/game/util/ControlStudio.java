package com.hgs.game.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.hgs.game.Point;
import com.hgs.game.bullet.Bullet;
import com.hgs.game.resource.R.Home;
import com.hgs.game.tank.EnemyTank;
import com.hgs.game.tank.MyTank;
import com.hgs.game.wall.BaseWall;
import com.hgs.game.wall.WallFactory;

/**
 * 控制台，管理墙、我方坦克、敌方坦克、子弹等等
 * 
 * @author Administrator
 *
 */

public class ControlStudio {

	private static List<BaseWall> wallList = new ArrayList<>();
	private static List<BaseWall> edgeWalls = new ArrayList<>();
	private static List<MyTank> mTanks = new ArrayList<>();
	private static Vector<EnemyTank> eTanks = new Vector<>();
	private static Vector<Bullet> myBullets = new Vector<Bullet>();
	private static Vector<Bullet> enemyBullets = new Vector<Bullet>();
	public static BaseWall home;
	static {
		initEdge();
	}

	// 初始化边界
	private static void initEdge() {
		int wallThickness = 40;
		createWall(-wallThickness, -wallThickness, 940, wallThickness);
		createWall(-wallThickness, -wallThickness, wallThickness, 680);
		createWall(880, -wallThickness, wallThickness, 680);
		createWall(-wallThickness, 600, 940, wallThickness);
	}

	// 创建墻
	private static void createWall(int x, int y, int width, int height) {
		BaseWall bWall = new WallFactory().createBaseWall();// 工厂生产墙体
		bWall.setWallLocation(new Point(x, y));
		bWall.setWallWidthAndHeight(width, height);
		edgeWalls.add(bWall);
	}

	public static void addMyTank(MyTank mTank) {
		mTanks.add(mTank);
	}

	public static void addEnemyTank(EnemyTank eTank) {
		eTanks.add(eTank);
	}

	public static void addWall(BaseWall baseWall) {
		wallList.add(baseWall);
	}

	public static void removeMyTank(MyTank mTank) {
		mTanks.remove(mTank);
	}

	public static void removeEnemyTank(EnemyTank mTank) {
		boolean haveRemove = false;
		synchronized (eTanks) {
			haveRemove = eTanks.remove(mTank);
		}
		if (haveRemove) {
			System.out.println("remove...");
			// 增加得分
			GameData.score += mTank.value;
			GameData.totalEnemyTankNum++;
			GameData.residueNum--;
		}
	}

	public static void removeWall(BaseWall baseWall) {
		synchronized (wallList) {
			wallList.remove(baseWall);
		}
	}

	public static List<BaseWall> getWallList() {
		return wallList;
	}

	public static List<BaseWall> getEdgeWalls() {
		return edgeWalls;
	}

	public static List<MyTank> getMyTanks() {
		return mTanks;
	}

	public static Vector<EnemyTank> getEnemyTanks() {
		return eTanks;
	}

	public static void setWallList(List<BaseWall> wallList) {
		ControlStudio.wallList = wallList;
	}

	public static void setMyTanks(List<MyTank> mTanks) {
		ControlStudio.mTanks = mTanks;
	}

	public static void setEnemyTanks(Vector<EnemyTank> eTanks) {
		ControlStudio.eTanks = eTanks;
	}

	// MyBullets
	public static List<Bullet> getMyBullets() {
		return myBullets;
	}

	public static void setMyBullets(Vector<Bullet> bullets) {
		ControlStudio.myBullets = bullets;
	}

	public static void addMyBullets(Bullet bullet) {
		myBullets.add(bullet);
	}

	public static void removeMyBullets(Bullet bullet) {
		synchronized (myBullets) {
			myBullets.remove(bullet);
		}
	}

	/**
	 * 删除所有我方子弹
	 */
	public static void clearMyBullets() {
		enemyBullets.clear();
	}

	// EnemyBullets
	public static Vector<Bullet> getEnemyBullets() {
		return enemyBullets;
	}

	public static void setEnemyBullets(Vector<Bullet> enemyBullets) {
		ControlStudio.enemyBullets = enemyBullets;
	}

	public static void addEnemyBullets(Bullet bullet) {
		enemyBullets.add(bullet);
	}

	public static void removeEnemyBullets(Bullet bullet) {
		synchronized (enemyBullets) {
			enemyBullets.remove(bullet);
		}
	}

	/**
	 * 删除所有敌方子弹
	 */
	public static void clearEnemyBullets() {
		enemyBullets.clear();
	}

	/**
	 * 清理所以数据
	 */
	public static void clearAll() {
		enemyBullets.clear();
		myBullets.clear();
		eTanks.clear();
		wallList.clear();
	}
}
