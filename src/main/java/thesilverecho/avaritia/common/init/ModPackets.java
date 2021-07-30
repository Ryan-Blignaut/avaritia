package thesilverecho.avaritia.common.init;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.packet.*;

public class ModPackets
{
	private static final String PROTOCOL_VERSION = Integer.toString(1);
	public static SimpleChannel INSTANCE;//= NetworkRegistry.ChannelBuilder.named(new ResourceLocation(Avaritia.MOD_ID, "main_channel")).clientAcceptedVersions(PROTOCOL_VERSION::equals).serverAcceptedVersions(PROTOCOL_VERSION::equals).networkProtocolVersion(() -> PROTOCOL_VERSION).simpleChannel();
	private static int ID = 0;

	public static void setup()
	{
		INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(Avaritia.MOD_ID, "main_channel"),
				() -> PROTOCOL_VERSION,
				PROTOCOL_VERSION::equals,
				PROTOCOL_VERSION::equals);


		/*final BiConsumer<ItemTogglePacket, Supplier<NetworkEvent.Context>> handle = ItemTogglePacket::handle;
		INSTANCE.messageBuilder(ItemTogglePacket.class, getNextID())
				.encoder(ItemTogglePacket::encode)
				.decoder(ItemTogglePacket::decode)
				.consumer(handle)
				.add();*/


		INSTANCE.registerMessage(getNextID(), ItemTogglePacket.class, ItemTogglePacket::encode, ItemTogglePacket::decode, ItemTogglePacket::handle);
		INSTANCE.registerMessage(getNextID(), ToggleStackNbt.class, ToggleStackNbt::encode, ToggleStackNbt::decode, ToggleStackNbt::handle);
		INSTANCE.registerMessage(getNextID(), ToggleStackNbtNew.class, ToggleStackNbtNew::encode, ToggleStackNbtNew::decode, ToggleStackNbtNew::handle);
		INSTANCE.registerMessage(getNextID(), XpPacket.class, XpPacket::encode, XpPacket::decode, XpPacket::handle);
		INSTANCE.registerMessage(getNextID(), TileUpdateXpPacket.class, TileUpdateXpPacket::encode, TileUpdateXpPacket::decode, TileUpdateXpPacket::handle);
		INSTANCE.registerMessage(getNextID(), TileUpdateXpPacket.class, TileUpdateXpPacket::encode, TileUpdateXpPacket::decode, TileUpdateXpPacket::handle);
		INSTANCE.registerMessage(getNextID(), SpawnClientParticles.class, SpawnClientParticles::encode, SpawnClientParticles::decode, SpawnClientParticles::handle);
		INSTANCE.registerMessage(getNextID(), UpdateColourPacket.class, UpdateColourPacket::encode, UpdateColourPacket::decode, UpdateColourPacket::handle);
		INSTANCE.registerMessage(getNextID(), HandleRightClickPacket.class, HandleRightClickPacket::encode, HandleRightClickPacket::decode, HandleRightClickPacket::handle);
		INSTANCE.registerMessage(getNextID(), EditStackNbtPacket.class, EditStackNbtPacket::encode, EditStackNbtPacket::decode, EditStackNbtPacket::handle);

	}


	public static int getNextID()
	{
		return ID++;
	}

	public static <MSG> void sendToCustom(MSG message, ServerPlayerEntity player)
	{
		ModPackets.INSTANCE.sendTo(message, player.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
	}


//	public static void sendToAllClients(World world, PacketBase packet) {
//		for (PlayerEntity player : world.getPlayers()) {
//			ServerPlayerEntity sp = ((ServerPlayerEntity) player);
//			ModPackets.INSTANCE.sendTo(packet,					sp.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
//		}
//	}
}
