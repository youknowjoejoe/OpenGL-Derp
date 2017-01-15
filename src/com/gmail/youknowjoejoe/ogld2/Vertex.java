package com.gmail.youknowjoejoe.ogld2;

/*public class Vertex {
	private float[] values;
	private boolean hasTexCoord = false;
	private boolean hasColor = false;
	
	public Vertex(Vec3 coord, Vec2 texCoord, Vec3 color){
		values = new float[8];
		values[0] = coord.getX();
		values[1] = coord.getY();
		values[2] = coord.getZ();
		values[3] = texCoord.getX();
		values[4] = texCoord.getY();
		values[5] = color.getX();
		values[6] = color.getY();
		values[7] = color.getZ();
		hasTexCoord = true;
		hasColor = true;
	}
	
	public Vertex(Vec3 coord, Vec2 texCoord){
		values = new float[5];
		values[0] = coord.getX();
		values[1] = coord.getY();
		values[2] = coord.getZ();
		values[3] = texCoord.getX();
		values[4] = texCoord.getY();
		hasTexCoord = true;
	}
	
	public Vertex(Vec3 coord, Vec3 color){
		values = new float[6];
		values[0] = coord.getX();
		values[1] = coord.getY();
		values[2] = coord.getZ();
		values[3] = color.getX();
		values[4] = color.getY();
		values[5] = color.getZ();
		hasColor = true;
	}
	
	public Vertex(Vec3 coord){
		values = coord.getCoord();
	}
	
	public float[] getValues(){
		return values;
	}
	
	public boolean hasTexCoord(){
		return hasTexCoord;
	}
	
	public boolean hasColor(){
		return hasColor;
	}
}*/

public class Vertex {
	
	private float[] values;
	private int[] valuePieceLengths;
	
	public Vertex(float[]... valuesArg){
		int length = 0;
		valuePieceLengths = new int[valuesArg.length];
		for(int rep = 0; rep < valuesArg.length; rep++){
			valuePieceLengths[rep] = valuesArg[rep].length;
			length+=valuesArg[rep].length;
		}
		
		this.values = new float[length];
		int loc = 0;
		for(float[] vals: valuesArg){
			for(int rep = 0; rep < vals.length; rep++){
				values[loc] = vals[rep];
				loc++;
			}
		}
	}
	
	public float[] getValues(){
		return values;
	}
	
	public int[] getValuePieceLengths(){
		return valuePieceLengths;
	}
}
