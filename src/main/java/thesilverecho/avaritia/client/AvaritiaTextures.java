package thesilverecho.avaritia.client;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import thesilverecho.avaritia.common.Avaritia;

import java.util.ArrayList;
import java.util.List;

public class AvaritiaTextures
{
	public static ResourceLocation HALO = new ResourceLocation(Avaritia.MOD_ID, "halo/halo128");
	public static ResourceLocation HALO_NOISE = new ResourceLocation(Avaritia.MOD_ID, "halo/halo_noise");
	public static List<ResourceLocation> LIST = new ArrayList<>();
	public static List<TextureAtlasSprite> COSMIC = new ArrayList<>();

	static
	{
		for (int i = 0; i < 10; i++)
			LIST.add(new ResourceLocation(Avaritia.MOD_ID, "shader/cosmic_" + i));
//		LIST.forEach(location -> COSMIC.add(Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(location)));
	}


}
