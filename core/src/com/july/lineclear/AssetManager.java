package com.july.lineclear;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * resource management class. Use single instance design model
 * 
 * @author july
 * 
 */
public class AssetManager {
	public static AssetManager instance;

	public TextureAtlas[] atlas;
	// menu resource
	public TextureRegion menuBg;
	public TextureRegion title;
	public TextureRegion lian;
	public TextureRegion meng;
	public TextureRegion start;
	public TextureRegion more;
	public TextureRegion exit;
	public TextureRegion soundOn;
	public TextureRegion soundOff;

	// level resource
	public TextureRegion[] stars;
	public TextureRegion levelBgLock;
	public TextureRegion levelBg;
	public TextureRegion go;

	// game resource
	public TextureRegion bg;
	public TextureRegion timeBg;
	public TextureRegion[] timeFill;

	public TextureRegion level; // level box image to show the current level number
	public TextureRegion score; // like the upper
	public TextureRegion best;
	public TextureRegion pause;

	public TextureRegion selected;
	public TextureRegion[] animals;
	public TextureRegion[] line;

	// result resource
	public TextureRegion resultBg;
	public TextureRegion succeed;
	public TextureRegion failed;
	public TextureRegion[] continueBtn;
	public TextureRegion[] returnBtn;
	public TextureRegion[] zan;

	private AssetManager() {

	}

	public static AssetManager getInstance() {
		if (instance == null)
			instance = new AssetManager();

		return instance;
	}

	public void loadTexture() {
		atlas = new TextureAtlas[3];
		atlas[0] = new TextureAtlas(Gdx.files.internal("gfx/menu/menu.pack"));
		menuBg = atlas[0].findRegion("menubg");
		title = atlas[0].findRegion("title");
		lian = atlas[0].findRegion("lian");
		meng = atlas[0].findRegion("meng");
		start = atlas[0].findRegion("start");
		more = atlas[0].findRegion("more");
		exit = atlas[0].findRegion("exit");
		soundOn = atlas[0].findRegion("menu_sound_on");
		soundOff = atlas[0].findRegion("menu_sound_off");

		levelBg = atlas[0].findRegion("levelbg");
		levelBgLock = atlas[0].findRegion("levelbg_lock");
		stars = new TextureRegion[2];
		stars[0] = atlas[0].findRegion("star", 0);
		stars[1] = atlas[0].findRegion("star", 1);
		go = atlas[0].findRegion("go");
		
		/*
		 * initial the game texture
		 */
		atlas[1] = new TextureAtlas(Gdx.files.internal("gfx/game/game.pack"));
		bg = atlas[1].findRegion("bg");
		timeBg = atlas[1].findRegion("time_bg");
		timeFill = new TextureRegion[2];
		timeFill[0] = atlas[1].findRegion("time_fill", 1);
		timeFill[1] = atlas[1].findRegion("time_fill", 2);
		score = atlas[1].findRegion("score");
		best = atlas[1].findRegion("best");
		level = atlas[1].findRegion("level");
		pause = atlas[1].findRegion("pause");
		selected = atlas[1].findRegion("select");
		
		line = new TextureRegion[6];
		for (int i = 0; i < 6; i++)
			line[i] = atlas[1].findRegion("l", i);

		animals = new TextureRegion[10];
		for(int i = 1; i < 11; i++)
			animals[i - 1] = atlas[1].findRegion("" + i);
		
		/*
		 * initial the dialog texture
		 */
		atlas[2] = new TextureAtlas(
				Gdx.files.internal("gfx/dialog/dialog.pack"));
		resultBg = atlas[2].findRegion("result");
		succeed = atlas[2].findRegion("succeed");
		failed = atlas[2].findRegion("fail");
		continueBtn = new TextureRegion[2];
		continueBtn[0] = atlas[0].findRegion("continue", 0);
		continueBtn[1] = atlas[0].findRegion("continue", 1);
		returnBtn = new TextureRegion[2];
		returnBtn[0] = atlas[0].findRegion("return", 0);
		returnBtn[1] = atlas[1].findRegion("return", 1);
		zan = new TextureRegion[5];
		for(int i = 0; i < 5; i++)
			zan[i] = atlas[1].findRegion("zan", i);
		
	}

	public void loadMusic() {

	}

	public void dispose() {
		atlas[0].dispose();
		atlas[1].dispose();
		atlas[2].dispose();
	}
}
