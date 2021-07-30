package thesilverecho.avaritia.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.container.CapacitorContainer;

import javax.annotation.Nonnull;

public class CapacitorScreen extends ContainerScreen<CapacitorContainer>
{
	private static final ResourceLocation SCREEN = new ResourceLocation(Avaritia.MOD_ID, "textures/gui/charger.png");

	public CapacitorScreen(CapacitorContainer screenContainer, PlayerInventory inv, ITextComponent titleIn)
	{
		super(screenContainer, inv, titleIn);
	}

	@Override
	protected void init()
	{
		super.init();
		addButton(new Button(10, 10, 20, 20, new TranslationTextComponent("F"), p_onPress_1_ ->
		{
		}));
	}

	@Override
	public void renderTooltip(@Nonnull MatrixStack matrixStack, @Nonnull ITextComponent text, int mouseX, int mouseY)
	{
		super.renderTooltip(matrixStack, text, mouseX, mouseY);
	}

	@Override
	protected void renderBg(@Nonnull MatrixStack matrixStack, float partialTicks, int x, int y)
	{

		if (minecraft == null)
			return;
		minecraft.getTextureManager().bind(SCREEN);
		int relX = (this.width - this.imageWidth) / 2;
		int relY = (this.height - this.imageHeight) / 2;
		this.blit(matrixStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
//		int k = (int) (container.getEnergyScaled() /*/ 100f*/ * 55f);
//		this.blit(matrixStack, guiLeft + 9, this.guiTop + 70 - k, 176 + 12, 70 - k, 12, k + 1);
//		this.blit(matrixStack, 20, 30, 0, 0, this.xSize, this.ySize);


	}

	@Override
	protected void renderLabels(@Nonnull MatrixStack matrixStack, int x, int y)
	{
		super.renderLabels(matrixStack, x, y);
		FontRenderer fontRenderer = Minecraft.getInstance().font;
//		fontRenderer.drawStringWithShadow(matrixStack, String.format("Filled: %.2f%%", container.getEnergyScaled() * 100), 28 + 6, 14 + 6, -1);
//		fontRenderer.drawStringWithShadow(matrixStack, "Energy: " + container.getEnergy(), 28 + 6, 14 + 6 + fontRenderer.FONT_HEIGHT + 2, -1);
	}

	@Override
	public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderTooltip(matrixStack, mouseX, mouseY);
//		if (mouseX > guiLeft + 9 && mouseX < guiLeft + 21 && mouseY < this.guiTop + 70 && mouseY > this.guiTop)
//			this.renderComponentTooltip(matrixStack, ImmutableList.of(new TranslationTextComponent("avaritia.energy", container.getEnergy())), mouseX /*- guiLeft*/, mouseY /*- guiTop*/);
	}

}
