package net.multifusionlib.MultiFusionLib.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TestBE extends AbstractBlockEntity{


    public TestBE(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState, int size) {
        super(pType, pPos, pBlockState, size);
    }

    @Override
    protected int getContainerSize() {
        return 0;
    }

    @Override
    protected Container getContainer() {
        return null;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("H");
    }
}
