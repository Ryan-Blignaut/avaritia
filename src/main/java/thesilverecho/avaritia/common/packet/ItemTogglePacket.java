package thesilverecho.avaritia.common.packet;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import thesilverecho.avaritia.common.util.IToggleable;

import java.util.Objects;
import java.util.function.Supplier;

public class ItemTogglePacket
{
	private final int slot;

	public ItemTogglePacket(int slot)
	{
		this.slot = slot;
	}

//
//	public boolean handle(Supplier<NetworkEvent.Context> ctx) {
//		ctx.get().enqueueWork(() -> {
//			ServerWorld spawnWorld = ctx.get().getSender().world.getServer().getWorld(type);
//			EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(id);
//			if (entityType == null) {
//				throw new IllegalStateException("This cannot happen! Unknown id '" + id.toString() + "'!");
//			}
//			entityType.spawn(spawnWorld, null, null, pos, SpawnReason.SPAWN_EGG, true, true);
//		});
//		return true;
//	}

	public static void handle(ItemTogglePacket message, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() ->
		{
			ServerPlayerEntity player = ctx.get().getSender();
			if (Objects.requireNonNull(player).containerMenu == null)
			{
				return;
			}
			int scount = player.containerMenu.slots.size();
			//this is an edge case but it DID happen: put charmin your hotbar and then open a creative inventory tab. avoid index OOB
			if (message.slot >= scount)
			{
				//will NOT work in creative mode. slots are messed up
				return;
			}
			Slot slotObj = player.containerMenu.getSlot(message.slot);
			if (!slotObj.getItem().isEmpty())
			{
				ItemStack maybeCharm = slotObj.getItem();
				if (maybeCharm.getItem() instanceof IToggleable)
				{
					//example: is a charm or something
					IToggleable c = (IToggleable) maybeCharm.getItem();
					c.toggle(player, maybeCharm);
				}
			}
		});
		ctx.get().setPacketHandled(true);
	}

	public static ItemTogglePacket decode(PacketBuffer buf)
	{
		return new ItemTogglePacket(buf.readInt());
	}

	public static void encode(ItemTogglePacket msg, PacketBuffer buf)
	{
		buf.writeInt(msg.slot);
	}
}
