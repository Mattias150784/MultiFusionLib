package net.multifusionlib.MultiFusionLib.pathfinding;

import net.minecraft.core.BlockPos;

import java.util.Objects;

public class AStarNode
{
    public final BlockPos position;
    public AStarNode parent;
    public double gCost;
    public final double hCost;
    public double fCost;

    public AStarNode(BlockPos blockPos, AStarNode parent, double gCost, double hCost)
    {
        this.position = blockPos;
        this.parent = parent;
        this.gCost = gCost;
        this.hCost = hCost;
        this.fCost = gCost+hCost;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AStarNode aStarNode = (AStarNode) obj;
        return position.equals(aStarNode.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }
}
