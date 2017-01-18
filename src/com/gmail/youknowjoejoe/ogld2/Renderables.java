package com.gmail.youknowjoejoe.ogld2;

import com.gmail.youknowjoejoe.ogld2.math.Vec2;
import com.gmail.youknowjoejoe.ogld2.math.Vec3;

public class Renderables {
	
	public static Vertex[] getVertices(Vec3 v1, Vec3 v2, Vec3 v3, Vec2 t1, Vec2 t2, Vec2 t3){
		Vec3 ab = v2.plus(v1.negative());
		Vec3 ac = v3.plus(v1.negative());
		
		Vec3 normal = ac.cross(ab).normalized();
		
		return new Vertex[]{
				new Vertex(v1.getCoord(),t1.getCoord(),normal.getCoord()),
				new Vertex(v2.getCoord(),t2.getCoord(),normal.getCoord()),
				new Vertex(v3.getCoord(),t3.getCoord(),normal.getCoord())
				};
	}
	
	
}
