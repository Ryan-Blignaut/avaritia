package thesilverecho.avaritia.common.packet;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ToggleStackNbtNew
{
	private final int slot;
	private final String nbtName;
	private final CompoundNBT nbt;

	public ToggleStackNbtNew(int slot, String nbtName, CompoundNBT nbt)
	{
		this.slot = slot;
		this.nbtName = nbtName;
		this.nbt = nbt;
	}

	public static void handle(ToggleStackNbtNew message, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() ->
		{
			ServerPlayerEntity player = ctx.get().getSender();
			if (player == null)
				return;
			CompoundNBT tag = player.inventory.getItem(message.slot).getOrCreateTag();
			tag.put(message.nbtName, message.nbt);
		});
		ctx.get().setPacketHandled(true);
	}

	public static ToggleStackNbtNew decode(PacketBuffer buf)
	{
		return new ToggleStackNbtNew(buf.readInt(), buf.readUtf(), buf.readNbt());
	}

	public static void encode(ToggleStackNbtNew msg, PacketBuffer buf)
	{
		buf.writeInt(msg.slot);
		buf.writeUtf(msg.nbtName);
		buf.writeNbt(msg.nbt);
	}
}
