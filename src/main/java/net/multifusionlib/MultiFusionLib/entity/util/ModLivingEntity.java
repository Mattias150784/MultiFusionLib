package net.multifusionlib.MultiFusionLib.entity.util;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class ModLivingEntity extends LivingEntity 
{
    private Vec3 startAttackVec;
    private Vec3 startPreyVec;
    private LivingEntity prevAttackTarget = null;
    protected ModLivingEntity(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    
    public void setTarget(LivingEntity target)
    {
        if (prevAttackTarget != target) {
            if (target != null) {
                startPreyVec = new Vec3(target.getX(), target.getY(), target.getZ());
            } else {
                startPreyVec = new Vec3(target.getX(), target.getY(), target.getZ());
            }
            startAttackVec = new Vec3(target.getX(), target.getY(), target.getZ());
        }
        prevAttackTarget = target;
    }
}
