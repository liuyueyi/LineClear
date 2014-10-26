package com.july.lineclear;

public class SoundManager {
	private static SoundManager instance;

	public static final int btnMusic = 1;
	public static final int removeMusic = 2;
	public static final int succeedMusic = 3;
	public static final int failedMusic = 4;
	public static final int cheerMusic = 5;
	public static final int flingMusic = 6;

	private SoundManager() {
		if (AssetManager.getInstance().backMusic == null)
			AssetManager.getInstance().loadMusic();

		if (AssetManager.getInstance().isSounding)
			AssetManager.getInstance().backMusic.play();
	}

	public static SoundManager getInstance() {
		if (null == instance)
			instance = new SoundManager();
		return instance;
	}

	public void play(int type) {
		if (!AssetManager.getInstance().isSounding)
			return;

		switch (type) {
		case btnMusic:
			AssetManager.getInstance().btnMusic.play();
			break;
		case succeedMusic:
			AssetManager.getInstance().winMusic.play();
			break;
		case failedMusic:
			AssetManager.getInstance().loseMusic.play();
			break;
		case removeMusic:
			AssetManager.getInstance().removeMusic.play();
			break;
		case cheerMusic:
			AssetManager.getInstance().cheerMusic.play();
			break;
		case flingMusic:
			AssetManager.getInstance().flingMusic.play();
		}
	}

	public void closeMusic() {
		AssetManager.getInstance().isSounding = false;
		AssetManager.getInstance().backMusic.stop();
	}

	public void openMusic() {
		AssetManager.getInstance().isSounding = true;
		AssetManager.getInstance().backMusic.play();
	}
}
