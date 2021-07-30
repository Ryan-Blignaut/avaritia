package thesilverecho.avaritia.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeBlock;

public interface IQuickBreak extends IForgeBlock
{
	default void quickBreak(World world, BlockPos pos, BlockState state, RayTraceResult target, PlayerEntity player, boolean returnDrops)
	{
		ItemStack dropBlock = this.getPickBlock(state, target, world, pos, player);
		world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		if (returnDrops && player != null)
		{
			player.addItem(dropBlock);
		} else
		{
			ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), dropBlock);
			world.addFreshEntity(itemEntity);
		}

	}

	default boolean canQuickBreak(World world, BlockPos pos, BlockState state, PlayerEntity player)
	{
		return true;
	}
}
