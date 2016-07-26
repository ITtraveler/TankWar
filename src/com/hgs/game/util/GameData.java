package com.hgs.game.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.hgs.game.ui.GameRule;

/**
 * 游戏数据，与显示
 * 
 * @author hgs
 *
 */
public class GameData {
	public static final String COPYRIGHT = "版权所有人：黄国胜";
	public static final String GAME_HELP = "地图说明：每一关的地图都为为随机产生。\n操作：通过上下左右可以控制坦克的八方位移动，d键为子弹发射\n键，f为密集子弹发射键，密集子弹将消耗您的分数。当您的血量\n为0时游戏结束。";
	public static int MyTankBlood = 1000;// 我的坦克血量
	public static int score = 0;
	public static int enemyTankNum = 0;
	public static int eBulletNum = 0;
	public static int mBulletNum = 0;
	public static int totalEnemyTankNum = 0;
	public static int customsPass = 0;//关卡
	public static int residueNum = 0;
	static {
		enemyTankNum = ControlStudio.getEnemyTanks().size();
	}

	/**
	 * 将数据画出来
	 * 
	 * @param g
	 */
	public static void showGameData(Graphics g) {
		update();
		g.setColor(Color.BLACK);
		g.setFont(new Font("华文行楷", Font.BOLD, 24));
		g.drawString("血量：" + MyTankBlood, 50, 20);
		g.drawString("得分：" + score, 200, 20);
		g.drawString("敌方数量：" + enemyTankNum, 350, 20);
		g.drawString("杀敌数："+totalEnemyTankNum, 540, 20);
		g.drawString("关卡：" + customsPass, 700, 20);
		g.drawString("剩余杀敌数：" + residueNum, 700, 50);
		//g.drawString("敌方子弹：" + eBulletNum, 700, 20);
		//g.drawString("我子弹：" + mBulletNum, 700, 40);

	}

	/**
	 * 更新数据
	 */
	public static void update() {
		enemyTankNum = ControlStudio.getEnemyTanks().size();
		eBulletNum = ControlStudio.getEnemyBullets().size();
		mBulletNum = ControlStudio.getMyBullets().size();
	}
	/**
	 * 游戏数据清理
	 */
	public static void clear(){
		MyTankBlood = 1000;// 我的坦克血量
		score = 0;
		enemyTankNum = 0;
		eBulletNum = 0;
		mBulletNum = 0;
		totalEnemyTankNum = 0;
		customsPass = 0;
		residueNum = 0;
		ControlStudio.clearAll();
	}
}
