package thesilverecho.avaritia.common.intergration.curios;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.init.ModItems;
import thesilverecho.avaritia.common.util.FlightHelper;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CuriosCompat
{
	private static final ResourceLocation MAGNET_ICON = new ResourceLocation(Avaritia.MOD_ID, "curios/magnet_slot");

	public static void sendImc()
	{
		InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("angle_ring").build());
		InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("magnet").size(1).icon(MAGNET_ICON).build());

	}

	public static ICapabilityProvider initCapabilities()
	{
		ICurio iCurio = new ICurio()
		{
			@Override
			public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
			{

			}

			@Override
			public boolean canRightClickEquip()
			{
				return true;
			}

			@Override
			public void onEquip(String identifier, int index, LivingEntity livingEntity)
			{
				if (livingEntity instanceof PlayerEntity)
					FlightHelper.startFlying((PlayerEntity) livingEntity);
			}

			@Override
			public void onUnequip(String identifier, int index, LivingEntity livingEntity)
			{
				if (livingEntity instanceof PlayerEntity)
					FlightHelper.stopFlying((PlayerEntity) livingEntity);
			}


			@Override
			public void curioTick(String identifier, int index, LivingEntity livingEntity)
			{
				if (livingEntity instanceof PlayerEntity)
				{
					final PlayerEntity player = ((PlayerEntity) livingEntity);
					if (!player.abilities.mayfly)
						FlightHelper.startFlying(player);
				}
			}

			@Override
			public boolean canEquip(String identifier, LivingEntity entityLivingBase)
			{
				return !CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.ANGLE_RING.get(), entityLivingBase).isPresent();
			}

			@Override
			public void playRightClickEquipSound(LivingEntity entityLivingBase)
			{
				entityLivingBase.level.playSound(null, entityLivingBase.blockPosition(),
						SoundEvents.NOTE_BLOCK_HARP, SoundCategory.NEUTRAL,
						1.0F, 1.0F);
			}
		};
		return new ICapabilityProvider()
		{
			private final LazyOptional<ICurio> curioOpt = LazyOptional.of(() -> iCurio);

			@Nonnull
			@Override
			public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side)
			{
				return CuriosCapability.ITEM.orEmpty(capability, curioOpt);
			}
		};
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onStitch(TextureStitchEvent.Pre event)
	{
		event.addSprite(MAGNET_ICON);
	}


}
