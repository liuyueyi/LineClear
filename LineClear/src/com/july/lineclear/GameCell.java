package com.july.lineclear;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class GameCell extends Actor {
	int cellType;
	boolean moveOut = false;
	float duration = 0f;

	public GameCell() {
		setSize(Constants.cellWidth, Constants.cellHeight);
	}

	public GameCell(int cellType, int row, int column) {
		this();
		setValue(cellType, row, column);
	}

	public void setValue(int cellType, int row, int column) {
		this.cellType = cellType;
		setPosition(Constants.cellX + column * Constants.cellWidth,
				Constants.cellY + row * Constants.cellHeight);
	}

	public int getType() {
		if (moveOut)
			return -1;
		else
			return cellType;
	}

	public boolean isClicked(float x, float y) {
		return x >= getX() && x < getX() + getWidth() && y >= getY()
				&& y < getY() + getHeight() ? true : false;
	}

	/**
	 * 添加退出动画
	 */
	public void addMoveOutAction() {
		moveOut = true;
		duration = 0f;
		Action action = Actions.fadeOut(Gdx.graphics.getDeltaTime() * 10);
		addAction(action);
	}

	@Override
	public void draw(Batch batch, float alphaDelta) {
		if (moveOut) { // 执行退出动画后，9s后注销此actor
			duration += Gdx.graphics.getDeltaTime();
			if (duration > 9)
				this.remove();
		}
		Color c = batch.getColor();
		batch.setColor(getColor());
		batch.draw(AssetManager.getInstance().animals[cellType], getX(),
				getY(), getWidth(), getHeight());
		batch.setColor(c);
	}
}
