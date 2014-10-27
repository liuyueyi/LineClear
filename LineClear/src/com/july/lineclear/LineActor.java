package com.july.lineclear;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;

public class LineActor extends Actor {
	Array<Vector2> array;
	float time = 0f;
	Animation horizontal;
	Animation vertical;
	TextureRegion hLine, vLine;
	boolean moveOut = false;

	public LineActor() {
		horizontal = new Animation(0.3f, AssetManager.getInstance().hLine);
		vertical = new Animation(0.3f, AssetManager.getInstance().vLine);
	}

	public LineActor(Array<Vector2> array) {
		super();
		this.array = array;
	}

	public void setArray(Array<Vector2> array) {
		this.array = array;
		addMoveOutAction();
	}

	public void addMoveOutAction() {
		moveOut = true;
		Action action = Actions.fadeOut(Gdx.graphics.getDeltaTime() * 20);
		addAction(action);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		time += Gdx.graphics.getDeltaTime();
		hLine = horizontal.getKeyFrame(time, true);
		vLine = vertical.getKeyFrame(time, true);
		if (moveOut && time > 20)
			this.remove();
		Color c = batch.getColor();
		batch.setColor(getColor());
		super.draw(batch, parentAlpha);
		Vector2 v1 = array.get(0);
		float x, y, width, height;
		for (int i = 1; i < array.size; i++) {
			Vector2 v2 = array.get(i);
			if (v1.x == v2.x) {
				// ºáÏß
				height = 9;
				width = Math.abs(v1.y - v2.y) * Constants.cellWidth;
				int smally = (int) (v1.y < v2.y ? v1.y : v2.y);
				x = Constants.cellX + Constants.cellWidth * (smally + 0.5f) + 4;
				if (x + width + 9 > Constants.width)
					width = Constants.width - x - 9;
				y = Constants.cellY + Constants.cellHeight * (v1.x + 0.5f);
				batch.draw(hLine, x, y, width, height);
			} else {
				// ÊúÏß
				width = 9;
				height = Math.abs(v1.x - v2.x) * Constants.cellHeight + 4;
				int smallx = (int) (v1.x < v2.x ? v1.x : v2.x);
				x = Constants.cellX + Constants.cellWidth * (v2.y + 0.5f);
				if (x > Constants.width - width)
					x = Constants.width - 2 * width;
				y = Constants.cellY + Constants.cellHeight * (smallx + 0.5f);
				batch.draw(vLine, x, y, width, height);
			}
			v1 = v2;
		}
		batch.setColor(c);
	}

}
