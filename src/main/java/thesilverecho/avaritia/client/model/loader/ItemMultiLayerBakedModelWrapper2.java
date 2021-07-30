package thesilverecho.avaritia.client.model.loader;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraftforge.client.model.BakedItemModel;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.ItemMultiLayerBakedModel;
import thesilverecho.avaritia.client.shader.CosmicShader;

import java.util.Collection;
import java.util.List;

public class ItemMultiLayerBakedModelWrapper2 extends ItemMultiLayerBakedModel
{


	public ItemMultiLayerBakedModelWrapper2(boolean smoothLighting, boolean shadedInGui, boolean sideLit, TextureAtlasSprite particle, ItemOverrideList overrides, ImmutableMap<ItemCameraTransforms.TransformType, TransformationMatrix> cameraTransforms, ImmutableList<Pair<IBakedModel, RenderType>> layerModels)
	{
		super(smoothLighting, shadedInGui, sideLit, particle, overrides, cameraTransforms, layerModels);
	}

	public static CustomBuilder builder1(IModelConfiguration owner, TextureAtlasSprite particle, ItemOverrideList overrides,
	                                     ImmutableMap<ItemCameraTransforms.TransformType, TransformationMatrix> cameraTransforms)
	{
		return new CustomBuilder(owner, particle, overrides, cameraTransforms);
	}

	@Override
	public IBakedModel handlePerspective(ItemCameraTransforms.TransformType cameraTransformType, MatrixStack mat)
	{
		CosmicShader.inventory = cameraTransformType == ItemCameraTransforms.TransformType.GUI;
		return super.handlePerspective(cameraTransformType, mat);
	}

	public static class CustomBuilder
	{
		private final ImmutableList.Builder<Pair<IBakedModel, RenderType>> builder = ImmutableList.builder();
		private final List<BakedQuad> quads = Lists.newArrayList();
		private final ItemOverrideList overrides;
		private final ImmutableMap<ItemCameraTransforms.TransformType, TransformationMatrix> cameraTransforms;
		private final IModelConfiguration owner;
		private final TextureAtlasSprite particle;
		private RenderType lastRt = null;

		private CustomBuilder(IModelConfiguration owner, TextureAtlasSprite particle, ItemOverrideList overrides,
		                      ImmutableMap<ItemCameraTransforms.TransformType, TransformationMatrix> cameraTransforms)
		{
			this.owner = owner;
			this.particle = particle;
			this.overrides = overrides;
			this.cameraTransforms = cameraTransforms;
		}

		private void addLayer(ImmutableList.Builder<Pair<IBakedModel, RenderType>> builder, List<BakedQuad> quads, RenderType rt)
		{
			IBakedModel model = new BakedItemModel(ImmutableList.copyOf(quads), particle, ImmutableMap.of(), ItemOverrideList.EMPTY, true, owner.isSideLit());
			builder.add(Pair.of(model, rt));
		}

		private void flushQuads(RenderType rt)
		{
			if (rt != lastRt)
			{
				if (quads.size() > 0)
				{
					addLayer(builder, quads, lastRt);
					quads.clear();
				}
				lastRt = rt;
			}
		}

		public CustomBuilder addQuads(RenderType rt, Collection<BakedQuad> quadsToAdd)
		{
			flushQuads(rt);
			quads.addAll(quadsToAdd);
			return this;
		}

		public IBakedModel build()
		{
			if (quads.size() > 0)
				addLayer(builder, quads, lastRt);
			return new ItemMultiLayerBakedModelWrapper2(owner.useSmoothLighting(), owner.isShadedInGui(), owner.isSideLit(),
					particle, overrides, cameraTransforms, builder.build());
		}
	}


}
