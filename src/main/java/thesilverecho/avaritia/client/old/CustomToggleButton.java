package thesilverecho.avaritia.client.old;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import thesilverecho.avaritia.common.Avaritia;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class CustomToggleButton extends Button
{
	private static final ResourceLocation BUTTON_SHEET = new ResourceLocation(Avaritia.MOD_ID, "textures/gui/button_sheet.png");
	private static final ResourceLocation SPRITES = new ResourceLocation(Avaritia.MOD_ID, "textures/gui/sprites.png");
	private final int spriteReference;
	private final List<ITextComponent> toolTips = new ArrayList<>();
	private final String nbtKey;
	private boolean activeState;

	public CustomToggleButton(int x, int y, boolean activeState, int spriteReference, String title, IPressable pressedAction, String nbtKey)
	{
		super(x, y, 20, 20, new TranslationTextComponent(title), pressedAction, Button::renderToolTip);
		this.activeState = activeState;
		this.spriteReference = spriteReference;
		this.nbtKey = nbtKey;
	}

	public String getNbtKey()
	{
		return nbtKey;
	}

	public boolean isActiveState()
	{
		return activeState;
	}

	public CustomToggleButton addToolTip(ITextComponent toolTip)
	{
		this.toolTips.add(toolTip);
		return this;
	}

	public CustomToggleButton setToolTip(ITextComponent toolTip, int i)
	{
		this.toolTips.set(i, toolTip);
		return this;
	}

	public List<ITextComponent> getToolTips()
	{
		return toolTips;
	}

	@Override
	public void renderButton(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		Minecraft.getInstance().textureManager.bind(BUTTON_SHEET);
		blit(matrixStack, x, y, isHovered() ? 0 : 20, activeState ? 0 : 20, this.width, this.height, 40, 40);
		Minecraft.getInstance().textureManager.bind(SPRITES);
		blit(matrixStack, x + 2, y + 2, spriteReference * 16, activeState ? 0 : 16, this.width - 4, this.height - 4, 256, 32);
	}


	@Override
	public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}

	@Override
	public void onClick(double mouseX, double mouseY)
	{
		super.onClick(mouseX, mouseY);
		this.activeState = !activeState;
	}

	@Override
	public void renderToolTip(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY)
	{
		super.renderToolTip(matrixStack, mouseX, mouseY);

	}
}
