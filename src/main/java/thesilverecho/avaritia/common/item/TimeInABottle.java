package thesilverecho.avaritia.common.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.entity.Ticker;
import thesilverecho.avaritia.common.util.TextUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class TimeInABottle extends Item
{
	private final boolean isInfinite;

	public TimeInABottle(boolean isInfinite)
	{
		super(new Properties().tab(ModGroup.AVARITIA).stacksTo(1).rarity(isInfinite ? Avaritia.COSMIC : Rarity.EPIC));
		this.isInfinite = isInfinite;
	}

	@Override
	public ActionResultType useOn(ItemUseContext context)
	{
		final World world = context.getLevel();
		final BlockPos pos = context.getClickedPos();
		final PlayerEntity player = context.getPlayer();
		final CompoundNBT timeInfo = context.getItemInHand().getOrCreateTagElement("time_info");
		final int storedTime = timeInfo.getInt("value");

		if (!world.isClientSide && player != null && player.isShiftKeyDown())
			if (world.getBlockEntity(pos) != null && world.getBlockEntity(pos) instanceof ITickableTileEntity)
			{
				final Optional<Ticker> tickerOptional = world.getEntitiesOfClass(Ticker.class, new AxisAlignedBB(pos)).stream().findFirst();
				if (tickerOptional.isPresent())
				{
					final Ticker ticker = tickerOptional.get();
					final int rate = ticker.getTimeRate();

					if (rate < 32)
					{
						int nextRate = rate * 2;
						int timeRequired = nextRate / 2 * 20 * 30;
						if (storedTime >= timeRequired || player.isCreative() || isInfinite)
						{

							final int timeAdded = (nextRate * 20 * 30 - ticker.getTimeLeft() - rate * 20 * 30 - ticker.getTimeLeft()) / nextRate;

							if (!player.isCreative() || isInfinite)
								timeInfo.putInt("value", storedTime - timeRequired);

							ticker.setTimeRate(nextRate);
							ticker.setTimeLeft(ticker.getTimeLeft() + timeAdded);
							playSound(rate, world, pos);
							return ActionResultType.SUCCESS;
						}
					}

				} else if (storedTime >= 20 * 30 || player.isCreative() || isInfinite)
				{
					if (!player.isCreative() || isInfinite)
						timeInfo.putInt("value", storedTime - 20 * 30);
					final Ticker ticker = new Ticker(world, pos, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
					ticker.setTimeLeft(20 * 30);
					ticker.setTimeRate(1);
//					ticker.setPosition(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f);
//					ticker.setBoundingBox(new AxisAlignedBB(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f,pos.getX() + 2.5f, pos.getY() + 2.5f, pos.getZ() + 2.5f));
					world.addFreshEntity(ticker);
					playSound(1, world, pos);
					return ActionResultType.SUCCESS;
				}
			}
		return super.useOn(context);
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if (!isInfinite)
		{
			if (worldIn.getGameTime() % 20 == 0)
			{
				final CompoundNBT timeInfo = stack.getOrCreateTagElement("time_info");
				final int storedTime = timeInfo.getInt("value");
				if (storedTime < 630800000/*a year's worth of ticks ;)*/)
					timeInfo.putInt("value", storedTime + 20);
			}
		}
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable World
			worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		final IFormattableTextComponent component = new TranslationTextComponent("tooltip.avaritia.time").withStyle(TextFormatting.GRAY);
		if (!isInfinite)
		{
			final int value = stack.getOrCreateTagElement("time_info").getInt("value");
			int i = value / 20;
			int hours = i / 3600;
			int minutes = (i % 3600) / 60;
			int seconds = i % 60;
			component.append(new TranslationTextComponent("tooltip.avaritia.time_format", hours, minutes, seconds).withStyle(TextFormatting.GRAY));
		} else
			component.append(TextUtil.makeFabulousText(new TranslationTextComponent("tooltip.avaritia.infinite")));

		tooltip.add(component);

		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

	private void playSound(int rate, World world, BlockPos pos)
	{
		switch (rate)
		{
			case 1:
				world.playSound(null, pos, SoundEvents.NOTE_BLOCK_HARP, SoundCategory.BLOCKS, 0.5F, 0.749154F);
				break;
			case 2:
				world.playSound(null, pos, SoundEvents.NOTE_BLOCK_HARP, SoundCategory.BLOCKS, 0.5F, 0.793701F);
				break;
			case 4:
			case 32:
				world.playSound(null, pos, SoundEvents.NOTE_BLOCK_HARP, SoundCategory.BLOCKS, 0.5F, 0.890899F);
				break;
			case 8:
				world.playSound(null, pos, SoundEvents.NOTE_BLOCK_HARP, SoundCategory.BLOCKS, 0.5F, 1.059463F);
				break;
			case 16:
				world.playSound(null, pos, SoundEvents.NOTE_BLOCK_HARP, SoundCategory.BLOCKS, 0.5F, 0.943874F);
				break;
		}
	}

}
