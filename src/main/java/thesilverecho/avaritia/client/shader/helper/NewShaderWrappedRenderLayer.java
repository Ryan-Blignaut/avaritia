package thesilverecho.avaritia.client.shader.helper;

import net.minecraft.client.renderer.RenderType;

import java.util.Objects;
import java.util.Optional;

public class NewShaderWrappedRenderLayer extends RenderType
{
	private final RenderType delegate;
	private final ShaderHelper.AvaritiaShader shader;

	private final ShaderCallback cb;

	public NewShaderWrappedRenderLayer(ShaderHelper.AvaritiaShader shader, ShaderCallback cb, RenderType delegate)
	{
		super("avaritia" + delegate.toString() + "_with_" + shader.name(), delegate.format(), delegate.mode(), delegate.bufferSize(), delegate.affectsCrumbling(), true,
				() ->
				{
					delegate.setupRenderState();
					ShaderHelper.useShader(shader, cb);
				},
				() ->
				{
					ShaderHelper.releaseShader();
					delegate.clearRenderState();
				});
		this.delegate = delegate;
		this.shader = shader;
		this.cb = cb;
	}

	@Override
	public Optional<RenderType> outline()
	{
		return delegate.outline();
	}

	@Override
	public boolean equals(Object other)
	{
		return other instanceof NewShaderWrappedRenderLayer
				&& delegate.equals(((NewShaderWrappedRenderLayer) other).delegate)
				&& shader == ((NewShaderWrappedRenderLayer) other).shader
				&& Objects.equals(cb, ((NewShaderWrappedRenderLayer) other).cb);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(delegate, shader, cb);
	}
}
