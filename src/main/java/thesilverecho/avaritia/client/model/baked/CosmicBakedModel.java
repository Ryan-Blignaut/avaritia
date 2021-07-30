/*
package thesilverecho.avaritia.client.model.baked;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class CosmicBakedModel implements IBakedModel
{
	private final IBakedModel originalModel;

	public CosmicBakedModel(IBakedModel originalModel)
	{
		this.originalModel = originalModel;
	}

	@Override
	public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand)
	{
		return originalModel.getQuads(state, side, rand);
	}

	@Override
	public boolean isAmbientOcclusion()
	{
		return originalModel.isAmbientOcclusion();
	}

	@Override
	public boolean isGui3d()
	{
		return originalModel.isGui3d();
	}

	@Override
	public boolean isSideLit()
	{
		return originalModel.isSideLit();
	}

	@Override
	public boolean isBuiltInRenderer()
	{
		return true;
	}

	@Override
	public TextureAtlasSprite getParticleTexture()
	{
		return originalModel.getParticleTexture();
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms()
	{
		return originalModel.getItemCameraTransforms();
	}

	@Override
	public ItemOverrideList getOverrides()
	{
		return originalModel.getOverrides();
	}

	@Override
	public IBakedModel handlePerspective(@Nonnull ItemCameraTransforms.TransformType cameraTransformType, MatrixStack stack)
	{
		final IBakedModel model = IBakedModel.super.handlePerspective(cameraTransformType, stack);
		inventory = cameraTransformType == ItemCameraTransforms.TransformType.GUI;
		return model;
	}

}
*/
