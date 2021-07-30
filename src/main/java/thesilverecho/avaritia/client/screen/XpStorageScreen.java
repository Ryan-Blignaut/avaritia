package thesilverecho.avaritia.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import thesilverecho.avaritia.client.old.CustomToggleButton;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.container.XpStorageContainer;

import javax.annotation.Nonnull;
import java.awt.*;

public class XpStorageScreen extends ContainerScreen<XpStorageContainer>
{
	private static final ResourceLocation SCREEN = new ResourceLocation(Avaritia.MOD_ID, "textures/gui/xp_screen.png");

	public XpStorageScreen(XpStorageContainer screenContainer, PlayerInventory inv, ITextComponent titleIn)
	{
		super(screenContainer, inv, titleIn);

	}

	@Override
	protected void init()
	{
		super.init();

		addButton(new CustomToggleButton(this.width / 2 - 10, this.height / 2, false, 6, "e", b ->
		{
//			int xp = container.getXp();
//			ModPackets.INSTANCE.sendToServer(new TileUpdateXpPacket(-xp, container.getTileEntity().getPos()));
//			ModPackets.INSTANCE.sendToServer(new XpPacket(xp));
//			XpUtil.addPlayerXP(container.playerEntity, 10);

		}, ""));
		addButton(new CustomToggleButton(this.width / 2 + 10, this.height / 2, false, 6, "e", b ->
		{
//			int xp = container.playerEntity.experienceTotal;
//			ModPackets.INSTANCE.sendToServer(new TileUpdateXpPacket(xp, container.getTileEntity().getPos()));
//			ModPackets.INSTANCE.sendToServer(new XpPacket(-xp));
		}, "nbt_key"));

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
	}

	@Override
	protected void renderLabels(@Nonnull MatrixStack matrixStack, int x, int y)
	{

//		fill();

		FontRenderer fontRenderer = Minecraft.getInstance().font;
//		String text = "XP Levels: " + container.getXp();
//		int relX = (this.xSize - fontRenderer.getStringPropertyWidth(new StringTextComponent(text))) / 2;
//		int relY = (10 + fontRenderer.FONT_HEIGHT) / 2;
//		drawString(matrixStack, fontRenderer, text, relX, relY, Color.green.getRGB());
//		fill(matrixStack, this.xSize / 4, relY + 15, this.xSize / 4 + 85, relY + 20, Color.green.getRGB());

		super.renderLabels(matrixStack, x, y);
	}

	@Override
	public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		this.renderTooltip(matrixStack, mouseX, mouseY);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}

	private void render(MatrixStack matrixStack, int x, int y, int length, @Nonnull XpStorageContainer xpCont, int required)
	{

		String text = xpCont.getExperienceLevel() + "";
		int color = 8453920;
		boolean shadow = true;
		if (required > 0)
		{
			text += "/" + required;
			if (required > xpCont.getExperienceLevel())
			{
//				color = ColorUtil.getRGB(1f, 0, 0.1f);
				shadow = false;
			}
		}
		FontRenderer fr = Minecraft.getInstance().font;
		int strX = x + length / 2 - fr.width(text) / 2;
		if (shadow)
			fr.draw(matrixStack, text, strX, y - 11, color);
		else
			fr.drawShadow(matrixStack, text, strX, y - 11, color);

//		RenderUtil.bindTexture(IconEIO.TEXTURE);
//		GlStateManager.color(1f, 1f, 1f, 1f);
//		int xpScaled = xpCont.getXpBarScaled(length - 2);

		// x, y, u, v, width, height
		// start of 'slot'
//		gui.drawTexturedModalRect(x, y, 0, 91, 1, 5);
//		gui.drawTexturedModalRect(x + 1, y, 1, 91, length - 2, 5);
//		gui.drawTexturedModalRect(x + length - 1, y, 125, 91, 1, 5);
		fill(matrixStack, x + 1, y + 1, /*0,*/ 90/*xpScaled*/, 3, new Color(0, 127, 14).getRGB());
//		RenderUtil.renderQuad2D(x + 1, y + 1, 0, xpScaled, 3, ColorUtil.getRGB(0, 127, 14));
	}

	//	taken from vanilla PlayerEntity
//	public int xpBarCap()
//	{
////		if (this.container.getXp() >= 30)
////			return 112 + (this.container.getXp() - 30) * 9;
////		else
////			return this.container.getXp() >= 15 ? 37 + (this.container.getXp() - 15) * 5 : 7 + this.container.getXp() * 2;
//	}

}
