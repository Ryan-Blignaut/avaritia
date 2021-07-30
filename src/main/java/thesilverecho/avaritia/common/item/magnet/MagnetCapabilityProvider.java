package thesilverecho.avaritia.common.item.magnet;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import thesilverecho.avaritia.common.util.CustomEnergyHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MagnetCapabilityProvider implements ICapabilitySerializable<CompoundNBT>
{
	private final LazyOptional<CustomEnergyHandler> energy = LazyOptional.of(() -> new CustomEnergyHandler(1_000_000, 20000)
	{
	});
	private int slots;
	private final LazyOptional<ItemStackHandler> inventory = LazyOptional.of(() -> new ItemStackHandler(slots)
	{
		@Override
		public boolean isItemValid(int slot, @Nonnull ItemStack stack)
		{
			return !(stack.getItem() instanceof MagnetItem) && super.isItemValid(slot, stack);
		}
	});

	public MagnetCapabilityProvider(int slots)
	{
		this.slots = slots;
	}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
	{
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return inventory.cast();
		if (cap == CapabilityEnergy.ENERGY)
			return energy.cast();
		return LazyOptional.empty();

	}

	@Override
	public CompoundNBT serializeNBT()
	{
		CompoundNBT tag = new CompoundNBT();
		if (inventory.resolve().isPresent())
			tag.put("inv", inventory.resolve().get().serializeNBT());
		if (energy.resolve().isPresent())
			tag.put("energy", energy.resolve().get().serializeNBT());
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt)
	{
		inventory.ifPresent(h -> h.deserializeNBT(nbt.getCompound("inv")));
		energy.ifPresent(h -> h.deserializeNBT(nbt.getCompound("energy")));
	}
}
