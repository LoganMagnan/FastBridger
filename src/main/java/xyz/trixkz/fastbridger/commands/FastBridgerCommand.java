package xyz.trixkz.fastbridger.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import java.util.List;

public abstract class FastBridgerCommand {

    public abstract void executeAs(CommandSender sender, Command cmd, String label, String[] args);

    public abstract List<String> getTabCompletions(CommandSender sender, Command cmd, String label, String[] args);
}
