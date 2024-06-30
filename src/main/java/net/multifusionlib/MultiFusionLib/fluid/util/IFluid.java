package net.multifusionlib.MultiFusionLib.fluid.util;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidType;

/**
 * Interface representing the various behaviors and properties of a fluid in Minecraft.
 */
public interface IFluid {

    // Existing methods

    /**
     * Determines if the fluid is a source block.
     */
    default void isSource() {}

    /**
     * Gets the particle that drips from the fluid.
     */
    default ParticleOptions getDripParticle() {return null;}

    /**
     * Animates the fluid's tick behavior.
     */
    default void animateTick() {}

    /**
     * Determines if the fluid is flammable.
     * @return true if the fluid is flammable, false otherwise.
     */
    default boolean isFlammable() { return false; }

    /**
     * Determines if the fluid can be placed in a cauldron.
     * @return true if the fluid can be placed in a cauldron, false otherwise.
     */
    default boolean isCauldronPlaceable() { return false; }

    /**
     * Gets the distance the fluid can find a slope.
     * @return the slope find distance.
     */
    default int getSlopeFindDistance() { return 0; }

    // New methods

    /**
     * Determines if the fluid is infinite, like water in oceans.
     * @return true if the fluid is infinite, false otherwise.
     */
    default boolean isInfinite() {
        return false;
    }

    /**
     * Gets the delay for the fluid to spread to neighboring blocks.
     * @param level The level in which the fluid exists.
     * @param pos The position of the fluid block.
     * @param fluidState The current state of the fluid.
     * @param neighborFluidState The state of the neighboring fluid.
     * @return the delay in ticks for the fluid to spread.
     */
    default int getSpreadDelay(Level level, BlockPos pos, FluidState fluidState, FluidState neighborFluidState) {
        return 0;
    }

    /**
     * Gets the flow speed and direction of the fluid.
     * @param level The level in which the fluid exists.
     * @param pos The position of the fluid block.
     * @param fluidState The current state of the fluid.
     * @return a Vec3 representing the flow speed and direction.
     */
    default Vec3 getFlow(Level level, BlockPos pos, FluidState fluidState) {
        return Vec3.ZERO;
    }

    /**
     * Gets the fluid level within the block.
     * @param fluidState The current state of the fluid.
     * @return the fluid level.
     */
    default int getFluidLevel(FluidState fluidState) {
        return 0;
    }

    /**
     * Checks if the fluid can extinguish fire.
     * @return true if the fluid can extinguish fire, false otherwise.
     */
    default boolean canExtinguishFire() {
        return false;
    }

    /**
     * Gets the temperature of the fluid in Kelvin.
     * @return the temperature of the fluid.
     */
    default int getTemperature() {
        return 300; // Default temperature in Kelvin
    }

    /**
     * Gets the viscosity of the fluid in centipoise.
     * @return the viscosity of the fluid.
     */
    default int getViscosity() {
        return 1000; // Default viscosity in centipoise
    }

    /**
     * Gets the color of the fluid as an RGB integer.
     * @return the color of the fluid.
     */
    default int getColor() {
        return 0xFFFFFF; // Default color (white)
    }

    /**
     * Determines if the fluid can interact with the given block state.
     * @param blockState The block state to check interaction with.
     * @return true if the fluid can interact with the block, false otherwise.
     */
    default boolean canInteractWithBlock(BlockState blockState) {
        return false;
    }

    /**
     * Determines if the fluid is hazardous to entities.
     * @return true if the fluid is hazardous, false otherwise.
     */
    default boolean isHazardousToEntities() {
        return false;
    }

    /**
     * Determines the amount of light the fluid emits.
     * @return the light level emitted by the fluid.
     */
    default int getLightLevel() {
        return 0;
    }

    /**
     * Determines if the fluid provides buoyancy, like water.
     * @return true if the fluid is buoyant, false otherwise.
     */
    default boolean isBuoyant() {
        return false;
    }

    /**
     * Determines if the fluid is poisonous.
     * @return true if the fluid is poisonous, false otherwise.
     */
    default boolean isPoisonous() {
        return false;
    }

    /**
     * Gets the evaporation rate of the fluid.
     * @return the evaporation rate of the fluid.
     */
    default int getEvaporationRate() {
        return 0;
    }

    /**
     * Determines if the fluid can freeze under cold conditions.
     * @return true if the fluid can freeze, false otherwise.
     */
    default boolean canFreeze() {
        return false;
    }

    /**
     * Gets the density of the fluid in kg/m^3.
     * @return the density of the fluid.
     */
    default int getDensity() {
        return 1000; // Default density in kg/m^3
    }

    /**
     * Checks that the liquid is holy or not
     * @return if the liquid is holy
     */
    default boolean isHoly(){
        return false;
    }

    default FluidType getFluidType()
    {
        return null;
    }

    default Fluid getFlowing()
    {
        return null;
    }

    default Fluid getSource()
    {
        return null;
    }
}
