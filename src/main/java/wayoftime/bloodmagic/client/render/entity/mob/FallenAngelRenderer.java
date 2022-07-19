package wayoftime.bloodmagic.client.render.entity.mob;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.client.render.BloodMagicModelLayerLocations;
import wayoftime.bloodmagic.client.render.model.mob.ModelFallenAngel;
import wayoftime.bloodmagic.entity.mob.EntityFallenAngel;

public class FallenAngelRenderer extends HumanoidMobRenderer<EntityFallenAngel, ModelFallenAngel<EntityFallenAngel>> {

    private static final ResourceLocation TEXTURE = BloodMagic.rl("textures/models/fallenangel.png");

    public FallenAngelRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ModelFallenAngel<>(renderManager.bakeLayer(BloodMagicModelLayerLocations.FALLEN_ANGEL_LOC)), 0.5F);
    }


    @Override
    public @NotNull ResourceLocation getTextureLocation(EntityFallenAngel p_114482_) {
        return TEXTURE;
    }
}
