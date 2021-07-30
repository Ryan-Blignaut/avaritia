package thesilverecho.avaritia.client.render.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.util.ResourceLocation;

public interface ICosmicRender
{
	ResourceLocation getOverlayTexture();

	default IBakedModel getOverlay()
	{
		return Minecraft.getInstance().getModelManager().getModel(getOverlayTexture());
	}
}
