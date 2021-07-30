package thesilverecho.avaritia.client.screen;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.container.CompactorContainer;

public class CompactorScreen extends BasicContainerScreen<CompactorContainer>
{
	public CompactorScreen(CompactorContainer screenContainer, PlayerInventory inv, ITextComponent titleIn)
	{
		super(screenContainer, inv, titleIn, 256, 256);
	}

	@Override
	public ResourceLocation getBackgroundTexture()
	{
		return new ResourceLocation(Avaritia.MOD_ID, "textures/gui/compactor.png");
	}

}
