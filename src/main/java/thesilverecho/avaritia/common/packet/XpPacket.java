package thesilverecho.avaritia.common.packet;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class XpPacket
{

	private final int amount;

	public XpPacket(int amount)
	{
		this.amount = amount;
	}

	public static void handle(XpPacket message, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() ->
		{
			ServerPlayerEntity player = ctx.get().getSender();
			if (player == null)
				return;
			player.giveExperiencePoints(message.amount);
		});
		ctx.get().setPacketHandled(true);
	}

	public static XpPacket decode(PacketBuffer buf)
	{
		return new XpPacket(buf.readInt());
	}

	public static void encode(XpPacket msg, PacketBuffer buf)
	{
		buf.writeInt(msg.amount);
	}

}
