#version 330 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 textureCoord;
layout(location = 2) in vec3 normal;

out vec2 texCoord;
out vec3 fragPosition;
out vec3 fragNormal;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;
uniform mat4 normalMatrix;

void main()
{
	texCoord = textureCoord;
	fragNormal = (normalMatrix*vec4(normal,0.0)).xyz;
	fragPosition = (viewMatrix*modelMatrix*vec4(position,1.0)).xyz;
	gl_Position = projectionMatrix*viewMatrix*modelMatrix*vec4(position,1.0);
}