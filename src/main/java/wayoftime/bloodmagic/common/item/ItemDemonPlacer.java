package wayoftime.bloodmagic.common.item;


import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.block.BloodMagicBlocks;
import wayoftime.bloodmagic.common.tile.TileAlchemyArray;
import wayoftime.bloodmagic.entity.mob.EntityDemon;

import java.util.List;

public class ItemDemonPlacer extends Item {
    public ItemDemonPlacer() {
        super(new Item.Properties().tab(BloodMagic.TAB).stacksTo(1));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(new TranslatableComponent("tooltip.bloodmagic.demonplacer").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        BlockPos newPos = context.getClickedPos().relative(context.getClickedFace());
        Level level = context.getLevel();
        Player player = context.getPlayer();


        if (level.isEmptyBlock(newPos)) {
            if (!level.isClientSide) {
//                String demonName = ItemDemonPlacer.getDemonString(stack);
                Entity demon = spawnCreature(level, "DEMON", newPos, stack);
//                Direction rotation = Direction.fromYRot(player.getYHeadRot());

                stack.hurtAndBreak(1, player, (entity) -> {
                    entity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                });

            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }
    public static Entity spawnCreature(Level level, String demonName,BlockPos pos, ItemStack itemStack)
    {
        Sheep sheep = EntityType.SHEEP.create(level);
        sheep.setPos(pos.getX() + 0.5, pos.getY(),pos.getZ() + 0.5);
        level.addFreshEntity(sheep);
        sheep.playAmbientSound();
        

//            if (entity != null && entity instanceof EntityLivingBase)
//            {
//                EntityLiving entityliving = (EntityLiving) entity;
//                entity.setLocationAndAngles(par2, par4, par6, MathHelper.wrapAngleTo180_float(level.rand.nextFloat() * 360.0F), 0.0F);
//                entityliving.rotationYawHead = entityliving.rotationYaw;
//                entityliving.renderYawOffset = entityliving.rotationYaw;
//                if (entityliving instanceof EntityDemon)
//                {
//                    Entity owner = SpellHelper.getPlayerForUsername(DemonPlacer.getOwnerName(itemStack));
//                    if(owner != null)
//                    {
//                        ((EntityDemon) entityliving).func_152115_b(owner.getPersistentID().toString());
//
//                        if (!DemonPlacer.getOwnerName(itemStack).equals(""))
//                        {
//                            ((EntityDemon) entityliving).setTamed(true);
//                        }
//                    }
//                }
//
//                EntitySheep.create()
//                entityliving.playLivingSound();
//            }

        return sheep;
    }


    public static void setOwnerName(ItemStack itemStack, String ownerName) {
        if (itemStack.getTag() == null) {
            itemStack.setTag(new CompoundTag());
        }
        itemStack.getTag().putString("ownerName", ownerName);
    }

    public static String getOwnerName(ItemStack itemStack) {
        if (itemStack.getTag() == null) {
            itemStack.setTag(new CompoundTag());
        }

        return itemStack.getTag().getString("ownerName");
    }

    public static void setDemonString(ItemStack itemStack, String demonName) {
        if (itemStack.getTag() == null) {
            itemStack.setTag(new CompoundTag());
        }

        itemStack.getTag().putString("demonName", demonName);
    }

    public static String getDemonString(ItemStack itemStack) {
        if (itemStack.getTag() == null) {
            itemStack.setTag(new CompoundTag());
        }

        return itemStack.getTag().getString("demonName");
    }
}
