package thesilverecho.avaritia.common.item.magnet;


import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import thesilverecho.avaritia.client.old.CustomToggleButton;
import thesilverecho.avaritia.client.old.ModGuiHelper;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.init.ModPackets;
import thesilverecho.avaritia.common.packet.ToggleStackNbt;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class MagnetFilterScreen extends ContainerScreen<MagnetContainer>
{

	public static final String MAIN_FILTER_TAG = "filter_options";

	public static final String TAG_BLACKLIST_KEY = "black_list_active";
	public static final String TAG_STICKY_KEY = "sticky_active";
	public static final String TAG_XP_KEY = "xp_active";

	public static final String ITEM_ENDER_MODE_KEY = "item_ender_active";
	public static final String XP_ENDER_MODE_KEY = "xp_ender_active";
	public static final String VISUALS_KEY = "visuals_active";

	public static final String NBT_FILTER_KEY = "nbt_filter";
	public static final String META_FILTER_KEY = "meta_filter";
	public static final String TAG_FILTER_KEY = "tag_filter";

	private static final ResourceLocation SCREEN = new ResourceLocation(Avaritia.MOD_ID, "textures/gui/charger.png");
	private static final ResourceLocation BOX = new ResourceLocation(Avaritia.MOD_ID, "textures/gui/box.png");
	private final ImmutableList<String> list = ImmutableList.of(TAG_BLACKLIST_KEY, TAG_STICKY_KEY, TAG_XP_KEY, NBT_FILTER_KEY, META_FILTER_KEY, TAG_FILTER_KEY, ITEM_ENDER_MODE_KEY, XP_ENDER_MODE_KEY, VISUALS_KEY);


	public MagnetFilterScreen(MagnetContainer screenContainer, PlayerInventory inv, ITextComponent titleIn)
	{
		super(screenContainer, inv, titleIn);
	}

	@Override
	protected void init()
	{
		super.init();
		AtomicInteger x = new AtomicInteger(1);
		AtomicInteger y = new AtomicInteger(45);
		AtomicInteger ref = new AtomicInteger();

		list.forEach(s ->
		{
			if (this.menu.stack.getOrCreateTagElement(MAIN_FILTER_TAG).contains(s))
			{
				if (x.getAndIncrement() % 4 == 0)
				{
					x.set(2);
					y.getAndAdd(22);
				}
				addButton(new CustomToggleButton(x.get() * 22, y.get(), true, ref.get(), s, button -> ModPackets.INSTANCE.sendToServer(new ToggleStackNbt(this.menu.slot, s)), s).addToolTip(new TranslationTextComponent("replace")).addToolTip(new TranslationTextComponent("tooltip.avaritia.desc.base").append(new TranslationTextComponent("tooltip.avaritia.desc." + s))));
			}
			ref.getAndIncrement();
		});
	}

	private void addButtonRange(int xPos, int yPos, int dx, int spriteStart, String... tags)
	{
		for (int i = 0; i < tags.length; i++)
			addCustomToggleButton(tags[i], xPos + dx * i, yPos, spriteStart + i);
	}


	private void addCustomToggleButton(String nbtKey, int x, int y, int spriteReference)
	{
		final CompoundNBT compound = this.menu.stack.getOrCreateTag().getCompound(MAIN_FILTER_TAG);
		if (compound.contains(nbtKey))
			addButton(new CustomToggleButton(x, y, compound.getBoolean(nbtKey), spriteReference, nbtKey, button -> ModPackets.INSTANCE.sendToServer(new ToggleStackNbt(this.menu.slot, nbtKey)), nbtKey).addToolTip(new TranslationTextComponent("replace")).addToolTip(new TranslationTextComponent("tooltip.avaritia.desc.base").append(new TranslationTextComponent("tooltip.avaritia.desc." + nbtKey))));
	}


	@Override
	protected void renderLabels(@Nonnull MatrixStack matrixStack, int x, int y)
	{
		super.renderLabels(matrixStack, x, y);

	}

	@Override
	public void onClose()
	{
		final ItemStack stack = this.menu.stack;
		if (stack.getItem() instanceof MagnetItem)
		{
			final MagnetItem item = (MagnetItem) stack.getItem();
			item.loadAndCache(stack);

		}
		super.onClose();
	}

	@Override
	protected void renderBg(@Nonnull MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY)
	{
		Objects.requireNonNull(minecraft).getTextureManager().bind(SCREEN);
		int relX = (this.width - this.imageWidth) / 2;
		int relY = (this.height - this.imageHeight) / 2;
		this.blit(matrixStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
		Minecraft.getInstance().textureManager.bind(BOX);
		this.menu.slots.forEach(slot -> blit(matrixStack, leftPos + slot.x - 1, topPos + slot.y - 1, 0, 0, 18, 18, 18, 18));
	}

	@Override
	public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderTooltip(matrixStack, mouseX, mouseY);
		buttons.stream().filter(widget -> widget.isHovered() && widget instanceof CustomToggleButton).map(widget -> (CustomToggleButton) widget).filter(button -> button.getToolTips() != null).forEach(button ->
		{
			if (button.getNbtKey().equals(TAG_BLACKLIST_KEY))
				button.setToolTip(new TranslationTextComponent("tooltip.avaritia." + button.getNbtKey(), button.isActiveState() ? "Blacklist" : "Whitelist"), 0);
			else
				button.setToolTip(new TranslationTextComponent("tooltip.avaritia." + button.getNbtKey(), button.isActiveState() ? "active" : "not active"), 0);
			ModGuiHelper.drawHoveringText(matrixStack, button.getToolTips(), mouseX, mouseY, Minecraft.getInstance().getWindow().getScreenWidth(), Minecraft.getInstance().getWindow().getScreenHeight(), 120, new Color(83, 85, 98).getRGB(), new Color(147, 151, 174).getRGB(), new Color(51, 52, 60).getRGB(), Minecraft.getInstance().font);
		});
	}


}
