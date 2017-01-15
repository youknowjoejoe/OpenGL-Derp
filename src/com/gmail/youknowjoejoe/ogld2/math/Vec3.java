package com.gmail.youknowjoejoe.ogld2.math;

import java.util.Arrays;

public class Vec3 {
	
	public static Vec3 vertical = new Vec3(0,1,0);
	public static Vec3 zero = new Vec3(0,0,0);
	
	private float[] coord;
	
	public Vec3(float[] coord){
		this.coord = coord;
	}
	
	public Vec3(float x, float y, float z){
		this.coord = new float[]{x,y,z};
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
	
	public float[] getCoord(){
		return coord;
	}
	
	public Vec3 plus(Vec3 v){
		return new Vec3(getX()+v.getX(),getY()+v.getY(),getZ()+v.getZ());
	}
	
	public Vec3 cross(Vec3 v){
		return new Vec3(
				getY()*v.getZ()-getZ()*v.getY(),
				getZ()*v.getX()-getX()*v.getZ(),
				getX()*v.getY()-getY()*v.getX()
				);
	}
	
	public Vec3 negative(){
		return new Vec3(-getX(),-getY(),-getZ());
	}
	
	public Vec3 normalized(){
		float m = getMagnitude();
		return new Vec3(getX()/m,getY()/m,getZ()/m);
	}
	
	public float getMagnitude(){
		return (float) Math.sqrt(getX()*getX()+getY()*getY()+getZ()*getZ());
	}
	
	public Vec3 scaledBy(float f){
		return new Vec3(f*getX(),f*getY(),f*getZ());
	}
	
	@Override
	public String toString() {
		return "<"+getX()+","+getY()+","+getZ()+">";
	}

	@Override
	public boolean equals(Object obj){
		if(this == obj){
			return true;
		}
		Vec3 v = (Vec3) obj;
		return getX()==v.getX()&&getY()==v.getY()&&getZ()==v.getZ();
	}
}
