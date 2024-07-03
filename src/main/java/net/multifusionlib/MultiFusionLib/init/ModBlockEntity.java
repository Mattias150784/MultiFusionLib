package net.multifusionlib.MultiFusionLib.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.multifusionlib.MultiFusionLib.MultiFusionLib;

public class ModBlockEntity
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MultiFusionLib.MOD_ID);



    public static void register(IEventBus eventBus){
        BLOCK_ENTITY_TYPE.register(eventBus);
    }
}
