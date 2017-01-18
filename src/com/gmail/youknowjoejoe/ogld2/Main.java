package com.gmail.youknowjoejoe.ogld2;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import com.gmail.youknowjoejoe.ogld2.math.Matrix44f;
import com.gmail.youknowjoejoe.ogld2.math.Vec2;
import com.gmail.youknowjoejoe.ogld2.math.Vec3;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main {
	
	// The window handle
	private long window;
	
	private final int WIDTH = 800;
	private final int HEIGHT = 600;
	
	private Scene sc;
	
	private double oldTime;
	private double currentTime;
	private double accumulator;
	private float dt = 1.0f/60.0f;
	
	public void run() {
		System.out.println("Using LWJGL Version: " + Version.getVersion());
		
		initGLFW();
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();
		
		System.out.println("OpenGL Version: " + glGetString(GL_VERSION));
		
		initOpenGL();
		loop();
		cleanup();
		
	}
	
	private void initGLFW() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();
		
		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");
		
		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // the window will be resizable
		
		// Create the window
		window = glfwCreateWindow(WIDTH, HEIGHT, "OpenGL Derp 2", NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");
		
		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});
		
		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*
			
			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);
			
			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			
			// Center the window
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically
		
		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);
		
		// Make the window visible
		glfwShowWindow(window);
	}
	
	private void initOpenGL(){
		Shader s = new Shader("./shaders/shader.vsh","./shaders/shader.fsh");
		
		Vertex[] boxVertices = new Vertex[]{
				//front
				new Vertex(new Vec3(-1f,-1f,-1f).getCoord(),  new Vec2(0,0).getCoord(), new Vec3(0,0,-1).getCoord()),
				new Vertex(new Vec3(1f,-1f,-1f).getCoord(),     new Vec2(1,0).getCoord(), new Vec3(0,0,-1).getCoord()),
				new Vertex(new Vec3(1f,1f,-1f).getCoord(), new Vec2(1,1).getCoord(), new Vec3(0,0,-1).getCoord()),
				new Vertex(new Vec3(-1f,1f,-1f).getCoord(), new Vec2(0,1).getCoord(), new Vec3(0,0,-1).getCoord()),
				
				//back
				new Vertex(new Vec3(-1f,-1f,1f).getCoord(),  new Vec2(0,0).getCoord(), new Vec3(0,0,1).getCoord()),
				new Vertex(new Vec3(1f,-1f,1f).getCoord(),     new Vec2(1,0).getCoord(), new Vec3(0,0,1).getCoord()),
				new Vertex(new Vec3(1f,1f,1f).getCoord(), new Vec2(1,1).getCoord(), new Vec3(0,0,1).getCoord()),
				new Vertex(new Vec3(-1f,1f,1f).getCoord(), new Vec2(0,1).getCoord(), new Vec3(0,0,1).getCoord()),
				
				//bottom
				new Vertex(new Vec3(-1f,-1f,-1f).getCoord(),  new Vec2(0,0).getCoord(), new Vec3(0,-1,0).getCoord()),
				new Vertex(new Vec3(1f,-1f,-1f).getCoord(),     new Vec2(1,0).getCoord(), new Vec3(0,-1,0).getCoord()),
				new Vertex(new Vec3(1f,-1f,1f).getCoord(), new Vec2(1,1).getCoord(), new Vec3(0,-1,0).getCoord()),
				new Vertex(new Vec3(-1f,-1f,1f).getCoord(), new Vec2(0,1).getCoord(), new Vec3(0,-1,0).getCoord()),
				
				//top
				new Vertex(new Vec3(-1f,1f,-1f).getCoord(),  new Vec2(0,0).getCoord(), new Vec3(0,1,0).getCoord()),
				new Vertex(new Vec3(1f,1f,-1f).getCoord(),     new Vec2(1,0).getCoord(), new Vec3(0,1,0).getCoord()),
				new Vertex(new Vec3(1f,1f,1f).getCoord(), new Vec2(1,1).getCoord(), new Vec3(0,1,0).getCoord()),
				new Vertex(new Vec3(-1f,1f,1f).getCoord(), new Vec2(0,1).getCoord(), new Vec3(0,1,0).getCoord()),
				
				//left
				new Vertex(new Vec3(-1f,-1f,-1f).getCoord(),  new Vec2(0,0).getCoord(), new Vec3(-1,0,0).getCoord()),
				new Vertex(new Vec3(-1f,-1f,1f).getCoord(),     new Vec2(1,0).getCoord(), new Vec3(-1,0,0).getCoord()),
				new Vertex(new Vec3(-1f,1f,1f).getCoord(), new Vec2(1,1).getCoord(), new Vec3(-1,0,0).getCoord()),
				new Vertex(new Vec3(-1f,1f,-1f).getCoord(), new Vec2(0,1).getCoord(), new Vec3(-1,0,0).getCoord()),
				
				//right
				new Vertex(new Vec3(1f,-1f,-1f).getCoord(),  new Vec2(0,0).getCoord(), new Vec3(1,0,0).getCoord()),
				new Vertex(new Vec3(1f,-1f,1f).getCoord(),     new Vec2(1,0).getCoord(), new Vec3(1,0,0).getCoord()),
				new Vertex(new Vec3(1f,1f,1f).getCoord(), new Vec2(1,1).getCoord(), new Vec3(1,0,0).getCoord()),
				new Vertex(new Vec3(1f,1f,-1f).getCoord(), new Vec2(0,1).getCoord(), new Vec3(1,0,0).getCoord()),
				};
		byte[] boxElements = {
				0,1,2,0,2,3,
				4,5,6,4,6,7,
				8,9,10,8,10,11,
				12,13,14,12,14,15,
				16,17,18,16,18,19,
				20,21,22,20,22,23
		};
		Renderable l = new Renderable(
				boxVertices, 
				boxElements,
				s,
				Matrix44f.getTranslation(new Vec3(0,10,0)).times(Matrix44f.getScale(new Vec3(0.1f,0.1f,0.1f))),
				new Texture[]{new Texture("texture1","./resources/crate.png",GL_CLAMP_TO_EDGE, GL_NEAREST)}
				);
		Renderable r = new Renderable(
				boxVertices, 
				boxElements,
				s,
				Matrix44f.getTranslation(new Vec3(5,0,-5)).times(Matrix44f.getRotation((float) Math.PI/6.0f, Matrix44f.Direction.Y)),
				new Texture[]{new Texture("texture1","./resources/crate.png",GL_CLAMP_TO_EDGE, GL_NEAREST)}
				);
		Renderable r2 = new Renderable(
				boxVertices, 
				boxElements,
				s,
				Matrix44f.getTranslation(new Vec3(7,4,0)).times(Matrix44f.getRotation((float) Math.PI/3.0f, Matrix44f.Direction.Y)),
				new Texture[]{new Texture("texture1","./resources/crate.png",GL_CLAMP_TO_EDGE, GL_NEAREST)}
				);
		
		FlyingPlayer p = new FlyingPlayer(new Camera());
		sc = new Scene(new Renderable[]{l,r,r2},Matrix44f.identity,Matrix44f.getPerspective((float)Math.PI/4.0f,((float)WIDTH)/((float)HEIGHT),0.1f,1000.0f),p);
		glfwSetKeyCallback(window, p.getKeys());
		glfwSetCursorPosCallback(window, p.getMouse());
		glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);  
	}

	private void loop() {
		// Set the clear color
		glClearColor(0.0f, 0.05f, 0.1f, 0.0f);
		
		glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);
		glEnable(GL_DEPTH_TEST);
		
		oldTime = glfwGetTime();
		currentTime = oldTime;
		accumulator = 0;
		
		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while ( !glfwWindowShouldClose(window) ) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
			
			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
			
			
			currentTime = glfwGetTime();
			accumulator+=currentTime-oldTime;
			oldTime = currentTime;
			
			while(accumulator > dt){
				//update the scene
				sc.update(dt);
				accumulator-=dt;
			}
			
			
			// Render commands
			sc.render();
			
			glfwSwapBuffers(window); // swap the color buffers
		}
	}
	
	private void cleanup(){
		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		
		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	public static void main(String[] args) {
		new Main().run();
	}

}