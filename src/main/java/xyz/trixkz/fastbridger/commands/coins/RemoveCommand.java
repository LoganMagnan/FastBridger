package xyz.trixkz.fastbridger.commands.coins;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.trixkz.fastbridger.Main;
import xyz.trixkz.fastbridger.commands.FastBridgerCommand;
import xyz.trixkz.fastbridger.managers.user.User;
import xyz.trixkz.fastbridger.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class RemoveCommand extends FastBridgerCommand {

    private Main main;

    public RemoveCommand(Main main) {
        this.main = main;
    }

    @Override
    public void executeAs(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission(Utils.ADMIN_PERMISSION_NODE)) {
            return;
        }

        if (!isNumeric(args[2])) {
            return;
        }

        int value = Integer.parseInt(args[2]);

        Player target = main.getServer().getPlayer(args[1]);

        if (target == null || !target.isOnline()) {
            return;
        }

        User user = User.getByUUID(target.getUniqueId());

        user.removeCoinsBalance(value);
    }

    @Override
    public List<String> getTabCompletions(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> tabCompletions = new ArrayList<String>();

        return tabCompletions;
    }

    protected boolean isNumeric(String string) {
        return regexNumeric(string).length() == 0;
    }

    protected String regexNumeric(String string) {
        return string.replaceAll("[0-9]", "").replaceFirst("\\.", "");
    }
}
