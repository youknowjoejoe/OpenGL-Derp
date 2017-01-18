package com.gmail.youknowjoejoe.ogld2.math;

public class Matrix44f {
	
	private static final int w = 4;
	//private static final int h = 4;
	
	public static final Matrix44f identity = new Matrix44f(new float[]{
			1,0,0,0,
			0,1,0,0,
			0,0,1,0,
			0,0,0,1
			});
	
	private float[] mm;
	private float[] transposed;
	
	public Matrix44f(float[] mm){
		this.mm = mm;
	}
	
	public static Matrix44f getRotation(float theta, Direction d){
		float[] mm = null;
		switch(d){
		case X:
			mm = new float[]{
					1,0,0,0,
					0,(float)Math.cos(theta),-(float)Math.sin(theta),0,
					0,(float)Math.sin(theta),(float)Math.cos(theta),0,
					0,0,0,1
					};
			break;
		case Y:
			mm = new float[]{
					(float)Math.cos(theta),0,(float)Math.sin(theta),0,
					0,1,0,0,
					-(float)Math.sin(theta),0,(float)Math.cos(theta),0,
					0,0,0,1
					};
			break;
		case Z:
			mm = new float[]{
					(float)Math.cos(theta),-(float)Math.sin(theta),0,0,
					(float)Math.sin(theta),(float)Math.cos(theta),0,0,
					0,0,1,0,
					0,0,0,1};
			break;
		}
		
		return new Matrix44f(mm);
	}
	
	public static Matrix44f getRotation(float cosX, float sinX, float cosY, float sinY, float cosZ, float sinZ){
		return new Matrix44f(new float[]{
				cosY*cosZ,-cosZ*sinX*sinY-cosX*sinZ,cosX*cosZ*sinY+sinX*sinZ,0,
				cosY*sinZ, cosX*cosZ+sinX*sinY*sinZ, cosX*sinY*sinZ-cosZ*sinX,0,
				-sinY, cosY*sinX, cosX*cosY,0,
				0,0,0,1
		});
	}
	
	public static Matrix44f getRotation(float pitch, float yaw, float roll){
		return getRotation((float) Math.cos(pitch),(float) Math.sin(pitch),(float) Math.cos(yaw),(float) Math.sin(yaw),(float) Math.cos(roll),(float) Math.sin(roll));
	}
	
	public enum Direction{
		X,Y,Z
	}
	
	public static Matrix44f getTranslation(Vec3 tx){
		float[] mm = new float[] {
				1,0,0,tx.getX(),
				0,1,0,tx.getY(),
				0,0,1,tx.getZ(),
				0,0,0,1
		};
		return new Matrix44f(mm);
	}
	
	public static Matrix44f getScale(Vec3 sx){
		return new Matrix44f(new float[]{
				sx.getX(),0,0,0,
				0,sx.getY(),0,0,
				0,0,sx.getZ(),0,
				0,0,0,1
		});
	}
	
	public static Matrix44f getPerspective(float fov, float aspectRatio, float near, float far){
		float[] mm = new float[]{
				(float) (1.0f/(aspectRatio*Math.tan(fov/2.0))),0,0,0,
				0,(float) (1.0f/Math.tan(fov/2.0)), 0,0,
				0,0,-(far+near)/(far-near),-(2.0f*far*near)/(far-near),
				0,0,-1.0f,0
				};
		return new Matrix44f(mm);
	}
	
	public Matrix44f times(Matrix44f m){
		return new Matrix44f(new float[]{
				dot(0,0,m),dot(0,1,m),dot(0,2,m),dot(0,3,m),
				dot(1,0,m),dot(1,1,m),dot(1,2,m),dot(1,3,m),
				dot(2,0,m),dot(2,1,m),dot(2,2,m),dot(2,3,m),
				dot(3,0,m),dot(3,1,m),dot(3,2,m),dot(3,3,m)
		});
	}
	
	public float dot(int row, int col, Matrix44f m){
		return get(row,0)*m.get(0, col)+get(row,1)*m.get(1, col)+get(row,2)*m.get(2, col)+get(row,3)*m.get(3, col);
	}
	
