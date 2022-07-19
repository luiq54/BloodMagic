package wayoftime.bloodmagic.client.render.model.mob;


import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class ModelFallenAngel<T extends LivingEntity> extends HumanoidModel<T> {


    public ModelFallenAngel(ModelPart root) {
        super(root);

    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0);
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

        var body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create()
                        .texOffs(16, 16)
                        .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition leftWing = body.addOrReplaceChild("left_wing", CubeListBuilder.create()
                        .texOffs(33, 8).mirror()
                        .addBox(0.0F, 0.0F, 0.0F, 10.0F, 7.0F, 0.0F, CubeDeformation.NONE).mirror(false),
                PartPose.offsetAndRotation(0.0F, 1.0F, 2.0F, 0F, (float) (-45 * Math.PI / 180), 0));
        PartDefinition rightWing = body.addOrReplaceChild("right_wing", CubeListBuilder.create()
                        .texOffs(33, 8)
                        .addBox(-10.0F, 0.0F, 0.0F, 10.0F, 7.0F, 0.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(0.0F, 1.0F, 2.0F, 0.0F, (float) (45 * Math.PI / 180), 0));
        return LayerDefinition.create(meshdefinition, 64, 32);
    }


}