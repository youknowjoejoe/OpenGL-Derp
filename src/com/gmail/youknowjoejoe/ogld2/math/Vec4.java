package com.gmail.youknowjoejoe.ogld2.math;

public class Vec4 {
	private float[] coord;
	
	public Vec4(float[] coord){
		this.coord = coord;
	}
	
	public Vec4(float x, float y, float z, float w){
		this.coord = new float[]{x,y,z,w};
	}
	
	public Vec4(Vec3 v, float w){
		this.coord = new float[]{v.getX(),v.getY(),v.getZ(),w};
	}
	
	public Vec4(Vec2 v, float z, float w){
		this.coord = new float[]{v.getX(),v.getY(),z,w};
	}
	
	public Vec3 getVec3(){
		return new Vec3(getX(),getY(),getZ());
	}
	
	public Vec2 getVec2(){
		return new Vec2(getX(),getY());
	}
	
	public float getX(){
		return coord[0];
	}
	
	public float getY(){
		return coord[1];
	}
	
	public float getZ(){
		return coord[2];
	}
	
	public float getW(){
		return coord[3];
	}
	
	public float[] getCoord(){
		return coord;
	}
}
