package thesilverecho.avaritia.common.item;

import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.TorchBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import thesilverecho.avaritia.common.util.EntityUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class SmartTorcher extends BaseToggleItem
{
	private final String torches = "torch_count";
	private boolean ticking = false;

	public SmartTorcher()
	{
		super(new Properties().tab(ModGroup.AVARITIA).stacksTo(1));
	}

	@Override
	public void inventoryTick(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull Entity entityIn, int itemSlot, boolean isSelected)
	{
		if (!(entityIn instanceof PlayerEntity) || !isActive(stack) || worldIn.isClientSide)
			return;
		PlayerEntity player = (PlayerEntity) entityIn;
		if (player.isSpectator())
			return;
//		if (!(worldIn.getGameTime() % 20 == 0))
//			return;
		if (ticking)
		{
			EntityUtil.getBlocksInArea(player, 8, 2).forEach(blockPos ->
			{
				Block block = worldIn.getBlockState(blockPos).getBlock();
				if (blockPos.getX() % 7 == 0 && blockPos.getZ() % 7 == 0)
				{
					if (block instanceof AirBlock && worldIn.getBlockState(blockPos.below()).canOcclude() && hasTorches(stack))
						if (worldIn.setBlockAndUpdate(blockPos, Blocks.TORCH.defaultBlockState()))
							removeTorch(stack);
				} else if (block instanceof TorchBlock)
					if (worldIn.removeBlock(blockPos, false))
						addTorch(stack);
			});
			ticking = false;
		} else
		{
			EntityUtil.adsorbFast(player, item -> item == Blocks.TORCH.asItem(), count -> modifyTorches(stack, count));
			ticking = true;
		}

	}

	private boolean hasTorches(ItemStack stack)
	{
		return getTorchesTag(stack).getInt(torches) > 0;
	}

	private void addTorch(ItemStack stack)
	{
		modifyTorches(stack, 1);
	}

	private void removeTorch(ItemStack stack)
	{
		modifyTorches(stack, -1);
	}

	private boolean modifyTorches(ItemStack stack, int count)
	{
		CompoundNBT tag = getTorchesTag(stack);
		tag.putInt(this.torches, tag.getInt(this.torches) + count);
		return true;
	}

	public CompoundNBT getTorchesTag(ItemStack stack)
	{
		CompoundNBT tag = stack.getOrCreateTag();
		if (!tag.contains(torches))
			tag.putInt(torches, 0);
		return tag;
	}

	@Override
	public void appendHoverText(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn)
	{
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(new TranslationTextComponent("tooltip.avaritia.torcher", Integer.toString(getTorchesTag(stack).getInt(torches))));

	}
}
