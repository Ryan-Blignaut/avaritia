package thesilverecho.avaritia.common.item.wrench;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thesilverecho.avaritia.common.block.IQuickBreak;
import thesilverecho.avaritia.common.item.ModGroup;

import javax.annotation.Nonnull;

public class WrenchItem extends Item
{

	public WrenchItem()
	{
		super(new Properties().tab(ModGroup.AVARITIA).stacksTo(1));
	}


	@Override
	public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context)
	{
		final World world = context.getLevel();
		final BlockPos pos = context.getClickedPos();
		final BlockState blockState = world.getBlockState(pos);
		final Block block = blockState.getBlock();
//context.getHitVec()
		if (block instanceof IQuickBreak)
		{
			((IQuickBreak) block).quickBreak(world, pos, blockState, Minecraft.getInstance().hitResult, context.getPlayer(), true);
			return ActionResultType.SUCCESS;
		}

		if (block instanceof IWrenchable)
		{
			((IWrenchable) block).onWrench(context.getPlayer(), world, pos, context.getClickedFace());
			return ActionResultType.CONSUME;
		} else if (context.getPlayer().isShiftKeyDown())
			blockState.rotate(world, pos, Rotation.CLOCKWISE_90);
//		block.rotate(blockState, world, pos, Rotation.CLOCKWISE_90);


		return super.onItemUseFirst(stack, context);
	}

	@Override
	@Nonnull
	public ActionResultType useOn(@Nonnull ItemUseContext context)
	{

	/*	final Block block = context.getWorld().getBlockState(context.getPos()).getBlock();
		if (block instanceof IWrenchable)
		{
			((IWrenchable) block).onWrench(context.getPlayer(),context.getWorld(),context.getPos());
			return ActionResultType.CONSUME;
		}
	*/
		return super.useOn(context);
	}

}
