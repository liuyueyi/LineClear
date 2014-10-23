package com.july.lineclear;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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

	public void setMoveOutAction() {
		Action action1 = Actions.fadeOut(Gdx.graphics.getDeltaTime() * 10);
		Action action2 = Actions.moveTo(-Constants.width, getY(),
				Gdx.graphics.getDeltaTime() * 10);
		this.addAction(Actions.parallel(action1, action2));
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if (getX() < -0.6f * Constants.width) {
			this.clear();
		}
	}
}
