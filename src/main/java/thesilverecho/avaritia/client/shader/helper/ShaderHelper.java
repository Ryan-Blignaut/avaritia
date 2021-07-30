package thesilverecho.avaritia.client.shader.helper;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.IShaderManager;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.client.shader.ShaderLoader;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;
import org.lwjgl.system.MemoryUtil;
import thesilverecho.avaritia.common.Avaritia;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

public class ShaderHelper
{
	// Scratch buffer to use for uniforms
	public static final FloatBuffer FLOAT_BUF = MemoryUtil.memAllocFloat(1);
	private static final Map<AvaritiaShader, ShaderProgram> PROGRAMS = new EnumMap<>(AvaritiaShader.class);
	private static boolean hasIncompatibleMods = false;
	private static boolean checkedIncompatibility = false;

	@SuppressWarnings("deprecation")
	public static void initShaders()
	{
		// Can be null when running datagenerators due to the unfortunate time we call this
		if (Minecraft.getInstance() != null
				&& Minecraft.getInstance().getResourceManager() instanceof IReloadableResourceManager)
		{
			((IReloadableResourceManager) Minecraft.getInstance().getResourceManager()).registerReloadListener(
					(IResourceManagerReloadListener) manager ->
					{
						PROGRAMS.values().forEach(ShaderLinkHelper::releaseProgram);
						PROGRAMS.clear();
						loadShaders(manager);
					});
		}
	}

	private static void loadShaders(IResourceManager manager)
	{
		if (!useShaders())
		{
			return;
		}

		for (AvaritiaShader shader : AvaritiaShader.values())
		{
			createProgram(manager, shader);
		}
	}

	public static void useShader(AvaritiaShader shader, ShaderCallback callback)
	{
		if (!useShaders())
		{
			return;
		}

		ShaderProgram prog = PROGRAMS.get(shader);
		if (prog == null)
		{
			return;
		}

		int program = prog.getId();
		ShaderLinkHelper.glUseProgram(program);

		int time = GlStateManager._glGetUniformLocation(program, "time");
//		GlStateManager.uniform1i(time, ClientTickHandler.ticksInGame);

		if (callback != null)
		{
			callback.call(program);
		}
	}

	public static void useShader(AvaritiaShader shader)
	{
		useShader(shader, null);
	}

	public static void releaseShader()
	{
		ShaderLinkHelper.glUseProgram(0);
	}

	public static boolean useShaders()
	{
		return /*ConfigHandler.CLIENT.useShaders.get() &&*/ checkIncompatibleMods();
	}

	private static boolean checkIncompatibleMods()
	{
		if (!checkedIncompatibility)
		{
			hasIncompatibleMods = ModList.get().isLoaded("optifine");
			checkedIncompatibility = true;
		}

		return !hasIncompatibleMods;
	}

	private static void createProgram(IResourceManager manager, AvaritiaShader shader)
	{
		try
		{
			ShaderLoader vert = createShader(manager, shader.vertexShaderPath, ShaderLoader.ShaderType.VERTEX);
			ShaderLoader frag = createShader(manager, shader.fragmentShaderPath, ShaderLoader.ShaderType.FRAGMENT);
			int progId = ShaderLinkHelper.createProgram();
			ShaderProgram prog = new ShaderProgram(progId, vert, frag);
			ShaderLinkHelper.linkProgram(prog);
			PROGRAMS.put(shader, prog);
		} catch (IOException ex)
		{
			ex.printStackTrace();
//			Botania.LOGGER.error("Failed to load program {}", shader.name(), ex);
		}
	}

	private static ShaderLoader createShader(IResourceManager manager, String filename, ShaderLoader.ShaderType shaderType) throws IOException
	{
		ResourceLocation loc = new ResourceLocation(Avaritia.MOD_ID, filename);
		try (InputStream is = new BufferedInputStream(manager.getResource(loc).getInputStream()))
		{
			return ShaderLoader.compileShader(shaderType, loc.toString(), is, shaderType.name().toLowerCase(Locale.ROOT));
		}
	}

	public enum AvaritiaShader
	{
//		PYLON_GLOW(LibResources.SHADER_PASSTHROUGH_VERT, LibResources.SHADER_PYLON_GLOW_FRAG),
//		ENCHANTER_RUNE(LibResources.SHADER_PASSTHROUGH_VERT, LibResources.SHADER_ENCHANTER_RUNE_FRAG),
//		MANA_POOL(LibResources.SHADER_PASSTHROUGH_VERT, LibResources.SHADER_MANA_POOL_FRAG),
//		DOPPLEGANGER(LibResources.SHADER_DOPLLEGANGER_VERT, LibResources.SHADER_DOPLLEGANGER_FRAG),
//		HALO(LibResources.SHADER_PASSTHROUGH_VERT, LibResources.SHADER_HALO_FRAG),
//		DOPPLEGANGER_BAR(LibResources.SHADER_PASSTHROUGH_VERT, LibResources.SHADER_DOPLLEGANGER_BAR_FRAG),
//		TERRA_PLATE(LibResources.SHADER_PASSTHROUGH_VERT, LibResources.SHADER_TERRA_PLATE_RUNE_FRAG),
//		FILM_GRAIN(LibResources.SHADER_PASSTHROUGH_VERT, LibResources.SHADER_FILM_GRAIN_FRAG),
//		GOLD(LibResources.SHADER_PASSTHROUGH_VERT, LibResources.SHADER_GOLD_FRAG),
//		ALPHA(LibResources.SHADER_PASSTHROUGH_VERT, LibResources.SHADER_ALPHA_FRAG);

		COSMIC_SHADER("shaders/cosmic.vert", "shaders/cosmic.frag");

		public final String vertexShaderPath;
		public final String fragmentShaderPath;

		AvaritiaShader(String vertexShaderPath, String fragmentShaderPath)
		{
			this.vertexShaderPath = vertexShaderPath;
			this.fragmentShaderPath = fragmentShaderPath;
		}
	}

	private static class ShaderProgram implements IShaderManager
	{
		private final int program;
		private final ShaderLoader vert;
		private final ShaderLoader frag;

		private ShaderProgram(int program, ShaderLoader vert, ShaderLoader frag)
		{
			this.program = program;
			this.vert = vert;
			this.frag = frag;
		}

		@Override
		public int getId()
		{
			return program;
		}

		@Override
		public void markDirty()
		{

		}

		@Override
		public ShaderLoader getVertexProgram()
		{
			return vert;
		}

		@Override
		public ShaderLoader getFragmentProgram()
		{
			return frag;
		}
	}


}
