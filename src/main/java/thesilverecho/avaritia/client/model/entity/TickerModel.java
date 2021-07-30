package thesilverecho.avaritia.client.model.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import thesilverecho.avaritia.common.entity.Ticker;

public class TickerModel<T extends Ticker> extends EntityModel<T>
{
	public ModelRenderer modelRenderer;

	public TickerModel()
	{
		modelRenderer = new ModelRenderer(this, 0, 0);
	}

	@Override
	public void setupAnim(Ticker entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{

	}

	@Override
	public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)
	{
		modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
	}
}
