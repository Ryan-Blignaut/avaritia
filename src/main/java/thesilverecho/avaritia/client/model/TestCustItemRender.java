package thesilverecho.avaritia.client.model;

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
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.registries.ForgeRegistries;
import thesilverecho.avaritia.client.old.CustomColor;
import thesilverecho.avaritia.client.render.item.IHaloRender;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TestCustItemRender extends ItemStackTileEntityRenderer
{
	public static IBakedModel BAKE;
	protected static Map<ItemStack, IBakedModel> itemModelCache = new HashMap<>();
	//	Model model = new
//	ModelResourceLocation modelResourceLocation = new ModelResourceLocation(null);
	private static ItemCameraTransforms.TransformType TRANSFORM_TYPE;
	private final int time = 0;

	public static void setTransformType(ItemCameraTransforms.TransformType transformType)
	{
		TRANSFORM_TYPE = transformType;
	}

	@Override
	public void renderByItem(ItemStack stack, ItemCameraTransforms.TransformType p_239207_2_, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay)
	{
		boolean inv = false;
		matrixStack.pushPose();
		RenderSystem.enableBlend();
		RenderSystem.disableDepthTest();
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		RenderSystem.disableAlphaTest();

//Entity mob = Minecraft.getInstance().getEntityByID(1);
		final EntityType<?> type = ForgeRegistries.ENTITIES.getValue(new ResourceLocation("minecraft:skeleton"));
		final ClientWorld world = Minecraft.getInstance().level;
		if (world == null)
			return;
		final Entity mob = type.create(world);
		float scale1 = 0.6F / Math.max(mob.getBbWidth(), mob.getBbHeight());
		matrixStack.translate(0.5, 0.175, 0.5);
		matrixStack.scale(scale1, scale1, scale1);
//		GlStateManager.translated(0.5, 0.175, 0.5);
//		GlStateManager.scaled(scale1, scale1, scale1);
		if (stack.getItem() instanceof IHaloRender)
		{
//			RenderSystem.pushMatrix();
			matrixStack.mulPose(Vector3f.YP.rotationDegrees((float) (Math.sin((Util.getMillis() + Minecraft.getInstance().getFrameTime()) / 6000) * 360)));
			matrixStack.mulPose(Vector3f.XP.rotationDegrees(-30.0F));
//			Minecraft.getInstance().getItemRenderer().renderQuads();
//			RenderSystem.rotatef((float) (Math.sin((time++ + Minecraft.getInstance().getRenderPartialTicks()) / 50F) * 15F), 1, 0, -0.5F);
//			RenderSystem.rotatef((time + Minecraft.getInstance().getRenderPartialTicks()) * 3, 0, 1, 0);
			Minecraft.getInstance().getEntityRenderDispatcher().render(mob, 0, 0, 0, 0, /*Minecraft.getInstance().getRenderPartialTicks()*/0, matrixStack, buffer, combinedLight);
//			RenderSystem.popMatrix();

//			matrixStack.rotate(Vector3d.);
			inv = true;
			Tessellator tess = Tessellator.getInstance();
			BufferBuilder bufferBuilder = tess.getBuilder();
			final Matrix4f matrix = matrixStack.last().pose();
			if (TRANSFORM_TYPE == ItemCameraTransforms.TransformType.GUI)
			{
				final IHaloRender iHaloRender = (IHaloRender) stack.getItem();

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

				if (iHaloRender.shouldDrawPulse())
				{
					float scale = (float) (new Random().nextFloat() * 0.15 + 0.95);
					double trans = (1 - scale) / 2;
					matrixStack.translate(trans, trans, 0);
					matrixStack.scale(scale, scale, 1.0001f);
				}

			}
		}
		final RenderType renderType = RenderTypeLookup.getRenderType(stack, inv);
		final IVertexBuilder vertexBuilder = buffer.getBuffer(renderType);
		final IBakedModel iBakedModel = itemModelCache.computeIfAbsent(stack, stack1 -> Minecraft.getInstance().getItemRenderer().getModel(stack, null, null));

		final IBakedModel model = ForgeHooksClient.handleCameraTransforms(matrixStack, iBakedModel, TRANSFORM_TYPE, false);
		Minecraft.getInstance().getItemRenderer().renderModelLists(model, stack, combinedLight, combinedOverlay, matrixStack, vertexBuilder);

		RenderSystem.enableAlphaTest();
		RenderSystem.enableDepthTest();
		RenderSystem.enableRescaleNormal();
		RenderSystem.disableBlend();

		matrixStack.popPose();

	}
}
