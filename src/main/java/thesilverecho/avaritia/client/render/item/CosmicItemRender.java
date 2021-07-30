package thesilverecho.avaritia.client.render.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class CosmicItemRender extends ItemStackTileEntityRenderer
{
	public static final Supplier<Callable<ItemStackTileEntityRenderer>> SUPPLIER = () -> CosmicItemRender::new;
	protected static Map<ItemStack, IBakedModel> itemModelCache = new HashMap<>();

	@Override
	public void renderByItem(ItemStack stack, ItemCameraTransforms.TransformType p_239207_2_, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay)
	{
		if (stack.getItem() instanceof ICosmicRender)
		{
			final ICosmicRender cosmicRender = (ICosmicRender) stack.getItem();

			final IBakedModel itemModel = itemModelCache.computeIfAbsent(stack, stack1 -> Minecraft.getInstance().getItemRenderer().getModel(stack, null, null));
			matrixStack.pushPose();

			final RenderType renderType = RenderTypeLookup.getRenderType(stack, false);
			final IVertexBuilder itemLayer = buffer.getBuffer(renderType);
			Minecraft.getInstance().getItemRenderer().renderModelLists(itemModel, stack, combinedLight, combinedOverlay, matrixStack, itemLayer);
//			final IVertexBuilder test = buffer.getBuffer(new BrightWrappedRenderLayer(renderType, CosmicShader.COSMIC_SHADER));

//			final IItemPropertyGetter pull = ItemModelsProperties.getProperty(stack, new ResourceLocation("pull"));

//			final IBakedModel overlay = cosmicRender.getOverlay();
//			Minecraft.getInstance().getItemRenderer().renderModel(overlay, stack, combinedLight, combinedOverlay, matrixStack, test);
			matrixStack.popPose();


		}

	}


}
