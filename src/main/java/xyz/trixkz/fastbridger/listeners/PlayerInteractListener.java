package xyz.trixkz.fastbridger.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import xyz.trixkz.fastbridger.Main;
import xyz.trixkz.fastbridger.menusystem.menu.SettingsMenu;
import xyz.trixkz.fastbridger.utils.Utils;
import java.io.IOException;

public class PlayerInteractListener implements Listener {

    private Main main;

    public PlayerInteractListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        String arena = main.getUtils().getPlayerArena().get(player);

        String abcValue = main.getUtils().getPlayerAbc().get(player);

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            try {
                if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(Utils.translate("&bSettings &7(Right Click)"))) {
                    event.setCancelled(true);

                    if (main.getUtils().getIngameAll().contains(player)) {
                        event.setCancelled(true);

                        new SettingsMenu(main.getPlayerMenuUtil(player), main).open();
                    }
                } else if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(Utils.translate("&bGo To Hub &7(Right Click)"))) {
                    event.setCancelled(true);

                    if (main.getUtils().getIngameAll().contains(player)) {
                        main.getFiles().getArenasConfig().set(arena + ".spawn" + abcValue + ".open", true);

                        try {
                            main.getFiles().getArenasConfig().save(main.getFiles().getArenasConfigFile());
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }

                        main.getUtils().resetBlocks(player);

                        main.getUtils().getIngameAll().remove(player);

                        main.getUtils().getPlayerArena().remove(player);

                        main.getUtils().getPlayerAbc().remove(player);

                        player.kickPlayer("");
                    }
                }
            } catch (NullPointerException exception) {

            }
        }
    }
}
