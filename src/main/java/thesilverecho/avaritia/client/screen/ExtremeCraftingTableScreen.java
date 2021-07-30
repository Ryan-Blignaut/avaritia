package thesilverecho.avaritia.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.container.ExtremeCraftingTableContainer;

public class ExtremeCraftingTableScreen extends BasicContainerScreen<ExtremeCraftingTableContainer>
{
	public ExtremeCraftingTableScreen(ExtremeCraftingTableContainer screenContainer, PlayerInventory inv, ITextComponent titleIn)
	{
		super(screenContainer, inv, titleIn, 234, 278, 512, 512);
	}

	@Override
	public ResourceLocation getBackgroundTexture()
	{
		return new ResourceLocation(Avaritia.MOD_ID, "textures/gui/extreme_crafting_table1.png");
	}

	@Override
	protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY)
	{
		String title = this.getTitle().getString();

		this.font.draw(matrixStack, title, 8.0F, 6.0F, 4210752);
		String inventory = this.inventory.getDisplayName().getString();
		this.font.draw(matrixStack, inventory, 39.0F, this.imageHeight - 94.0F, 4210752);
	}


}
