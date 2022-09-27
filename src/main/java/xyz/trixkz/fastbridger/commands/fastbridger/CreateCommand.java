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

public class CreateCommand extends FastBridgerCommand {

    private Main main;

    public CreateCommand(Main main) {
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
            main.getFiles().getArenasConfig().set(arena + ".created-by", player.getName());
            main.getFiles().getConfig().getStringList("arenas").add(arena);
            main.getFiles().getConfig().set("arenas", main.getFiles().getConfig().getStringList("arenas"));

            try {
                main.getFiles().getConfig().save(main.getFiles().getConfigFile());
                main.getFiles().getArenasConfig().save(main.getFiles().getArenasConfigFile());
            } catch (IOException exception) {
                exception.printStackTrace();
            }

            player.sendMessage("You have successfully made the arena called " + arena);
        } else {
            player.sendMessage("The specified arena has already been made");
        }
    }

    @Override
    public List<String> getTabCompletions(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> tabCompletions = new ArrayList<String>();

        return tabCompletions;
    }
}
