package net.multifusionlib.MultiFusionLib.pathfinding;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.*;

/**
 * AStarPathfinder is a class for finding paths in a Minecraft world using the A* algorithm.
 */
public class AStarPathfinder {
    private final Level world;
    private final BlockPos start;
    private final BlockPos goal;
    private final int maxSearchDistance;

    /**
     * Constructs an AStarPathfinder instance.
     *
     * @param world             The world in which the pathfinding takes place.
     * @param start             The starting position.
     * @param goal              The goal position.
     * @param maxSearchDistance The maximum search distance.
     */
    public AStarPathfinder(Level world, BlockPos start, BlockPos goal, int maxSearchDistance) {
        this.world = world;
        this.start = start;
        this.goal = goal;
        this.maxSearchDistance = maxSearchDistance;
    }

    /**
     * Finds the path from start to goal using the A* algorithm.
     *
     * @return A list of BlockPos representing the path from start to goal.
     */
    public List<BlockPos> findPaths() {
        PriorityQueue<AStarNode> openList = new PriorityQueue<>(Comparator.comparingDouble(node -> node.fCost));
        Set<BlockPos> closedList = new HashSet<>();

        AStarNode startNode = new AStarNode(start, null, 0, getHeuristicCost(start, goal));
        openList.add(startNode);

        while (!openList.isEmpty()) {
            AStarNode currentNode = openList.poll();

            if (currentNode.position.equals(goal)) {
                return reconstructPath(currentNode);
            }

            closedList.add(currentNode.position);

            for (BlockPos neighborPos : getNeighbors(currentNode.position)) {
                if (closedList.contains(neighborPos) || !isWalkable(neighborPos)) {
                    continue;
                }

                double tentativeGCost = currentNode.gCost + getMovementCost(currentNode.position, neighborPos);
                AStarNode neighborNode = new AStarNode(neighborPos, currentNode, tentativeGCost, getHeuristicCost(neighborPos, goal));

                if (!openList.contains(neighborNode) || tentativeGCost < neighborNode.gCost) {
                    neighborNode.gCost = tentativeGCost;
                    neighborNode.fCost = neighborNode.gCost + neighborNode.hCost;
                    neighborNode.parent = currentNode;

                    openList.add(neighborNode);
                }
            }
        }

        return Collections.emptyList();
    }

    /**
     * Calculates the heuristic cost (Manhattan distance) from start to goal.
     *
     * @param start The starting position.
     * @param goal  The goal position.
     * @return The heuristic cost.
     */
    private double getHeuristicCost(BlockPos start, BlockPos goal) {
        return Math.abs(start.getX() - goal.getX()) + Math.abs(start.getY() - goal.getY()) + Math.abs(start.getZ() - goal.getZ());
    }

    /**
     * Gets the neighbors of the given position, including diagonals.
     *
     * @param pos The position.
     * @return A list of neighboring positions.
     */
    public List<BlockPos> getNeighbors(BlockPos pos) {
        List<BlockPos> neighbors = new ArrayList<>();
        neighbors.add(pos.north());
        neighbors.add(pos.south());
        neighbors.add(pos.west());
        neighbors.add(pos.east());
        neighbors.add(pos.above());
        neighbors.add(pos.below());
        neighbors.add(pos.north().west());
        neighbors.add(pos.north().east());
        neighbors.add(pos.south().west());
        neighbors.add(pos.south().east());
        return neighbors;
    }

    /**
     * Determines if the given position is walkable.
     *
     * @param pos The position.
     * @return True if the position is walkable, false otherwise.
     */
    public boolean isWalkable(BlockPos pos) {
        return world.getBlockState(pos).isSolid();
    }

    /**
     * Gets the movement cost from the current position to the neighbor position.
     *
     * @param current  The current position.
     * @param neighbor The neighbor position.
     * @return The movement cost.
     */
    private double getMovementCost(BlockPos current, BlockPos neighbor) {
        Block currentBlock = world.getBlockState(current).getBlock();
        Block neighborBlock = world.getBlockState(neighbor).getBlock();
        double baseCost = current.distSqr(neighbor);

        // Example of terrain cost adjustment (customize based on your game's blocks)
        double terrainCost = 1.0;
        if (neighborBlock == Blocks.WATER) {
            terrainCost = 3.0; // Higher cost for water blocks
        } else if (neighborBlock == Blocks.SAND) {
            terrainCost = 1.5; // Higher cost for sand blocks
        }

        return baseCost * terrainCost;
    }

    /**
     * Reconstructs the path from the goal node back to the start node.
     *
     * @param node The goal node.
     * @return A list of BlockPos representing the path.
     */
    private List<BlockPos> reconstructPath(AStarNode node) {
        List<BlockPos> path = new ArrayList<>();
        while (node != null) {
            path.add(node.position);
            node = node.parent;
        }
        Collections.reverse(path);
        return path;
    }

}
