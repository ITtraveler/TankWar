package com.hgs.game.tank;

import java.util.Vector;

public abstract class AbstractTankFactory {
	
	public abstract MyTank createTank();
	/**
	 * ��������̹��
	 * @param <T>
	 * @param <T>
	 * 
	 * @param tankNum
	 *            Ҫ������̹��������
	 * @return
	 */
	public abstract <T> Vector<T> batchProcess(int tankNum);
}
