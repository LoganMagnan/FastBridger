package xyz.trixkz.fastbridger.commands.fastbridger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.trixkz.fastbridger.Main;
import xyz.trixkz.fastbridger.commands.FastBridgerCommand;
import xyz.trixkz.fastbridger.utils.Utils;
import java.util.ArrayList;
import java.util.List;

public class CheckCommand extends FastBridgerCommand {

    private Main main;

    public CheckCommand(Main main) {
        this.main = main;
    }

    @Override
    public void executeAs(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission(Utils.ADMIN_PERMISSION_NODE)) {
            return;
        }

        Player player = (Player) sender;

        String arena = args[1];

        if (!main.getFiles().getArenasConfig().contains(arena)) {
            String height = main.getFiles().getArenasConfig().getString(arena + ".height");

            int distance = main.getFiles().getArenasConfig().getInt(arena + ".distance");
        } else {
            player.sendMessage("The specified arena does not currently exist");
        }
    }

    @Override
    public List<String> getTabCompletions(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> tabCompletions = new ArrayList<String>();

        return tabCompletions;
    }
}
