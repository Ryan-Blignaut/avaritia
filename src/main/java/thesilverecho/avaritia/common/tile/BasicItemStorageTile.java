package thesilverecho.avaritia.common.tile;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class BasicItemStorageTile extends TileEntity
{
	protected final LazyOptional<IItemHandler> inventory = LazyOptional.of(this::getInventory);

	public BasicItemStorageTile(TileEntityType<?> tileEntityTypeIn)
	{
		super(tileEntityTypeIn);
	}

	public abstract ItemStackHandler getInventory();

	protected boolean isWithinUsableDistance(PlayerEntity player)
	{
		return player.distanceToSqr(worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5) <= 64;
	}

	@Override
	public CompoundNBT save(CompoundNBT tag)
	{
		super.save(tag);
		tag.put("storage", getInventory().serializeNBT());
		return tag;
	}

	@Override
	public void load(@Nonnull BlockState state, CompoundNBT tag)
	{
		super.load(state, tag);
		if (tag.contains("storage"))
			getInventory().deserializeNBT(tag.getCompound("storage"));
	}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
	{
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, this.inventory);
//			return inventory.cast();
		return super.getCapability(cap, side);
	}

	@Override
	public void setRemoved()
	{
		super.setRemoved();
		inventory.invalidate();
	}

}
