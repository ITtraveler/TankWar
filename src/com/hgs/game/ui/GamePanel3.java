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
	private GameMap1 gMap;// ��ͼ
	// private int count = 0;// ͳ�ƴ��˶��ٹ�
	// private int eTankNum = 10;
	private Image background;
	private GameRule gameRule = new GameRule();
	private int killCount = 0;
	private int oldKillCount = 0;
	private long oldTime, curTime, betweenTime;
	private boolean haveRecord = false;// �Ƿ��¼

	public GamePanel3(MyTank tank) {
		this.mTank = tank;
		gMap = new GameMap1();
		initMyTank();
		initEnemyTank();
		background = Images.getImage(R.Background.BACKGROUND);
		System.out.println("tank:" + tank.isLive());
	}

	// ��ʼ���ҵ�̹��
	private void initMyTank() {
		reInitMyTank();
		mTank.putAllWalls(ControlStudio.getWallList());
		for (BaseWall baseWall : ControlStudio.getEdgeWalls()) {
			mTank.addWall(baseWall);
		}
	}

	/**
	 * ���³�ʼ���ҵ�̹�ˣ�����λ�ã�Ѫ��
	 */
	private void reInitMyTank() {
		mTank.setInitLocation(new Point(320, 550));
		mTank.setInitBlood(1000);
		GameData.MyTankBlood = 1000;
	}

	// ��ʼ������TANK
	private void initEnemyTank() {
		oldKillCount = GameData.totalEnemyTankNum;
		// ��ʼ����ͼ
		gMap.randMap();
		gMap.initMap();
		gameRule.initGame();
		// ���з�̹������
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
		if (mTank.isLive()) {// ͨ���ҵ�̹���Ƿ�����ж���Ϸ�Ƿ�ʧ��
			drawGame(g2d);
		}
	}

	/**
	 * ��Ϸ�Ļ���
	 * 
	 * @param g2d
	 */
	private void drawGame(Graphics2D g2d) {
		mTank.draw(g2d);// �Ȼ�̹�ˣ�������ƺ������ס̹��
		mTank.putAllCollisionObject(ControlStudio.getEnemyTanks());
		// ���»��Ƶз�̹��
		updateEnemyTank(g2d);
		// �����ҷ��ӵ�
		for (Bullet bullet : ControlStudio.getMyBullets()) {
			bullet.draw(g2d);
		}
		// ���Ƶз��ӵ�
		for (Bullet bullet : ControlStudio.getEnemyBullets()) {
			bullet.draw(g2d);
		}
		// ��ǽ
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
		// ��Ϸս��
		GameData.showGameData(g2d);
		// if(GameData.enemyTankNum)
		killCount = GameData.totalEnemyTankNum - oldKillCount;
		// System.out.println(gameRule.getTotalTankNum());
		if (GameData.residueNum == 0) {// ʣ��ɱ����Ϊ0���������һ�ء�
			if (!haveRecord) {
				oldTime = System.currentTimeMillis();
				haveRecord = true;
			}
			curTime = System.currentTimeMillis();
			betweenTime = curTime - oldTime;
			if (betweenTime <= 2000) {
				g2d.setFont(new Font("���Ĳ���", Font.BOLD, 60));
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
	 * ���µз�̹������
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
