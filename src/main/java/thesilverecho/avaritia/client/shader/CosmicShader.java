package thesilverecho.avaritia.client.shader;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.ARBShaderObjects;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.event.ClientTicker;

import static org.lwjgl.opengl.GL20.glUseProgram;

public class CosmicShader extends Shader
{
	public static final CosmicShader COSMIC_SHADER = new CosmicShader("cosmicShader1.vert", "cosmicShader1.frag");
	public static boolean inventory;

	public CosmicShader(String vertFile, String fragFile)
	{
		super(vertFile, fragFile);
		create();
	}

	@Override
	public void bind()
	{
		final Minecraft mc = Minecraft.getInstance();
		glUseProgram(proId);
		int time = GlStateManager._glGetUniformLocation(proId, "time");
		GlStateManager._glUniform1i(time, ClientTicker.ticksInGame);
		float yaw = 0;
		float pitch = 0;
		float scale = 1.0f;
		if (!inventory)
		{
			assert mc.player != null;
			yaw = (float) ((mc.player.yRot * 2 * Math.PI) / 360.0);
			pitch = -(float) ((mc.player.xRot * 2 * Math.PI) / 360.0);
		} else
			scale = 25.0f;


		int x = ARBShaderObjects.glGetUniformLocationARB(proId, "yaw");
		ARBShaderObjects.glUniform1fARB(x, yaw);

		int z = ARBShaderObjects.glGetUniformLocationARB(proId, "pitch");
		ARBShaderObjects.glUniform1fARB(z, pitch);

		int lightmix = ARBShaderObjects.glGetUniformLocationARB(proId, "lightmix");
		ARBShaderObjects.glUniform1fARB(lightmix, 0.2f);

		int uvs = ARBShaderObjects.glGetUniformLocationARB(proId, "cosmicuvs");
		ARBShaderObjects.glUniformMatrix2fvARB(uvs, false, Avaritia.cosmicUVs);

		int s = ARBShaderObjects.glGetUniformLocationARB(proId, "externalScale");
		ARBShaderObjects.glUniform1fARB(s, scale);

		int o = ARBShaderObjects.glGetUniformLocationARB(proId, "opacity");
		ARBShaderObjects.glUniform1fARB(o, 1);

	}

}
