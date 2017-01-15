package com.gmail.youknowjoejoe.ogld2;

import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.util.HashMap;
import java.util.Scanner;

public class Shader {
	private int shaderProgram;
	
	private HashMap<String, Integer> uniforms;
	
	public Shader(String vsPath, String fsPath){
		uniforms = new HashMap<String, Integer>();
		String vertexShader = getStringFromFile(vsPath);
		String fragmentShader = getStringFromFile(fsPath);
		
		int vsID = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vsID,vertexShader);
		glCompileShader(vsID);
		if(glGetShaderi(vsID, GL_COMPILE_STATUS) == GL_FALSE){
			System.err.println("Failed to compile vertex shader");
			System.err.println(glGetShaderInfoLog(vsID));
		}
		
		int fsID = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fsID,fragmentShader);
		glCompileShader(fsID);
		if(glGetShaderi(fsID, GL_COMPILE_STATUS) == GL_FALSE){
			System.err.println("Failed to compile fragment shader");
			System.err.println(glGetShaderInfoLog(fsID));
		}
		
		shaderProgram = glCreateProgram();
		glAttachShader(shaderProgram, vsID);
		glAttachShader(shaderProgram, fsID);
		
		glLinkProgram(shaderProgram);
		//how do you see linking log?
		if(GL20.glGetProgrami(shaderProgram, GL_LINK_STATUS) == GL_FALSE){
			System.out.println("Failed to link shaders");
			System.out.println(glGetProgramInfoLog(shaderProgram));
		}
		
		glDeleteShader(vsID);
		glDeleteShader(fsID);
	}
	
	public String getStringFromFile(String path){
		Scanner sc = new Scanner(this.getClass().getResourceAsStream(path));
		String text = "";
		
		while(sc.hasNext()){
			text += sc.nextLine() + "\n";
		}
		sc.close();
		
		return text;
	}
	
	public void use(){
		glUseProgram(shaderProgram);
	}
	
	public int getShaderProgramID(){
		return shaderProgram;
	}
	
	public void unbind(){
		glUseProgram(0);
	}
	
	public int createUniform(String uniformName) throws Exception {
	    int uniformLocation = glGetUniformLocation(shaderProgram,uniformName);
	    if (uniformLocation < 0) {
	        throw new Exception("Could not find uniform:" +
	            uniformName);
	    }
	    uniforms.put(uniformName, uniformLocation);
	    return uniformLocation;
	}
	
	public int getUniform(String uniformName) {
		return uniforms.get(uniformName);
	}
}
