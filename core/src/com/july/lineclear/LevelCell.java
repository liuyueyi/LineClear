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
	int addx;

	public LevelCell() {
		this(LOCKED, 0, -1);
	}

	public LevelCell(int level, int star, int score) {
		setLevel(level);
		setScore(score);
		starImg = new TextureRegion[3];
		setStar(star);
		if (level == LOCKED)
			levelBg = AssetManager.getInstance().levelBgLock;
		else
			levelBg = AssetManager.getInstance().levelBg;
	}

	public void setStar(int star) {
		if (level == -1)
			this.star = 0;
		else
			this.star = star;

		int i = 0;
		while (i < star)
			starImg[i++] = AssetManager.getInstance().stars[1];
		while (i < 3)
			starImg[i++] = AssetManager.getInstance().stars[0];
	}

	public void setScore(int score) {
		if (score == -1)
			this.score = "???";
		else
			this.score = "" + score;
	}

	public void setLevel(int level) {
		this.level = level;
		levelBg = AssetManager.getInstance().levelBg;
		if (level > 99)
			addx = 10;
		else if (level > 9)
			addx = 15;
		else
			addx = 20;
	}

	public int getLevel() {
		return level;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(levelBg, getX(), getY(), Constants.levelBgWidth,
				Constants.levelBgHeight);

		for (int i = 0; i < 3; i++) {
			batch.draw(starImg[i], Constants.width - Constants.levelBgX
					- (3.6f - i * 1.2f) * Constants.starWidth, getY()
					+ (Constants.levelBgHeight - Constants.starHeight) / 2,
					Constants.starWidth, Constants.starHeight);
		}
		AssetManager.getInstance().font.draw(batch, score, getX() + 0.33f
				* getWidth(), getY() + getHeight() - 10);
		if (level != -1)
			AssetManager.getInstance().font.draw(batch, "" + level, getX()
					+ addx, getY() + getHeight() - 5);
	}

	public boolean clicked(float x, float y) {
		return x >= getX() && x < getX() + getWidth() && y >= getY()
				&& y < getY() + getHeight() ? true : false;
	}
}
