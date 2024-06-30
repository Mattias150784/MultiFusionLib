package net.multifusionlib.MultiFusionLib.init;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.multifusionlib.MultiFusionLib.MultiFusionLib;
import net.multifusionlib.MultiFusionLib.fluid.ModFluids;

public class ModItems
{
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MultiFusionLib.MOD_ID);

    public static final RegistryObject<Item> TEMP_WATER_BUCKET = ITEMS.register("temp_water_bucket",
            ()-> new BucketItem(ModFluids.SOURCE_SOAP_WATER.get(),new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
