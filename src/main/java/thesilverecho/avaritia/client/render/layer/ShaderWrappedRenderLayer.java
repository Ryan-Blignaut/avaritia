package thesilverecho.avaritia.client.render.layer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.RenderType;
import org.lwjgl.opengl.GL11;
import thesilverecho.avaritia.client.shader.Shader;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

public class ShaderWrappedRenderLayer extends RenderType
{

	private final RenderType delegate;
	private final Shader shader;

	public ShaderWrappedRenderLayer(RenderType delegate, Shader shader)
	{
		super("ShaderRenderLayer", delegate.format(), delegate.mode(), delegate.bufferSize(), true, delegate.isOutline(), () ->
		{
			delegate.setupRenderState();
			RenderSystem.matrixMode(GL11.GL_PROJECTION);
			RenderSystem.pushMatrix();
			RenderSystem.scalef(0.9999F, 0.9999F, 0.9999F);
			RenderSystem.matrixMode(GL11.GL_MODELVIEW);
			shader.bind();
		}, () ->
		{
			shader.unBind();
			RenderSystem.matrixMode(GL11.GL_PROJECTION);
			RenderSystem.popMatrix();
			RenderSystem.matrixMode(GL11.GL_MODELVIEW);
			delegate.clearRenderState();
		});
		this.delegate = delegate;
		this.shader = shader;
	}

	@Override
	public Optional<RenderType> outline()
	{
		return this.delegate.outline();
	}

	@Override
	public boolean equals(@Nullable Object other)
	{
		return other instanceof ShaderWrappedRenderLayer && this.delegate.equals(((ShaderWrappedRenderLayer) other).delegate) && this.shader.equals(((ShaderWrappedRenderLayer) other).shader);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(this.delegate, this.shader);
	}
}
