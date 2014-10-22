package com.july.lineclear;

/**
 * resource management class. Use single instance design model
 * @author july
 *
 */
public class AssetManager {
	public static AssetManager instance;

	private AssetManager() {

	}

	public static AssetManager getInstance() {
		if(instance == null)
			instance = new AssetManager();
		
		return instance;
	}

	public void loadTexture(){
		
	}
	
	public void loadMusic(){
		
	}
	
	public void dispose(){
		
	}
}
