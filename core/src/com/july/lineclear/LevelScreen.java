package com.july.lineclear;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class LevelScreen extends ScreenAdapter implements GestureListener {
	MainGame game;

	private TextureRegion bg;
	private SpriteBatch batch;
	private Stage stage;

	LevelCellGroup group;
	LevelCellGroup nextGroup;
	int currentPage = 0;

	public LevelScreen(MainGame game) {
		this.game = game;
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		stage = new Stage();
		bg = AssetManager.getInstance().menuBg;

		group = new LevelCellGroup(1 + 8 * currentPage);
		stage.addActor(group);
		Gdx.input.setInputProcessor(new GestureDetector(this));
		Gdx.input.setCatchBackKey(true);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (Gdx.input.isKeyPressed(Input.Keys.BACK)
				|| Gdx.input.isKeyPressed(Input.Keys.A)) {
			game.setScreen(game.menuScreen);
		}
		batch.begin();
		batch.draw(bg, 0, 0, Constants.width, Constants.height);
		AssetManager.getInstance().font.draw(batch, "" + (currentPage + 1),
				Constants.width / 2, Constants.height);
		batch.end();

		stage.draw();
		stage.act();
	}

	@Override
	public void dispose() {
		Gdx.app.log("wzb", "level dispose");
		batch.dispose();
		stage.dispose();

	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		y = Constants.height - y;
		for (LevelCell cell : group.array) {
			if (cell.isClicked(x, y) && cell.getLevel() != -1) {
				dispose();
				game.setScreen(new GameScreen(game, cell.getLevel()));
			}
		}
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		if (velocityX < 5f) {
			if (currentPage != 5) {
				++currentPage;
				nextGroup = new LevelCellGroup(1 + currentPage * 8);
				stage.addActor(nextGroup);
				nextGroup.setMoveInAction(LevelCell.LEFT);
				group.setMoveOutAction(LevelCell.LEFT);
				group = nextGroup;
			}
		} else if (velocityX > -5f) {
			if (currentPage != 0) {
				--currentPage;
				nextGroup = new LevelCellGroup(1 + currentPage * 8);
				stage.addActor(nextGroup);
				nextGroup.setMoveInAction(LevelCell.RIGHT);
				group.setMoveOutAction(LevelCell.RIGHT);
				group = nextGroup;
			}
		}
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}
}
