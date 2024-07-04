package net.multifusionlib.MultiFusionLib.entity.util;

public interface IPassiveAI extends IEntityAI
{
    @Override
    default boolean isPassive() {
        return true;
    }
}
