package thesilverecho.avaritia.client.render.layer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.RenderType;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

public class DarkWrappedRenderLayer extends RenderType
{

	private final RenderType delegate;

	public DarkWrappedRenderLayer(RenderType delegate)
	{
		super("BrightRenderLayer", delegate.format(), delegate.mode(), delegate.bufferSize(), true, delegate.isOutline(), () ->
		{
			delegate.setupRenderState();
			RenderSystem.enableLighting();
		}, () ->
		{
			RenderSystem.disableLighting();
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
		return other instanceof DarkWrappedRenderLayer && this.delegate.equals(((DarkWrappedRenderLayer) other).delegate);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(this.delegate);
	}
}
