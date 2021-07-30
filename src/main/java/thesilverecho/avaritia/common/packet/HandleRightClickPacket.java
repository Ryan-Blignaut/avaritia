package thesilverecho.avaritia.common.packet;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import thesilverecho.avaritia.common.util.IItemRightClickable;

import java.util.function.Supplier;

public class HandleRightClickPacket
{
	private final int slot;


	public HandleRightClickPacket(int slot)
	{
		this.slot = slot;
	}

	public static void handle(HandleRightClickPacket message, Supplier<NetworkEvent.Context> ctx)
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
			if (!stack.isEmpty() && stack.getItem() instanceof IItemRightClickable)
				((IItemRightClickable) stack.getItem()).onClick(player, stack, slot);
		});
		context.setPacketHandled(true);
	}

	public static HandleRightClickPacket decode(PacketBuffer buf)
	{
		return new HandleRightClickPacket(buf.readInt());
	}

	public static void encode(HandleRightClickPacket msg, PacketBuffer buf)
	{
		buf.writeInt(msg.slot);
	}
}
