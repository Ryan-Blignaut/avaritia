package thesilverecho.avaritia.client.render.item;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import thesilverecho.avaritia.client.old.CustomColor;

public interface IHaloRender
{
	TextureAtlasSprite getHaloTexture();

	CustomColor getHaloColour();

	int getHaloSize();

	boolean shouldDrawPulse();

}
