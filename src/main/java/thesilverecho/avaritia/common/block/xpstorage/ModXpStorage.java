package thesilverecho.avaritia.common.block.xpstorage;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class ModXpStorage extends XpStorage implements INBTSerializable<CompoundNBT>
{
	public ModXpStorage()
	{
		super();
	}

	protected int onXpChange(int i)
	{
		return i;
	}

	public void setXp(int xp)
	{
		this.xp = xp;
		onXpChange(xp);
	}


	@Override
	public int addXp(int amount, boolean simulate)
	{
		return onXpChange(super.addXp(amount, simulate));
	}

	@Override
	public int removeXp(int amount, boolean simulate)
	{
		return onXpChange(super.removeXp(amount, simulate));
	}

	@Override
	public CompoundNBT serializeNBT()
	{
		CompoundNBT tag = new CompoundNBT();
		tag.putInt("xp", getXpStored());
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundNBT tag)
	{
		setXp(tag.getInt("xp"));
	}
}
