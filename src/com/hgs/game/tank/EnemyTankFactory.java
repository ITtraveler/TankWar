package com.hgs.game.tank;

import java.util.Vector;

public class EnemyTankFactory extends AbstractTankFactory {

	@Override
	public EnemyTank createTank() {
		return new EnemyTank();
	}

	/**
	 * ��������̹��
	 * 
	 * @param tankNum
	 *            Ҫ������̹��������
	 * @return
	 */
	public Vector<EnemyTank> batchProcess(int tankNum) {
		Vector<EnemyTank> tanks = new Vector<EnemyTank>();
		for (int i = 0; i < tankNum; i++) {
			tanks.add(new EnemyTank());
		}
		return tanks;
	}

}
