package com.hgs.game.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.Vector;

import javax.swing.JPanel;

import com.hgs.game.Point;
import com.hgs.game.bullet.Bullet;
import com.hgs.game.resource.R;
import com.hgs.game.tank.EnemyTank;
import com.hgs.game.tank.MyTank;
import com.hgs.game.util.ControlStudio;
import com.hgs.game.util.GameData;
import com.hgs.game.util.Images;
import com.hgs.game.wall.BaseWall;
import com.hgs.game.wall.Grassland;

public class GamePanel3 extends JPanel {
	private MyTank mTank;
	private List<BaseWall> walls = new ArrayList<>();
	private Vector<EnemyTank> eTanks = new Vector<>();
	private GameMap1 gMap;// 地图
	// private int count = 0;// 统计打了多少关
	// private int eTankNum = 10;
	private Image background;
	private GameRule gameRule = new GameRule();
	private int killCount = 0;
	private int oldKillCount = 0;
	private long oldTime, curTime, betweenTime;
	private boolean haveRecord = false;// 是否记录

	public GamePanel3(MyTank tank) {
		this.mTank = tank;
		gMap = new GameMap1();
		initMyTank();
		initEnemyTank();
		background = Images.getImage(R.Background.BACKGROUND);
		System.out.println("tank:" + tank.isLive());
	}

	// 初始化我的坦克
	private void initMyTank() {
		reInitMyTank();
		mTank.putAllWalls(ControlStudio.getWallList());
		for (BaseWall baseWall : ControlStudio.getEdgeWalls()) {
			mTank.addWall(baseWall);
		}
	}

	/**
	 * 重新初始化我的坦克，包括位置，血量
	 */
	private void reInitMyTank() {
		mTank.setInitLocation(new Point(320, 550));
		mTank.setInitBlood(1000);
		GameData.MyTankBlood = 1000;
	}

	// 初始化敵方TANK
	private void initEnemyTank() {
		oldKillCount = GameData.totalEnemyTankNum;
		// 初始化地图
		gMap.randMap();
		gMap.initMap();
		gameRule.initGame();
		// 给敌方坦克生命
		int eTankNum = ControlStudio.getEnemyTanks().size();
		for (int i = 0; i < eTankNum; i++) {
			EnemyTank eTank = ControlStudio.getEnemyTanks().get(i);
			eTank.addTank(mTank);
			eTank.putAllWalls(ControlStudio.getWallList());
			for (BaseWall baseWall : ControlStudio.getEdgeWalls()) {
				eTank.addWall(baseWall);
			}

			new Thread(eTank).start();
		}
		// totalETank = GameRule.totalTankNum;
		reInitMyTank();
		GameData.residueNum = gameRule.getTotalTankNum();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		// g2d.drawImage(background,0,0,1000,700,null);
		if (mTank.isLive()) {// 通过我的坦克是否活着判断游戏是否失败
			drawGame(g2d);
		}
	}

	/**
	 * 游戏的绘制
	 * 
	 * @param g2d
	 */
	private void drawGame(Graphics2D g2d) {
		mTank.draw(g2d);// 先画坦克，这样草坪就能遮住坦克
		mTank.putAllCollisionObject(ControlStudio.getEnemyTanks());
		// 更新绘制敌方坦克
		updateEnemyTank(g2d);
		// 绘制我方子弹
		for (Bullet bullet : ControlStudio.getMyBullets()) {
			bullet.draw(g2d);
		}
		// 绘制敌方子弹
		for (Bullet bullet : ControlStudio.getEnemyBullets()) {
			bullet.draw(g2d);
		}
		// 画墙
		// List<BaseWall> walls = new ArrayList<>();
		walls.clear();
		for (BaseWall baseWall : ControlStudio.getWallList()) {
			walls.add(baseWall);
		}
		for (BaseWall baseWall : walls) {
			baseWall.draw(g2d);
			if (!(baseWall instanceof Grassland)) {
				baseWall.checkCollision(g2d);
			}
		}
		for (BaseWall baseWall : ControlStudio.getEdgeWalls()) {
			baseWall.checkCollision(g2d);
		}
		// 游戏战况
		GameData.showGameData(g2d);
		// if(GameData.enemyTankNum)
		killCount = GameData.totalEnemyTankNum - oldKillCount;
		// System.out.println(gameRule.getTotalTankNum());
		if (GameData.residueNum == 0) {// 剩余杀敌数为0，则进入下一关。
			if (!haveRecord) {
				oldTime = System.currentTimeMillis();
				haveRecord = true;
			}
			curTime = System.currentTimeMillis();
			betweenTime = curTime - oldTime;
			if (betweenTime <= 2000) {
				g2d.setFont(new Font("华文彩云", Font.BOLD, 60));
				g2d.setColor(Color.BLACK);
				g2d.drawString("Next One", 200, 200);
			} else {
				haveRecord = false;
				initEnemyTank();
			}
		} else {
			if (gameRule.getControlNum() > ControlStudio.getEnemyTanks().size()
					&& killCount + gameRule.getControlNum() <= gameRule
							.getTotalTankNum()) {
				createAETank();
			}
		}

		if (!ControlStudio.home.isLive()) {
			System.out.println("deals");
			mTank.setLive(false);
		}
	}

	private void createAETank() {
		EnemyTank enemyTank = gameRule.addRandTank(3);

		for (BaseWall baseWall : ControlStudio.getEdgeWalls()) {
			enemyTank.addWall(baseWall);
		}
		new Thread(enemyTank).start();
	}

	/**
	 * 更新敌方坦克数量
	 * 
	 * @param g2d
	 */
	private void updateEnemyTank(Graphics2D g2d) {
		Vector<EnemyTank> curETanks = new Vector<>();
		// tBullets = ControlStudio.getEnemyBullets();
		for (EnemyTank enemyTank : ControlStudio.getEnemyTanks()) {
			curETanks.add(enemyTank);
		}
		for (EnemyTank enemyTank : curETanks) {
			enemyTank.putAllWalls(ControlStudio.getWallList());
			enemyTank.addTank(mTank);
			enemyTank.draw(g2d);
		}
	}
}
