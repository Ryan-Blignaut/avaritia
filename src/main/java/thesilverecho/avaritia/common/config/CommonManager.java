package thesilverecho.avaritia.common.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class CommonManager
{
	public static final ClientConfig CLIENT;
	public static final ForgeConfigSpec CLIENT_SPEC;

	static
	{
		final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
		CLIENT_SPEC = specPair.getRight();
		CLIENT = specPair.getLeft();
	}

	public static class ClientConfig
	{
		public final ForgeConfigSpec.IntValue horizontalRange;
		public final ForgeConfigSpec.IntValue verticalRange;
		public final ForgeConfigSpec.BooleanValue blacklist;
		public final ForgeConfigSpec.IntValue matterCapacity;
		public final ForgeConfigSpec.BooleanValue magnetHud;
		public final ForgeConfigSpec.BooleanValue logInfo;

		ClientConfig(ForgeConfigSpec.Builder builder)
		{
			builder.comment("test.").push("hud");
			horizontalRange = builder.defineInRange("Horizontal range of magnet", 6, 1, 20);
			verticalRange = builder.defineInRange("Vertical range of magnet", 2, 1, 20);
			blacklist = builder.define("if magnet is defaulted to black or white list", false);
			matterCapacity = builder.defineInRange("max capacity of the Matter Cluster", 6000, 1, 32767);
			magnetHud = builder.define("If magnet will display on hud if equipped", true);
			logInfo = builder.define("If debug info will be logged", false);
			builder.pop();
		}
	}
}
