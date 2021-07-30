package thesilverecho.avaritia.common.packet;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenScreenPacket
{

	private final int amount;

	public OpenScreenPacket(int amount)
	{
		this.amount = amount;
	}

	public static void handle(OpenScreenPacket message, Supplier<NetworkEvent.Context> ctx)
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

	public static OpenScreenPacket decode(PacketBuffer buf)
	{
		return new OpenScreenPacket(buf.readInt());
	}

	public static void encode(OpenScreenPacket msg, PacketBuffer buf)
	{
		buf.writeInt(msg.amount);
	}

}
