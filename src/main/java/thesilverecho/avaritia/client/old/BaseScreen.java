package thesilverecho.avaritia.client.old;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import thesilverecho.avaritia.common.Avaritia;

import javax.annotation.Nonnull;
import java.awt.*;

public class BaseScreen<T extends Container> extends ContainerScreen<T>
{
	private static final ResourceLocation BOX = new ResourceLocation(Avaritia.MOD_ID, "textures/gui/box.png");

	public BaseScreen(T screenContainer, PlayerInventory inv, ITextComponent titleIn)
	{
		super(screenContainer, inv, titleIn);
	}

	@Override
	protected void renderBg(@Nonnull MatrixStack matrixStack, float partialTicks, int x, int y)
	{

	}

	@Override
	protected void renderLabels(@Nonnull MatrixStack matrixStack, int x, int y)
	{
		Minecraft.getInstance().textureManager.bind(BOX);
		this.menu.slots.forEach(slot -> blit(matrixStack, /*guiLeft + */slot.x - 1, /*guiTop +*/ slot.y - 1, 0, 0, 18, 18, 18, 18));
		super.renderLabels(matrixStack, x, y);

	}


	@Override
	public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		buttons.stream().filter(widget -> widget.isHovered() && widget instanceof CustomToggleButton).forEach(button -> renderCustomHover(matrixStack, mouseX, mouseY, (CustomToggleButton) button));
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}

	protected void renderCustomHover(MatrixStack matrixStack, int mouseX, int mouseY, CustomToggleButton button)
	{
		if (button.getToolTips() != null)
			ModGuiHelper.drawHoveringText(matrixStack, button.getToolTips(), mouseX, mouseY, Minecraft.getInstance().getWindow().getScreenWidth(), Minecraft.getInstance().getWindow().getScreenHeight(), 120, new Color(83, 85, 98).getRGB(), new Color(147, 151, 174).getRGB(), new Color(51, 52, 60).getRGB(), Minecraft.getInstance().font);
	}

}
