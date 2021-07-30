package thesilverecho.avaritia.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public abstract class BasicContainerScreen<T extends Container> extends ContainerScreen<T>
{
	private final int bgWidth;
	private final int bgHeight;

//	public BasicContainerScreen(T screenContainer, PlayerInventory inv, ITextComponent titleIn)
//	{
//		super(screenContainer, inv, titleIn);
//	}


	public BasicContainerScreen(T container, PlayerInventory inventory, ITextComponent title, int xSize, int ySize)
	{
		this(container, inventory, title, xSize, ySize, 256, 256);
	}

	public BasicContainerScreen(T container, PlayerInventory inventory, ITextComponent title, int xSize, int ySize, int bgWidth, int bgHeight)
	{
		super(container, inventory, title);
		this.imageWidth = xSize;
		this.imageHeight = ySize;
		this.bgWidth = bgWidth;
		this.bgHeight = bgHeight;
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		renderTooltip(matrixStack, mouseX, mouseY);
	}

	@Override
	protected void renderBg(MatrixStack stack, float partialTicks, int mouseX, int mouseY)
	{
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		minecraft.getTextureManager().bind(getBackgroundTexture());
		int x = this.getGuiLeft();
		int y = this.getGuiTop();
		blit(stack, x, y, 0, 0, this.imageWidth, this.imageHeight, this.bgWidth, this.bgHeight);
//		blit(stack, this.getGuiLeft(), this.getGuiTop(), 0, 0, xSize, ySize,512, 512);
	}

	@Override
	protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY)
	{
		super.renderLabels(matrixStack, mouseX, mouseY);
	}

	public abstract ResourceLocation getBackgroundTexture();
}
