package xyz.trixkz.fastbridger.menusystem.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.trixkz.fastbridger.Main;
import xyz.trixkz.fastbridger.managers.user.User;
import xyz.trixkz.fastbridger.menusystem.PaginatedMenu;
import xyz.trixkz.fastbridger.menusystem.PlayerMenuUtil;
import xyz.trixkz.fastbridger.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BlocksMenu extends PaginatedMenu {

    private Main main;

    public BlocksMenu(PlayerMenuUtil playerMenuUtil, Main main) {
        super(playerMenuUtil);

        this.main = main;
    }

    @Override
    public String getMenuName() {
        return Utils.translate("&bBlocks");
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        User user = User.getByUUID(player.getUniqueId());

        if (event.getView().getTitle().equalsIgnoreCase(Utils.translate("&bBlocks"))) {
            if (event.getCurrentItem().getItemMeta().getLore().contains(Utils.translate("&aUnlocked"))) {
                player.getInventory().setItem(0, new ItemStack(event.getCurrentItem().getType(), 64));
                player.getInventory().setItem(1, new ItemStack(event.getCurrentItem().getType(), 64));

                user.setBlock(event.getCurrentItem().getType().toString());

                player.sendMessage(Utils.translate("&2" + event.getCurrentItem().getType().name() + " &aselected"));

                new BlocksMenu(main.getPlayerMenuUtil(player), main).open();
            } else if (event.getCurrentItem().getItemMeta().getLore().contains(Utils.translate("&cLocked"))) {
                String price = event.getCurrentItem().getItemMeta().getLore().get(1).replaceAll("[^0-9]", "").substring(1, event.getCurrentItem().getItemMeta().getLore().get(1).replaceAll("[^0-9]", "").length() - 1);

                int coinsPrice = Integer.parseInt(price);

                if (user.getCoinsBalance() >= coinsPrice) {
                    player.getInventory().setItem(0, new ItemStack(event.getCurrentItem().getType(), 64));
                    player.getInventory().setItem(1, new ItemStack(event.getCurrentItem().getType(), 64));

                    user.setBlock(event.getCurrentItem().getType().toString());

                    user.getBlocks().add(event.getCurrentItem().getType().toString());

                    user.removeCoinsBalance(coinsPrice);

                    player.sendMessage(Utils.translate("&a" + event.getCurrentItem().getItemMeta().getDisplayName() + " selected"));

                    new BlocksMenu(main.getPlayerMenuUtil(player), main).open();
                } else {
                    player.sendMessage(Utils.translate("&cNot enough coins"));
                }
            }

            switch (event.getCurrentItem().getType()) {
                case WOOD_BUTTON:
                    if (page == 0) {
                        player.sendMessage(Utils.translate("&bYou are on the first page"));
                    } else {
                        page--;

                        super.open();
                    }
                    break;
                case STONE_BUTTON:
                    if (!((index + 1) >= main.getFiles().getConfig().getConfigurationSection("blocks").getKeys(false).size())) {
                        page++;

                        super.open();
                    } else {
                        player.sendMessage(Utils.translate("&bYou are on the last page"));
                    }
                    break;
                case BARRIER:
                    player.closeInventory();

                    break;
            }
        }
    }

    @Override
    public void setMenuItems() {
        addMenuBorder();

        Player player = User.getPlayer();

        User user = User.getByUUID(player.getUniqueId());

        for (int i = 0; i < main.getFiles().getConfig().getConfigurationSection("blocks").getKeys(false).size(); i++) {
            ItemStack itemStack = new ItemStack(Objects.requireNonNull(Material.getMaterial(Objects.requireNonNull(main.getFiles().getConfig().getString("blocks." + i + ".material")))));
            ItemMeta itemMeta = itemStack.getItemMeta();
            List<String> lore = new ArrayList<>();
            if (user.getBlock().equals(main.getFiles().getConfig().getString("blocks." + i + ".material"))) {
                lore.add(Utils.translate("&aSelected"));
            } else if (user.getBlocks().contains(main.getFiles().getConfig().getString("blocks." + i + ".material"))) {
                lore.add(Utils.translate("&aUnlocked"));
                lore.add(Utils.translate("&7Click to use"));
            } else {
                lore.add(Utils.translate("&cLocked"));
                lore.add(Utils.translate("&7Click to buy for &b" + (50 * i) * 2 + " &7coins"));
            }
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            inventory.addItem(itemStack);
        }
    }
}
