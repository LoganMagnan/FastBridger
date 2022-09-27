package xyz.trixkz.fastbridger.commands.fastbridger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.trixkz.fastbridger.Main;
import xyz.trixkz.fastbridger.commands.FastBridgerCommand;
import xyz.trixkz.fastbridger.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SetSpawnCommand extends FastBridgerCommand {

    private Main main;

    public SetSpawnCommand(Main main) {
        this.main = main;
    }

    @Override
    public void executeAs(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission(Utils.ADMIN_PERMISSION_NODE)) {
            return;
        }

        Player player = (Player) sender;

        String arena = args[1];
        String abcValue = args[2];
        String direction = main.getUtils().getDirection(player);

        if (main.getFiles().getArenasConfig().contains(arena)) {
            if (abcValue.equals("a") || abcValue.equals("b") || abcValue.equals("c") || abcValue.equals("d") ||
                    abcValue.equals("e") || abcValue.equals("f") || abcValue.equals("g") ||
                    abcValue.equals("h") || abcValue.equals("i") || abcValue.equals("j") ||
                    abcValue.equals("k") || abcValue.equals("l") || abcValue.equals("m") ||
                    abcValue.equals("n") || abcValue.equals("o") || abcValue.equals("p") ||
                    abcValue.equals("q") || abcValue.equals("r") || abcValue.equals("s") ||
                    abcValue.equals("t") || abcValue.equals("u") || abcValue.equals("v") ||
                    abcValue.equals("w") || abcValue.equals("x") || abcValue.equals("y") ||
                    abcValue.equals("z")) {
                if (direction.equals("N") || direction.equals("E") || direction.equals("S") || direction.equals("W")) {
                    main.getFiles().getArenasConfig().set(arena + ".world-name", player.getWorld().getName());

                    main.getUtils().saveDirection(arena, player);

                    main.getUtils().saveSpawn(player, abcValue, arena);

                    main.getFiles().getArenasConfig().set(arena + ".spawn" + abcValue + ".open", true);

                    try {
                        main.getFiles().getArenasConfig().save(main.getFiles().getArenasConfigFile());
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }

                    player.sendMessage("You have successfully set the spawn " + abcValue + " on the arena called " + arena);
                } else {
                    player.sendMessage("You have to be looking straight to be able to set the spawn inside of an arena");
                }
            } else if (abcValue.equalsIgnoreCase("next")) {
                if (direction.equals("N") || direction.equals("E") || direction.equals("S") || direction.equals("W")) {
                    main.getUtils().fillABC();

                    for (String abc : main.getUtils().getAbc()) {
                        if (!main.getFiles().getArenasConfig().contains(arena + ".spawns." + abc)) {
                            player.chat("/fastbridger setspawn " + arena + " " + abc);

                            break;
                        }
                    }
                } else {
                    player.sendMessage("You have to be looking straight to be able to set the spawn inside of an arena");
                }
            } else {
                player.sendMessage("You can only use the letters from the alphabet");
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
