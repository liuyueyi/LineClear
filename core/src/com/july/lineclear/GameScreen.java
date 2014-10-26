package com.july.lineclear;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;

public class GameScreen extends ScreenAdapter {
	MainGame game;
	SpriteBatch batch;
	Stage stage;

	TextureRegion bg;
	TextureRegion timeBg;
	TextureRegion timeFill;
	float baseTime, time;
	Label score, level, best;
	int levelNum, scoreNum, bestNum, starNum;
	LabelStyle style[];
	Image pause;
	TextureRegionDrawable trd[];

	GameCellGroup gameCells;
	Array<Vector2> line;
	LineActor lineActor;
	int count = 1; // ��ʾ��������
	float countTime = 0;

	int leftCellNum = GameCellGroup.ROW * GameCellGroup.COLUMN; // ʣ�൥Ԫ�ĸ���

	ResultDialog resultDialog;

	public GameScreen(MainGame game, int level) {
		this.game = game;
		AssetManager.getInstance().currentLevel = level;
		levelNum = level;
		bestNum = AssetManager.getInstance().record.get(level).get(0);
		starNum = AssetManager.getInstance().record.get(level).get(1);

		baseTime = 200 - level * 10;
		time = baseTime;
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		stage = new Stage();

		bg = AssetManager.getInstance().bg;
		trd = new TextureRegionDrawable[] {
				new TextureRegionDrawable(AssetManager.getInstance().score),
				new TextureRegionDrawable(AssetManager.getInstance().best),
				new TextureRegionDrawable(AssetManager.getInstance().level) };
		style = new LabelStyle[] {
				new LabelStyle(AssetManager.getInstance().defaultFont,
						Color.BLACK),
				new LabelStyle(AssetManager.getInstance().defaultFont,
						Color.WHITE),
				new LabelStyle(AssetManager.getInstance().defaultFont,
						Color.NAVY) };
		style[0].background = trd[2];
		level = new Label("" + levelNum, style[0]);
		level.setAlignment(Align.center);
		level.setBounds(Constants.levelX, Constants.infoLabelY,
				Constants.infoLabelWidth, Constants.infoLabelHeight);
		stage.addActor(level);

		style[1].background = trd[0];
		score = new Label("" + scoreNum, style[1]);
		score.setAlignment(Align.center);
		score.setBounds(Constants.scoreX, Constants.infoLabelY,
				Constants.infoLabelWidth, Constants.infoLabelHeight);
		stage.addActor(score);

		style[2].background = trd[1];
		best = new Label("" + bestNum, style[2]);
		best.setAlignment(Align.center);
		best.setBounds(Constants.bestX, Constants.infoLabelY,
				Constants.infoLabelWidth, Constants.infoLabelHeight);
		stage.addActor(best);

		pause = new Image(AssetManager.getInstance().pause);
		pause.setBounds(Constants.pauseX, Constants.infoLabelY,
				Constants.pauseWidth, Constants.pauseHeight);
		pause.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// show pause dialog
				// Gdx.app.log("wzb", "pause button clicked!");
				resultDialog.show(ResultDialog.PAUSE, 0, 0);
			}
		});
		stage.addActor(pause);

		timeBg = AssetManager.getInstance().timeBg;
		timeFill = AssetManager.getInstance().timeFill[1];

		line = new Array<Vector2>();
		gameCells = new GameCellGroup();
		gameCells.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				int column = (int) ((x - Constants.cellX) / Constants.cellWidth);
				int row = (int) ((y - Constants.cellY) / Constants.cellHeight);
				if (gameCells.clicked(row, column, line)) { // ����
					removeCells(x, y);
				}
			}
		});
		stage.addActor(gameCells);
		Gdx.input.setInputProcessor(stage);

		resultDialog = new ResultDialog(this);
	}

	@Override
	public void render(float duration) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (!resultDialog.show) {
			time -= Gdx.graphics.getDeltaTime();
			if (time < 0) {
				// ʧ��
				time = 0;
				resultDialog.show(ResultDialog.LOSE, scoreNum, 0);
			}
		}

		if (countTime > 0)
			countTime -= Gdx.graphics.getDeltaTime();

		if (Gdx.input.isKeyPressed(Input.Keys.BACK)
				|| Gdx.input.isKeyPressed(Input.Keys.A)) {
			game.setScreen(game.menuScreen);
		}
		batch.begin();
		batch.draw(bg, 0, 0, Constants.width, Constants.height);
		batch.draw(timeBg, Constants.timeX, Constants.timeY,
				Constants.timeWidth, Constants.timeHeight);
		batch.draw(timeFill, Constants.timeX, Constants.timeY,
				Constants.timeWidth * (time / baseTime), Constants.timeHeight);
		batch.end();

		stage.draw();
		stage.act();

		resultDialog.draw(batch);
	}

	@Override
	public void dispose() {
		batch.dispose();
		stage.dispose();
		resultDialog.dispose();
	}

	/**
	 * ���������ͼƬ��������ʱ������ ���·���������������ʾ�����ƺţ������ߣ��ж��Ƿ�ɹ��������ǣ�����ʾ�ɹ��ĶԻ��򣬲����±������Ľ����
	 * 
	 * @param x
	 *            : ��ʾ�����ƺŵ�����x
	 * @param y
	 *            : ��ʾ�����ƺŵ�����y
	 */
	public void removeCells(float x, float y) {
		// ��ӷ���
		if (countTime > 0)
			count++;
		else
			count = 1;
		// update the star number
		if (count >= 8)
			starNum = 3;
		else if (count >= 5) {
			if (starNum < 2)
				starNum = 2;
		} else if (count >= 3) {
			if (starNum == 0)
				starNum = 1;
		}

		countTime = 3;
		scoreNum += 100 * count;
		score.setText("" + scoreNum);
		if (scoreNum > bestNum) {
			bestNum = scoreNum;
			best.setText("" + bestNum);
		}

		TipGroup tip = new TipGroup(count, x, y);
		stage.addActor(tip);

		// ��ʾ������
		lineActor = Pools.obtain(LineActor.class);
		lineActor.setArray(line);
		stage.addActor(lineActor);

		leftCellNum -= 2;
		if (leftCellNum == 0) {
			// ���سɹ�,���³ɼ���������
			Gdx.app.log("wzb", "succeed!");
			AssetManager.getInstance()
					.updateRecord(levelNum, scoreNum, starNum);
			resultDialog.show(ResultDialog.WIN, scoreNum, starNum);
		}
	}
}
