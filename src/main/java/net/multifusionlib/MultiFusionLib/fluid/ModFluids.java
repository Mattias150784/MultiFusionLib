package net.multifusionlib.MultiFusionLib.fluid;

import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.multifusionlib.MultiFusionLib.MultiFusionLib;
import net.multifusionlib.MultiFusionLib.init.ModBlocks;
import net.multifusionlib.MultiFusionLib.init.ModItems;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, MultiFusionLib.MOD_ID);

    public static final RegistryObject<FlowingFluid> SOURCE_SOAP_WATER = FLUIDS.register("temp_water_fluid",
            () -> new AbstractFluid.Source(ModFluids.TEMP_WATER_FLUID_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FLOWING_SOAP_WATER = FLUIDS.register("flowing_soap_water_temp",
            () -> new AbstractFluid.Flowing(ModFluids.TEMP_WATER_FLUID_PROPERTIES));

    public static final AbstractFluid.ABSProperties TEMP_WATER_FLUID_PROPERTIES = (AbstractFluid.ABSProperties) new AbstractFluid.ABSProperties(
            ModFluidTypes.SOAP_WATER_FLUID_TYPE, SOURCE_SOAP_WATER, FLOWING_SOAP_WATER)
            .slopeFindDistance(2)
            .levelDecreasePerBlock(1)
            .block(() -> ModBlocks.TEMP_WATER_BLOCK.get())
            .bucket(() -> ModItems.TEMP_WATER_BUCKET.get());

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
