package com.july.lineclear;

import com.badlogic.gdx.Game;

public class MainGame extends Game {

	MenuScreen menuScreen;

	@Override
	public void create() {
		// TODO Auto-generated method stub
		// first load the texture into RAM
		AssetManager.getInstance().loadTexture();
		SoundManager.getInstance().play(0);
		// goto MenuScreen
		menuScreen = new MenuScreen(this);
		this.setScreen(menuScreen);
	}

	@Override
	public void dispose() {
		// dispose the texture
		// Gdx.app.log("wzb", "dispose all the texture!");
		AssetManager.getInstance().dispose();
		menuScreen.dispose();
	}
}
