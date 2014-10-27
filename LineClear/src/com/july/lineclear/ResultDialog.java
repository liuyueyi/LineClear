package com.july.lineclear;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ResultDialog {
	GameScreen gameScreen;
	Stage dialogStage;
	TextureRegion resultBg;
	TextureRegion star[];
	TextureRegion title;
	Image continueBtn, returnBtn;
	Label scoreLabel;

	boolean show;

	int type;
	static final int WIN = 0;
	static final int LOSE = 1;
	static final int PAUSE = 2;

	public ResultDialog(GameScreen gameScreen) {
		show = false;
		this.gameScreen = gameScreen;
		dialogStage = new Stage();

		resultBg = AssetManager.getInstance().resultBg;
		star = new TextureRegion[3];
		setStarNum(0);

		continueBtn = new Image(AssetManager.getInstance().continueBtn[0]);
		continueBtn.addListener(clickListener);
		continueBtn.setBounds(Constants.continueBtnX, Constants.btnY,
				Constants.btnWidth, Constants.btnHeight);
		dialogStage.addActor(continueBtn);

		returnBtn = new Image(AssetManager.getInstance().returnBtn[0]);
		returnBtn.addListener(clickListener);
		returnBtn.setBounds(Constants.returnBtnX, Constants.btnY,
				Constants.btnWidth, Constants.btnHeight);
		dialogStage.addActor(returnBtn);

		scoreLabel = new Label("", AssetManager.getInstance().scoreStyle);
		scoreLabel.setBounds(Constants.resultX, Constants.btnY + 10
				+ Constants.btnHeight, Constants.resultWidth,
				Constants.infoHeight);
		scoreLabel.setAlignment(Align.center);
		dialogStage.addActor(scoreLabel);

	}

	public void setType(int type) {
		this.type = type;
		switch (type) {
		case WIN:
			title = AssetManager.getInstance().succeed;
			break;
		case LOSE:
			title = AssetManager.getInstance().failed;
			break;
		case PAUSE:
			title = AssetManager.getInstance().rest;
			break;
		}
	}

	public void setStarNum(int starNum) {
		int i = 0;
		while (i < starNum)
			star[i++] = AssetManager.getInstance().stars[1];

		while (i < 3)
			star[i++] = AssetManager.getInstance().stars[0];
	}

	public void show(int type, int score, int starNum) {
		show = true;
		setType(type);
		setStarNum(starNum);
		if (type != PAUSE)
			scoreLabel.setText("" + score);
		Gdx.input.setInputProcessor(dialogStage);
	}

	public EventListener clickListener = new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y) {
			if (continueBtn == event.getListenerActor()) {
				// 继续游戏
				switch (type) {
				case PAUSE:
					show = false;
					Gdx.input.setInputProcessor(gameScreen.stage);
					break;
				default:
					gameScreen.game.setScreen(new GameScreen(gameScreen.game,
							AssetManager.getInstance().currentLevel));
					gameScreen.dispose();
					break;
				}
			} else {
				// 返回菜单
				gameScreen.game.setScreen(new LevelScreen(gameScreen.game));
				gameScreen.dispose();
			}
		}
	};

	public void draw(Batch batch) {
		if (show) {
			batch.begin();
			batch.draw(resultBg, Constants.resultX, Constants.resultY,
					Constants.resultWidth, Constants.resultHeight);
			batch.draw(title, Constants.infoX, Constants.infoY,
					Constants.infoWidth, Constants.infoHeight);
			if (type != PAUSE)
				for (int i = 0; i < 3; i++)
					batch.draw(star[i], Constants.starX + Constants.starAddX
							* i, Constants.starY, Constants.starWidth,
							Constants.starHeight);
			batch.end();
			dialogStage.draw();
			dialogStage.act();
		}
	}

	public void dispose() {
		dialogStage.dispose();
	}
}
