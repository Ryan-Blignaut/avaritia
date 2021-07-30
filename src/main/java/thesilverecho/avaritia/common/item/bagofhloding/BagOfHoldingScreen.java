package thesilverecho.avaritia.common.item.bagofhloding;


import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import thesilverecho.avaritia.common.Avaritia;

import java.util.Objects;

public class BagOfHoldingScreen extends ContainerScreen<BagOfHoldingContainer>
{
	private static final ResourceLocation SCREEN = new ResourceLocation(Avaritia.MOD_ID, "textures/gui/xp_screen.png");
	private static final ResourceLocation BOX = new ResourceLocation(Avaritia.MOD_ID, "textures/gui/box.png");


	public BagOfHoldingScreen(BagOfHoldingContainer screenContainer, PlayerInventory inv, ITextComponent titleIn)
	{
		super(screenContainer, inv, titleIn);
	}

	@Override
	protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y)
	{
		Objects.requireNonNull(minecraft).getTextureManager().bind(SCREEN);
		int relX = (this.width - this.imageWidth) / 2;
		int relY = (this.height - this.imageHeight) / 2;
		this.blit(matrixStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
		Minecraft.getInstance().textureManager.bind(BOX);
		this.menu.slots.forEach(slot -> blit(matrixStack, leftPos + slot.x - 1, topPos + slot.y - 1, 0, 0, 18, 18, 18, 18));

	}


}
