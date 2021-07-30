package thesilverecho.avaritia.common.packet;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class EditStackNbtPacket
{
	private final int slot;
	private final CompoundNBT nbt;
	private final String tagName;

	public EditStackNbtPacket(String tagName, int slot, CompoundNBT nbt)
	{
		this.slot = slot;
		this.nbt = nbt;
		this.tagName = tagName;
	}

	public static void handle(EditStackNbtPacket message, Supplier<NetworkEvent.Context> ctx)
	{
		final NetworkEvent.Context context = ctx.get();
		context.enqueueWork(() ->
		{

			ServerPlayerEntity player = context.getSender();
			if (player == null)
				return;
			final Container container = player.containerMenu;
			final int slot = message.slot;
/*			if (slot >= container.slots.size())
//				return;*/
			final ItemStack stack = container.getSlot(slot).getItem();
			stack.getOrCreateTagElement(message.tagName).merge(message.nbt);
		});
		ctx.get().setPacketHandled(true);
	}

	public static EditStackNbtPacket decode(PacketBuffer buf)
	{
		return new EditStackNbtPacket(buf.readUtf(), buf.readInt(), buf.readNbt());
	}

	public static void encode(EditStackNbtPacket msg, PacketBuffer buf)
	{
		buf.writeUtf(msg.tagName);
		buf.writeInt(msg.slot);
		buf.writeNbt(msg.nbt);
	}
}
