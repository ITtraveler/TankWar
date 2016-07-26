package com.hgs.game.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.swing.JPanel;

import com.hgs.game.Point;
import com.hgs.game.bullet.Bullet;
import com.hgs.game.resource.R;
import com.hgs.game.resource.R.Home;
import com.hgs.game.tank.EnemyTank;
import com.hgs.game.tank.EnemyTank2Factory;
import com.hgs.game.tank.EnemyTank3Factory;
import com.hgs.game.tank.MyTank;
import com.hgs.game.util.ControlStudio;
import com.hgs.game.util.GameData;
import com.hgs.game.util.Images;
import com.hgs.game.wall.BaseWall;
import com.hgs.game.wall.Grassland;
import com.hgs.game.wall.WallFactory;

public class GamePanel2 extends JPanel {
	private MyTank mTank;
	private List<BaseWall> walls = new ArrayList<>();
	private List<BaseWall> eWalls = new ArrayList<>();
	private WallFactory wallFactory = new WallFactory();
	//private Vector<EnemyTank> eTanks = new Vector<>();
	private GameMap1 gMap;// 地图
	private int count = 0;// 统计打了多少关
	private int eTankNum = 10;
	public static final int GAME_UI = 1;
	private int curStatus = 1;
	private Image background;
	
	public GamePanel2(MyTank tank) {
		this.mTank = tank;
		gMap = new GameMap1();
		initMyTank();
		initEnemyTank(count);
		background = Images.getImage(R.Background.BACKGROUND);
	}

	public void initEnemyTankNum(){
		eTankNum = 10;
	}
	public void setGameStatus(int status) {
		this.curStatus = status;
	}

	// 初始化我的坦克
	private void initMyTank() {
		mTank.setInitLocation(new Point(400, 550));
		mTank.putAllWalls(ControlStudio.getWallList());
		for (BaseWall baseWall : ControlStudio.getEdgeWalls()) {
			mTank.addWall(baseWall);
		}
	}

	// 初始化敵方TANK
	private void initEnemyTank(int count) {
		mTank.setInitLocation(new Point(320, 550));
		mTank.setInitBlood(1000);
		GameData.MyTankBlood = 1000;
		GameData.update();
		// 初始化地图
		gMap.randMap();
		gMap.initMap();
		// 敌方坦克1生成
		ControlStudio.setEnemyTanks(new EnemyTank3Factory()
				.batchProcess(eTankNum));
		// for(int i =0;i <count;i++){
		// ControlStudio.addEnemyTank(new EnemyTankFactory().createTank());
		// }

		Random r = new Random();
		// 给敌方坦克生命
		for (int i = 0; i < eTankNum; i++) {
			EnemyTank eTank = ControlStudio.getEnemyTanks().get(i);
			int rand = r.nextInt(3);
			if (rand == 0) {
				eTank.setInitLocation(new Point(0, 10));
			} else if (rand == 1) {
				eTank.setInitLocation(new Point(400, 10));
			} else if (rand == 2) {
				eTank.setInitLocation(new Point(840, 10));
			}
			eTank.addTank(mTank);
			eTank.putAllWalls(ControlStudio.getWallList());
			for (BaseWall baseWall : ControlStudio.getEdgeWalls()) {
				eTank.addWall(baseWall);
			}

			new Thread(eTank).start();
		//	eTanks.add(eTank);

		}
		eTankNum++;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		//g2d.drawImage(background,0,0,1000,700,null);
		
		drawGame(g2d);
	}

	/**
	 * 游戏的绘制
	 * 
	 * @param g2d
	 */
	private void drawGame(Graphics2D g2d) {
		if (mTank.isLive()) {
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

			if (ControlStudio.getEnemyTanks().size() == 0) {
				initEnemyTank(count);
				count++;
			}
			if(!ControlStudio.home.isLive()){
				System.out.println("deals");
				mTank.setLive(false);
			}
		} else {

		}
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
