package thesilverecho.avaritia.client.shader;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.ARBShaderObjects;
import thesilverecho.avaritia.common.event.ClientTicker;

import static org.lwjgl.opengl.GL20.glUseProgram;

public class EnderShader extends Shader
{
	public static final EnderShader ENDER_SHADER = new EnderShader("ender.vert", "ender.frag");
	public static boolean inventory;

	public EnderShader(String vertFile, String fragFile)
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
		if (!inventory)
		{
			assert mc.player != null;
			yaw = (float) ((mc.player.yRot * 2 * Math.PI) / 360.0);
			pitch = -(float) ((mc.player.xRot * 2 * Math.PI) / 360.0);
		}

		int x = ARBShaderObjects.glGetUniformLocationARB(proId, "yaw");
		ARBShaderObjects.glUniform1fARB(x, yaw);

		int z = ARBShaderObjects.glGetUniformLocationARB(proId, "pitch");
		ARBShaderObjects.glUniform1fARB(z, pitch);


	}

}
