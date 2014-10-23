package com.july.lineclear;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class MainGame extends Game {

	@Override
	public void create() {
		// TODO Auto-generated method stub
		// first load the texture into RAM
		AssetManager.getInstance().loadTexture();
		// goto MenuScreen
		this.setScreen(new MenuScreen(this));
	}

	@Override
	public void dispose() {
		// dispose the texture
		Gdx.app.log("wzb", "dispose all the texture!");
		AssetManager.getInstance().dispose();
	}
}
