package thesilverecho.avaritia.common.packet;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ToggleStackNbt
{
	private final int slot;
	private final String nbtName;

	public ToggleStackNbt(int slot, String nbtName)
	{
		this.slot = slot;
		this.nbtName = nbtName;
	}

	public static void handle(ToggleStackNbt message, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() ->
		{
			ServerPlayerEntity player = ctx.get().getSender();
			if (player == null)
				return;
			CompoundNBT tag = player.inventory.getItem(message.slot).getOrCreateTag();
			if (!tag.contains("filter_options"))
				tag.put("filter_options", new CompoundNBT());

			CompoundNBT nbt = tag.getCompound("filter_options");
			nbt.putBoolean(message.nbtName, !nbt.getBoolean(message.nbtName));
		});
		ctx.get().setPacketHandled(true);
	}

	public static ToggleStackNbt decode(PacketBuffer buf)
	{
		return new ToggleStackNbt(buf.readInt(), buf.readUtf());
	}

	public static void encode(ToggleStackNbt msg, PacketBuffer buf)
	{
		buf.writeInt(msg.slot);
		buf.writeUtf(msg.nbtName);
	}
}
