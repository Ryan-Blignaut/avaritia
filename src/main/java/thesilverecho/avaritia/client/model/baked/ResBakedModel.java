package thesilverecho.avaritia.client.model.baked;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;

import java.util.List;
import java.util.Random;

public class ResBakedModel implements IBakedModel
{
	private final IBakedModel originalModel;

	public ResBakedModel(IBakedModel originalModel)
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
		return originalModel.isCustomRenderer();
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
		return new ItemOverrideList()
		{
			@Override
			public IBakedModel resolve(IBakedModel model, ItemStack stack, ClientWorld world, LivingEntity entity)
			{
				if (Screen.hasShiftDown())
				{
					ItemStack containedStack = new ItemStack(Items.ICE);//VoidTearItem.getTearContents(stack, true);
					if (!containedStack.isEmpty())
					{
						IBakedModel bakedModel = Minecraft.getInstance().getItemRenderer().getModel(containedStack, world, entity);
						if (!bakedModel.isCustomRenderer())
						{
							return bakedModel;
						}
					}
				}

				return originalModel.getOverrides().resolve(model, stack, world, entity);
			}
		};
	}

	@Override
	public IBakedModel handlePerspective(ItemCameraTransforms.TransformType cameraTransformType, MatrixStack mat)
	{
		return originalModel.handlePerspective(cameraTransformType, mat);
	}

}
