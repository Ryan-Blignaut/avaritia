package thesilverecho.avaritia.common.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import thesilverecho.avaritia.client.AvaritiaTextures;
import thesilverecho.avaritia.client.old.CustomColor;
import thesilverecho.avaritia.client.render.item.ExtremeItemRender;
import thesilverecho.avaritia.client.render.item.IHaloRender;

public class ResourceItem extends Item implements IHaloRender
{

	private boolean pulse;
	private boolean noise;
	private int color;
	private int size;
	private Rarity rarity;

	public ResourceItem(boolean pulse, boolean noise, int color, int size, Rarity rarity)
	{
		super(new Properties().tab(ModGroup.AVARITIA).setISTER(ExtremeItemRender.SUPPLIER).rarity(rarity));
		this.pulse = pulse;
		this.noise = noise;
		this.color = color;
		this.size = size;
	}

	public ResourceItem()
	{
		this(false, false, 0xFF000000, 8, Rarity.UNCOMMON);
	}

	public ResourceItem setRarity(Rarity rarity)
	{
		this.rarity = rarity;
		return this;
	}

	public ResourceItem pulse()
	{
		this.pulse = true;
		return this;
	}

	public ResourceItem noise()
	{
		this.noise = true;
		return this;
	}

	public ResourceItem setColor(int color)
	{
		this.color = color;
		return this;
	}

	public ResourceItem setSize(int size)
	{
		this.size = size;
		return this;
	}

	@Override
	public TextureAtlasSprite getHaloTexture()
	{
		return Minecraft.getInstance().getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(noise ? AvaritiaTextures.HALO_NOISE : AvaritiaTextures.HALO);
	}

	@Override
	public CustomColor getHaloColour()
	{
		return new CustomColor(color);
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
