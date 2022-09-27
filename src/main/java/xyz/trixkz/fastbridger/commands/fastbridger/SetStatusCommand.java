package xyz.trixkz.fastbridger.commands.fastbridger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.trixkz.fastbridger.Main;
import xyz.trixkz.fastbridger.commands.FastBridgerCommand;
import xyz.trixkz.fastbridger.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SetStatusCommand extends FastBridgerCommand {

    private Main main;

    public SetStatusCommand(Main main) {
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
        String status = args[3];

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
                if (status.equalsIgnoreCase("on") || status.equalsIgnoreCase("off")) {
                    switch (status) {
                        case "on":
                            main.getUtils().setStatus(arena, true, abcValue);

                            player.sendMessage("The bridge's status has been enabled");

                            break;
                        case "off":
                            main.getUtils().setStatus(arena, false, abcValue);

                            player.sendMessage("The bridge's status has been disabled");

                            break;
                    }
                } else {
                    player.sendMessage("You can only type either 'on' and/or 'off'");
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
