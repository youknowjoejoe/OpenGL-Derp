package com.gmail.youknowjoejoe.ogld2.math;

public class Matrix33f {
	
	private static final int w = 3;
	//private static final int h = 3;
	
	public static final Matrix33f identity = new Matrix33f(new float[]{
			1,0,0,
			0,1,0,
			0,0,1
			});
	
	private float[] mm;
	
	public Matrix33f(float[] mm){
		this.mm = mm;
	}
	
	public float getDeterminant(){
		float det = 0;
		
		int row = 0;
		for(int rep = 0; rep < w; rep++){
			det+=get(row, rep)*getCofactor(row,rep);
		}
		
		return det;
	}
	
	public float getAssociatedSign(int row, int col){
		if((row+col)%2 == 0){
			return 1;
		}
		return -1;
	}
	
	public float getMinorMatrixDeterminant(int row, int col){
		int rows[] = null;
		if(row == 0)
			rows = new int[]{1,2};
		if(row == 1)
			rows = new int[]{0,2};
		if(row == 2)
			rows = new int[]{0,1};
		
		int cols[] = null;
		if(col == 0)
			cols = new int[]{1,2};
		if(col == 1)
			cols = new int[]{0,2};
		if(col == 2)
			cols = new int[]{0,1};
		
		return new Matrix22f(
				new float[]{
						get(rows[0],cols[0]),get(rows[0],cols[1]),
						get(rows[1],cols[0]),get(rows[1],cols[1])
						}).getDeterminant();
	}
	
	public float getCofactor(int row, int col){
		return getAssociatedSign(row,col)*getMinorMatrixDeterminant(row, col);
	}
	
	public float get(int a, int b){
		return mm[a*w+b];
	}
	
	public float[] getValues(){
		return mm;
	}
}
