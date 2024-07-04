package net.multifusionlib.MultiFusionLib.entity.util;

public interface INeutralAI extends IEntityAI
{
    @Override
    default boolean isNeutral() {
        return true;
    }
}
