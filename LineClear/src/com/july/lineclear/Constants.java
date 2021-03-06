package com.july.lineclear;

import com.badlogic.gdx.Gdx;

public class Constants {
	static float width = Gdx.graphics.getWidth();
	static float height = Gdx.graphics.getHeight();
	static float wrate = width / 480;
	static float hrate = height / 800;

	static float adHeight = 60 * hrate;
	// menu constants
	static float titleWidth = 240 * wrate;
	static float titleHeight = 680 * hrate;
	static float titleX = 0;
	static float titleY = adHeight;
	static float menuBtnWidth = 167 * wrate;
	static float menuBtnHeight = 77 * hrate;
	static float soundWidth = 48f * wrate;
	static float soundHeight = 48f * wrate;
	static float soundX = width - soundWidth * 2f;
	static float soundY = adHeight + soundHeight;
	static float menuBtnX = width - 1.2f * menuBtnWidth;
	static float exitBtnY = soundY + menuBtnHeight;
	static float menuBtnAddY = menuBtnHeight * 1.5f;

	// level Constants
	static float levelBgWidth = 420 * wrate;
	static float levelBgHeight = levelBgWidth / 6f;
	static float levelBgAddY = (height - 2 * adHeight - 8 * levelBgHeight) / 9f;
	static float levelBgX = 30 * wrate;
	static float levelBgY = height - adHeight - levelBgAddY - levelBgHeight;

	static float lockHeight = 60 * hrate;
	static float lockWidth = 60 * wrate;
	static float starWidth = 50 * hrate;
	static float starHeight = starWidth;

	// static int maxLevel = 40;
	// static int currentLevel = 1;

	// game Constants
	static float infoLabelWidth = 130 * wrate;
	static float infoLabelHeight = infoLabelWidth / 4;
	static float infoLabelY = 0.78f * height;
	static float levelX = 10;
	static float scoreX = levelX + infoLabelWidth;
	static float bestX = scoreX + infoLabelWidth;
	static float pauseWidth = 40 * wrate;
	static float pauseHeight = infoLabelHeight;
	static float pauseX = bestX + infoLabelWidth + pauseWidth * 0.5f;

	static float timeWidth = width - 10;
	static float timeHeight = 10;
	static float timeX = 10;
	static float timeY = infoLabelY - 12;

	static float cellWidth = width / (GameCellGroup.COLUMN + 1);
	static float cellHeight = cellWidth;
	static float cellX = cellWidth / 2;
	static float cellY = timeY - 4 - GameCellGroup.ROW * cellHeight;

	// dialog constants
	static float resultWidth = 0.75f * width;
	static float resultHeight = resultWidth * 408f / 450f;
	static float resultX = (width - resultWidth) / 2f;
	static float resultY = (height - resultHeight) / 2f;
	static float starX = resultX + (resultWidth - 4 * starWidth) / 2;
	static float starAddX = starWidth * 1.5f;
	static float starY = resultY + (340 - 2 * starHeight) / 405f * resultHeight;
	static float infoWidth = 320f / 450f * resultWidth;
	static float infoHeight = infoWidth / 4f;
	static float infoX = resultX + (resultWidth - infoWidth) / 2f;
	static float infoY = starY - infoHeight;

	static float btnWidth = 197f / 450f * resultWidth;
	static float btnHeight = btnWidth / 4f;
	static float btnY = resultY + 20;
	static float continueBtnX = resultX + 15;
	static float returnBtnX = resultX + resultWidth - 15 - btnWidth;

	// android choose
	public static final int MORE = 1;
	public static final int CHAPIN = 2;
	public static final int EXIT = 3;
}
