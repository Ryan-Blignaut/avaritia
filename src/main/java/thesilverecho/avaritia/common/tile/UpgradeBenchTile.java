package thesilverecho.avaritia.common.tile;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.ItemStackHandler;
import thesilverecho.avaritia.common.container.UpgradeBenchContainer;
import thesilverecho.avaritia.common.init.ModTiles;
import thesilverecho.avaritia.common.item.magnet.MagnetFilterScreen;
import thesilverecho.avaritia.common.item.magnet.MagnetItem;
import thesilverecho.avaritia.common.item.magnet.UpgradeItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class UpgradeBenchTile extends BasicItemStorageTile/*ColouredBlockTile*/ implements ITickableTileEntity, INamedContainerProvider
{
	private final int TOTAL_WORK_NEEDED = 20 * 30;
	private final ItemStackHandler itemStackHandler = new ItemStackHandler(3)
	{

		@Override
		public boolean isItemValid(int slot, @Nonnull ItemStack stack)
		{
			if (slot == 0)
				return stack.getItem() instanceof MagnetItem;
			if (slot == 1)
				return stack.getItem() instanceof UpgradeItem;

			return slot != 2;
		}
	};
	private int workTimer = 0;


	public UpgradeBenchTile()
	{
		super(ModTiles.UPGRADE_BENCH_TILE.get());
	}


	@Override
	public void load(BlockState state, CompoundNBT tag)
	{
		super.load(state, tag);
		if (tag.contains("work"))
			workTimer = tag.getInt("work");
	}


	@Override
	public ItemStackHandler getInventory()
	{
		return itemStackHandler;
	}

	@Nonnull
	@Override
	public CompoundNBT save(CompoundNBT tag)
	{
		super.save(tag);
		tag.putInt("work", workTimer);
		return tag;
	}

	@Override
	public void tick()
	{
		if (inventory.isPresent())
		{
			final ItemStack stackInSlot = itemStackHandler.getStackInSlot(0);
			if (stackInSlot.getItem() instanceof MagnetItem && itemStackHandler.getStackInSlot(1).getItem() instanceof UpgradeItem)
			{
				final CompoundNBT tag = stackInSlot.getOrCreateTagElement(MagnetFilterScreen.MAIN_FILTER_TAG);
				final String key = ((UpgradeItem) itemStackHandler.getStackInSlot(1).getItem()).getTag();
				if (!tag.contains(key))
				{
					workTimer += 60;
					if (workTimer >= TOTAL_WORK_NEEDED)
					{
						tag.putBoolean(key, true);
						itemStackHandler.setStackInSlot(2, stackInSlot);
						itemStackHandler.extractItem(0, 1, false);
						itemStackHandler.extractItem(1, 1, false);
						workTimer = 0;
					}

				}

			}
		}
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return new TranslationTextComponent("avaritia.container.upgrade_bench");
	}

	@Nullable
	@Override
	public Container createMenu(int id, PlayerInventory inventory, PlayerEntity playerEntity)
	{
		return new UpgradeBenchContainer(id, inventory, getInventory());
	}
}
