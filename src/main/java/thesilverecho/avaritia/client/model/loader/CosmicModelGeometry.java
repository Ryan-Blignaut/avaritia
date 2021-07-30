package thesilverecho.avaritia.client.model.loader;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraftforge.client.ForgeRenderTypes;
import net.minecraftforge.client.model.*;
import net.minecraftforge.client.model.geometry.IModelGeometry;
import thesilverecho.avaritia.client.render.layer.ShaderWrappedRenderLayer;
import thesilverecho.avaritia.client.shader.CosmicShader;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

public class CosmicModelGeometry implements IModelGeometry<CosmicModelGeometry>
{
	private final ImmutableSet<Integer> overlayLayers;
	private ImmutableList<RenderMaterial> textures;

	public CosmicModelGeometry(@Nullable ImmutableList<RenderMaterial> textures, ImmutableSet<Integer> overlayLayers)
	{
		this.textures = textures;
		this.overlayLayers = overlayLayers;
	}

	private static ImmutableList<RenderMaterial> getTextures(IModelConfiguration model)
	{
		ImmutableList.Builder<RenderMaterial> builder = ImmutableList.builder();
		for (int i = 0; model.isTexturePresent("layer" + i); i++)
			builder.add(model.resolveTexture("layer" + i));
		return builder.build();
	}

	public static RenderType getLayerRenderType(boolean isOverlay)
	{

		return isOverlay ? new ShaderWrappedRenderLayer(ForgeRenderTypes.ITEM_UNSORTED_UNLIT_TRANSLUCENT.get(), CosmicShader.COSMIC_SHADER) : ForgeRenderTypes.ITEM_UNSORTED_TRANSLUCENT.get();
	}

	@Override
	public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<RenderMaterial, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation)
	{
		ImmutableMap<ItemCameraTransforms.TransformType, TransformationMatrix> transformMap = PerspectiveMapWrapper.getTransforms(new ModelTransformComposition(owner.getCombinedTransform(), modelTransform));
		TransformationMatrix transform = modelTransform.getRotation();
		TextureAtlasSprite particle = spriteGetter.apply(owner.isTexturePresent("particle") ? owner.resolveTexture("particle") : textures.get(0));

		ItemMultiLayerBakedModelWrapper2.CustomBuilder builder = ItemMultiLayerBakedModelWrapper2.builder1(owner, particle, overrides, transformMap);

//		ItemMultiLayerBakedModelWrapper.Builder builder = ItemMultiLayerBakedModelWrapper.builder(owner, particle, overrides, transformMap);
		for (int i = 0; i < textures.size(); i++)
		{
			TextureAtlasSprite tas = spriteGetter.apply(textures.get(i));
			RenderType rt = getLayerRenderType(overlayLayers.contains(i));
			builder.addQuads(rt, ItemLayerModel.getQuadsForSprite(i, tas, transform, false));
		}

		return builder.build();
	}

	@Override
	public Collection<RenderMaterial> getTextures(IModelConfiguration owner, Function<ResourceLocation, IUnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors)
	{
		textures = getTextures(owner);
		return textures;
	}

	public static class Loader implements IModelLoader<CosmicModelGeometry>
	{
		public static final CosmicModelGeometry.Loader INSTANCE = new CosmicModelGeometry.Loader();

		@Override
		public void onResourceManagerReload(IResourceManager resourceManager)
		{
			// nothing to do
		}

		@Override
		public CosmicModelGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents)
		{
			ImmutableSet.Builder<Integer> overlayLayers = ImmutableSet.builder();
			if (modelContents.has("overlay_layers"))
			{
				JsonArray arr = JSONUtils.getAsJsonArray(modelContents, "overlay_layers");
				for (int i = 0; i < arr.size(); i++)
					overlayLayers.add(arr.get(i).getAsInt());
			}
			return new CosmicModelGeometry(null, overlayLayers.build());
		}
	}

}
