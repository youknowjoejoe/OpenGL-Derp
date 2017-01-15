package com.gmail.youknowjoejoe.ogld2;

import org.lwjgl.opengl.*;

import com.gmail.youknowjoejoe.ogld2.math.Matrix44f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Renderable {
	private Vertex[] vertices;
	private int vboID;
	
	private byte[] elements;
	private int eboID;
	
	private Shader shader;
	private int vaoID;
	
	private Matrix44f modelMatrix;
	private int projectionMatrixLocation = -1;
	private int modelViewMatrixUniformLocation = -1;
	private int normalMatrixUniformLocation = -1;
	
	private Texture[] textures;
	private int[] textureUniformLocations;
	
	public Renderable(Vertex[] vertices, byte[] elements, Shader shader, Matrix44f modelMatrix, Texture[] textures){
		initialize(vertices,elements,shader,modelMatrix,textures);
	}
	
	//simply abstraction for creating renderable objects in OpenGL
	//WARNING: vertices must be consistent in regards to having texture coordinates or colors
	public void initialize(Vertex[] vertices, byte[] elements, Shader shader, Matrix44f modelMatrix, Texture[] textures){
		this.vertices = vertices;
		this.elements = elements;
		this.shader = shader;
		this.modelMatrix = modelMatrix;
		this.textures = textures;
		
		textureUniformLocations = new int[textures.length];
		for(int i =0; i < textures.length; i++){
			try {
				textureUniformLocations[i] = shader.createUniform(textures[i].getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//create and bind vao
		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);
		
		//make a single array of vertex data
		int stride = vertices[0].getValues().length;
		float[] vertexData = new float[vertices.length*stride];
		for(int i = 0; i < vertices.length; i++){
			for(int i2 = 0; i2 < stride; i2++){
				vertexData[i*stride + i2] = vertices[i].getValues()[i2];
			}
		}
		
		//make vbo to store vertex data
		vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, (vertexData), GL_STATIC_DRAW);
		
		//make ebo to store elements
		eboID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, Utilities.createByteBuffer(elements), GL_STATIC_DRAW);
		
		int offset = 0;
		for(int location = 0; location < vertices[0].getValuePieceLengths().length; location++){
			GL20.glVertexAttribPointer(location, vertices[0].getValuePieceLengths()[location], GL_FLOAT, false, stride*Utilities.floatSize, offset*Utilities.floatSize);
			GL20.glEnableVertexAttribArray(location);
			offset+=vertices[0].getValuePieceLengths()[location];
		}
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
		
		try {
			shader.createUniform("projectionMatrix");
			shader.createUniform("modelViewMatrix");
			//shader.createUniform("normalMatrix");
		} catch (Exception e) {
			e.printStackTrace();
		}
		projectionMatrixLocation = shader.getUniform("projectionMatrix");
		modelViewMatrixUniformLocation = shader.getUniform("modelViewMatrix");
		//normalMatrixUniformLocation = shader.getUniform("normalMatrix");
	}
	
	public Renderable(Vertex[] vertices, Shader shader, Matrix44f modelMatrix, Texture[] textures){
		byte[] elements = new byte[vertices.length];
		for(byte rep = 0; rep < elements.length; rep++){
			elements[rep] = rep;
		}
		initialize(vertices, elements, shader, modelMatrix, textures);
	}
	
	public void render(Matrix44f projectionMatrix, Matrix44f viewMatrix){
		shader.use();
		if(textures != null){
			for(int i = 0; i < textures.length; i++){
				glActiveTexture(GL_TEXTURE0+i);
				glBindTexture(GL_TEXTURE_2D, textures[i].getTexID());
				glUniform1i(textureUniformLocations[i],i);
			}
		}
		glUniformMatrix4fv(projectionMatrixLocation,true,projectionMatrix.getValues());
		glUniformMatrix4fv(modelViewMatrixUniformLocation,true,viewMatrix.times(modelMatrix).getValues());
		//glUniformMatrix4fv(normalMatrixUniformLocation,true,modelMatrix.excludeTranslation().getInverse().getTransposedValues());
		glBindVertexArray(vaoID);
		//glPolygonMode( GL_FRONT_AND_BACK, GL_LINE ); //used for wireframe mode
		glDrawElements(GL_TRIANGLES, elements.length, GL_UNSIGNED_BYTE, 0);
		glBindVertexArray(0);
		shader.unbind();
	}
}
