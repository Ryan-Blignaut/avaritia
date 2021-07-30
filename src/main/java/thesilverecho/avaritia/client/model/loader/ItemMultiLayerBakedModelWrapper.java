package thesilverecho.avaritia.client.model.loader;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraftforge.client.model.BakedItemModel;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;
import thesilverecho.avaritia.client.shader.CosmicShader;

import java.util.Collection;
import java.util.List;
import java.util.Random;

public class ItemMultiLayerBakedModelWrapper implements IDynamicBakedModel
{
	private final boolean smoothLighting;
	private final boolean shadedInGui;
	private final boolean sideLit;
	private final TextureAtlasSprite particle;
	private final ItemOverrideList overrides;
	private final ImmutableList<Pair<IBakedModel, RenderType>> layerModels;
	private final ImmutableMap<ItemCameraTransforms.TransformType, TransformationMatrix> cameraTransforms;

	public ItemMultiLayerBakedModelWrapper(boolean smoothLighting, boolean shadedInGui, boolean sideLit,
	                                       TextureAtlasSprite particle, ItemOverrideList overrides,
	                                       ImmutableMap<ItemCameraTransforms.TransformType, TransformationMatrix> cameraTransforms,
	                                       ImmutableList<Pair<IBakedModel, RenderType>> layerModels)
	{
		this.smoothLighting = smoothLighting;
		this.shadedInGui = shadedInGui;
		this.sideLit = sideLit;
		this.particle = particle;
		this.overrides = overrides;
		this.layerModels = layerModels;
		this.cameraTransforms = cameraTransforms;
	}

	public static Builder builder(IModelConfiguration owner, TextureAtlasSprite particle, ItemOverrideList overrides,
	                              ImmutableMap<ItemCameraTransforms.TransformType, TransformationMatrix> cameraTransforms)
	{
		return new Builder(owner, particle, overrides, cameraTransforms);
	}

	@Override
	public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand, IModelData modelData)
	{
		List<BakedQuad> quads = Lists.newArrayList();
		layerModels.forEach(lm -> quads.addAll(lm.getFirst().getQuads(state, side, rand, modelData)));
		return quads;
	}

	@Override
	public boolean useAmbientOcclusion()
	{
		return smoothLighting;
	}

	@Override
	public boolean isGui3d()
	{
		return shadedInGui;
	}

	@Override
	public boolean usesBlockLight()
	{
		return sideLit;
	}

	@Override
	public boolean isCustomRenderer()
	{
		return false;
	}

	@Override
	public TextureAtlasSprite getParticleIcon()
	{
		return particle;
	}

	@Override
	public ItemOverrideList getOverrides()
	{
		return overrides;
	}

	@Override
	public boolean doesHandlePerspectives()
	{
		return true;
	}

	@Override
	public IBakedModel handlePerspective(ItemCameraTransforms.TransformType cameraTransformType, MatrixStack mat)
	{
		CosmicShader.inventory = cameraTransformType == ItemCameraTransforms.TransformType.GUI;
		return PerspectiveMapWrapper.handlePerspective(this, cameraTransforms, cameraTransformType, mat);
	}

	//@Override
	public boolean isLayered()
	{
		return true;
	}

	//@Override
	public List<Pair<IBakedModel, RenderType>> getLayerModels(ItemStack itemStack, boolean fabulous)
	{
		return layerModels;
	}

	public static class Builder
	{
		private final ImmutableList.Builder<Pair<IBakedModel, RenderType>> builder = ImmutableList.builder();
		private final List<BakedQuad> quads = Lists.newArrayList();
		private final ItemOverrideList overrides;
		private final ImmutableMap<ItemCameraTransforms.TransformType, TransformationMatrix> cameraTransforms;
		private final IModelConfiguration owner;
		private final TextureAtlasSprite particle;
		private RenderType lastRt = null;

		private Builder(IModelConfiguration owner, TextureAtlasSprite particle, ItemOverrideList overrides,
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

		public Builder addQuads(RenderType rt, Collection<BakedQuad> quadsToAdd)
		{
			flushQuads(rt);
			quads.addAll(quadsToAdd);
			return this;
		}

		public IBakedModel build()
		{
			if (quads.size() > 0)
			{
				addLayer(builder, quads, lastRt);
			}
			return new ItemMultiLayerBakedModelWrapper(owner.useSmoothLighting(), owner.isShadedInGui(), owner.isSideLit(),
					particle, overrides, cameraTransforms, builder.build());
		}
	}


}
