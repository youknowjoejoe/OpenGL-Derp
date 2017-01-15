package com.gmail.youknowjoejoe.ogld2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.gmail.youknowjoejoe.ogld2.math.Vec2;
import com.gmail.youknowjoejoe.ogld2.math.Vec3;

public class Utilities {
	
	//thanks to @elliotforbes : https://github.com/elliotforbes/AlgebraTutorial/blob/master/src/Util/Utilities.java
	public static final int floatSize = 4;
	
	public static FloatBuffer createFloatBuffer(float[] array){
		FloatBuffer result = ByteBuffer.allocate(array.length * floatSize).order(ByteOrder.nativeOrder()).asFloatBuffer();
		result.put(array).flip();
		return result;
	}
	
	public static IntBuffer createIntBuffer(int[] array){
		IntBuffer result = ByteBuffer.allocateDirect(array.length *4).order(ByteOrder.nativeOrder()).asIntBuffer();
		result.put(array).flip();
		return result;
	}
	
	public static ByteBuffer createByteBuffer(byte[] array){
		ByteBuffer result = ByteBuffer.allocateDirect(array.length).order(ByteOrder.nativeOrder());
		result.put(array).flip();
		return result;
	}
}
