package com.july.lineclear;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Pools;

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
	public TextureRegion rest;
	public TextureRegion[] continueBtn;
	public TextureRegion[] returnBtn;
	public TextureRegion[] zan;

	// recored
	public boolean isSounding;
	public int maxLevel = 1;
	public int currentLevel = 1;
	public int totalLevel = 64;
	public Map<Integer, Vector<Integer>> record;
	public FileHandle file;

	// music
	public Music backMusic;
	public Music btnMusic;
	public Music winMusic;
	public Music loseMusic;
	public Music cheerMusic;
	public Sound removeMusic;
	public Sound flingMusic;

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

		Texture fonTexture2 = new Texture(Gdx.files.internal("font/num2.png"));
		fonTexture2.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		defaultFont = new BitmapFont(Gdx.files.internal("font/num2.fnt"),
				new TextureRegion(fonTexture2), false);
//		defaultFont = new BitmapFont();
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

		animals = new TextureRegion[16];
		for (int i = 1; i <= 16; i++)
			animals[i - 1] = atlas[1].findRegion("" + i);

		/*
		 * initial the dialog texture
		 */
		atlas[2] = new TextureAtlas(
				Gdx.files.internal("gfx/dialog/dialog.pack"));
		resultBg = atlas[2].findRegion("result");
		succeed = atlas[2].findRegion("succeed");
		rest = atlas[2].findRegion("rest");
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

		file = Gdx.files.local("data/.score");
		record = new HashMap<Integer, Vector<Integer>>();
		if (file.exists()) {
			loadRecord();
		} else {
			initRecord();
		}
	}

	public void loadMusic() {
		backMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/bg.ogg"));
		backMusic.setVolume(0.4f);
		btnMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/click.mp3"));
		winMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/win.ogg"));
		winMusic.setVolume(1);
		loseMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/fail.ogg"));
		loseMusic.setVolume(1);
		cheerMusic = Gdx.audio.newMusic(Gdx.files
				.internal("audio/congratulation.ogg"));
		removeMusic = Gdx.audio
				.newSound(Gdx.files.internal("audio/remove.ogg"));
		flingMusic = Gdx.audio.newSound(Gdx.files.internal("audio/change.ogg"));
	}

	public void loadRecord() {
		try {
			String strs = file.readString();
			String str[] = strs.split("\\n");
			String tag[] = str[0].split("\\$");
			isSounding = Boolean.parseBoolean(tag[0]);
			maxLevel = Integer.parseInt(tag[1]);
			currentLevel = maxLevel;
			totalLevel = Integer.parseInt(tag[2]);
			for (int i = 1; i <= totalLevel; i++) {
				tag = str[i].split("\\$");
				Vector<Integer> v = new Vector<Integer>();
				v.add(Integer.parseInt(tag[0]));
				v.add(Integer.parseInt(tag[1]));
				record.put(i, v);
			}
		} catch (Exception e) {
			initRecord();
		}
	}

	public void initRecord() {
		StringBuffer temp = Pools.obtain(StringBuffer.class);
		temp.append("true$1$64\n");
		for (int i = 1; i <= 64; i++) {
			temp.append("0$0\n");
		}
		file.writeString(temp.toString(), false);
		loadRecord();
	}

	public void updateRecord(int level, int score, int star) {
		if (currentLevel < totalLevel)
			currentLevel++;
		if (level == maxLevel && level < totalLevel)
			maxLevel++;

		boolean updated = false;
		if (record.get(level).get(0) < score) {
			record.get(level).setElementAt(score, 0);
			updated = true;
		}

		if (record.get(level).get(1) < star) {
			record.get(level).setElementAt(star, 1);
			updated = true;
		}

		if (updated)
			saveRecord();
	}

	public void saveRecord() {
		StringBuffer temp = Pools.obtain(StringBuffer.class);
		temp.append(isSounding + "$" + maxLevel + "$" + totalLevel + "\n");
		for (int i = 1; i <= totalLevel; i++) {
			temp.append(record.get(i).get(0) + "$" + record.get(i).get(1)
					+ "\n");
		}
		file.writeString(temp.toString(), false);
	}

	public void dispose() {
		atlas[0].dispose();
		atlas[1].dispose();
		atlas[2].dispose();
		font.dispose();
		defaultFont.dispose();

		backMusic.dispose();
		btnMusic.dispose();
		winMusic.dispose();
		cheerMusic.dispose();
		loseMusic.dispose();
		removeMusic.dispose();
		flingMusic.dispose();
	}
}
