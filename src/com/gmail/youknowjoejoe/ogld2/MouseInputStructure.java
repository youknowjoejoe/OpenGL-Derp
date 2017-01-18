package com.gmail.youknowjoejoe.ogld2;

import org.lwjgl.glfw.GLFWCursorPosCallbackI;

import com.gmail.youknowjoejoe.ogld2.math.Vec2;

public class MouseInputStructure implements GLFWCursorPosCallbackI {
	private Vec2 oldPos = null;
	private Vec2 currentPos = null;
	private boolean resetOld = false;
	
	@Override
    public void invoke(long window, double xpos, double ypos) {
		if(currentPos == null){
			currentPos = new Vec2((float)xpos, (float)ypos);
			oldPos = currentPos;
		}
		currentPos = new Vec2((float)xpos,(float)ypos);
		if(resetOld){
			oldPos = currentPos;
		}
	}
	
	public void reset(){
		resetOld = true;
	}
	
	public Vec2 getDelta(){
		if(currentPos == null) return new Vec2(0,0);
		Vec2 result = currentPos.plus(oldPos.negative());
		oldPos = currentPos;
		return result;
	}
	
	public Vec2 getPos(){
		if(currentPos == null){
			return new Vec2(0,0);
		}
		return currentPos;
	}
}
