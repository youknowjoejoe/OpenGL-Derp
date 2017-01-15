package com.gmail.youknowjoejoe.ogld2.math;

public class Matrix22f {
	
	public static final Matrix22f identity = new Matrix22f(new float[]{
			1,0,0,
			0,1,0,
			0,0,1
			});
	
	private float[] mm;
	
	public Matrix22f(float[] mm){
		this.mm = mm;
	}
	
	public float getDeterminant(){
		return (get(0,0)*get(1,1))-(get(1,0)*get(0,1));
	}
	
	public float get(int a, int b){
		return mm[a*2+b];
	}
	
	public float[] getValues(){
		return mm;
	}
}
