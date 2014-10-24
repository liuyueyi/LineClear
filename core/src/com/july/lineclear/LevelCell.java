package com.july.lineclear;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class LevelCell extends Actor {
	private String score; // best score
	private int star; // the star number
	private int level; // the level number,level==-1, it means this level is
						// locked
	private TextureRegion levelBg;
	private TextureRegion starImg[];

	public static final int LOCKED = -1;
	int addx;
	boolean moveIn = true; // moveIn tag

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
			addx = 13;
		else
			addx = 20;
	}

	public int getLevel() {
		return level;
	}

	public int getStar() {
		return star;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (!moveIn
				&& (getX() < Constants.levelBgX - Constants.width * 0.8f || getX() > Constants.width)) {
			this.remove();
			return;
		}
		Color c = batch.getColor();
		batch.setColor(getColor());

		batch.draw(levelBg, getX(), getY(), Constants.levelBgWidth,
				Constants.levelBgHeight);

		// draw star
		for (int i = 0; i < 3; i++) {
			batch.draw(starImg[i], getX() + getWidth() - (3.6f - i * 1.2f)
					* Constants.starWidth, getY()
					+ (Constants.levelBgHeight - Constants.starHeight) / 2,
					Constants.starWidth, Constants.starHeight);
		}
		// draw score
		AssetManager.getInstance().font.draw(batch, score, getX() + 0.33f
				* getWidth(), getY() + getHeight() - 10);
		// draw level
		if (level != -1)
			AssetManager.getInstance().font.draw(batch, "" + level, getX()
					+ addx, getY() + getHeight() - 5);
		batch.setColor(c);
	}

	/**
	 * 判断当前level是否被选中
	 * 
	 * @param x
	 *            点击的x坐标
	 * @param y
	 *            点击的y坐标
	 * @return true表示选中； false表示没选中
	 */
	public boolean isClicked(float x, float y) {
		return x >= getX() && x < getX() + getWidth() && y >= getY()
				&& y < getY() + getHeight() ? true : false;
	}

	static final int LEFT = -1;
	static final int RIGHT = 1;

	/**
	 * 添加退出动画
	 * 
	 * @param direction
	 *            : 退出的方向
	 */
	public void addMoveOutAction(int direction) {
		moveIn = false;
		Action action1 = Actions.fadeOut(Gdx.graphics.getDeltaTime() * 10);
		Action action2 = Actions.moveTo(direction * Constants.width + getX(),
				getY(), Gdx.graphics.getDeltaTime() * 10);
		this.addAction(Actions.parallel(action1, action2));
	}

	public void addMoveInAction(int direction) {
		float x = getX();
		float y = getY();
		setPosition(-direction * Constants.width + getX(), getY());
		Action action1 = Actions.fadeIn(Gdx.graphics.getDeltaTime() * 10);
		Action action2 = Actions.moveTo(x, y, Gdx.graphics.getDeltaTime() * 10);
		this.addAction(Actions.parallel(action1, action2));

	}
}
