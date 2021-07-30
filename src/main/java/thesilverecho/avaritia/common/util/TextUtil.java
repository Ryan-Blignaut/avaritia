package thesilverecho.avaritia.common.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.awt.*;

import static net.minecraft.util.text.TextFormatting.*;

public class TextUtil
{

	private static final TextFormatting[] fabulousness = new TextFormatting[]{RED, GOLD, YELLOW, GREEN, AQUA, BLUE, LIGHT_PURPLE};
	private static final TextFormatting[] sanic = new TextFormatting[]{BLUE, BLUE, BLUE, BLUE, WHITE, BLUE, WHITE, WHITE, BLUE, WHITE, WHITE, BLUE, RED, WHITE, GRAY, GRAY, GRAY, GRAY, GRAY, GRAY, GRAY, GRAY, GRAY, GRAY, GRAY, GRAY, GRAY, GRAY, GRAY, GRAY};

	public static String makeFabulous(String input)
	{
		return ludicrousFormatting(input, fabulousness, 80.0, 1, 1);
	}

	public static ITextComponent makeFabulousText(ITextComponent input)
	{
		return new StringTextComponent(ludicrousFormatting(input.getString(), fabulousness, 80.0, 1, 1));
	}

	public static String makeSANIC(String input)
	{
		return ludicrousFormatting(input, sanic, 50.0, 2, 1);
	}

	public static String ludicrousFormatting(String input, TextFormatting[] colours, double delay, int step, int posstep)
	{
		StringBuilder sb = new StringBuilder(input.length() * 3);
		if (delay <= 0)
		{
			delay = 0.001;
		}

		int offset = (int) Math.floor(Util.getMillis() / delay) % colours.length;

		for (int i = 0; i < input.length(); i++)
		{
			char c = input.charAt(i);

			int col = ((i * posstep) + colours.length - offset) % colours.length;

			sb.append(colours[col].toString());
			sb.append(c);
		}

		return sb.toString();
	}


	public static Color getChromaColor(double x, double y, double offsetScale)
	{
		float v = 2000.0F;
		return new Color(Color.HSBtoRGB((float) (((double) System.currentTimeMillis() - x * 10.0D * offsetScale - y * 10.0D * offsetScale) % (double) v) / v, 0.8F, 0.8F));
	}

	public static void drawChromaString(String text, MatrixStack matrixStack, int x, int y, double offsetScale)
	{
		FontRenderer renderer = Minecraft.getInstance().font;
		for (char c : text.toCharArray())
		{
			int i = getChromaColor(x, y, offsetScale).getRGB();
			String tmp = String.valueOf(c);
			renderer.draw(matrixStack, tmp, x, y, i);
			x += renderer.width(tmp);
		}

	}


}
