package thesilverecho.avaritia.common.tile;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import thesilverecho.avaritia.common.init.ModTiles;
import thesilverecho.avaritia.common.item.magnet.MagnetItem;
import thesilverecho.avaritia.common.item.magnet.UpgradeItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SolarPanelTile extends ColouredBlockTile implements ITickableTileEntity
{
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


	private final LazyOptional<IItemHandler> inventory = LazyOptional.of(() -> itemStackHandler);


	public SolarPanelTile()
	{
		super(ModTiles.UPGRADE_BENCH_TILE.get());
	}


	@Override
	public void setRemoved()
	{
		super.setRemoved();
		inventory.invalidate();
	}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
	{
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return inventory.cast();
		return super.getCapability(cap, side);
	}

	@Override
	public void load(BlockState state, CompoundNBT tag)
	{
		super.load(state, tag);
		if (tag.contains("storage"))
			itemStackHandler.deserializeNBT(tag.getCompound("storage"));
	}


	@Nonnull
	@Override
	public CompoundNBT save(CompoundNBT tag)
	{
		super.save(tag);
		tag.put("storage", itemStackHandler.serializeNBT());
		return tag;
	}

	@Override
	public void tick()
	{
	}
}
