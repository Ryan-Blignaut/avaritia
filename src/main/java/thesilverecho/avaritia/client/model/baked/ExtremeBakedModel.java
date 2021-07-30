package thesilverecho.avaritia.client.model.baked;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import thesilverecho.avaritia.client.render.item.ExtremeItemRender;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class ExtremeBakedModel implements IBakedModel
{
	private final IBakedModel originalModel;

	public ExtremeBakedModel(IBakedModel originalModel)
	{
		this.originalModel = originalModel;
	}

	@Override
	public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand)
	{
		return originalModel.getQuads(state, side, rand);
	}

	@Override
	public boolean useAmbientOcclusion()
	{
		return originalModel.useAmbientOcclusion();
	}

	@Override
	public boolean isGui3d()
	{
		return originalModel.isGui3d();
	}

	@Override
	public boolean usesBlockLight()
	{
		return originalModel.usesBlockLight();
	}

	@Override
	public boolean isCustomRenderer()
	{
		return true;
	}

	@Override
	public TextureAtlasSprite getParticleIcon()
	{
		return originalModel.getParticleIcon();
	}

	@Override
	public ItemCameraTransforms getTransforms()
	{
		//noinspection deprecation
		return originalModel.getTransforms();
	}

	@Override
	public ItemOverrideList getOverrides()
	{
		return originalModel.getOverrides();
	}

	/*	@Override
	public ItemOverrideList getOverrides()
	{
//		return originalModel.getOverrides();
		final ExtremeBakedModel model1 = this;
		return new ItemOverrideList()
		{
			@Override
			public IBakedModel getOverrideModel(IBakedModel model, ItemStack stack, ClientWorld world, LivingEntity entity)
			{
				if (Screen.hasShiftDown())
				{
					ItemStack containedStack = new ItemStack(Items.ICE);//VoidTearItem.getTearContents(stack, true);
					if (!containedStack.isEmpty())
					{
						IBakedModel bakedModel = Minecraft.getInstance().getItemRenderer().getItemModelWithOverrides(containedStack, world, entity);
						if (!bakedModel.isBuiltInRenderer())
						{
							return bakedModel;
						}
					}
				}

				return originalModel.getOverrides().getOverrideModel(model, stack, world, entity);
			}
		};
	}*/

	@Override
	public IBakedModel handlePerspective(@Nonnull ItemCameraTransforms.TransformType cameraTransformType, MatrixStack stack)
	{
		final IBakedModel model = IBakedModel.super.handlePerspective(cameraTransformType, stack);
		ExtremeItemRender.setTransformType(cameraTransformType);
		return model;
	}
}
