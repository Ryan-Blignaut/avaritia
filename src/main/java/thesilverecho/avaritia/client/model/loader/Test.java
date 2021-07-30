package thesilverecho.avaritia.client.model.loader;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

public class Test implements IUnbakedModel
{
	@Override
	public Collection<ResourceLocation> getDependencies()
	{
		return null;
	}

	@Override
	public Collection<RenderMaterial> getMaterials(Function<ResourceLocation, IUnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors)
	{
		return null;
	}

	@Nullable
	@Override
	public IBakedModel bake(ModelBakery modelBakeryIn, Function<RenderMaterial, TextureAtlasSprite> spriteGetterIn, IModelTransform transformIn, ResourceLocation locationIn)
	{
		return null;
	}
}
