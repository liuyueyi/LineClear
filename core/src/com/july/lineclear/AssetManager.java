package com.july.lineclear;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

/**
 * resource management class. Use single instance design model
 * 
 * @author july
 * 
 */
public class AssetManager {
	public static AssetManager instance;

	public BitmapFont font;
	public LabelStyle style, scoreStyle;
	public BitmapFont defaultFont;

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

	public TextureRegion level; // level box image to show the current level
								// number
	public TextureRegion score; // like the upper
	public TextureRegion best;
	public TextureRegion pause;

	public TextureRegion selected;
	public TextureRegion[] animals;
	public TextureRegion[] hLine;
	public TextureRegion[] vLine;

	// result resource
	public TextureRegion resultBg;
	public TextureRegion succeed;
	public TextureRegion failed;
	public TextureRegion[] continueBtn;
	public TextureRegion[] returnBtn;
	public TextureRegion[] zan;

	// recored
	public int maxLevel = 30;
	public int currentLevel = 1;
	public Map<Integer, Vector<Integer>> record;

	private AssetManager() {

	}

	public static AssetManager getInstance() {
		if (instance == null)
			instance = new AssetManager();

		return instance;
	}

	public void loadTexture() {
		Texture fonTexture = new Texture(Gdx.files.internal("font/font.png"));
		fonTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		font = new BitmapFont(Gdx.files.internal("font/font.fnt"),
				new TextureRegion(fonTexture), false);
		font.setScale(Constants.hrate);
		scoreStyle = new LabelStyle(font, font.getColor());

		defaultFont = new BitmapFont();
		style = new LabelStyle(defaultFont, Color.WHITE);

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

		vLine = new TextureRegion[3];
		for (int i = 0; i < 3; i++)
			vLine[i] = atlas[1].findRegion("l", i);
		hLine = new TextureRegion[3];
		for (int i = 3; i < 6; i++)
			hLine[i - 3] = atlas[1].findRegion("l", i);

		animals = new TextureRegion[10];
		for (int i = 1; i < 11; i++)
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
		continueBtn[0] = atlas[2].findRegion("continue", 0);
		continueBtn[1] = atlas[2].findRegion("continue", 1);
		returnBtn = new TextureRegion[2];
		returnBtn[0] = atlas[2].findRegion("return", 0);
		returnBtn[1] = atlas[2].findRegion("return", 1);
		zan = new TextureRegion[5];
		for (int i = 0; i < 5; i++)
			zan[i] = atlas[2].findRegion("zan", i);

		record = new HashMap<Integer, Vector<Integer>>();
		loadRecord();
	}

	public void loadMusic() {

	}

	public void loadRecord() {
		maxLevel = 30;
		currentLevel = 30;
		for (int i = 0; i < 48; i++) {
			Vector<Integer> v = new Vector<Integer>();
			v.add((i + 1) * 2000);
			v.add((int) (Math.random() * 3 + 1));
			record.put(i + 1, v);
		}
	}

	public void updateRecord(int level, int score, int star) {
		currentLevel++;
		if (level == maxLevel && level < 48)
			maxLevel++;

		boolean updated = false;
		if (record.get(level).indexOf(0) < score) {
			record.get(level).setElementAt(score, 0);
			updated = true;
		}

		if (record.get(level).indexOf(1) < star) {
			record.get(level).setElementAt(star, 1);
			updated = true;
		}

		if (updated)
			saveRecord();
	}

	public void saveRecord() {

	}

	public void dispose() {
		atlas[0].dispose();
		atlas[1].dispose();
		atlas[2].dispose();
		font.dispose();
		defaultFont.dispose();
	}
}
