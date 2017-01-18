package com.gmail.youknowjoejoe.ogld2;

import com.gmail.youknowjoejoe.ogld2.math.Matrix44f;
import com.gmail.youknowjoejoe.ogld2.math.Vec2;
import com.gmail.youknowjoejoe.ogld2.math.Vec3;

public class FlyingPlayer {
	
	private Camera c;
	private KeyInputStructure keys;
	private MouseInputStructure mouse;
	
	private float radiansPerSecond = 0.005f;
	private float unitsPerSecond = 0.06f;
	
	public FlyingPlayer(Camera c){
		this.c = c;
		this.keys = new KeyInputStructure();
		this.mouse = new MouseInputStructure();
	}
	
	public KeyInputStructure getKeys(){
		return keys;
	}
	
	public MouseInputStructure getMouse(){
		return mouse;
	}
	
	public void update(float dt){
		/*if(keys.isUpDown())
			c.rotate(dt*radiansPerSecond, 0);
		if(keys.isDownDown())
			c.rotate(-dt*radiansPerSecond, 0);
		if(keys.isRightDown())
			c.rotate(0,-dt*radiansPerSecond);
		if(keys.isLeftDown())
			c.rotate(0,dt*radiansPerSecond);*/
		
		Vec2 mouseDelta = mouse.getDelta();
		c.rotate(-mouseDelta.getY()*radiansPerSecond, -mouseDelta.getX()*radiansPerSecond);
		
		Vec3 direction = c.getDirection();
		Vec3 movement = Vec3.zero;
		
		if(!(keys.isADown() || keys.isDDown())){
			if(keys.isWDown() && !keys.isSDown()){
				movement = direction;
			} else if (keys.isSDown() && !keys.isWDown()){
				movement = direction.negative();
			}
		} else {
			Vec3 right = Vec3.vertical.cross(direction).normalized();
			
			if(keys.isWDown())
				movement = movement.plus(direction);
			if(keys.isSDown())
				movement = movement.plus(direction.negative());
			if(keys.isADown())
				movement = movement.plus(right);
			if(keys.isDDown())
				movement = movement.plus(right.negative());
			
			if(!movement.equals(movement.zero)){
				movement = movement.normalized();
			}
		}
		
		c.translate(movement.scaledBy(unitsPerSecond));
	}
	
	public Matrix44f getViewMatrix(){
		return c.getViewMatrix();
	}
	
}
