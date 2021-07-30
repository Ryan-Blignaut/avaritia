package thesilverecho.avaritia.common.init;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.recipe.CompactorRecipe;
import thesilverecho.avaritia.common.recipe.ExtremeRecipe;

import java.util.function.Supplier;

public class ModRecipes
{
	public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Avaritia.MOD_ID);

	private static final ResourceLocation EXTREME_RES = new ResourceLocation(Avaritia.MOD_ID, "extreme");
	public static final IRecipeType<ExtremeRecipe> EXTREME_TYPE = IRecipeType.register(EXTREME_RES.toString());
	public static final RegistryObject<IRecipeSerializer<?>> EXTREME = RECIPE_SERIALIZERS.register(EXTREME_RES.getPath(), ExtremeRecipe.Serializer::new);

	private static final ResourceLocation COMPACTOR_RES = new ResourceLocation(Avaritia.MOD_ID, "compactor");
	public static final IRecipeType<CompactorRecipe> COMPACTOR_TYPE = IRecipeType.register(COMPACTOR_RES.toString());
	public static final RegistryObject<IRecipeSerializer<?>> COMPACTOR_SERIALIZER = register(COMPACTOR_RES, CompactorRecipe.Serializer::new);

//	public static final RegistryObject<IRecipeSerializer<?>> MAGNET_SERIALIZER = register(new ResourceLocation(Avaritia.MOD_ID, "test"), (Supplier<IRecipeSerializer<?>>) MagnetUpgradingRecipe.SERIALIZER);

	public static RegistryObject<IRecipeSerializer<?>> register(ResourceLocation res, Supplier<IRecipeSerializer<?>> factory)
	{
		return RECIPE_SERIALIZERS.register(res.getPath(), factory);
	}

	public static void init()
	{
		RECIPE_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

	static void register()
	{
	}


}
