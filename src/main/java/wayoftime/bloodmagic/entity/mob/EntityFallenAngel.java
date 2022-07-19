package wayoftime.bloodmagic.entity.mob;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class EntityFallenAngel extends EntityDemon implements RangedAttackMob {

    private static final String mobID = "fallenangel";

    public EntityFallenAngel(EntityType<? extends TamableAnimal> type, Level worldIn) {
        super(type, worldIn, mobID);
        this.setAggro(true);
        this.setTame(false);
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.FOLLOW_RANGE, 32)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.MAX_HEALTH, 50.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3);
    }

    public void performRangedAttack(LivingEntity livingEntity, float power) {
        Snowball snowball = new Snowball(this.level, this);
        double d0 = livingEntity.getEyeY() - (double) 1.1F;
        double d1 = livingEntity.getX() - this.getX();
        double d2 = d0 - snowball.getY();
        double d3 = livingEntity.getZ() - this.getZ();
        double d4 = Math.sqrt(d1 * d1 + d3 * d3) * (double) 0.2F;
        snowball.shoot(d1, d2 + d4, d3, 1.6F, 12.0F);
        this.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level.addFreshEntity(snowball);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false)); //used values from wolf
        this.goalSelector.addGoal(3, new RangedAttackGoal(this, 1.0D, 20, 15.0F));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(6, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngry));
    }

    public boolean isAngry(LivingEntity livingEntity) {
        return isTame();
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        var damage = this.isTame() ? 4 : 2;
        return entity.hurt(DamageSource.mobAttack(this), damage);
    }

    @Override
    public boolean isFood(ItemStack item) {
        return item.isEdible();
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        ItemStack itemstack = player.getItemInHand(interactionHand);
        Item item = itemstack.getItem();
        if (this.level.isClientSide) {
            boolean flag = this.isOwnedBy(player) || this.isTame();
            return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
        } else {
            if (this.isTame()) {
                if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                    if (!player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }

                    this.heal((float) itemstack.getFoodProperties(this).getNutrition());
                    this.gameEvent(GameEvent.MOB_INTERACT, this.eyeBlockPosition());
                    return InteractionResult.SUCCESS;
                }


                InteractionResult interactionresult = super.mobInteract(player, interactionHand);
                if ((!interactionresult.consumesAction() || this.isBaby()) && this.isOwnedBy(player)) {
                    this.setOrderedToSit(!this.isOrderedToSit());
                    this.jumping = false;
                    this.navigation.stop();
                    this.setTarget(null);
                    return InteractionResult.SUCCESS;
                }

                return interactionresult;


            }

            return super.mobInteract(player, interactionHand);
        }
    }

    @Override
    public boolean removeWhenFarAway(double p_27598_) {
        return false;
    }


}
