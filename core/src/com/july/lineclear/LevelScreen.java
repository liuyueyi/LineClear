package com.july.lineclear;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class LevelScreen extends ScreenAdapter {
	MainGame game;
	
	private TextureRegion bg;
	private SpriteBatch batch;
	private Stage stage;
	//private ArrayList<LevelCell> arrays;
	
	public LevelScreen(MainGame game){
		this.game = game;
	}
	
	
	@Override
	public void show() {
		batch = new SpriteBatch();
		stage = new Stage();
		bg = AssetManager.getInstance().menuBg;
		
		LevelCell cell = new LevelCell();
		cell.setPosition(20, 100);
		stage.addActor(cell);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(bg, 0, 0, Constants.width, Constants.height);
		batch.end();
		
		stage.draw();
	}

	@Override
	public void dispose() {
		batch.dispose();

	}
}
