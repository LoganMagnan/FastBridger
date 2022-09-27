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

public class SetHeightCommand extends FastBridgerCommand {

    private Main main;

    public SetHeightCommand(Main main) {
        this.main = main;
    }

    @Override
    public void executeAs(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission(Utils.ADMIN_PERMISSION_NODE)) {
            return;
        }

        Player player = (Player) sender;

        String arena = args[1];

        if (main.getFiles().getArenasConfig().contains(arena)) {
            int location = (int) player.getLocation().getY();

            main.getFiles().getArenasConfig().set(arena + ".height", location);

            try {
                main.getFiles().getArenasConfig().save(main.getFiles().getArenasConfigFile());
            } catch (IOException exception) {
                exception.printStackTrace();
            }

            player.sendMessage("You have successfully set the height on the arena called " + arena + " to " + location);
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
