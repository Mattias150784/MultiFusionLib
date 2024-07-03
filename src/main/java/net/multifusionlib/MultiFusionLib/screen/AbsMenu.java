package net.multifusionlib.MultiFusionLib.screen;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraftforge.registries.ObjectHolder;
import net.multifusionlib.MultiFusionLib.block.entity.AbstractBlockEntity;
import org.jetbrains.annotations.Nullable;

/**
 * Abstract class representing a custom menu for a block entity.
 * Handles the creation of slots for the player's inventory, hotbar, and the block entity's inventory.
 */
public class AbsMenu<T extends BlockEntity> extends AbstractContainerMenu {

    private final Block blockEntityBlock;
    private final T blockEntity;
    private final Level level;
    private final ContainerData data;

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private static final int TE_INVENTORY_SLOT_COUNT = AbstractBlockEntity.size;

    @ObjectHolder(registryName = "multifusionlib:abs_menu", value = "multifusionlib:abs_menu"  )
    public static final MenuType<AbsMenu<?>> TYPE = null;

    public AbsMenu(int pContainerId, Inventory inv, FriendlyByteBuf friendlyByteBuf) {
        this(pContainerId, inv, getBlockEntity(inv, friendlyByteBuf.readBlockPos()), new SimpleContainerData(TE_INVENTORY_SLOT_COUNT));
    }

    /**
     * Constructor for AbsMenu.
     *
     * @param pContainerId the container ID
     * @param inv          the player's inventory
     * @param entity       the block entity
     * @param data         the container data
     */
    public AbsMenu(int pContainerId, Inventory inv, T entity, ContainerData data) {
        super(TYPE, pContainerId);
        this.data = data;
        this.blockEntityBlock = entity.getBlockState().getBlock();
        this.blockEntity = entity;
        this.level = inv.player.level();

        addPlayerInventory(inv);
        addPlayerHotbar(inv);
        addDataSlots(data);
    }

    @SuppressWarnings("unchecked")
    private static <T extends BlockEntity> T getBlockEntity(Inventory inv, BlockPos pos) {
        BlockEntity entity = inv.player.level().getBlockEntity(pos);
        if (entity == null) throw new IllegalStateException("BlockEntity not found at " + pos);
        return (T) entity;
    }

    /**
     * Handles quick movement of item stacks within the container.
     *
     * @param playerIn the player interacting with the container
     * @param pIndex   the index of the slot clicked
     * @return the resulting ItemStack after the move
     */
    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (!sourceSlot.hasItem()) return ItemStack.EMPTY;

        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }

        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    /**
     * Checks whether the container is still valid for the given player.
     *
     * @param pPlayer the player
     * @return true if the container is still valid, false otherwise
     */
    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(pPlayer.level(), blockEntity.getBlockPos()), pPlayer, blockEntityBlock);
    }

    /**
     * Adds the player's inventory slots to the container.
     *
     * @param playerInventory the player's inventory
     */
    private void addPlayerInventory(Inventory playerInventory) {
        for (int row = 0; row < PLAYER_INVENTORY_ROW_COUNT; ++row) {
            for (int col = 0; col < PLAYER_INVENTORY_COLUMN_COUNT; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
    }

    /**
     * Adds the player's hotbar slots to the container.
     *
     * @param playerInventory the player's inventory
     */
    private void addPlayerHotbar(Inventory playerInventory) {
        for (int col = 0; col < HOTBAR_SLOT_COUNT; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    @Nullable
    @Override
    public MenuType<?> getType() {
        return TYPE;
    }
}
