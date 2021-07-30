package thesilverecho.avaritia.common.packet;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;
import thesilverecho.avaritia.common.block.xpstorage.CapabilityXp;

import java.util.function.Supplier;

public class TileUpdateXpPacket
{

	private final int amount;
	private final BlockPos pos;

	public TileUpdateXpPacket(int amount, BlockPos pos)
	{
		this.amount = amount;
		this.pos = pos;
	}

	public static void handle(TileUpdateXpPacket message, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() ->
		{
			ServerPlayerEntity player = ctx.get().getSender();
			if (player == null)
				return;
			final TileEntity tileEntity = player.level.getBlockEntity(message.pos);

			if (tileEntity != null)
				tileEntity.getCapability(CapabilityXp.XP).ifPresent(iXpStorage ->
				{
					if (message.amount > 0)
						iXpStorage.addXp(message.amount, false);
					else if (message.amount < 0)
						iXpStorage.removeXp(-message.amount, false);

				});

		});
		ctx.get().setPacketHandled(true);
	}

	public static TileUpdateXpPacket decode(PacketBuffer buf)
	{
		return new TileUpdateXpPacket(buf.readInt(), buf.readBlockPos());
	}

	public static void encode(TileUpdateXpPacket msg, PacketBuffer buf)
	{
		buf.writeInt(msg.amount);
		buf.writeBlockPos(msg.pos);
	}

}
