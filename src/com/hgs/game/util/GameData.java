package com.hgs.game.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.hgs.game.ui.GameRule;

/**
 * ��Ϸ���ݣ�����ʾ
 * 
 * @author hgs
 *
 */
public class GameData {
	public static final String COPYRIGHT = "��Ȩ�����ˣ��ƹ�ʤ";
	public static final String GAME_HELP = "��ͼ˵����ÿһ�صĵ�ͼ��ΪΪ���������\n������ͨ���������ҿ��Կ���̹�˵İ˷�λ�ƶ���d��Ϊ�ӵ�����\n����fΪ�ܼ��ӵ���������ܼ��ӵ����������ķ�����������Ѫ��\nΪ0ʱ��Ϸ������";
	public static int MyTankBlood = 1000;// �ҵ�̹��Ѫ��
	public static int score = 0;
	public static int enemyTankNum = 0;
	public static int eBulletNum = 0;
	public static int mBulletNum = 0;
	public static int totalEnemyTankNum = 0;
	public static int customsPass = 0;//�ؿ�
	public static int residueNum = 0;
	static {
		enemyTankNum = ControlStudio.getEnemyTanks().size();
	}

	/**
	 * �����ݻ�����
	 * 
	 * @param g
	 */
	public static void showGameData(Graphics g) {
		update();
		g.setColor(Color.BLACK);
		g.setFont(new Font("�����п�", Font.BOLD, 24));
		g.drawString("Ѫ����" + MyTankBlood, 50, 20);
		g.drawString("�÷֣�" + score, 200, 20);
		g.drawString("�з�������" + enemyTankNum, 350, 20);
		g.drawString("ɱ������"+totalEnemyTankNum, 540, 20);
		g.drawString("�ؿ���" + customsPass, 700, 20);
		g.drawString("ʣ��ɱ������" + residueNum, 700, 50);
		//g.drawString("�з��ӵ���" + eBulletNum, 700, 20);
		//g.drawString("���ӵ���" + mBulletNum, 700, 40);

	}

	/**
	 * ��������
	 */
	public static void update() {
		enemyTankNum = ControlStudio.getEnemyTanks().size();
		eBulletNum = ControlStudio.getEnemyBullets().size();
		mBulletNum = ControlStudio.getMyBullets().size();
	}
	/**
	 * ��Ϸ��������
	 */
	public static void clear(){
		MyTankBlood = 1000;// �ҵ�̹��Ѫ��
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
