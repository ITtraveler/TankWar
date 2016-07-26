package com.hgs.game.tank;

import java.util.Vector;

public abstract class AbstractTankFactory {
	
	public abstract MyTank createTank();
	/**
	 * 批量生产坦克
	 * @param <T>
	 * @param <T>
	 * 
	 * @param tankNum
	 *            要生产的坦克数量。
	 * @return
	 */
	public abstract <T> Vector<T> batchProcess(int tankNum);
}
