package thesilverecho.avaritia.client.render.layer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.RenderType;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

public class BrightWrappedRenderLayer extends RenderType
{

	private final RenderType delegate;

	public BrightWrappedRenderLayer(RenderType delegate)
	{
		super("BrightRenderLayer", delegate.format(), delegate.mode(), delegate.bufferSize(), true, delegate.isOutline(), () ->
		{
			delegate.setupRenderState();
			RenderSystem.disableLighting();
		}, () ->
		{
			RenderSystem.enableLighting();
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
