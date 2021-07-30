package thesilverecho.avaritia.client.util;

import net.minecraft.util.Util;

import java.awt.*;

public class ColourHelper
{
	public static int getRed(int color)
	{
		return color >> 16 & 0xff;
	}

	public static int getGreen(int color)
	{
		return color >> 8 & 0xff;
	}

	public static int getBlue(int color)
	{
		return color & 0xff;
	}

	public static int getAlpha(int color)
	{
		return color >> 24 & 0xff;
	}


	public static float getRedFloat(int color)
	{
		return (color >> 16 & 0xff) / 255f;
	}

	public static float getGreenFloat(int color)
	{
		return (color >> 8 & 0xff) / 255f;
	}

	public static float getBlueFloat(int color)
	{
		return (color & 0xff) / 255f;
	}

	public static float getAlphaFloat(int color)
	{
		return (color >> 24 & 0xff) / 255f;
	}


	public static int toColour(int red, int green, int blue, int alpha)
	{

		int r = red << 16 & 0xff;
		int g = green << 8 & 0xff;
		int b = blue & 0xff;
		int a = alpha << 24 & 0xff;

		return a | r | g | b;
	}


	public static int toColour(int red, int green, int blue)
	{

		return toColour(red, green, blue, 255);
	}

	//	public static int toColour(RenderHandEvent)
//	{
//
//		return toColour(red, green, blue, 255);
//	}
	public static int getChromaColor()
	{
		float v = 2000.0F;
		return Color.HSBtoRGB((float) ((double) Util.getMillis() % (double) v) / v, 0.8F, 0.8F);
	}


}
