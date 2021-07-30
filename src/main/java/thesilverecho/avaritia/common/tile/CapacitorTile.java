package thesilverecho.avaritia.common.tile;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import thesilverecho.avaritia.common.init.ModTiles;
import thesilverecho.avaritia.common.util.CustomEnergyHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class CapacitorTile extends TileEntity implements ITickableTileEntity
{


	public static final ModelProperty<World> WORLD_PROP = new ModelProperty<>();
	public static final ModelProperty<BlockPos> POS_PROP = new ModelProperty<>();
	private final ItemStackHandler itemStackHandler = new ItemStackHandler(3)
	{
		@Override
		public boolean isItemValid(int slot, @Nonnull ItemStack stack)
		{
			return !stack.isEmpty() && stack.getCapability(CapabilityEnergy.ENERGY).map(e -> e.canReceive() && e.getEnergyStored() < e.getMaxEnergyStored()).orElse(Boolean.FALSE);
		}

		@Override
		protected void onContentsChanged(int slot)
		{
			setChanged();
		}

		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
		{

//			return stack.getCapability(CapabilityEnergy.ENERGY).map(iEnergyStorage -> iEnergyStorage.getEnergyStored() < iEnergyStorage.getMaxEnergyStored() ? stack:stack).orElse(super.insertItem(slot, stack, simulate));
			AtomicBoolean isValid = new AtomicBoolean();
			stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(iEnergyStorage ->
					isValid.set(iEnergyStorage.getEnergyStored() < iEnergyStorage.getMaxEnergyStored()));
			return !isValid.get() ? stack : super.insertItem(slot, stack, simulate);
		}

		@Nonnull
		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate)
		{
			AtomicBoolean isValid = new AtomicBoolean();
			final ItemStack stack = this.stacks.get(slot);
			stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(iEnergyStorage -> isValid.set(iEnergyStorage.getEnergyStored() >= iEnergyStorage.getMaxEnergyStored()));
			return isValid.get() ? stack : super.extractItem(slot, amount, simulate);
		}
	};
	private final CustomEnergyHandler energyHandler = new CustomEnergyHandler(500000, 1000)
	{
		@Override
		protected void onEnergyChanged()
		{
			setChanged();
		}
	};
	private final LazyOptional<IItemHandler> inventory = LazyOptional.of(() -> itemStackHandler);
	private final LazyOptional<CustomEnergyHandler> energy = LazyOptional.of(() -> energyHandler);

	public CapacitorTile()
	{
		super(ModTiles.CAPACITOR_TILE.get());
	}

	@Override
	public void setRemoved()
	{
		super.setRemoved();
		inventory.invalidate();
		energy.invalidate();
	}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
	{
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return inventory.cast();
		if (cap == CapabilityEnergy.ENERGY)
			return energy.cast();
		return super.getCapability(cap, side);
	}

	@Override
	public void load(@Nonnull BlockState state, CompoundNBT tag)
	{
		super.load(state, tag);
		if (tag.contains("storage"))
			itemStackHandler.deserializeNBT(tag.getCompound("storage"));
		if (tag.contains("energy"))
			energyHandler.deserializeNBT(tag.getCompound("energy"));
	}

	@Nonnull
	@Override
	public CompoundNBT save(CompoundNBT tag)
	{
		super.save(tag);
		tag.put("storage", itemStackHandler.serializeNBT());
		tag.put("energy", energyHandler.serializeNBT());
		return tag;
	}

	@Override
	public void tick()
	{
		sendOutPowerToItem();
		for (Direction direction : Direction.values())
		{
			final TileEntity tileEntity = level.getBlockEntity(worldPosition.relative(direction));
			if (tileEntity instanceof CapacitorTile)
			{
				autoBalanceEnergy(((CapacitorTile) tileEntity));
			}
		}
//		if (!getTileEntity().getTileData().getBoolean("test"))
//			sendOutPower();
	}

	private void sendOutPowerToItem()
	{
		AtomicInteger capacity = new AtomicInteger(energyHandler.getEnergyStored());
		if (capacity.get() > 0)
		{
			for (int i = 0; i < itemStackHandler.getSlots(); i++)
			{
				final ItemStack stack = itemStackHandler.getStackInSlot(i);
				if (!stack.isEmpty())
				{
					final boolean energy = stack.getCapability(CapabilityEnergy.ENERGY).map(iEnergyStorage ->
					{
						if (iEnergyStorage.canReceive())
						{
							int received = iEnergyStorage.receiveEnergy(Math.min(capacity.get(), 500), false);
							capacity.addAndGet(-received);
							energyHandler.consumeEnergy(received);
							setChanged();
							return capacity.get() > 0;
						} else return true;
					}).orElse(true);
					if (!energy)
						return;
				}
			}

		}

	}

	private void sendOutPower()
	{
		if (level == null)
			return;
		AtomicInteger capacity = new AtomicInteger(energyHandler.getEnergyStored());
		if (capacity.get() > 0)
			for (Direction direction : Direction.values())
			{
				final TileEntity tileEntity = level.getBlockEntity(worldPosition.relative(direction));
				if (tileEntity != null)
				{
					final boolean energy = tileEntity.getCapability(CapabilityEnergy.ENERGY, direction).map(iEnergyStorage ->
					{
						if (iEnergyStorage.canReceive())
						{
							int received = iEnergyStorage.receiveEnergy(Math.min(capacity.get(), 1000), false);
							capacity.addAndGet(-received);
							energyHandler.consumeEnergy(received);
							setChanged();
							return capacity.get() > 0;
						} else return true;
					}).orElse(true);
					if (!energy)
						return;
				}
			}

	}

	@Override
	public IModelData getModelData()
	{
		return new ModelDataMap.Builder().withInitial(WORLD_PROP, level).withInitial(POS_PROP, worldPosition).build();
	}

	public int autoBalanceEnergy(CapacitorTile tileEntity)
	{
		return tileEntity.getCapability(CapabilityEnergy.ENERGY).map(iEnergyStorage ->
		{
			int delta = energyHandler.getEnergyStored() - iEnergyStorage.getEnergyStored();
			final int i = energyHandler.extractEnergy(iEnergyStorage.receiveEnergy(energyHandler.extractEnergy(iEnergyStorage.receiveEnergy(delta / 2, true), true), false), false);
			if (delta > 0)
				return i;
			else if (delta < 0)
				return tileEntity.autoBalanceEnergy(this);
			else
				return 0;
		}).orElse(0);
	}

	private void takeInPower()
	{
		if (level == null)
			return;
		AtomicInteger capacity = new AtomicInteger(energyHandler.getEnergyStored());
		if (capacity.get() > 0)
			for (Direction direction : Direction.values())
			{
				final TileEntity tileEntity = level.getBlockEntity(worldPosition.relative(direction));
				if (tileEntity != null)
				{
					final boolean energy = tileEntity.getCapability(CapabilityEnergy.ENERGY, direction).map(iEnergyStorage ->
					{
						if (energyHandler.canReceive())
						{
							int received = energyHandler.receiveEnergy(Math.min(capacity.get(), 1000), false);
							capacity.addAndGet(-received);
							iEnergyStorage.extractEnergy(received, false);
							setChanged();
							return capacity.get() > 0;
						} else return true;
					}).orElse(true);
					if (!energy)
						return;
				}
			}

	}

}
