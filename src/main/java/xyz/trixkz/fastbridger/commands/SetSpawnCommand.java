package xyz.trixkz.fastbridger.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.trixkz.fastbridger.Main;
import xyz.trixkz.fastbridger.utils.Utils;

public class SetSpawnCommand implements CommandExecutor {

    private Main main;

    public SetSpawnCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        if (!player.hasPermission(Utils.ADMIN_PERMISSION_NODE)) {
            player.sendMessage(Utils.translate(Utils.NO_PERMISSION_MESSAGE));

            return true;
        }

        Location location = player.getLocation();

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        player.getWorld().setSpawnLocation(x, y, z);

        player.sendMessage(Utils.translate(Utils.SPAWN_POINT_SET_MESSAGE));

        return true;
    }
}
