package thesilverecho.avaritia.client.render.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Matrix4f;
import thesilverecho.avaritia.client.old.CustomColor;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class ExtremeItemRender extends ItemStackTileEntityRenderer
{

	public static final Supplier<Callable<ItemStackTileEntityRenderer>> SUPPLIER = () -> ExtremeItemRender::new;


	protected static Map<ItemStack, IBakedModel> itemModelCache = new HashMap<>();
	private static ItemCameraTransforms.TransformType TRANSFORM_TYPE;

	public static void setTransformType(ItemCameraTransforms.TransformType transformType)
	{
		TRANSFORM_TYPE = transformType;
	}

	@Override
	public void renderByItem(ItemStack stack, ItemCameraTransforms.TransformType p_239207_2_, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay)
	{
		matrixStack.pushPose();
		RenderSystem.enableBlend();
		RenderSystem.disableDepthTest();
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		RenderSystem.disableAlphaTest();

		final RenderType renderType = RenderTypeLookup.getRenderType(stack, false);
		final IVertexBuilder itemLayer = buffer.getBuffer(renderType);
//		final IVertexBuilder customLayer = buffer.getBuffer(new ShaderRenderLayer("Cosmic", renderType/*RenderType.getGlint()*/, CosmicShader.COSMIC_SHADER));


//		final IVertexBuilder iVertexBuilder = VertexBuilderUtils.newDelegate(itemLayer, customLayer);

		final IBakedModel iBakedModel = itemModelCache.computeIfAbsent(stack, stack1 -> Minecraft.getInstance().getItemRenderer().getModel(stack, null, null));
		if (stack.getItem() instanceof IHaloRender)
		{
			final IHaloRender iHaloRender = (IHaloRender) stack.getItem();
			Tessellator tess = Tessellator.getInstance();
			BufferBuilder bufferBuilder = tess.getBuilder();
			final Matrix4f matrix = matrixStack.last().pose();
			if (TRANSFORM_TYPE == ItemCameraTransforms.TransformType.GUI)
			{
				final float spread = iHaloRender.getHaloSize() / 16f;
				final float min = 0 - spread;
				final float max = 1 + spread;

				final TextureAtlasSprite sprite = iHaloRender.getHaloTexture();
				final float minU = sprite.getU0();
				final float maxU = sprite.getU1();
				final float minV = sprite.getV0();
				final float maxV = sprite.getV1();

				final CustomColor haloColour = iHaloRender.getHaloColour();
				final int red = haloColour.getRed();
				final int green = haloColour.getGreen();
				final int blue = haloColour.getBlue();
				final int alpha = haloColour.getAlpha();

				bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR_TEX);
				bufferBuilder.vertex(matrix, max, max, 0).color(red, blue, green, alpha).uv(maxU, minV).endVertex();
				bufferBuilder.vertex(matrix, min, max, 0).color(red, blue, green, alpha).uv(minU, minV).endVertex();
				bufferBuilder.vertex(matrix, min, min, 0).color(red, blue, green, alpha).uv(minU, maxV).endVertex();
				bufferBuilder.vertex(matrix, max, min, 0).color(red, blue, green, alpha).uv(maxU, maxV).endVertex();
				tess.end();


			}
			if (iHaloRender.shouldDrawPulse())
			{
				float scale = (float) (new Random().nextFloat() * 0.15 + 0.95);
				double trans = (1 - scale) / 2;
				matrixStack.translate(trans, trans, 0);
				matrixStack.scale(scale, scale, 1.0001f);
			}

			Minecraft.getInstance().getItemRenderer().renderModelLists(iBakedModel, stack, combinedLight, combinedOverlay, matrixStack, itemLayer);
		}

		RenderSystem.enableAlphaTest();
		RenderSystem.enableDepthTest();
		RenderSystem.enableRescaleNormal();
		RenderSystem.disableBlend();
		matrixStack.popPose();
	}

}
