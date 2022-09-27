package xyz.trixkz.fastbridger.commands.fastbridger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import xyz.trixkz.fastbridger.Main;

public class FastBridgerCommand implements CommandExecutor {

    private Main main;

    public FastBridgerCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Usage:");
            sender.sendMessage("/fastbridger create <arena>");
            sender.sendMessage("/fastbridger delete <arena>");
            sender.sendMessage("/fastbridger setspawn <arena> <a, b, c... | next>");
            sender.sendMessage("/fastbridger setheight <arena>");
            sender.sendMessage("/fastbridger setdistance <arena> <distance>");
            sender.sendMessage("/fastbridger setplayers <arena> <max players>");
            sender.sendMessage("/fastbridger join <arena>");
            sender.sendMessage("/fastbridger setstatus <arena> <bridge> <on | off>");
            sender.sendMessage("/fastbridger list");
            sender.sendMessage("/fastbridger check <arena>");
        } else {
            switch (args[0]) {
                case "create":
                    new CreateCommand(main).executeAs(sender, cmd, label, args);

                    break;
                case "delete":
                    new DeleteCommand(main).executeAs(sender, cmd, label, args);

                    break;
                case "setspawn":
                    new SetSpawnCommand(main).executeAs(sender, cmd, label, args);

                    break;
                case "setheight":
                    new SetHeightCommand(main).executeAs(sender, cmd, label, args);

                    break;
                case "setdistance":
                    new SetDistanceCommand(main).executeAs(sender, cmd, label, args);

                    break;
                case "setplayers":
                    new SetPlayersCommand(main).executeAs(sender, cmd, label, args);

                    break;
                case "join":
                    new JoinCommand(main).executeAs(sender, cmd, label, args);

                    break;
                case "setstatus":
                    new SetStatusCommand(main).executeAs(sender, cmd, label, args);

                    break;
                case "list":
                    new ListCommand(main).executeAs(sender, cmd, label, args);

                    break;
                case "check":
                    break;
            }
        }

        return true;
    }
}
