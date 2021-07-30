package thesilverecho.avaritia.common.event;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import thesilverecho.avaritia.client.old.IItemStackOverlayRender;

import javax.annotation.Nonnull;
import java.util.Objects;

@Mod.EventBusSubscriber
public class CustomOverlayRenderer
{
	@SubscribeEvent
	public static void renderOverlay(@Nonnull RenderGameOverlayEvent.Post event)
	{
		if (event.getType() == RenderGameOverlayEvent.ElementType.ALL)
		{
			ItemStack itemStack = Objects.requireNonNull(Minecraft.getInstance().player).getMainHandItem();
			if (!itemStack.isEmpty() && itemStack.getItem() instanceof IItemStackOverlayRender)
				((IItemStackOverlayRender) itemStack.getItem()).renderOverlay(event.getMatrixStack(), itemStack);
		}
	}

}
