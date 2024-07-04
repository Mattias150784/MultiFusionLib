package net.multifusionlib.MultiFusionLib.entity.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.core.BlockPos;

/**
 * Interface defining common AI behaviors for entities.
 */
public interface IEntityAI {

    /**
     * Determines if the entity is passive.
     *
     * @return true if the entity is passive, false otherwise.
     */
    default boolean isPassive() { return false; }

    /**
     * Determines if the entity is aggressive.
     *
     * @return true if the entity is aggressive, false otherwise.
     */
    default boolean isAggressive() { return false; }

    /**
     * Determines if the entity is neutral.
     *
     * @return true if the entity is neutral, false otherwise.
     */
    default boolean isNeutral() { return false; }

    /**
     * Determines if the entity is currently attacking.
     *
     * @return true if the entity is attacking, false otherwise.
     */
    default boolean isAttacking() { return false; }

    /**
     * Checks if the entity currently has a path to follow.
     *
     * @return true if the entity has a path, false otherwise.
     */
    default boolean hasPath() { return false; }

    /**
     * Gets the current path of the entity.
     *
     * @return the current path of the entity, or null if there is no path.
     */
    default Path getPath() { return null; }

    /**
     * Sets the path for the entity to follow.
     *
     * @param path the path for the entity to follow.
     */
    default void setPath(Path path) {}

    /**
     * Commands the entity to move to a specific position.
     *
     * @param targetPos the target position to move to.
     */
    default void moveTo(BlockPos targetPos) {}

    /**
     * Commands the entity to move to another entity.
     *
     * @param targetEntity the target entity to move to.
     */
    default void moveTo(Entity targetEntity) {}

    /**
     * Checks if the entity is healthy.
     *
     * @return true if the entity is healthy, false otherwise.
     */
    default boolean isHealthy() { return true; }

    /**
     * Checks if the entity is injured.
     *
     * @return true if the entity is injured, false otherwise.
     */
    default boolean isInjured() { return !isHealthy(); }

    /**
     * Determines if the entity can interact with another entity.
     *
     * @param entity the entity to check interaction with.
     * @return true if the entity can interact, false otherwise.
     */
    default boolean canInteractWith(Entity entity) { return false; }

    /**
     * Performs an interaction with another entity.
     *
     * @param entity the entity to interact with.
     */
    default void interactWith(Entity entity) {}

    /**
     * Checks if the entity can see a specific position.
     *
     * @param pos the position to check.
     * @return true if the entity can see the position, false otherwise.
     */
    default boolean canSee(BlockPos pos) { return true; }

    /**
     * Checks if the entity can see another entity.
     *
     * @param entity the entity to check.
     * @return true if the entity can see the other entity, false otherwise.
     */
    default boolean canSee(Entity entity) { return true; }

    /**
     * Checks if the entity is in danger.
     *
     * @return true if the entity is in danger, false otherwise.
     */
    default boolean isInDanger() { return false; }

    /**
     * Makes the entity idle.
     */
    default boolean idle() {return false;}

    /**
     * Makes the entity patrol.
     */
    default boolean patrol() {return false;}

    /**
     * Makes the entity flee from a threat.
     *
     * @param threat the entity to flee from.
     */
    default boolean flee(Entity threat) {return false;}

    /**
     * Makes the entity attack a target.
     *
     * @param target the entity to attack.
     */
    default boolean attack(LivingEntity target) {return false;}

    /**
     * Makes the entity defend an ally.
     *
     * @param ally the entity to defend.
     */
    default void defend(Entity ally) {}

    /**
     * Makes the entity follow a leader.
     *
     * @param leader the entity to follow.
     */
    default void follow(Entity leader) {}

    /**
     * Calculates the distance to a specific position.
     *
     * @param pos the position to calculate the distance to.
     * @return the distance to the position.
     */
    default double getDistanceTo(BlockPos pos) { return 0.0; }

    /**
     * Calculates the distance to another entity.
     *
     * @param entity the entity to calculate the distance to.
     * @return the distance to the entity.
     */
    default double getDistanceTo(Entity entity) { return 0.0; }

    /**
     * Checks if the entity currently has a task.
     *
     * @return true if the entity has a task, false otherwise.
     */
    default boolean hasTask() { return false; }

    /**
     * Sets the current task for the entity.
     *
     * @param task the task to set.
     */
    default void setTask(String task) {}

    /**
     * Gets the current task of the entity.
     *
     * @return the current task.
     */
    default String getCurrentTask() { return ""; }
}
