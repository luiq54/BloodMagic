package wayoftime.bloodmagic.entity.mob;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import wayoftime.bloodmagic.util.ChatUtil;

public class EntityDemon extends TamableAnimal implements IDemon {
    private boolean isAggro;
    private String demonID;

    protected boolean dropCrystal = true;

    public EntityDemon(EntityType<? extends TamableAnimal> type, Level worldIn, String demonID) {
        super(type, worldIn);
        this.demonID = demonID;
    }

    @Override
    public boolean getDoesDropCrystal() {
        return dropCrystal;
    }

    @Override
    public void setDropCrystal(boolean crystal) {
        this.dropCrystal = crystal;
    }

    @Override
    public void setSummonedConditions() {
        this.setAggro(true);
    }

    @Override
    public boolean isAggro() {
        return this.isAggro;
    }

    @Override
    public void setAggro(boolean aggro) {
        this.isAggro = aggro;
    }


    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    public boolean save(CompoundTag tag) {
        tag.putBoolean("dropCrystal", this.getDoesDropCrystal());
        return super.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        dropCrystal = tag.getBoolean("dropCrystal");
    }


    @Override
    public void tick() {
        super.tick();
        if (!this.isAggro() && getLevel().getGameTime() % 100 == 0) {
            this.heal(1);
        }
    }


    public void sendSittingMessageToPlayer(Player owner, boolean isSitting) {
        if (owner != null && !owner.level.isClientSide) {
            if (isSitting) {
                ChatUtil.sendNoSpam(owner, new TranslatableComponent("message.demon.willstay"));
            } else {
                ChatUtil.sendNoSpam(owner, new TranslatableComponent("message.demon.shallfollow"));
            }

        }
    }

    public String getDemonID() {
        return this.demonID;
    }

    protected void setDemonID(String id) {
        this.demonID = id;
    }


    //    @Override
//    protected void dropFewItems(boolean par1, int par2)
//    {
//        if(this.getDoesDropCrystal())
//        {
//            ItemStack drop = new ItemStack(BASIC);
//
//            DemonPlacer.setDemonString(drop, this.getDemonID());
//
//            if ((this.getOwner() instanceof EntityPlayer))
//            {
//                DemonPlacer.setOwnerName(drop, SpellHelper.getUsername((EntityPlayer) this.getOwner()));
//            }
//
//            if (this.hasCustomNameTag())
//            {
//                drop.setStackDisplayName(this.getCustomNameTag());
//            }
//
//            this.entityDropItem(drop, 0.0f);
//        }
//    }


}
