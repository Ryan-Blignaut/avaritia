package thesilverecho.avaritia.client.old;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.container.item.FilterContainer;
import thesilverecho.avaritia.common.init.ModPackets;
import thesilverecho.avaritia.common.packet.EditStackNbtPacket;

import javax.annotation.Nonnull;

public class NbtToggleButton extends Button
{
	private static final ResourceLocation BUTTON_SHEET = new ResourceLocation(Avaritia.MOD_ID, "textures/gui/button_sheet.png");
	private static final ResourceLocation SPRITES = new ResourceLocation(Avaritia.MOD_ID, "textures/gui/sprites.png");
	private static final String MAIN_TAG = "filter";
	private final int spriteReference;
	private boolean activeState;

	public NbtToggleButton(int x, int y, int spriteReference, String nbtKey)
	{

		super(x, y, 20, 20, new TranslationTextComponent(nbtKey), b ->
		{
			final CompoundNBT nbt = new CompoundNBT();
			final boolean value = FilterContainer.stackStorage.getOrCreateTagElement(MAIN_TAG).getBoolean(nbtKey);
			nbt.putBoolean(nbtKey, !value);
			ModPackets.INSTANCE.sendToServer(new EditStackNbtPacket(MAIN_TAG, FilterContainer.slotStorage, nbt));
		}, Button::renderToolTip);
		this.activeState = FilterContainer.stackStorage.getOrCreateTagElement(MAIN_TAG).getBoolean(nbtKey);
		this.spriteReference = spriteReference;
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
