package thesilverecho.avaritia.common.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SpawnClientParticles
{
	private final int amount;
	private final BlockPos pos;
	private final int col;

	public SpawnClientParticles(int amount, BlockPos pos, int col)
	{
		this.amount = amount;
		this.pos = pos;
		this.col = col;
	}

	public static void handle(SpawnClientParticles message, Supplier<NetworkEvent.Context> ctx)
	{
		if (ctx.get().getDirection().getReceptionSide().isClient())
		{
			ctx.get().enqueueWork(() ->
			{
				final ClientWorld world = Minecraft.getInstance().level;
				if (world == null)
					return;
				float alpha = (float) (message.col >> 24 & 255) / 255.0F;
				float red = (float) (message.col >> 16 & 255) / 255.0F;
				float green = (float) (message.col >> 8 & 255) / 255.0F;
				float blue = (float) (message.col & 255) / 255.0F;
				for (int i = 0; i < message.amount; i++)
					world.addParticle(new RedstoneParticleData(red, green, blue, alpha), message.pos.getX(), message.pos.getY(), message.pos.getZ(), 0, 0, 0);
			});

		}
		ctx.get().setPacketHandled(true);
	}

	public static SpawnClientParticles decode(PacketBuffer buf)
	{
		return new SpawnClientParticles(buf.readInt(), buf.readBlockPos(), buf.readInt());
	}

	public static void encode(SpawnClientParticles msg, PacketBuffer buf)
	{
		buf.writeInt(msg.amount);
		buf.writeBlockPos(msg.pos);
		buf.writeInt(msg.col);
	}
}
