package com.july.lineclear;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MenuScreen extends ScreenAdapter {
	MainGame game;
	private SpriteBatch batch;
	private Stage stage;
	private TextureRegion bg, title;
	private Image start, more, exit, sound;
	final TextureRegionDrawable trd[] = {
			new TextureRegionDrawable(AssetManager.getInstance().soundOn),
			new TextureRegionDrawable(AssetManager.getInstance().soundOff) };

	public MenuScreen(MainGame game) {
		super();
		this.game = game;
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		stage = new Stage();

		bg = AssetManager.getInstance().menuBg;
		title = AssetManager.getInstance().title;
		exit = new Image(AssetManager.getInstance().exit);
		exit.setBounds(Constants.menuBtnX, Constants.exitBtnY,
				Constants.menuBtnWidth, Constants.menuBtnHeight);
		exit.addListener(clickListener);
		stage.addActor(exit);

		more = new Image(AssetManager.getInstance().more);
		more.setBounds(Constants.menuBtnX, Constants.exitBtnY
				+ Constants.menuBtnAddY, Constants.menuBtnWidth,
				Constants.menuBtnHeight);
		more.addListener(clickListener);
		stage.addActor(more);

		start = new Image(AssetManager.getInstance().start);
		start.setBounds(Constants.menuBtnX,
				more.getY() + Constants.menuBtnAddY, Constants.menuBtnWidth,
				Constants.menuBtnHeight);
		start.addListener(clickListener);
		stage.addActor(start);

		sound = new Image(AssetManager.getInstance().isSounding ? trd[0]
				: trd[1]);
		sound.setBounds(Constants.soundX, Constants.soundY,
				Constants.soundWidth, Constants.soundHeight);
		sound.addListener(clickListener);
		stage.addActor(sound);

		Gdx.input.setInputProcessor(stage);
		Gdx.input.setCatchBackKey(true);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(bg, 0, 0, Constants.width, Constants.height);
		batch.draw(title, Constants.titleX, Constants.titleY,
				Constants.titleWidth, Constants.titleHeight);
		batch.end();

		stage.draw();
	}

	@Override
	public void dispose() {
		// Gdx.app.log("wzb", "menuscreen dispose");
		batch.dispose();
		stage.dispose();
	}

	public ClickListener clickListener = new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y) {
			// button click sound
			SoundManager.getInstance().play(SoundManager.btnMusic);
			if (start == event.getListenerActor()) {
				game.setScreen(new LevelScreen(game));
			} else if (more == event.getListenerActor()) {
				// show the score wall
				game.main.showAdStatic(Constants.MORE);
			} else if (exit == event.getListenerActor()) {
				// exit the game
				game.main.showAdStatic(Constants.CHAPIN);
				game.main.showAdStatic(Constants.EXIT);
			} else if (sound == event.getListenerActor()) {
				// turn on the sound or turn off the sound
				if (AssetManager.getInstance().isSounding) {
					SoundManager.getInstance().closeMusic();
					sound.setDrawable(trd[1]);
				} else {
					SoundManager.getInstance().openMusic();
					sound.setDrawable(trd[0]);
				}
				AssetManager.getInstance().saveRecord();
			}
		}
	};
}
