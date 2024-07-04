package net.multifusionlib.MultiFusionLib.entity.util;

public interface IAggresiveAI extends IEntityAI
{
    @Override
    default boolean isAggressive() {
        return true;
    }
}
