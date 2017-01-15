package com.gmail.youknowjoejoe.ogld2;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL30.*;

public class Texture {
	
	private int width, height;
	private int texID;
	
	private String name;
	
	public Texture(String name, String path, int textureWrap){
		this(name, path, textureWrap, GL_LINEAR);
	}
	
	public Texture(String name, String path){
		this(name, path, GL_CLAMP_TO_EDGE);
	}
	
	public Texture(String name, String path, int textureWrap, int upScale){
		this.name = name;
		
		int[] pixels = null;
		try {
			BufferedImage image = ImageIO.read(this.getClass().getResourceAsStream(path));
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width*height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch(IOException e){
			e.printStackTrace();
		}
		
		//puts alpha at end rather than beginning, and flips the image upside down (so it starts at bottom left)
		int[] data = new int[width*height];
		for(int i = width*(height-1); i > 0; i-=width){
			for(int i2 = 0; i2 < width; i2++){
				int loc1 = i+i2;
				int loc2 = width*height-i+i2;
				int a = (pixels[loc2] & 0xff000000) >> 24;
				int r = (pixels[loc2] & 0xff0000) >> 16;
				int g = (pixels[loc2] & 0xff00) >> 8;
				int b = (pixels[loc2] & 0xff);
				
				data[loc1] = a << 24 | b << 16 | g << 8 | r;
			}
		}
		/*for(int i = 0; i < width*height; i++){
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0xff0000) >> 16;
			int g = (pixels[i] & 0xff00) >> 8;
			int b = (pixels[i] & 0xff);
			
			data[i] = a << 24 | b << 16 | g << 8 | r;
		}*/
		
		texID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texID);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, textureWrap);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, textureWrap);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, upScale);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
		glGenerateMipmap(GL_TEXTURE_2D);
		
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public int getTexID(){
		return texID;
	}
	
	public String getName(){
		return name;
	}
}
