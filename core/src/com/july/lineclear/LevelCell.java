package com.july.lineclear;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LevelCell extends Actor {
	private String score; // best score
	private int star; // the star number
	private int level; // the level number,level==-1, it means this level is
						// locked
	private TextureRegion levelBg;
	private TextureRegion starImg[];

	public static final int LOCKED = -1;

	public LevelCell() {
		this(LOCKED, 0, "score: ???");
	}

	public LevelCell(int level, int star, String score) {
		this.star = star;
		this.level = level;
		this.score = score;
		starImg = new TextureRegion[2];
		starImg[0] = AssetManager.getInstance().stars[0];
		starImg[1] = AssetManager.getInstance().stars[1];
		if (level == LOCKED)
			levelBg = AssetManager.getInstance().levelBgLock;
		else
			levelBg = AssetManager.getInstance().levelBg;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(levelBg, getX(), getY(), Constants.levelBgWidth,
				Constants.levelBgHeight);
		batch.draw(starImg[0], getX() + 100, getY()
				+ (Constants.levelBgHeight - Constants.starHeight) / 2,
				Constants.starWidth, Constants.starHeight);
	}
}
