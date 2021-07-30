package thesilverecho.avaritia.common.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import thesilverecho.avaritia.common.item.tool.InfinitySword;
import thesilverecho.avaritia.common.util.TextUtil;

import javax.annotation.Nonnull;
import java.util.List;

@Mod.EventBusSubscriber
public class ToolTip
{

	@SubscribeEvent
	public static void renderTip(@Nonnull ItemTooltipEvent event)
	{
		final ItemStack itemStack = event.getItemStack();
		final List<ITextComponent> tip = event.getToolTip();

		if (Minecraft.getInstance().options.advancedItemTooltips)
		{
			if (itemStack.hasTag())
				if (!Screen.hasShiftDown())
					tip.add(new TranslationTextComponent("Hold shift for more info").withStyle(TextFormatting.DARK_GRAY));
				else
				{
					final CompoundNBT tag = itemStack.getTag();
					assert tag != null;
					tip.add(new StringTextComponent(tag.toString()).withStyle(TextFormatting.DARK_GRAY));
//					Objects.requireNonNull(tag).getAllKeys().forEach(s -> tip.add(new StringTextComponent(s + ": " + tag.get(s).toString()).withStyle(TextFormatting.AQUA)));
				}
		}
		if (itemStack.getItem() instanceof InfinitySword)
		{
//			tip.stream().filter(textComponent -> textComponent.)
//			tip.forEach(textComponent ->
//			{
//				System.out.println(textComponent);
//				System.out.println(textComponent.getString());
//				System.out.println(textComponent.getUnformattedComponentText());
//
//
//			});
			for (int i = 0; i < tip.size(); i++)
			{
				final String damage = new TranslationTextComponent("attribute.name.generic.attack_damage").getString();
				if (tip.get(i).getString().contains(damage))
					tip.set(i, new StringTextComponent(TextFormatting.BLUE + "+" + TextUtil.makeFabulous(new TranslationTextComponent("tooltip.avaritia.infinite").getString()) + " " + TextFormatting.BLUE + damage));
			}
		}


	}
}
