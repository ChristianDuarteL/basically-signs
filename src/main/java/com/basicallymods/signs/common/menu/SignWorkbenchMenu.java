package com.basicallymods.signs.common.menu;

import com.basicallymods.signs.common.data.SignRecipe;
import com.basicallymods.signs.common.registry.ModBlocks;
import com.basicallymods.signs.common.registry.ModMenus;
import com.basicallymods.signs.common.registry.ModRecipeTypes;
import com.google.common.collect.Lists;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SignWorkbenchMenu extends AbstractContainerMenu {
    public static final int INPUT_SLOT = 0;
    public static final int DYE_SLOT = 1;
    public static final int RESULT_SLOT = 2;
    private static final int INV_SLOT_START = 3;
    private static final int INV_SLOT_END = 30;
    private static final int USE_ROW_SLOT_START = 30;
    private static final int USE_ROW_SLOT_END = 39;
    private final ContainerLevelAccess access;
    /** The index of the selected recipe in the GUI. */
    private final DataSlot selectedRecipeIndex = DataSlot.standalone();
    private final Level level;
    private List<SignRecipe> recipes = Lists.newArrayList();
    /** The {@linkplain net.minecraft.world.item.ItemStack} set in the input slot by the player. */
    private ItemStack input = ItemStack.EMPTY;
    private ItemStack dye = ItemStack.EMPTY;
    /**
     * Stores the game time of the last time the player took items from the the crafting result slot. This is used to
     * prevent the sound from being played multiple times on the same tick.
     */
    long lastSoundTime;
    final Slot inputSlot;
    /** The inventory slot that stores the output of the crafting recipe. */
    final Slot resultSlot;
    final Slot dyeSlot;
    Runnable slotUpdateListener = () -> {
    };
    public final Container container = new SimpleContainer(2) {
        /**
         * For block entities, ensures the chunk containing the block entity is saved to disk later - the game won't think
         * it hasn't changed and skip it.
         */
        public void setChanged() {
            super.setChanged();
            SignWorkbenchMenu.this.slotsChanged(this);
            SignWorkbenchMenu.this.slotUpdateListener.run();
        }
    };
    /** The inventory that stores the output of the crafting recipe. */
    final ResultContainer resultContainer = new ResultContainer();

    public SignWorkbenchMenu(int pContainerId, Inventory pPlayerInventory) {
        this(pContainerId, pPlayerInventory, ContainerLevelAccess.NULL);
    }

    public SignWorkbenchMenu(int pContainerId, Inventory pPlayerInventory, final ContainerLevelAccess pAccess) {
        super(ModMenus.SIGN_WORKBENCH_MENU.get(), pContainerId);
        this.access = pAccess;
        this.level = pPlayerInventory.player.level();
        this.inputSlot = this.addSlot(new Slot(this.container, INPUT_SLOT, 20, 15));
        this.dyeSlot = this.addSlot(new Slot(this.container, DYE_SLOT, 20, 52));
        this.resultSlot = this.addSlot(new Slot(this.resultContainer, RESULT_SLOT, 143, 33) {
            /**
             * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
             */
            public boolean mayPlace(ItemStack p_40362_) {
                return false;
            }

            public void onTake(@NotNull Player pPlayer, @NotNull ItemStack itemStack) {
                itemStack.onCraftedBy(pPlayer.level(), pPlayer, itemStack.getCount());
                SignWorkbenchMenu.this.resultContainer.awardUsedRecipes(pPlayer, this.getRelevantItems());
                ItemStack signItem = SignWorkbenchMenu.this.inputSlot.remove(1);
                ItemStack dyeItem = SignWorkbenchMenu.this.dyeSlot.remove(1);

                if (!signItem.isEmpty() && !dyeItem.isEmpty()) {
                    SignWorkbenchMenu.this.setupResultSlot();
                }

                pAccess.execute((level, blockPos) -> {
                    long l = level.getGameTime();
                    if (SignWorkbenchMenu.this.lastSoundTime != l) {
                        level.playSound(null, blockPos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 1.0F, 1.0F);
                        SignWorkbenchMenu.this.lastSoundTime = l;
                    }

                });
                super.onTake(pPlayer, itemStack);
            }

            private List<ItemStack> getRelevantItems() {
                return List.of(SignWorkbenchMenu.this.inputSlot.getItem(), SignWorkbenchMenu.this.dyeSlot.getItem());
            }
        });

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(pPlayerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(pPlayerInventory, k, 8 + k * 18, 142));
        }

        this.addDataSlot(this.selectedRecipeIndex);
    }

    /**
     * Returns the index of the selected recipe.
     */
    public int getSelectedRecipeIndex() {
        return this.selectedRecipeIndex.get();
    }

    public List<SignRecipe> getRecipes() {
        return this.recipes;
    }

    public int getNumRecipes() {
        return this.recipes.size();
    }

    public boolean hasInputItem() {
        return this.inputSlot.hasItem() && this.dyeSlot.hasItem() && !this.recipes.isEmpty();
    }

    /**
     * Determines whether supplied player can use this container
     */
    public boolean stillValid(Player pPlayer) {
        return stillValid(this.access, pPlayer, ModBlocks.SIGN_WORKBENCH.get());
    }

    /**
     * Handles the given Button-click on the server, currently only used by enchanting. Name is for legacy.
     */
    public boolean clickMenuButton(Player pPlayer, int pId) {
        if (this.isValidRecipeIndex(pId)) {
            this.selectedRecipeIndex.set(pId);
            this.setupResultSlot();
        }

        return true;
    }

    private boolean isValidRecipeIndex(int pRecipeIndex) {
        return pRecipeIndex >= 0 && pRecipeIndex < this.recipes.size();
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void slotsChanged(Container pInventory) {
        ItemStack signItem = this.inputSlot.getItem();
        ItemStack dyeItem = this.dyeSlot.getItem();
        if (!signItem.is(this.input.getItem()) || !dyeItem.is(this.dye.getItem())) {
            this.input = signItem.copy();
            this.dye = dyeItem.copy();
            this.setupRecipeList(pInventory, signItem);
        }
    }

    private void setupRecipeList(Container pContainer, ItemStack signItem) {
        this.recipes.clear();
        this.selectedRecipeIndex.set(-1);
        this.resultSlot.set(ItemStack.EMPTY);
        if (!signItem.isEmpty()) {
            this.recipes = this.level.getRecipeManager().getRecipesFor(ModRecipeTypes.SIGN_WORKBENCH_RECIPE.get(), pContainer, this.level);
        }

    }

    void setupResultSlot() {
        if (!this.recipes.isEmpty() && this.isValidRecipeIndex(this.selectedRecipeIndex.get())) {
            SignRecipe stonecutterrecipe = this.recipes.get(this.selectedRecipeIndex.get());
            ItemStack itemstack = stonecutterrecipe.assemble(this.container, this.level.registryAccess());
            if (itemstack.isItemEnabled(this.level.enabledFeatures())) {
                this.resultContainer.setRecipeUsed(stonecutterrecipe);
                this.resultSlot.set(itemstack);
            } else {
                this.resultSlot.set(ItemStack.EMPTY);
            }
        } else {
            this.resultSlot.set(ItemStack.EMPTY);
        }

        this.broadcastChanges();
    }

    public MenuType<?> getType() {
        return ModMenus.SIGN_WORKBENCH_MENU.get();
    }

    public void registerUpdateListener(Runnable pListener) {
        this.slotUpdateListener = pListener;
    }

    /**
     * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in is
     * null for the initial slot that was double-clicked.
     */
    public boolean canTakeItemForPickAll(ItemStack pStack, Slot pSlot) {
        return pSlot.container != this.resultContainer && super.canTakeItemForPickAll(pStack, pSlot);
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            Item item = itemstack1.getItem();
            itemstack = itemstack1.copy();
            if (pIndex == RESULT_SLOT) {
                item.onCraftedBy(itemstack1, pPlayer.level(), pPlayer);
                if (!this.moveItemStackTo(itemstack1, INV_SLOT_START, USE_ROW_SLOT_END, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (pIndex == INPUT_SLOT || pIndex == DYE_SLOT) {
                if (!this.moveItemStackTo(itemstack1, INV_SLOT_START, USE_ROW_SLOT_END, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.level.getRecipeManager().getRecipeFor(ModRecipeTypes.SIGN_WORKBENCH_RECIPE.get(), new SimpleContainer(itemstack1), this.level).isPresent()) {
                if (!this.moveItemStackTo(itemstack1, INPUT_SLOT, RESULT_SLOT, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (pIndex >= INV_SLOT_START && pIndex < USE_ROW_SLOT_START) {
                if (!this.moveItemStackTo(itemstack1, INPUT_SLOT, RESULT_SLOT, false) && !this.moveItemStackTo(itemstack1, USE_ROW_SLOT_START, USE_ROW_SLOT_END, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (pIndex >= USE_ROW_SLOT_START && pIndex < USE_ROW_SLOT_END) {
                if (!this.moveItemStackTo(itemstack1, INPUT_SLOT, RESULT_SLOT, false) && !this.moveItemStackTo(itemstack1, INV_SLOT_START, INV_SLOT_END, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            }

            slot.setChanged();
            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, itemstack1);
            this.broadcastChanges();
        }

        return itemstack;
    }

    /**
     * Called when the container is closed.
     */
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.resultContainer.removeItemNoUpdate(RESULT_SLOT);
        this.access.execute((p_40313_, p_40314_) -> {
            this.clearContainer(pPlayer, this.container);
        });
    }
}
