package thesilverecho.avaritia.common.block.xpstorage;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityXp
{
	@CapabilityInject(IXpStorage.class)
	public static Capability<IXpStorage> XP;

	public static void register()
	{
		CapabilityManager.INSTANCE.register(IXpStorage.class, new Capability.IStorage<IXpStorage>()
		{
			@Override
			public INBT writeNBT(Capability<IXpStorage> capability, IXpStorage instance, Direction side)
			{
				return IntNBT.valueOf(instance.getXpStored());
			}

			@Override
			public void readNBT(Capability<IXpStorage> capability, IXpStorage instance, Direction side, INBT nbt)
			{
				if (!(instance instanceof XpStorage))
					throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
				((XpStorage) instance).xp = ((IntNBT) nbt).getAsInt();
			}
		}, XpStorage::new);
	}
}
