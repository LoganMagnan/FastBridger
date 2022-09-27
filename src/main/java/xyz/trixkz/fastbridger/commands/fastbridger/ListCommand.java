package xyz.trixkz.fastbridger.commands.fastbridger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.trixkz.fastbridger.Main;
import xyz.trixkz.fastbridger.commands.FastBridgerCommand;
import xyz.trixkz.fastbridger.menusystem.menu.FastBridgerListMenu;
import xyz.trixkz.fastbridger.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ListCommand extends FastBridgerCommand {

    private Main main;

    public ListCommand(Main main) {
        this.main = main;
    }

    @Override
    public void executeAs(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission(Utils.ADMIN_PERMISSION_NODE)) {
            return;
        }

        Player player = (Player) sender;

        if (main.getFiles().getConfig().getStringList("arenas").size() <= 0) {
            player.sendMessage("There is currently no arenas that have been made yet");
        }

        new FastBridgerListMenu(main.getPlayerMenuUtil(player), main).open();
    }

    @Override
    public List<String> getTabCompletions(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> tabCompletions = new ArrayList<String>();

        return tabCompletions;
    }
}
