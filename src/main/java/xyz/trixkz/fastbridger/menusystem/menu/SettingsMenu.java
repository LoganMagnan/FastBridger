package xyz.trixkz.fastbridger.menusystem.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.trixkz.fastbridger.Main;
import xyz.trixkz.fastbridger.managers.user.User;
import xyz.trixkz.fastbridger.menusystem.Menu;
import xyz.trixkz.fastbridger.menusystem.PlayerMenuUtil;
import xyz.trixkz.fastbridger.utils.Utils;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SettingsMenu extends Menu {

    private Main main;

    public SettingsMenu(PlayerMenuUtil playerMenuUtil, Main main) {
        super(playerMenuUtil);

        this.main = main;
    }

    @Override
    public String getMenuName() {
        return Utils.translate("&bSettings");
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        User user = User.getByUUID(player.getUniqueId());

        if (event.getView().getTitle().equalsIgnoreCase(Utils.translate("&bSettings"))) {
            switch (event.getCurrentItem().getType()) {
                case SANDSTONE:
                    new BlocksMenu(main.getPlayerMenuUtil(player), main).open();

                    break;
            }
        }
    }

    @Override
    public void setMenuItems() {
        Player player = User.getPlayer();

        User user = User.getByUUID(player.getUniqueId());

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        ItemStack blocks = new ItemStack(Material.SANDSTONE);
        ItemMeta blocksMeta = blocks.getItemMeta();
        blocksMeta.setDisplayName(Utils.translate("&bBlocks"));
        List<String> blocksLore = new ArrayList<String>();
        blocksLore.add(Utils.translate("&7Click on this"));
        blocksLore.add(Utils.translate("&7to get some more blocks"));
        blocksMeta.setLore(blocksLore);
        blocks.setItemMeta(blocksMeta);

        ItemStack statistics = new ItemStack(Material.COMPASS);
        ItemMeta statisticsMeta = statistics.getItemMeta();
        statisticsMeta.setDisplayName(Utils.translate("&bStatistics"));
        List<String> statisticsLore = new ArrayList<String>();
        statisticsLore.add(Utils.translate("&8⚫ &7Personal best: &b" + decimalFormat.format(user.getPersonalBest())));
        statisticsMeta.setLore(statisticsLore);
        statistics.setItemMeta(statisticsMeta);

        inventory.setItem(11, blocks);
        inventory.setItem(15, statistics);
    }
}
