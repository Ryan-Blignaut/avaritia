#version 120

varying vec2 texcoord;
uniform int time;// Passed in, see ShaderHelper.java


void main() {
    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
    texcoord = vec2(gl_MultiTexCoord0);
}