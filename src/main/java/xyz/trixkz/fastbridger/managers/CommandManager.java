package xyz.trixkz.fastbridger.managers;

import xyz.trixkz.fastbridger.Main;
import xyz.trixkz.fastbridger.commands.ReloadFilesCommand;
import xyz.trixkz.fastbridger.commands.SetSpawnCommand;
import xyz.trixkz.fastbridger.commands.coins.CoinsCommand;
import xyz.trixkz.fastbridger.commands.fastbridger.FastBridgerCommand;

public class CommandManager {

    private Main main;

    public CommandManager(Main main) {
        this.main = main;

        registerCommands();
    }

    private void registerCommands() {
        main.addCommand("setspawn", new SetSpawnCommand(main));
        main.addCommand("reloadfiles", new ReloadFilesCommand(main));
        main.addCommand("fastbridger", new FastBridgerCommand(main));
        main.addCommand("coins", new CoinsCommand(main));
    }
}
