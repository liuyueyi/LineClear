package com.july.lineclear;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class TipActor extends Actor {
	TextureRegion zan;
	Label scoreLabel;
	boolean showZan = false;
	float time = 0;

	public TipActor() {

	}

	public TipActor(int count, float x, float y) {
		init(count, x, y);
	}

	public void init(int count, float x, float y) {
		scoreLabel = new Label(100 * count + "",
				AssetManager.getInstance().style);
		scoreLabel.setPosition(x, y);

		setPosition(x, y);
		zan = new TextureRegion();
		if (count < 3) {
			showZan = false;
		} else {
			showZan = true;
			if (count < 5) {
				zan = AssetManager.getInstance().zan[0];
			} else if (count < 7) {
				zan = AssetManager.getInstance().zan[1];
			} else if (count == 7) {
				zan = AssetManager.getInstance().zan[2];
			} else if (count == 8) {
				zan = AssetManager.getInstance().zan[3];
			} else {
				zan = AssetManager.getInstance().zan[4];
			}
		}

		addMoveOutAction();
	}

	public void addMoveOutAction() {
		float addx;
		if (getX() > getWidth() / 2)
			addx = -20;
		else
			addx = 20;

		Action action1 = Actions.moveTo(getX() + 10, getY() + 10,
				Gdx.graphics.getDeltaTime() * 8);
		Action action2 = Actions.moveTo(Constants.scoreX
				+ Constants.infoLabelWidth / 2, Constants.infoLabelY,
				Gdx.graphics.getDeltaTime() * 8);
		Action action3 = Actions.fadeOut(Gdx.graphics.getDeltaTime() * 20);
		scoreLabel.addAction(Actions.sequence(action1,
				Actions.parallel(action2, action3)));

		Action action4 = Actions.moveTo(getX() + addx, getY() + 10,
				Gdx.graphics.getDeltaTime() * 30);
		addAction(Actions.parallel(action3, action4));
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		time += Gdx.graphics.getDeltaTime();
		if (time > 30) {
			scoreLabel.remove();
			this.remove();
		}
		super.draw(batch, parentAlpha);
		if (showZan) {
			Color c = batch.getColor();
			batch.setColor(getColor());
			batch.draw(
					zan,
					getX(),
					getY(),
					Constants.cellWidth * 2,
					Constants.cellWidth * 2 * zan.getRegionHeight()
							/ zan.getRegionWidth());
			batch.setColor(c);
		}
	}
}