	public Vec4 times(Vec4 v){
		return new Vec4(dot(0,v),dot(1,v),dot(2,v),dot(3,v));
	}
	
	public float dot(int row, Vec4 v){
		return get(row,0)*v.getX()+get(row,1)*v.getY()+get(row,2)*v.getZ()+get(row,3)*v.getW();
	}
	
	public Matrix44f scaledBy(float f){
		return new Matrix44f(new float[]{
				f*get(0,0),f*get(0,1),f*get(0,2),f*get(0,3),
				f*get(1,0),f*get(1,1),f*get(1,2),f*get(1,3),
				f*get(2,0),f*get(2,1),f*get(2,2),f*get(2,3),
				f*get(3,0),f*get(3,1),f*get(3,2),f*get(3,3)
		});
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
	
	public float getMinorMatrixDeterminant(int row, int col){ //row and column blocked off
		int[] rows = null;
		if(row == 0){
			rows = new int[]{1,2,3};
		}
		if(row == 1){
			rows = new int[]{0,2,3};
		}
		if(row == 2){
			rows = new int[]{0,1,3};
		}
		if(row == 3){
			rows = new int[]{0,1,2};
		}
		int[] cols = null;
		if(col == 0){
			cols = new int[]{1,2,3};
		}
		if(col == 1){
			cols = new int[]{0,2,3};
		}
		if(col == 2){
			cols = new int[]{0,1,3};
		}
		if(col == 3){
			cols = new int[]{0,1,2};
		}
		return new Matrix33f(new float[]{
				get(rows[0],cols[0]),get(rows[0],cols[1]),get(rows[0],cols[2]),
				get(rows[1],cols[0]),get(rows[1],cols[1]),get(rows[1],cols[2]),
				get(rows[2],cols[0]),get(rows[2],cols[1]),get(rows[2],cols[2])
		}).getDeterminant();
	}
	
	public float getCofactor(int row, int col){
		return getAssociatedSign(row,col)*getMinorMatrixDeterminant(row, col);
	}
	
	public Matrix44f getInverse(){
		return new Matrix44f(new float[]{
				getCofactor(0,0),getCofactor(1,0),getCofactor(2,0),getCofactor(3,0),
				getCofactor(0,1),getCofactor(1,1),getCofactor(2,1),getCofactor(3,1),
				getCofactor(0,2),getCofactor(1,2),getCofactor(2,2),getCofactor(3,2),
				getCofactor(0,3),getCofactor(1,3),getCofactor(2,3),getCofactor(3,3),
		}).scaledBy(1.0f/this.getDeterminant()); //transposed cofactorMatrix scaled by 1/detA
	}
	
	public Matrix44f excludeTranslation(){
		float[] mm2 = mm.clone();
		mm2[3] = 0;
		mm2[7] = 0;
		mm2[11] = 0;
		mm2[12] = 0;
		mm2[13] = 0;
		mm2[14] = 0;
		return new Matrix44f(mm2);
	}
	
	public float get(int a, int b){
		return mm[a*w+b];
	}
	
	public float[] getValues(){
		return mm;
	}
	
	public float[] getTransposedValues(){
		if(transposed == null){
			transposed = new float[16];
			for(int rep = 0; rep < 4; rep++){
				transposed[rep] = mm[rep*4+0];
				transposed[4+rep] = mm[rep*4+1];
				transposed[8+rep] = mm[rep*4+2];
				transposed[12+rep] = mm[rep*4+3];
			}
		}
		return transposed;
	}
	
	
	@Override
	public String toString(){
		String str = "[";
		for(int rep = 0; rep < 16; rep+=4){
			str += "[";
			for(int rep2 = 0; rep2 < 4; rep2++){
				str+= mm[rep+rep2];
				if(rep2 != 3){
					str+=",";
				}
			}
			str += "]";
			if(rep < 12){
				str+= ",";
			}
		}
		str+= "]";
		return str;
	}
}
