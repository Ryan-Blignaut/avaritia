varying vec2 texcoord;
uniform sampler2D bgl_RenderedTexture;
uniform int time;// Passed in, see ShaderHelper.java

void main() {

    // vec4 c = texture(bgl_RenderedTexture, texcoord);
    // gl_FragColor = vec4(c.r,c.b,c.g,c.a);
    vec4 color = texture2D(bgl_RenderedTexture, texcoord);


    //    float angle = atan(-texcoord.y+0.25, texcoord.x+0.5)*0.1;
    //    float len = length(texcoord - vec2(0.5, 0.25));

    //    color+= sin(len*50);

    float alpha = color.a * color.r;
    gl_FragColor = vec4(color.r, color.g * (sin(time/320)+1) / 2, color.b, alpha);
}