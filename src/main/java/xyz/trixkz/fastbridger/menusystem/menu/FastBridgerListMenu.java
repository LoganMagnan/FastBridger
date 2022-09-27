package xyz.trixkz.fastbridger.menusystem.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.trixkz.fastbridger.Main;
import xyz.trixkz.fastbridger.menusystem.Menu;
import xyz.trixkz.fastbridger.menusystem.PlayerMenuUtil;
import xyz.trixkz.fastbridger.utils.Utils;

public class FastBridgerListMenu extends Menu {

    private Main main;

    public FastBridgerListMenu(PlayerMenuUtil playerMenuUtil, Main main) {
        super(playerMenuUtil);

        this.main = main;
    }

    @Override
    public String getMenuName() {
        return Utils.translate("&bArena List");
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        String arena = event.getCurrentItem().getItemMeta().getDisplayName().substring(2);

        if (event.getView().getTitle().equalsIgnoreCase(Utils.translate("&bArena List"))) {
            player.chat("/fastbridger check " + arena);
        }
    }

    @Override
    public void setMenuItems() {
        for (String arena : main.getFiles().getArenasConfig().getKeys(false)) {
            if (main.getFiles().getArenasConfig().contains(arena + ".spawns.a") &&
                    main.getFiles().getArenasConfig().contains(arena + ".height") &&
                    main.getFiles().getArenasConfig().contains(arena + ".distance") &&
                    main.getFiles().getArenasConfig().contains(arena + ".max-players")) {
                ItemStack itemStack = new ItemStack(Material.STAINED_CLAY, (short) 5);

                ItemMeta itemMeta = itemStack.getItemMeta();

                itemMeta.setDisplayName(Utils.translate("&a" + arena));

                itemStack.setItemMeta(itemMeta);

                inventory.addItem(itemStack);
            } else {
                ItemStack itemStack = new ItemStack(Material.STAINED_CLAY, (short) 14);

                ItemMeta itemMeta = itemStack.getItemMeta();

                itemMeta.setDisplayName(Utils.translate("&c" + arena));

                itemStack.setItemMeta(itemMeta);

                inventory.addItem(itemStack);
            }
        }
    }
}
