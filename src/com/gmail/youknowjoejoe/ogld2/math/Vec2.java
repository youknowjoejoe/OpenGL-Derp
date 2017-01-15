package com.gmail.youknowjoejoe.ogld2.math;

public class Vec2 {
	private float[] coord;
	
	public Vec2(float[] coord){
		this.coord = coord;
	}
	
	public Vec2(float x, float y){
		this.coord = new float[]{x,y};
	}
	
	public float getX(){
		return coord[0];
	}
	
	public float getY(){
		return coord[1];
	}
	
	public Vec2 plus(Vec2 v){
		return new Vec2(getX()+v.getX(),getY()+v.getY());
	}
	
	public Vec2 negative(){
		return new Vec2(-getX(),-getY());
	}
	
	public float getMagnitude(){
		return (float) Math.sqrt(this.getX()*this.getX()+this.getY()*this.getY());
	}
	
	public float[] getCoord(){
		return coord;
	}
}
