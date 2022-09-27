package xyz.trixkz.fastbridger.commands.fastbridger;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.trixkz.fastbridger.Main;
import xyz.trixkz.fastbridger.commands.FastBridgerCommand;
import xyz.trixkz.fastbridger.utils.Utils;
import java.util.ArrayList;
import java.util.List;

public class JoinCommand extends FastBridgerCommand {

    private Main main;

    public JoinCommand(Main main) {
        this.main = main;
    }

    @Override
    public void executeAs(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        String arena = args[1];

        if (main.getFiles().getArenasConfig().contains(arena)) {
            if (main.getFiles().getArenasConfig().contains(arena + ".spawns.a") &&
                    main.getFiles().getArenasConfig().contains(arena + ".height") &&
                    main.getFiles().getArenasConfig().contains(arena + ".distance") &&
                    main.getFiles().getArenasConfig().contains(arena + ".max-players")) {
                if (main.getUtils().getCurrentPlayers(arena) != main.getUtils().getMaxPlayers(arena)) {
                    main.getUtils().join(player, arena);

                    player.getInventory().clear();

                    main.getUtils().giveItems(player);

                    main.getUtils().getPlayerArena().put(player, arena);

                    main.getUtils().getArenaPlayer().put(arena, player);

                    main.getUtils().getIngameAll().add(player);

                    player.sendMessage(Utils.translate("&aYou have joined the arena called &2" + arena));

                    player.getInventory().clear();

                    player.getInventory().setArmorContents(null);

                    player.setGameMode(GameMode.SURVIVAL);

                    player.setHealth(20.0D);

                    player.setFoodLevel(20);

                    player.setLevel(0);

                    player.setExp(0.0F);

                    main.getUtils().giveItems(player);
                } else {
                    player.sendMessage(Utils.translate("&cThe specified arena is currently full"));
                }
            } else {
                player.sendMessage("The specified arena has not been fully setup yet");
            }
        } else {
            player.sendMessage("The specified arena has not already been made");
        }
    }

    @Override
    public List<String> getTabCompletions(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> tabCompletions = new ArrayList<String>();

        return tabCompletions;
    }
}
