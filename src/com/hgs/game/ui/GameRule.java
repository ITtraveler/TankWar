package com.hgs.game.ui;

import java.util.Random;

import com.hgs.game.Point;
import com.hgs.game.tank.AbstractTankFactory;
import com.hgs.game.tank.EnemyTank;
import com.hgs.game.tank.EnemyTank2Factory;
import com.hgs.game.tank.EnemyTank3Factory;
import com.hgs.game.tank.EnemyTankFactory;
import com.hgs.game.util.ControlStudio;
import com.hgs.game.util.GameData;
import com.hgs.game.wall.BaseWall;

/**
 * 
 * @author Administrator ������Ϸ�ؿ�����̹�˳��ֿ��Ƶȵ�,��Ӧ����������һ�Ų��ڴ˳���
 */
public class GameRule {
	private int customsPass = 0;// ��Ϸ�ؿ�
	private  int totalTankNum = 9;// һ���е�̹������
	private  int controlNum = 2;// ����̹�˳��ֵ�����
	private int eTankExport[][] = { { 10, 10 }, { 400, 10 }, { 820, 10 } };// �з�̹�˳���
	private EnemyTankFactory enemTank1Factory = new EnemyTankFactory();
	private EnemyTank2Factory enemTank2Factory = new EnemyTank2Factory();
	private EnemyTank3Factory enemTank3Factory = new EnemyTank3Factory();


	private Random random = new Random();

	public GameRule() {
		customsPass = GameData.customsPass;
	}

	public void initGame() {
		if (customsPass <= 2) {
			for (int i = 0; i < controlNum; i++) {
				addRandTank(1);
			}
		} else if (customsPass > 5 && customsPass <= 10) {
			for (int i = 0; i < controlNum; i++) {
				addRandTank(2);
			}

		} else if (customsPass > 10) {
			for (int i = 0; i < customsPass; i++) {
				addRandTank(3);
			}
			// for(int i = 0;i <customsPass - 10;i++){//����10�أ�����10�ĲΪ�����ɵ�̹������
			// addRandTank(3);
			// }
		}
		controlNum++;
		totalTankNum++;
		GameData.customsPass++;
	}

	/**
	 * ���һ�����̹�˵����������λ��
	 */
	public EnemyTank addRandTank(int randType) {
		int x = 0;
		int y = 10;
		int randL = random.nextInt(3);// ���λ��

		switch (randL) {
		case 0:
			x = eTankExport[0][0];
			y = eTankExport[0][1];

			break;
		case 1:
			x = eTankExport[1][0];
			y = eTankExport[1][1];

			break;
		case 2:
			x = eTankExport[2][0];
			y = eTankExport[2][1];
			break;
		default:
			break;
		}
		return randAddEnemyTank(new Point(x, y), randType);
	}

	/**
	 * ���һ�������̹��
	 * 
	 * @param location
	 *            ̹�˲�����λ��
	 * @param randType
	 *            ��Χ���ࡣ1~3
	 */
	public EnemyTank randAddEnemyTank(Point location, int randType) {
		int randT = random.nextInt(randType);// ���̹��
		EnemyTank enemyTank = null;
		switch (randT) {
		case 0:
			enemyTank = addEnemyTank1(location);
			break;
		case 1:
			enemyTank = addEnemyTank2(location);
			break;
		case 2:
			enemyTank = addEnemyTank3(location);
			break;
		default:
			break;
		}
		return enemyTank;
	}

	/**
	 * ���̹��1
	 * 
	 * @param location
	 */
	private EnemyTank addEnemyTank1(Point location) {
		EnemyTank eTank = enemTank1Factory.createTank();
		eTank.setInitLocation(location);
		ControlStudio.addEnemyTank(eTank);
		return eTank;
	}

	/**
	 * ���̹��2
	 * 
	 * @param location
	 */
	private EnemyTank addEnemyTank2(Point location) {
		EnemyTank eTank = enemTank2Factory.createTank();
		eTank.setInitLocation(location);
		ControlStudio.addEnemyTank(eTank);
		return eTank;
	}

	/**
	 * ���̹��3
	 * 
	 * @param location
	 */
	private EnemyTank addEnemyTank3(Point location) {
		EnemyTank eTank = enemTank3Factory.createTank();
		eTank.setInitLocation(location);
		ControlStudio.addEnemyTank(eTank);
		return eTank;
	}
	
	public void initData(){
		totalTankNum = 9;// һ���е�̹������
		controlNum = 2;// ����̹�˳��ֵ�����
	}

	public int getTotalTankNum() {
		return totalTankNum;
	}

	public int getControlNum() {
		return controlNum;
	}
}
