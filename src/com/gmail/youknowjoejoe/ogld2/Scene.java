package com.gmail.youknowjoejoe.ogld2;

import com.gmail.youknowjoejoe.ogld2.math.Matrix44f;

public class Scene {
	
	private Renderable[] objects;
	private Matrix44f viewMatrix;
	private Matrix44f projectionMatrix;
	
	private FlyingPlayer p;
	
	public Scene(Renderable[] objects, Matrix44f viewMatrix, Matrix44f projectionMatrix, FlyingPlayer p) {
		this.objects = objects;
		this.viewMatrix = viewMatrix;
		this.projectionMatrix = projectionMatrix;
		this.p = p;
	}
	
	public void update(float dt){
		p.update(dt);
		
		viewMatrix = p.getViewMatrix();
	}
	
	
	
	public void render(){
		for(Renderable r: objects){
			r.render(projectionMatrix,viewMatrix);
		}
	}
}
