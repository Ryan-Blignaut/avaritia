package thesilverecho.avaritia.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.RenderType;
import thesilverecho.avaritia.client.shader.Shader;

import java.util.Objects;
import java.util.Optional;

public class ShaderWrappedRenderLayerOld extends RenderType
{
	private final RenderType delegate;
	private final Shader shader;

	public ShaderWrappedRenderLayerOld(RenderType delegate, Shader shader)
	{
		super(delegate.toString() + "with custom shader", delegate.format(), delegate.mode(), delegate.bufferSize(), delegate.affectsCrumbling(), true, () ->
		{
			delegate.setupRenderState();
			RenderSystem.matrixMode(5889);
			RenderSystem.pushMatrix();
			RenderSystem.scalef(0.9999F, 0.9999F, 0.9999F);
			RenderSystem.matrixMode(5888);
			shader.bind();
		}, () ->
		{
			shader.unBind();
			RenderSystem.matrixMode(5889);
			RenderSystem.popMatrix();
			RenderSystem.matrixMode(5888);
			delegate.clearRenderState();
		});
		this.delegate = delegate;
		this.shader = shader;
	}


	@Override
	public Optional<RenderType> outline()
	{
		return delegate.outline();
	}


	public boolean equals(Object other)
	{
		return other instanceof ShaderWrappedRenderLayerOld && this.delegate.equals(((ShaderWrappedRenderLayerOld) other).delegate) && this.shader == ((ShaderWrappedRenderLayerOld) other).shader;
	}

	public int hashCode()
	{
		return Objects.hash(this.delegate, this.shader);
	}
}
