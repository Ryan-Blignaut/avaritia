package thesilverecho.avaritia.common.event;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawHighlightEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import thesilverecho.avaritia.client.util.ColourHelper;
import thesilverecho.avaritia.common.item.InfinityPickaxe;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicReference;

@Mod.EventBusSubscriber
public class WorldOverlayRender
{
	@SubscribeEvent
	public static void renderBlockOverlay(@Nonnull DrawHighlightEvent.HighlightBlock event)
	{
		final PlayerEntity player = Minecraft.getInstance().player;
		final ItemStack itemStack = player.getMainHandItem();

//		final World world = player.world;
		if (itemStack.getItem() instanceof InfinityPickaxe && InfinityPickaxe.isHammer(itemStack))
		{
			event.setCanceled(true);
			final BlockPos blockPos = event.getTarget().getBlockPos();
			final Direction face = event.getTarget().getDirection();
			World world = player.level;


			AtomicReference<VoxelShape> voxelShape = new AtomicReference<>(VoxelShapes.empty());

			final int range = 7;
			boolean doX = face.getStepX() == 0;
			boolean doY = face.getStepY() == 0;
			boolean doZ = face.getStepZ() == 0;


			Vector3i beginDiff = new Vector3i(doX ? -range : 0, doY ? -1 : 0, doZ ? -range : 0);
			Vector3i endDiff = new Vector3i(doX ? range : 0, doY ? range * 2 - 1 : 0, doZ ? range : 0);
			BlockPos.betweenClosed(blockPos.offset(beginDiff), blockPos.offset(endDiff)).forEach(pos ->
					{
						if (world.getBlockState(pos).getBlock() != Blocks.AIR)
							voxelShape.set(VoxelShapes.or(voxelShape.get(), VoxelShapes.block().move(pos.getX(), pos.getY(), pos.getZ())));
					}
			);


		/*	switch (face.getAxis())
			{
				case X:
					for (int y = -range; y <= range; y++)
						for (int z = -range; z <= range; z++)
//							if (world.getBlockState(blockPos.add(0, y, z)) != Blocks.AIR.getDefaultState())
							voxelShape = VoxelShapes.or(voxelShape, VoxelShapes.fullCube().withOffset(0, y, z));
//							voxelShape = VoxelShapes.or(voxelShape, world.getBlockState(blockPos.add(0, y, z)).getShape(world, blockPos.add(0, y, z)).withOffset(0, y, z) VoxelShapes.fullCube().withOffset(0, y, z));
					break;
				case Y:
					for (int x = -range; x <= range; x++)
						for (int z = -range; z <= range; z++)
//							if (world.getBlockState(blockPos.add(x, 0, z)) != Blocks.AIR.getDefaultState())
							voxelShape = VoxelShapes.or(voxelShape, VoxelShapes.fullCube().withOffset(x, 0, z));
					break;

				case Z:
					for (int x = -range; x <= range; x++)
						for (int y = -range; y <= range; y++)
//							if (world.getBlockState(blockPos.add(x, y, 0)) != Blocks.AIR.getDefaultState())
							voxelShape = VoxelShapes.or(voxelShape, VoxelShapes.fullCube().withOffset(x, y, 0));
					break;
			}*/

			Vector3d view = event.getInfo().getPosition();
			final MatrixStack matrix = event.getMatrix();
			matrix.pushPose();
			matrix.translate(-view.x(), -view.y(), -view.z());
			final IVertexBuilder buffer = event.getBuffers().getBuffer(RenderType.LINES);
			drawShape(matrix, buffer, voxelShape.get(), ColourHelper.getChromaColor());
			matrix.popPose();
		}


	}

	public static void drawShape(MatrixStack matrixStackIn, IVertexBuilder bufferIn, VoxelShape shapeIn, int colour)
	{
		final Matrix4f matrix4f = matrixStackIn.last().pose();
		final float red = ColourHelper.getRedFloat(colour);
		final float blue = ColourHelper.getBlueFloat(colour);
		final float green = ColourHelper.getGreenFloat(colour);

		shapeIn.forAllEdges((minX, minY, minZ, maxX, maxY, maxZ) ->
		{
			bufferIn.vertex(matrix4f, (float) minX, (float) (minY), (float) (minZ)).color(red, green, blue, 1).endVertex();
			bufferIn.vertex(matrix4f, (float) maxX, (float) (maxY), (float) (maxZ)).color(red, green, blue, 1).endVertex();
		});
	}

}
