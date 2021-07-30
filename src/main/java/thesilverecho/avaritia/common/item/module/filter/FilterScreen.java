package thesilverecho.avaritia.common.item.module.filter;


import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import thesilverecho.avaritia.client.old.NbtToggleButton;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.container.item.FilterContainer;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static thesilverecho.avaritia.common.item.module.filter.FilterItem.*;

public class FilterScreen extends ContainerScreen<FilterContainer>
{
	private static final ResourceLocation SCREEN = new ResourceLocation(Avaritia.MOD_ID, "textures/gui/filter.png");
	private static final ResourceLocation BOX = new ResourceLocation(Avaritia.MOD_ID, "textures/gui/box.png");
	private static final ResourceLocation CONFIG_UI = new ResourceLocation(Avaritia.MOD_ID, "textures/gui/config.png");
	private final ImmutableList<String> list = ImmutableList.of(NBT_FILTER_KEY, META_FILTER_KEY, TAG_FILTER_KEY);
	private boolean isSettingsOpen = false;
	private long lastPressTime = -1;

	public FilterScreen(FilterContainer screenContainer, PlayerInventory inv, ITextComponent titleIn)
	{
		super(screenContainer, inv, titleIn);
	}

	@Override
	protected void init()
	{
		super.init();
		AtomicInteger i = new AtomicInteger(3);
		AtomicInteger x = new AtomicInteger(1);
		AtomicInteger y = new AtomicInteger(45);
		list.forEach(s ->
		{
			if (x.getAndIncrement() % 4 == 0)
			{
				x.set(2);
				y.getAndAdd(22);
			}
			addButton(new NbtToggleButton(x.get() * 22, y.get(), i.getAndIncrement(), s));
		});
	}

	@Override
	protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY)
	{
		Objects.requireNonNull(minecraft).getTextureManager().bind(SCREEN);
		int relX = (this.width - this.imageWidth) / 2;
		int relY = (this.height - this.imageHeight) / 2;
		this.blit(matrixStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
		Minecraft.getInstance().textureManager.bind(BOX);
		this.menu.slots.forEach(slot -> blit(matrixStack, leftPos + slot.x - 1, topPos + slot.y - 1, 0, 0, 18, 18, 18, 18));
		addInventoryButtons(matrixStack, mouseX, mouseY);

	}

	private void addInventoryButtons(MatrixStack matrix, int mouseX, int mouseY)
	{
		Minecraft.getInstance().getTextureManager().bind(CONFIG_UI);
		double tanh = Math.tanh((Util.getMillis() - lastPressTime) / 400d);
		if (!isSettingsOpen)
		{
			this.blit(matrix, getGuiLeft() - 20, getGuiTop() + 6, 0, 32, 23, 26);
		} else
		{
			this.blit(matrix, getGuiLeft() - 56, getGuiTop() + 6, 0, 58, 59, 26 + (int) (81 * tanh));
		}

	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int btn)
	{
		final int x = getGuiLeft() - 20;
		final int y = getGuiTop() + 6;
		final int w = x + 23;
		final int h = y + 26;
		if (mouseX >= x && mouseY >= y && mouseX < w && mouseY < h)
		{
			isSettingsOpen = !isSettingsOpen;
//			if (lastPressTime == -1)
			lastPressTime = Util.getMillis();
		}


		return super.mouseClicked(mouseX, mouseY, btn);
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderTooltip(matrixStack, mouseX, mouseY);
	}
}
