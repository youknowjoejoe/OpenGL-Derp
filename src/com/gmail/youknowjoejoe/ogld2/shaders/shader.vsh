#version 330 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 textureCoord;
layout(location = 2) in vec3 color;

out vec3 vertexColor;
out vec2 texCoord;

uniform mat4 projectionMatrix;
uniform mat4 modelViewMatrix;
uniform mat4 normalMatrix;

void main()
{
	vertexColor = color;
	texCoord = textureCoord;
	gl_Position = projectionMatrix*modelViewMatrix*vec4(position, 1.0);
}