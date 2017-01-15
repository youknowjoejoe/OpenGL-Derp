package com.gmail.youknowjoejoe.ogld2;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWKeyCallbackI;

public class KeyInputStructure implements GLFWKeyCallbackI{
	
	private boolean rightDown = false;
	private boolean leftDown = false;
	private boolean upDown = false;
	private boolean downDown = false;
	private boolean wDown = false;
	private boolean aDown = false;
	private boolean dDown = false;
	private boolean sDown = false;
	
	public boolean isRightDown() {
		return rightDown;
	}

	public boolean isLeftDown() {
		return leftDown;
	}

	public boolean isUpDown() {
		return upDown;
	}

	public boolean isDownDown() {
		return downDown;
	}

	public boolean isWDown() {
		return wDown;
	}

	public boolean isADown() {
		return aDown;
	}

	public boolean isDDown() {
		return dDown;
	}

	public boolean isSDown() {
		return sDown;
	}
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
			glfwSetWindowShouldClose(window, true);
		if ( key == GLFW_KEY_UP && action == GLFW_PRESS ){
			upDown = true;
		}
		if ( key == GLFW_KEY_DOWN && action == GLFW_PRESS ){
			downDown = true;
		}
		if ( key == GLFW_KEY_RIGHT && action == GLFW_PRESS ){
			rightDown = true;
		}
		if ( key == GLFW_KEY_LEFT && action == GLFW_PRESS ){
			leftDown = true;
		}
		if ( key == GLFW_KEY_W && action == GLFW_PRESS ){
			wDown = true;
		}
		if ( key == GLFW_KEY_A && action == GLFW_PRESS ){
			aDown = true;
		}
		if ( key == GLFW_KEY_D && action == GLFW_PRESS ){
			dDown = true;
		}
		if ( key == GLFW_KEY_S && action == GLFW_PRESS ){
			sDown = true;
		}
		
		if ( key == GLFW_KEY_UP && action == GLFW_RELEASE ){
			upDown = false;
		}
		if ( key == GLFW_KEY_DOWN && action == GLFW_RELEASE ){
			downDown = false;
		}
		if ( key == GLFW_KEY_RIGHT && action == GLFW_RELEASE ){
			rightDown = false;
		}
		if ( key == GLFW_KEY_LEFT && action == GLFW_RELEASE ){
			leftDown = false;
		}
		if ( key == GLFW_KEY_W && action == GLFW_RELEASE ){
			wDown = false;
		}
		if ( key == GLFW_KEY_A && action == GLFW_RELEASE ){
			aDown = false;
		}
		if ( key == GLFW_KEY_D && action == GLFW_RELEASE ){
			dDown = false;
		}
		if ( key == GLFW_KEY_S && action == GLFW_RELEASE ){
			sDown = false;
		}
	}

}
