package thesilverecho.avaritia.common.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.Item;
import thesilverecho.avaritia.client.AvaritiaTextures;
import thesilverecho.avaritia.client.model.TestCustItemRender;
import thesilverecho.avaritia.client.old.CustomColor;
import thesilverecho.avaritia.client.render.item.IHaloRender;
import thesilverecho.avaritia.common.Avaritia;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class SingularityItem extends Item implements IHaloRender
{

	private static final Supplier<Callable<ItemStackTileEntityRenderer>> testCustItemRenderCallable = () -> TestCustItemRender::new;
	private final boolean noise;
	private final int col;
	private final int size;
	private final boolean pulse;
	private final String resource;

	public SingularityItem(boolean noise, int col, int size, boolean pulse, String resource)
	{
		super(new Properties().tab(ModGroup.AVARITIA).rarity(Avaritia.COSMIC).setISTER(testCustItemRenderCallable));
		this.noise = noise;
		this.col = col;
		this.size = size;
		this.pulse = pulse;
		this.resource = resource;
	}


	public String getResource()
	{
		return resource;
	}

	@Override
	public TextureAtlasSprite getHaloTexture()
	{
		return Minecraft.getInstance().getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(noise ? AvaritiaTextures.HALO_NOISE : AvaritiaTextures.HALO);
	}

	@Override
	public CustomColor getHaloColour()
	{
		return new CustomColor(col);
	}

	@Override
	public int getHaloSize()
	{
		return size;
	}

	@Override
	public boolean shouldDrawPulse()
	{
		return pulse;
	}
}
