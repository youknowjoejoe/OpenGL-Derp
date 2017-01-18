#version 330 core

in vec2 texCoord;
in vec3 fragPosition;
in vec3 fragNormal;

out vec4 color;

uniform sampler2D texture1;
uniform vec3 lightPos1;
uniform vec3 lightColor1;

vec3 calcLightDir(vec3 lightPos){
	return normalize(lightPos-fragPosition);
}

vec3 pointLightAmbient(float strength, vec3 lightColor)
{
	return strength * lightColor;
}

vec3 pointLightDiffuse(vec3 norm, vec3 lightDir, vec3 lightColor)
{
	float diff = max(dot(norm, lightDir),0.0);
	return diff*lightColor;
}

vec3 pointLightSpecular(vec3 norm, vec3 lightDir, vec3 lightColor, float strength, int shininess)
{
	vec3 viewDir = normalize(vec3(0,0,0) - fragPosition);
	vec3 reflectDir = reflect(-lightDir, norm);
	
	float spec = pow(max(dot(viewDir, reflectDir), 0.0), shininess);
	
	return strength*spec*lightColor;
}

void main()
{
	vec4 textureColor = texture(texture1, texCoord);
	vec3 objectColor = textureColor.xyz;
	
	vec3 lightDir1 = calcLightDir(lightPos1);
	vec3 norm = normalize(fragNormal);
	
	vec3 shading = (pointLightAmbient(0.1f,lightColor1)+pointLightDiffuse(norm,lightDir1,lightColor1)+pointLightSpecular(norm,lightDir1,lightColor1,0.5f,32));
	
	/*int cels = 24;
	shading = shading*cels;
	shading = vec3(ceil(shading.x),ceil(shading.y),ceil(shading.z));
	shading = shading/cels;*/
	
	color = vec4(objectColor*shading,textureColor.w);
}