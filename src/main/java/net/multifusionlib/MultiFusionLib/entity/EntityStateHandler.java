package net.multifusionlib.MultiFusionLib.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.multifusionlib.MultiFusionLib.entity.util.EntityStates;
import net.multifusionlib.MultiFusionLib.entity.util.IEntityAI;
import net.multifusionlib.MultiFusionLib.entity.util.TargetEntity;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Handles the state of an entity and manages transitions between different states
 * such as IDLE, PATROL, ATTACK, and FLEE.
 */
public class EntityStateHandler implements IEntityAI {
    private EntityStates currentState;
    @Nullable
    private final LivingEntity target;
    @Nullable
    private final Entity threat;
    private final LivingEntity entity;
    private final ServerLevel world;
    private List<BlockPos> patrolPoints;
    private int currentPatrolIndex;
    private Random random;

    /**
     * Constructor to initialize EntityStateHandler with optional target and threat entities.
     *
     * @param entity The entity being handled.
     * @param world  The world where the entity is located.
     * @param target The entity's target (can be null).
     * @param threat The entity's threat (can be null).
     */
    public EntityStateHandler(LivingEntity entity, ServerLevel world, @Nullable LivingEntity target, @Nullable Entity threat) {
        this.entity = entity;
        this.world = world;
        this.target = target;
        this.threat = threat;
        this.currentState = EntityStates.IDLE;
        this.patrolPoints = new ArrayList<>();
        this.random = new Random();
        initializePatrolPoints();
    }

    /**
     * Initializes patrol points for the entity.
     * This could be hard-coded points or dynamically generated based on the entity's initial position.
     */
    private void initializePatrolPoints() {
        // Example: Adding four patrol points around a central position
        BlockPos centralPos = entity.blockPosition();
        patrolPoints.add(centralPos.offset(10, 0, 0));
        patrolPoints.add(centralPos.offset(-10, 0, 0));
        patrolPoints.add(centralPos.offset(0, 0, 10));
        patrolPoints.add(centralPos.offset(0, 0, -10));
        currentPatrolIndex = 0;
    }

    /**
     * Gets the current state of the entity.
     *
     * @return The current state.
     */
    public EntityStates getCurrentState() {
        return currentState;
    }

    /**
     * Sets the current state of the entity.
     *
     * @param entityStates The state to set.
     */
    public void setCurrentState(EntityStates entityStates) {
        this.currentState = entityStates;
    }

    /**
     * Updates the state of the entity based on its current situation.
     */
    public void update() {
        if (this.idle()) {
            this.setCurrentState(EntityStates.IDLE);
        } else if (this.attack(target)) {
            this.setCurrentState(EntityStates.ATTACK);
        } else if (this.flee(threat)) {
            this.setCurrentState(EntityStates.FLEE);
        } else if (this.patrol()) {
            this.setCurrentState(EntityStates.PETROL);
        }
    }

    @Override
    public boolean idle() {
        return TargetEntity.getCurrentTarget() == null;
    }

    @Override
    public boolean attack(LivingEntity target) {
        if (target != null && this.isAggressive()) {
            moveTo(target.blockPosition());
            if (entity.distanceToSqr(target) < entity.getBbWidth() * entity.getBbWidth() + target.getBbWidth()) {
                entity.doHurtTarget(target);
            }
            return true;
        }
        return false;
    }


    @Override
    public boolean patrol() {
        if (patrolPoints.isEmpty()) {
            return false;
        }

        // Move to the next patrol point
        BlockPos nextPatrolPoint = patrolPoints.get(currentPatrolIndex);
        moveTo(nextPatrolPoint);

        // Check if the entity has reached the current patrol point
        if (hasReached(nextPatrolPoint)) {
            currentPatrolIndex = (currentPatrolIndex + 1) % patrolPoints.size();
        }

        return true;
    }

    @Override
    public boolean flee(Entity threat) {
        if (threat != null && this.isNeutral()) {
            BlockPos oppositeDirection = entity.blockPosition().subtract(threat.blockPosition());
            moveTo(oppositeDirection);
            return true;
        }
        return false;
    }

    /**
     * Moves the entity to the specified position.
     *
     * @param pos The position to move to.
     */
    public void moveTo(BlockPos pos) {
        ((PathfinderMob) this.entity).getNavigation().moveTo(pos.getX(), pos.getY(), pos.getZ(), 1.0);
    }

    /**
     * Checks if the entity has reached the specified position.
     *
     * @param pos The position to check.
     * @return True if the entity has reached the position, false otherwise.
     */
    private boolean hasReached(BlockPos pos) {
        return entity.blockPosition().closerThan(pos, 1.0);
    }
}
