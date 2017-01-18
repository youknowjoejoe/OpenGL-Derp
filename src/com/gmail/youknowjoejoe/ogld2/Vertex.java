package com.gmail.youknowjoejoe.ogld2;

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
