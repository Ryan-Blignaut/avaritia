package thesilverecho.avaritia.common.event;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ClientTicker
{

	public static int ticksInGame = 0;
	public static float partialTicks = 0;
	public static float delta = 0;
	public static float total = 0;

	private static void calcDelta()
	{
		float oldTotal = total;
		total = ticksInGame + partialTicks;
		delta = total - oldTotal;
	}

	@SubscribeEvent
	public static void renderTick(TickEvent.RenderTickEvent event)
	{
		if (event.phase != TickEvent.Phase.START)
		{
			calcDelta();
		}
	}

	@SubscribeEvent
	public static void clientTickEnd(TickEvent.ClientTickEvent event)
	{
		if (event.phase == TickEvent.Phase.END)
		{
			if (!Minecraft.getInstance().isPaused())
			{
				ticksInGame++;
				partialTicks = 0;
			}
			calcDelta();
		}
	}


}
