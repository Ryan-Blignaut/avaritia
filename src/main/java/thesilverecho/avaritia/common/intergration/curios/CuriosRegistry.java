package thesilverecho.avaritia.common.intergration.curios;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import thesilverecho.avaritia.common.Avaritia;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CuriosRegistry
{
	private static final ResourceLocation MAGNET_ICON = new ResourceLocation(Avaritia.MOD_ID, "curios/magnet_slot");

	/**
	 * https://github.com/TheIllusiveC4/Curios/wiki/How-to-Use:-Developers
	 **/
	public static void setup(FMLCommonSetupEvent event)
	{
		//https://github.com/TheIllusiveC4/Curios/blob/fc77c876b630dc6e4a325cb9ac627b551749a19b/src/main/java/top/theillusivec4/curios/api/CurioTags.java
//		InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("magnet").size(1).icon(MAGNET_ICON).build());
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onStitch(TextureStitchEvent.Pre event)
	{
//		event.addSprite(MAGNET_ICON);
	}
}
