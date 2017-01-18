package com.gmail.youknowjoejoe.ogld2;

import com.gmail.youknowjoejoe.ogld2.math.Matrix44f;
import com.gmail.youknowjoejoe.ogld2.math.Vec3;
import com.gmail.youknowjoejoe.ogld2.math.Vec4;

public class Camera {
	private Vec3 pos;
	private Vec4 dir;
	
	private Matrix44f rotation; //not camera rotation, world rotation
	private float pitch;
	private float yaw;
	
	public Camera(Vec3 pos, float pitch, float yaw){
		this.pos = pos;
		this.dir = new Vec4(0,0,1,0);
		this.pitch = pitch;
		this.yaw = yaw;
		updateRotationMatrix();
	}
	
	public Camera(){
		this.pos = Vec3.zero;
		this.dir = new Vec4(0,0,1,0);
		this.pitch = 0;
		this.yaw = 0;
		this.rotation = Matrix44f.identity;
	}
	
	public Matrix44f getViewMatrix(){
		return (rotation).times((Matrix44f.getTranslation(pos)));
	}
	
	public Vec3 getDirection(){
		return new Matrix44f(this.rotation.getTransposedValues()).times(dir).getVec3(); //if rotating world x, then rotating camera -x. Therefore, transpose matrix
	}
	
	public void translate(Vec3 tx){
		this.pos = pos.plus(tx);
	}
	
	public void rotate(float pitch, float yaw){
		this.pitch = Math.min(Math.max(this.pitch + pitch,(float)-Math.PI/2.0f+0.001f),(float)Math.PI/2.0f-0.001f); //ADD FEATURE
		//this.pitch += pitch;
		this.yaw += yaw;
		updateRotationMatrix();
	}
	
	private void updateRotationMatrix(){
		this.rotation = Matrix44f.getRotation(-this.pitch, Matrix44f.Direction.X).times(Matrix44f.getRotation(-this.yaw, Matrix44f.Direction.Y));
	}
}
