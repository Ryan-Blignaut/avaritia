package thesilverecho.avaritia.common.packet;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;
import thesilverecho.avaritia.common.tile.ColouredBlockTile;

import java.util.Objects;
import java.util.function.Supplier;

public class UpdateColourPacket
{
	private final int col;
	private final BlockPos pos;


	public UpdateColourPacket(int col, BlockPos pos)
	{
		this.col = col;
		this.pos = pos;
	}

	public static void handle(UpdateColourPacket message, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() ->
		{
			ServerPlayerEntity player = ctx.get().getSender();
			if (player == null)
				return;
			if (player.level.getBlockEntity(message.pos) instanceof ColouredBlockTile)
			{
				((ColouredBlockTile) Objects.requireNonNull(player.level.getBlockEntity(message.pos))).setColour(message.col);
			}
		});
		ctx.get().setPacketHandled(true);
	}


	public static UpdateColourPacket decode(PacketBuffer packetBuffer)
	{
		return new UpdateColourPacket(packetBuffer.readInt(), packetBuffer.readBlockPos());
	}

	public static void encode(UpdateColourPacket msg, PacketBuffer buf)
	{
		buf.writeInt(msg.col);
		buf.writeBlockPos(msg.pos);
	}

}
