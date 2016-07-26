package com.hgs.game.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;

import com.hgs.game.Point;
import com.hgs.game.bullet.Bullet;
import com.hgs.game.tank.EnemyTank;
import com.hgs.game.tank.EnemyTankFactory;
import com.hgs.game.tank.MyTank;
import com.hgs.game.util.ControlStudio;
import com.hgs.game.util.GameData;
import com.hgs.game.wall.BaseWall;
import com.hgs.game.wall.BrickWall;
import com.hgs.game.wall.FixMan;
import com.hgs.game.wall.River;
import com.hgs.game.wall.WallFactory;

public class GamePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<BaseWall> wallList = new ArrayList<>();
	private Vector<EnemyTank> etanks = new Vector<>();
	private EnemyTankFactory enemyTankFactory;
	private MyTank mTank;// 我控制的主要坦克
	private EnemyTank eTank;
	private WallFactory wallFactory;

	public GamePanel(MyTank mTank) {
		enemyTankFactory = new EnemyTankFactory();
		wallFactory = new WallFactory();
		// 我控制的坦克
		this.mTank = mTank;
		mTank.setInitLocation(new Point(200, 200));
		createEnemyTank();

		BrickWall brick = new WallFactory().createBrickWall();
		brick.setWallLocation(new Point(400, 200));
		wallList.add(brick);
		ControlStudio.addWall(brick);
	}

	public void createEnemyTank() {
		// 敌方坦克生成
		ControlStudio.setEnemyTanks(enemyTankFactory.batchProcess(15));
		etanks = ControlStudio.getEnemyTanks();
		// 给敌方坦克生命
		for (int i = 0; i < etanks.size(); i++) {
			eTank = etanks.get(i);
			eTank.setInitLocation(new Point(300, 10));
			new Thread(eTank).start();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		initWall(g2d);

		updateEnemyTank(g2d);
		mTank.putAllWalls(wallList);
		mTank.putAllCollisionObject(etanks);
		mTank.draw(g2d);

		FixMan.fixGrassland(g2d, 300, 250);

		for (Bullet bullet : ControlStudio.getMyBullets()) {
			bullet.draw(g2d);
		}
		for (Bullet bullet : ControlStudio.getEnemyBullets()) {
			bullet.draw(g2d);
		}

		GameData.showGameData(g2d);
		if (ControlStudio.getEnemyTanks().size() == 0) {
			createEnemyTank();
		}
		River river = FixMan.fixRiver(g2d, 400, 400);
		mTank.addWall(river);
		updateWall(g2d);
	}

	private void updateEnemyTank(Graphics2D g2d) {
		Vector<EnemyTank> curETanks = new Vector<>();
		// tBullets = ControlStudio.getEnemyBullets();
		for (EnemyTank enemyTank : ControlStudio.getEnemyTanks()) {
			curETanks.add(enemyTank);
		}
		for (EnemyTank enemyTank : curETanks) {
			enemyTank.putAllWalls(wallList);
			enemyTank.addTank(mTank);
			// enemyTank.addBullets(ControlStudio.getMyBullets());
			enemyTank.draw(g2d);
		}
	}

	// 初始化墙体
	private void initWall(Graphics2D g2d) {

		int wallThickness = 40;
		drawWall(g2d, -wallThickness, -wallThickness, 900, wallThickness);
		drawWall(g2d, -wallThickness, -wallThickness, wallThickness, 600);
		drawWall(g2d, 833, -wallThickness, wallThickness, 600);
		drawWall(g2d, -wallThickness, 560, 900, wallThickness);
		drawWall(g2d, 60, 60, 30, 200);
		drawWall(g2d, 60, 60, 200, 30);

	}

	public void updateWall(Graphics2D g2d) {
		List<BaseWall> walls = new ArrayList<>();
		// tBullets = ControlStudio.getEnemyBullets();
		for (BaseWall wall : ControlStudio.getWallList()) {
			walls.add(wall);
		}
//		for (BaseWall wall : walls) {
//			if (wall instanceof BrickWall) {
//				wall.draw(g2d);
//				wall.checkCollision();
//			}
//		}
	}

	/**
	 * 
	 * @param g
	 *            Graphic
	 * @param x
	 *            要画图形左上角x
	 * @param y
	 *            要画图形左上角y
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 */
	private void drawWall(Graphics2D g, int x, int y, int width, int height) {
		BaseWall bWall1 = wallFactory.createBaseWall();// 工厂生产墙体
		FixMan.fixWall(bWall1, g, x, y, width, height);// 安装工人安装墙体
		bWall1.checkCollision(g);
		wallList.add(bWall1);
	}
}
