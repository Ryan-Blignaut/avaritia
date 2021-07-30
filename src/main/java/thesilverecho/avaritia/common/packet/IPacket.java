package thesilverecho.avaritia.common.packet;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Function;
import java.util.function.Supplier;

public interface IPacket<T>
{
	void encode(PacketBuffer buf);

	boolean handle(Supplier<NetworkEvent.Context> ctx);

	//	 Function<PacketBuffer, M> decoder;
	T construct(Function<PacketBuffer, T> buf);


	/*public MessageBuilder<MSG> encoder(BiConsumer<MSG, PacketBuffer> encoder) {
		this.encoder = encoder;
		return this;
	}

	public MessageBuilder<MSG> decoder(Function<PacketBuffer, MSG> decoder) {
		this.decoder = decoder;
		return this;
	}*/


}
