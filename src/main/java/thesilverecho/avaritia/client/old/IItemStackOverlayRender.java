package thesilverecho.avaritia.client.old;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.item.ItemStack;

public interface IItemStackOverlayRender
{
	void renderOverlay(MatrixStack matrixStack, ItemStack itemStack);
}
