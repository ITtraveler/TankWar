package com.hgs.game.tank;

import java.util.Vector;

public class MyTankFactory extends AbstractTankFactory{
	@Override
	public MyTank createTank() {
		return new MyTank();
	}

	@Override
	public <T> Vector<T> batchProcess(int tankNum) {
		// TODO Auto-generated method stub
		return null;
	}
}
