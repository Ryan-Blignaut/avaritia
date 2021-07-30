package thesilverecho.avaritia.client.render.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class BasicItemRender
{

	BasicItemRender(ItemStack itemStackIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, ResourceLocation location)
	{
		this(itemStackIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, Minecraft.getInstance().getModelManager().getModel(location));
	}

	BasicItemRender(ItemStack itemStackIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, IBakedModel modelIn)
	{
		final RenderType renderType = RenderTypeLookup.getRenderType(itemStackIn, false);
		final IVertexBuilder buffer = ItemRenderer.getFoilBuffer(bufferIn, renderType, true, itemStackIn.hasFoil());
		Minecraft.getInstance().getItemRenderer().renderModelLists(modelIn, itemStackIn, combinedLightIn, combinedOverlayIn, matrixStackIn, buffer);
	}

//	public static IVertexBuilder getBuffer(IRenderTypeBuffer bufferIn, RenderType renderTypeIn, boolean isItemIn, boolean glintIn)
//	{
//		if (glintIn)
//			return Minecraft.isFabulousGraphicsEnabled() && renderTypeIn == Atlases.getItemEntityTranslucentCullType() ? VertexBuilderUtils.newDelegate(bufferIn.getBuffer(RenderType.getGlintTranslucent()), bufferIn.getBuffer(renderTypeIn)) : VertexBuilderUtils.newDelegate(bufferIn.getBuffer(isItemIn ? RenderType.getGlint() : RenderType.getEntityGlint()), bufferIn.getBuffer(renderTypeIn));
//		else
//			return bufferIn.getBuffer(renderTypeIn);
//	}


}
