package com.july.lineclear;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class LineActor extends Actor {
	Array<Vector2> array;
	
	public LineActor(Array<Vector2>array){
		this.array = array;
	}
	
}
