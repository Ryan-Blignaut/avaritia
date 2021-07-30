package thesilverecho.avaritia.client.render.block;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;
import thesilverecho.avaritia.common.tile.CompactorTile;

public class CompactorBlockTileRender extends TileEntityRenderer<CompactorTile>
{
	public CompactorBlockTileRender(TileEntityRendererDispatcher rendererDispatcherIn)
	{
		super(rendererDispatcherIn);
	}

	@Override
	public void render(CompactorTile tileEntityIn, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
	{
		final ItemStack stack = tileEntityIn.getInventory().getStackInSlot(0);
		if (!stack.isEmpty())
		{
			matrixStack.pushPose();

			matrixStack.translate(0.5D, 0.6D, 0.5D);
			float rotation = (float) (tileEntityIn.getLevel().getGameTime() % 80);
			matrixStack.scale(.4f, .4f, .4f);

			matrixStack.mulPose(Vector3f.YP.rotationDegrees(360f * rotation / 80f));

			Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStack, bufferIn);

			matrixStack.popPose();
		}
	}
}
