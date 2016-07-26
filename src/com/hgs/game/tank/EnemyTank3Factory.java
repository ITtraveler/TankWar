package com.hgs.game.tank;

import java.util.Vector;

public class EnemyTank3Factory extends AbstractTankFactory{

	@Override
	public EnemyTank3 createTank() {
		return new EnemyTank3();
	}

	@Override
	public Vector<EnemyTank> batchProcess(int tankNum) {
		Vector<EnemyTank> tanks = new Vector<EnemyTank>();
		for (int i = 0; i < tankNum; i++) {
			tanks.add(new EnemyTank3());
		}
		return tanks;
	}

}
