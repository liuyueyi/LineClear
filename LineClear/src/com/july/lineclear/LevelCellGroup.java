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
			if (baseLevel + i <= AssetManager.getInstance().maxLevel) {
				cell.setLevel(baseLevel + i);
				cell.setScore(AssetManager.getInstance().record.get(
						baseLevel + i).get(0));
				cell.setStar(AssetManager.getInstance().record.get(
						baseLevel + i).get(1));
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

	public void setSelectedAction(int level) {
		int index = (level - 1) % 8;
		for (int i = 0; i < 8; i++) {
			if (i != index)
				array.get(i).addMoveOutAction(LevelCell.LEFT);
			else
				array.get(i).addMoveOutAction(LevelCell.RIGHT);
		}
	}
}
