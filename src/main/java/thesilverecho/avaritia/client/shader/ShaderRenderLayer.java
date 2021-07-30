package thesilverecho.avaritia.client.shader;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import java.util.Optional;

public class ShaderRenderLayer extends RenderType
{
	public static RenderType EXTREME_OVER;

	static
	{

		final State state = State.builder().setWriteMaskState(COLOR_WRITE).setCullState(NO_CULL).setDepthTestState(EQUAL_DEPTH_TEST).setTransparencyState(GLINT_TRANSPARENCY).setTexturingState(new TexturingState("glint_transparency", () ->
		{
			CosmicShader.COSMIC_SHADER.bind();
		}, () ->
		{
			CosmicShader.COSMIC_SHADER.unBind();
		})).createCompositeState(false);

		final RenderType extreme = RenderType.create("extreme", DefaultVertexFormats.POSITION_TEX, 7, 256, state);

		EXTREME_OVER = new ShaderRenderLayer("x", extreme, CosmicShader.COSMIC_SHADER);

	}

	private final RenderType delegate;

	public ShaderRenderLayer(String name, RenderType delegate, Shader shader)
	{
		super(name, delegate.format(), delegate.mode(), delegate.bufferSize(), delegate.affectsCrumbling(), true, () ->
				{
					delegate.setupRenderState();
					shader.bind();
				},
				() ->
				{
					shader.unBind();
					delegate.clearRenderState();
				});
		this.delegate = delegate;

	}


	@Override
	public Optional<RenderType> outline()
	{
		return delegate.outline();
	}


}
