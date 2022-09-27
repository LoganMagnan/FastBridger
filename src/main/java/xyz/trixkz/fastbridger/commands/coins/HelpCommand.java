package xyz.trixkz.fastbridger.commands.coins;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.trixkz.fastbridger.Main;
import xyz.trixkz.fastbridger.commands.FastBridgerCommand;
import xyz.trixkz.fastbridger.utils.Utils;
import java.util.ArrayList;
import java.util.List;

public class HelpCommand extends FastBridgerCommand {

    private Main main;

    public HelpCommand(Main main) {
        this.main = main;
    }

    @Override
    public void executeAs(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission(Utils.ADMIN_PERMISSION_NODE)) {
            return;
        }

        sender.sendMessage("Usage:");
        sender.sendMessage("/coins");
        sender.sendMessage("/coins set <player> <amount>");
        sender.sendMessage("/coins add <player> <amount>");
        sender.sendMessage("/coins remove <player> <amount>");
    }

    @Override
    public List<String> getTabCompletions(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> tabCompletions = new ArrayList<String>();

        return tabCompletions;
    }
}
