package thesilverecho.avaritia.common.item.magnet;

import com.google.common.util.concurrent.AtomicDouble;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import thesilverecho.avaritia.client.old.IItemStackOverlayRender;
import thesilverecho.avaritia.common.block.xpstorage.CapabilityXp;
import thesilverecho.avaritia.common.config.CommonManager;
import thesilverecho.avaritia.common.init.ModPackets;
import thesilverecho.avaritia.common.item.BaseToggleItem;
import thesilverecho.avaritia.common.item.ModGroup;
import thesilverecho.avaritia.common.packet.SpawnClientParticles;
import thesilverecho.avaritia.common.util.EntityUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class MagnetItem extends BaseToggleItem implements IItemStackOverlayRender
{
	private static final String TAG_ITEM_TILE_BOUND_POS = "bound_item_tile";
	private static final String TAG_XP_TILE_BOUND_POS = "bound_xp_tile";
	//	what i am trying to do,(context) I will have 3 filter slots,filters are items that will contain items(what we want to filter)
//	and also an function "filter" eg mach tag,nbt,damage,(white/black potentially) list, so my thought is that i will have a list/ map in this
//	class where the item and relevant function will be stored and cached(only stored when the filter changes)
	public ArrayList<ItemStack> stacksList;


	public MagnetItem()
	{
		super(new Properties().tab(ModGroup.AVARITIA).stacksTo(1));
	}

	public static CompoundNBT getUpgrades(ItemStack stack)
	{
		if (!stack.hasTag() || !Objects.requireNonNull(stack.getTag()).contains(MagnetFilterScreen.MAIN_FILTER_TAG))
		{
			final CompoundNBT compoundNBT = new CompoundNBT();
			compoundNBT.put(MagnetFilterScreen.MAIN_FILTER_TAG, new CompoundNBT());
			return compoundNBT;
		}
		return stack.getTag().getCompound(MagnetFilterScreen.MAIN_FILTER_TAG);
	}

	@Override
	public void appendHoverText(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn)
	{
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		getItemPosFromStack(stack).ifPresent(pos -> tooltip.add(new TranslationTextComponent("tooltip.avaritia.itemLoc", pos.toString())));
		getXpTilePosFromStack(stack).ifPresent(pos -> tooltip.add(new TranslationTextComponent("tooltip.avaritia.xpLoc", pos.toString())));
	}

	@Override
	@ParametersAreNonnullByDefault
	public void inventoryTick(ItemStack magnetItemStack, World world, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if (world.isClientSide || !isActive(magnetItemStack) || !(entityIn instanceof PlayerEntity) || entityIn.isShiftKeyDown())
			return;
		if (stacksList == null)
			loadAndCache(magnetItemStack);


		PlayerEntity player = (PlayerEntity) entityIn;
//		if (!magnetItemStack.getOrCreateTag().contains(MagnetFilterScreen.MAIN_FILTER_TAG))
//			setUpTags(magnetItemStack.getOrCreateChildTag(MagnetFilterScreen.MAIN_FILTER_TAG));
		CompoundNBT tag = magnetItemStack.getOrCreateTagElement(MagnetFilterScreen.MAIN_FILTER_TAG);

		BiPredicate<ItemStack, ItemStack> stackPredicate = ItemStack::sameItem;
		BiPredicate<ItemStack, ItemStack> matchMeta = ItemStack::isSame;
		BiPredicate<ItemStack, ItemStack> matchTag = (stackA, stackB) -> tag.getBoolean("nbt_filter") && ItemStack.tagMatches(stackA, stackB);
//		BiPredicate<ItemStack, ItemStack> matchTag = (stackA, stackB) -> !tag.getBoolean("nbt_filter") || ItemStack.areItemStackTagsEqual(stackA, stackB);

		/*Predicate<ItemEntity> filter = itemEntity ->
		{
			AtomicBoolean flag = new AtomicBoolean(false);
			for (ItemStack stack1 : getItemsInFilter(magnetItemStack))
				if (stackPredicate.or(matchTag).test(stack1, itemEntity.getItem()))
					flag.set(true);
			return tag.getBoolean(MagnetFilterScreen.TAG_BLACKLIST_KEY) != flag.get();
		};*/
		Predicate<ItemEntity> filter = itemEntity ->
		{
			AtomicBoolean flag = new AtomicBoolean(true);
			for (ItemStack stack1 : stacksList)
				if (stackPredicate.or(matchTag).test(stack1, itemEntity.getItem()))
					flag.set(false);

			return flag.get();
		};

		EntityUtil.getEntitiesInRangeOfPlayer(ItemEntity.class, player, world, CommonManager.CLIENT.horizontalRange.get(), CommonManager.CLIENT.verticalRange.get())
				.stream()
				.filter(filter)
				.filter(Entity::isAlive)
				.filter(itemEntity -> !itemEntity.getTags().contains("cant_move"))
				.forEach(itemEntity -> pickupItem(itemEntity, player, magnetItemStack, world, tag));


		if (tag.getBoolean(MagnetFilterScreen.TAG_XP_KEY))
			EntityUtil.getEntitiesInRangeOfPlayer(ExperienceOrbEntity.class, player, world, CommonManager.CLIENT.horizontalRange.get(), CommonManager.CLIENT.verticalRange.get()).forEach(exp ->
			{
//				if (tag.getBoolean(MagnetFilterScreen.XP_ENDER_MODE_KEY))
//					putXpInTile(magnetItemStack, world, exp, tag);

				player.takeXpDelay = 0;
				exp.throwTime = 0;
				exp.playerTouch(player);
			});

	}

	public void loadAndCache(ItemStack magnetStack)
	{
		if (stacksList == null)
			stacksList = new ArrayList<>();

		stacksList.clear();
		magnetStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(magnetSlotHandler ->
		{
			for (int i = 0; i < magnetSlotHandler.getSlots(); i++)
			{
				magnetSlotHandler.getStackInSlot(i).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(filterSlotHandler ->
				{
					for (int j = 0; j < filterSlotHandler.getSlots(); j++)
						if (!filterSlotHandler.getStackInSlot(j).isEmpty())
							stacksList.add(filterSlotHandler.getStackInSlot(j));
				});
			}

		});
	}

	@Override
	@Nonnull
	public ActionResultType useOn(ItemUseContext context)
	{
		final TileEntity tileEntity = context.getLevel().getBlockEntity(context.getClickedPos());
		final Direction face = context.getClickedFace();
		final PlayerEntity player = context.getPlayer();
		if (tileEntity != null && player != null)
		{
			CompoundNBT tag = context.getItemInHand().getOrCreateTag();
			INBT nbt = BlockPos.CODEC.encodeStart(NBTDynamicOps.INSTANCE, context.getClickedPos()).get().orThrow();

			if (tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, face).isPresent())
			{
				tag.put(TAG_ITEM_TILE_BOUND_POS, nbt);
				player.playSound(SoundEvents.STONE_BUTTON_CLICK_ON, 0.8f, 1);
				return ActionResultType.CONSUME;

			} else if (tileEntity.getCapability(CapabilityXp.XP, face).isPresent())
			{
				tag.put(TAG_XP_TILE_BOUND_POS, nbt);
				player.playSound(SoundEvents.STONE_BUTTON_CLICK_ON, 0.8f, 1);
				return ActionResultType.CONSUME;
			}
		}
		return super.useOn(context);
	}

	@Nonnull
	@Override
	public ActionResult<ItemStack> use(@Nonnull World worldIn, @Nonnull PlayerEntity playerIn, @Nonnull Hand handIn)
	{
		ItemStack stack = playerIn.getItemInHand(handIn);
		if (worldIn.isClientSide)
			return ActionResult.fail(stack);

		if (playerIn.isShiftKeyDown())
		{
			NetworkHooks.openGui((ServerPlayerEntity) playerIn, new INamedContainerProvider()
			{
				@Override
				@Nonnull
				public ITextComponent getDisplayName()
				{
					return new TranslationTextComponent("magnet.filter.screen");
				}

				@Override
				@ParametersAreNonnullByDefault
				public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity)
				{
					return new MagnetContainer(i, playerInventory);
				}
			});

			return ActionResult.success(stack);
		} else
			return super.use(worldIn, playerIn, handIn);

	}

	@Nullable
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt)
	{
		return new MagnetCapabilityProvider(3);
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		AtomicDouble val = new AtomicDouble();
		stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(iEnergyStorage -> val.set((iEnergyStorage.getEnergyStored() + 1d) / (iEnergyStorage.getMaxEnergyStored() + 1d)));
		return 1 - val.get();
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		AtomicBoolean atomicBoolean = new AtomicBoolean();
		stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(iEnergyStorage -> atomicBoolean.set(iEnergyStorage.getEnergyStored() < iEnergyStorage.getMaxEnergyStored()));
		return atomicBoolean.get();
	}

	@Override
	public int getRGBDurabilityForDisplay(ItemStack stack)
	{
		return Color.ORANGE.getRGB();
	}

	private void pickupItem(ItemEntity itemEntity, PlayerEntity player, ItemStack magnetItemStack, World world, CompoundNBT tag)
	{
		if (tag.getBoolean(MagnetFilterScreen.ITEM_ENDER_MODE_KEY))
			putItemInTile(magnetItemStack, player, world, itemEntity, tag);

		if (itemEntity.tickCount <= 1)
			itemEntity.setPickUpDelay(1);
		itemEntity.playerTouch(player);

		if (tag.getBoolean(MagnetFilterScreen.TAG_STICKY_KEY))
			itemEntity.setPos(player.getX(), player.getY(), player.getZ());

	}

	private void putItemInTile(ItemStack magnetItemStack, PlayerEntity player, World world, ItemEntity itemEntity, CompoundNBT tag)
	{
		getItemPosFromStack(magnetItemStack).ifPresent(pos ->
		{
			TileEntity tileEntity = world.getBlockEntity(pos);
			if (tileEntity != null)
			{
				tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler ->
				{
					for (int i = 0; i < iItemHandler.getSlots(); i++)
						itemEntity.setItem(iItemHandler.insertItem(i, itemEntity.getItem(), false));
					if (tag.getBoolean(MagnetFilterScreen.VISUALS_KEY))
						ModPackets.sendToCustom(new SpawnClientParticles(3, itemEntity.blockPosition(), new Color(200, 0, 0).getRGB()), (ServerPlayerEntity) player);
				});

			}
		});
	}

	/*private void putXpInTile(ItemStack magnetItemStack, World world, ExperienceOrbEntity experienceOrbEntity, CompoundNBT tag)
	{
		getXpTilePosFromStack(magnetItemStack).map(world::getBlockEntity).map(tileEntity -> tileEntity.getCapability(CapabilityXp.XP)).ifPresent(lazyOptional ->
				lazyOptional.ifPresent(xpStorage ->
				{
					xpStorage.addXp(experienceOrbEntity.value, false);
					experienceOrbEntity.remove();
					if (tag.getBoolean(MagnetFilterScreen.VISUALS_KEY))
						world.addParticle(ParticleTypes.COMPOSTER, experienceOrbEntity.getX(), experienceOrbEntity.getY(), experienceOrbEntity.getZ(), 0, 0D, 0);
				}));
	}*/

	private List<ItemStack> getItemsInFilter(ItemStack stack)
	{
		ArrayList<ItemStack> stacks = new ArrayList<>();
		stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h ->
		{
			for (int i = 0; i < h.getSlots(); i++)
				if (!h.getStackInSlot(i).isEmpty())
					stacks.add(h.getStackInSlot(i));
		});
		return stacks;
	}

	private Optional<BlockPos> getItemPosFromStack(ItemStack stack)
	{
		if (stack.getOrCreateTag().contains(TAG_ITEM_TILE_BOUND_POS))
			return BlockPos.CODEC.parse(NBTDynamicOps.INSTANCE, stack.getOrCreateTag().get(TAG_ITEM_TILE_BOUND_POS)).result();
		return Optional.empty();
	}

	private Optional<BlockPos> getXpTilePosFromStack(ItemStack stack)
	{
		if (stack.getOrCreateTag().contains(TAG_XP_TILE_BOUND_POS))
			return BlockPos.CODEC.parse(NBTDynamicOps.INSTANCE, stack.getOrCreateTag().get(TAG_XP_TILE_BOUND_POS)).result();
		return Optional.empty();
	}

	@Override
	public void renderOverlay(MatrixStack matrixStack, ItemStack itemStack)
	{
		if (!CommonManager.CLIENT.magnetHud.get())
			return;
		FontRenderer fontRenderer = Minecraft.getInstance().font;
		AtomicInteger yCount = new AtomicInteger(0);


		//		AbstractGui.blit();
//		itemStack.getOrCreateTag().getCompound(MagnetFilterScreen.MAIN_FILTER_TAG).keySet().forEach(s ->
//				fontRenderer.drawStringWithShadow(matrixStack, s, 0, yCount.getAndAdd(fontRenderer.FONT_HEIGHT + 3), -1));
	}

}