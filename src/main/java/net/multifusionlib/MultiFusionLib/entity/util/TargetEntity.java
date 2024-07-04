package net.multifusionlib.MultiFusionLib.entity.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.*;

public class TargetEntity
{
    private ModLivingEntity entity;
    private Level world;
    private static Entity currentTarget;
    private Map<LivingEntity, Entity> attackers;
    private EntityStates currentState;

    public TargetEntity(ModLivingEntity entity) {
        this.entity = entity;
        this.world = entity.level();
        this.attackers = new HashMap<>();
        this.currentState = EntityStates.IDLE; // Start in IDLE state
    }

    // Method to update AI behavior
    public void update() {
        detectAttackers();

        switch (currentState) {
            case IDLE:
                if (currentTarget != null) {
                    currentState = EntityStates.PETROL;
                }
                break;
            case PETROL:
                if (currentTarget != null) {
                    currentState = EntityStates.ATTACK;
                    entity.setTarget((LivingEntity) currentTarget);
                } else {
                    currentState = EntityStates.IDLE;
                }
                break;
            case ATTACK:
                if (currentTarget == null || !currentTarget.isAlive()) {
                    currentState = EntityStates.IDLE;
                }
                break;
        }
    }

    // Detect nearby attackers (enemies)
    private void detectAttackers() {
        double detectionRange = 10.0; // Example detection range
        List<Entity> entities = world.getEntities(entity, entity.getBoundingBox().inflate(detectionRange));

        for (Entity potentialAttacker : entities) {
            if (potentialAttacker instanceof LivingEntity && !(potentialAttacker instanceof Player)) {
                // Consider this entity as an attacker
                attackers.put((LivingEntity) potentialAttacker, currentTarget);
            }
        }

        if (!attackers.isEmpty()) {
            // Set current target to the closest attacker for simplicity
            currentTarget = attackers.keySet().iterator().next();
        } else {
            currentTarget = null;
        }
    }

    // Method to handle damage events and record attackers
    public void onDamage(LivingEntity attacker, float damageAmount) {
        attackers.put(attacker, entity);
    }

    public static LivingEntity getCurrentTarget() {
        return (LivingEntity) currentTarget;
    }
}
