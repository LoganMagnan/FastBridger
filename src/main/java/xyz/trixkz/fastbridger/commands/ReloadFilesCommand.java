package xyz.trixkz.fastbridger.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import xyz.trixkz.fastbridger.Main;
import xyz.trixkz.fastbridger.utils.Utils;

import java.io.IOException;

public class ReloadFilesCommand implements CommandExecutor {

    private Main main;

    public ReloadFilesCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        if (!player.hasPermission(Utils.ADMIN_PERMISSION_NODE)) {
            player.sendMessage(Utils.translate(Utils.NO_PERMISSION_MESSAGE));

            return true;
        }

        try {
            main.getFiles().getConfig().load(main.getFiles().getConfigFile());
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (InvalidConfigurationException exception) {
            exception.printStackTrace();
        }

        player.sendMessage(Utils.translate(Utils.FILES_RELOADED_MESSAGE));

        return true;
    }
}
