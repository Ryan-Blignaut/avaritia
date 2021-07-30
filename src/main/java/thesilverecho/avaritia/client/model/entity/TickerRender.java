package thesilverecho.avaritia.client.model.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.BoatModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.World;
import thesilverecho.avaritia.client.util.ColourHelper;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.entity.Ticker;
import thesilverecho.avaritia.common.event.WorldOverlayRender;

public class TickerRender extends EntityRenderer<Ticker>
{
	protected final BoatModel modelBoat = new BoatModel();


	public TickerRender(EntityRendererManager renderManager)
	{
		super(renderManager);
	}

	@Override
	public void render(Ticker entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
	{
		matrixStackIn.pushPose();
		final int timeRate = entityIn.getTimeRate();
		final World world = entityIn.level;
		final BlockPos position = entityIn.blockPosition();
		final VoxelShape shape = world.getBlockState(position).getShape(world, position);
		final IVertexBuilder buffer = bufferIn.getBuffer(RenderType.LINES);
		WorldOverlayRender.drawShape(matrixStackIn, buffer, shape, ColourHelper.getChromaColor());
		final IVertexBuilder buffer1 = bufferIn.getBuffer(this.modelBoat.renderType(this.getTextureLocation(entityIn)));
		modelBoat.waterPatch().render(matrixStackIn, buffer1, packedLightIn, OverlayTexture.NO_OVERLAY);
		matrixStackIn.popPose();
	}


	@Override
	public ResourceLocation getTextureLocation(Ticker entity)
	{
		return new ResourceLocation(Avaritia.MOD_ID, "textures/item/base");
	}
}
