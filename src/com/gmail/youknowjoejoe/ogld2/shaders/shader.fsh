#version 330 core

in vec3 vertexColor;
in vec2 texCoord;

out vec4 color;

uniform sampler2D texture1;

void main()
{
	color = mix(texture(texture1,texCoord),vec4(vertexColor,1.0),0.5);
}