package xyz.trixkz.fastbridger.commands.coins;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.trixkz.fastbridger.Main;
import xyz.trixkz.fastbridger.managers.user.User;
import xyz.trixkz.fastbridger.utils.Utils;

public class CoinsCommand implements CommandExecutor {

    private Main main;

    public CoinsCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                return true;
            }

            Player player = (Player) sender;

            User user = User.getByUUID(player.getUniqueId());

            player.sendMessage(Utils.translate("&bCoins: &3" + user.getCoinsBalance()));
        } else {
            switch (args[0]) {
                case "help":
                    new HelpCommand(main).executeAs(sender, cmd, label, args);

                    break;
                case "set":
                    new SetCommand(main).executeAs(sender, cmd, label, args);

                    break;
                case "add":
                    new AddCommand(main).executeAs(sender, cmd, label, args);

                    break;
                case "remove":
                    new RemoveCommand(main).executeAs(sender, cmd, label, args);

                    break;
            }
        }

        return true;
    }
}
