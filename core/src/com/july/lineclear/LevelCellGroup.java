package com.july.lineclear;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;

public class LevelCellGroup extends Group {
	Array<LevelCell> array;

	int baseLevel;

	public LevelCellGroup(int baseLevel) {
		this.baseLevel = baseLevel;
		array = new Array<LevelCell>();
		for (int i = 0; i < 8; i++) {
			LevelCell cell = Pools.obtain(LevelCell.class);
			if (baseLevel + i <= Constants.maxLevel) {
				cell.setLevel(baseLevel + i);
				cell.setScore(1000);
				cell.setStar(2);
			}
			cell.setBounds(Constants.levelBgX, Constants.levelBgY - i
					* (Constants.levelBgHeight + Constants.levelBgAddY),
					Constants.levelBgWidth, Constants.levelBgHeight);
			this.addActor(cell);
			array.add(cell);
		}
	}

	public void setMoveOutAction(int direction) {
		for (LevelCell cell : array) {
			cell.addMoveOutAction(direction);
		}
		array.clear();
	}
	
	public void setMoveInAction(int direction) {
		for (LevelCell cell : array) {
			cell.addMoveInAction(direction);
		}
	}
}
