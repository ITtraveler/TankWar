package com.hgs.game.tank;

import java.util.Vector;

public class EnemyTankFactory extends AbstractTankFactory {

	@Override
	public EnemyTank createTank() {
		return new EnemyTank();
	}

	/**
	 * 批量生产坦克
	 * 
	 * @param tankNum
	 *            要生产的坦克数量。
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
