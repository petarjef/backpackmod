package backpack.modid;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;

public class BackpackItem extends Item {

    private static final int SIZE = 36;

    public BackpackItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (player.isShiftKeyDown()) {
            if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
                open(serverPlayer, player.getItemInHand(hand));
            }
            return InteractionResult.SUCCESS;
        }
        return super.use(level, player, hand); // vanilla: equip
    }

    public static void open(ServerPlayer player, ItemStack backpack) {
        SimpleContainer container = new SimpleContainer(SIZE);
        load(backpack, container);

        player.openMenu(new SimpleMenuProvider(
                (id, inventory, p) -> new ChestMenu(MenuType.GENERIC_9x4, id, inventory, container, 4) {
                    {

                        for (int i = 0; i < SIZE; i++) {
                            Slot old = this.slots.get(i);
                            Slot replacement = new Slot(container, i, old.x, old.y) {
                                @Override
                                public boolean mayPlace(ItemStack stack) {
                                    return !stack.is(ModItems.BACKPACK);
                                }
                            };
                            replacement.index = i;
                            this.slots.set(i, replacement);
                        }
                    }

                    @Override
                    public void removed(Player closer) {
                        super.removed(closer);
                        save(backpack, container);
                    }
                },
                Component.literal("Backpack")));
    }

    private static void load(ItemStack backpack, SimpleContainer container) {
        ItemContainerContents contents =
                backpack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
        NonNullList<ItemStack> items = NonNullList.withSize(SIZE, ItemStack.EMPTY);
        contents.copyInto(items);
        for (int i = 0; i < SIZE; i++) {
            container.setItem(i, items.get(i));
        }
    }

    private static void save(ItemStack backpack, SimpleContainer container) {
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            items.add(container.getItem(i).copy());
        }
        backpack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(items));
    }
}