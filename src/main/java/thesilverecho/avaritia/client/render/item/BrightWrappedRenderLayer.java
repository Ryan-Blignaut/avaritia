package thesilverecho.avaritia.client.render.item;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import thesilverecho.avaritia.client.shader.CosmicShader;
import thesilverecho.avaritia.client.shader.Shader;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

public class BrightWrappedRenderLayer extends RenderType
{

	public static final RenderType SHADER_TYPE = create("test", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.builder().setWriteMaskState(COLOR_WRITE).setCullState(NO_CULL).setDepthTestState(RenderState.EQUAL_DEPTH_TEST).setTransparencyState(GLINT_TRANSPARENCY).setTexturingState(new TexturingState("test", () ->
	{
//		RenderSystem.disableLighting();
//		CosmicShader.COSMIC_SHADER.bind();
		RenderSystem.matrixMode(5889);
		RenderSystem.pushMatrix();
		RenderSystem.scalef(0.9999F, 0.9999F, 0.9999F);
		RenderSystem.matrixMode(5888);
		CosmicShader.COSMIC_SHADER.bind();
//		RenderSystem.disableLighting();

	}, () ->
	{
//		CosmicShader.COSMIC_SHADER.unBind();
//		RenderSystem.enableLighting();
//		RenderSystem.enableLighting();
		CosmicShader.COSMIC_SHADER.unBind();
		RenderSystem.matrixMode(5889);
		RenderSystem.popMatrix();
		RenderSystem.matrixMode(5888);
	})).createCompositeState(false));


	private final RenderType delegate;

	public BrightWrappedRenderLayer(RenderType delegate, Shader shader)
	{
		super("a", delegate.format(), delegate.mode(), delegate.bufferSize(), true, delegate.isOutline(), () ->
		{
			delegate.setupRenderState();
			RenderSystem.matrixMode(5889);
			RenderSystem.pushMatrix();
			RenderSystem.scalef(0.9999F, 0.9999F, 0.9999F);
			RenderSystem.matrixMode(5888);
			shader.bind();
//			RenderSystem.disableLighting();
		}, () ->
		{
//			RenderSystem.enableLighting();
			shader.unBind();
			RenderSystem.matrixMode(5889);
			RenderSystem.popMatrix();
			RenderSystem.matrixMode(5888);
			delegate.clearRenderState();
		});
		this.delegate = delegate;
	}

	@Override
	public Optional<RenderType> outline()
	{
		return this.delegate.outline();
	}

	@Override
	public boolean equals(@Nullable Object other)
	{
		return other instanceof BrightWrappedRenderLayer && this.delegate.equals(((BrightWrappedRenderLayer) other).delegate);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(this.delegate);
	}
}
