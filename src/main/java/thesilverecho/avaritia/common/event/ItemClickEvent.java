package thesilverecho.avaritia.common.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.BufferUtils;
import thesilverecho.avaritia.client.AvaritiaTextures;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.init.ModPackets;
import thesilverecho.avaritia.common.item.MatterClusterItem;
import thesilverecho.avaritia.common.packet.HandleRightClickPacket;
import thesilverecho.avaritia.common.util.IItemRightClickable;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber
public class ItemClickEvent
{
	private static final Set<ItemStack> capturedDrops = new HashSet<>();
	public static boolean doItemCapture;

	public static Set<ItemStack> getCapturedDrops()
	{
		return capturedDrops;
	}

	@SubscribeEvent
	public static void onGuiClick(GuiScreenEvent.MouseClickedEvent.Pre event)
	{
		if (event.getGui() == null || !(event.getGui() instanceof ContainerScreen<?>))
			return;
		final ContainerScreen<?> gui = (ContainerScreen<?>) event.getGui();
		final boolean rightClickDown = event.getButton() == 1;
		final Slot slot = gui.getSlotUnderMouse();
		if (rightClickDown && slot != null)
		{
			final Item item = slot.getItem().getItem();
			if (item instanceof IItemRightClickable)
			{
				ModPackets.INSTANCE.sendToServer(new HandleRightClickPacket(gui.getMenu().slots.indexOf(slot)));
//				player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1, 1);
				event.setCanceled(true);
			}
		}

	}

	@SubscribeEvent
	public static void onRenderTick(TickEvent event)
	{
		if (event instanceof TickEvent.RenderTickEvent)
			if (event.phase == TickEvent.Phase.START)
			{
				Avaritia.cosmicUVs = BufferUtils.createFloatBuffer(4 * AvaritiaTextures.LIST.size());

				AvaritiaTextures.LIST.forEach(location ->
				{
					TextureAtlasSprite icon = (Minecraft.getInstance().getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(location));
					Avaritia.cosmicUVs.put(icon.getU0()).put(icon.getV0()).put(icon.getU1()).put(icon.getV1());
				});
				Avaritia.cosmicUVs.flip();
			}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onPickup(PlayerEvent.ItemPickupEvent event)
	{
		final ItemStack stack1 = event.getStack();
		if (stack1.getItem() instanceof MatterClusterItem)
		{
			final PlayerInventory inventory = event.getPlayer().inventory;
			for (int i = 0; i < inventory.getContainerSize(); i++)
			{
				final ItemStack stack = inventory.getItem(i);
				if (stack.getItem() instanceof MatterClusterItem)
				{
//					MatterClusterItem.mergeClusters(stack1, stack);
				}
			}

		}
	}

	@SubscribeEvent
	public static void spawn(EntityJoinWorldEvent event)
	{
		if (doItemCapture)
			if (event.getEntity() instanceof ItemEntity)
			{
				ItemStack stack = ((ItemEntity) event.getEntity()).getItem();
				capturedDrops.add(stack);
				event.setCanceled(true);
			}
	}
}
