package xyz.trixkz.fastbridger;

import io.github.thatkawaiisam.assemble.Assemble;
import io.github.thatkawaiisam.assemble.AssembleStyle;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.trixkz.fastbridger.listeners.MenuListener;
import xyz.trixkz.fastbridger.listeners.PlayerInteractListener;
import xyz.trixkz.fastbridger.listeners.RandomListeners;
import xyz.trixkz.fastbridger.managers.CommandManager;
import xyz.trixkz.fastbridger.managers.DatabaseManager;
import xyz.trixkz.fastbridger.managers.ScoreboardManager;
import xyz.trixkz.fastbridger.managers.user.UserManager;
import xyz.trixkz.fastbridger.menusystem.PlayerMenuUtil;
import xyz.trixkz.fastbridger.nms.NMS;
import xyz.trixkz.fastbridger.utils.BungeeUtils;
import xyz.trixkz.fastbridger.utils.Files;
import xyz.trixkz.fastbridger.utils.Utils;
import java.util.HashMap;

public class Main extends JavaPlugin {

    @Getter private static Main instance;
    @Getter private Files files;
    @Getter private Utils utils;
    @Getter private DatabaseManager databaseManager;
    @Getter private UserManager userManager;
    @Getter private ScoreboardManager scoreboardManager;
    private NMS nms;
    private static final HashMap<Player, PlayerMenuUtil> playerMenuUtilMap = new HashMap<>();

    public void onEnable() {
        instance = this;

        if (getServer().getVersion().equalsIgnoreCase("1.8")) {
            nms = Utils.make_1_8();
        }

        files = new Files(this);

        files.loadConfig();

        utils = new Utils(this);

        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeUtils(this));

        Assemble assemble = new Assemble(this, new ScoreboardManager(this));
        assemble.setTicks(2);
        assemble.setAssembleStyle(AssembleStyle.VIPER);

        Utils.debug("Making database connection");
        makeDatabaseConnection();
        Utils.debug("Initiating managers");
        initiateManagers();
        Utils.debug("Registering events");
        registerEvents();

        utils.updateSignInterval(15L);
        utils.openAllBridges();
    }

    public void onDisable() {
        instance = null;

        getServer().getScheduler().cancelTasks(this);
    }

    private void makeDatabaseConnection() {
        databaseManager = new DatabaseManager(
                files.getConfig().getString("mongodb.host"),
                files.getConfig().getInt("mongodb.port"),
                files.getConfig().getString("mongodb.database"),
                files.getConfig().getBoolean("mongodb.auth.enabled") ? true : false,
                files.getConfig().getString("mongodb.auth.username"),
                files.getConfig().getString("mongodb.auth.password"),
                files.getConfig().getString("mongodb.auth.auth-database"));
    }

    private void initiateManagers() {
        new CommandManager(this);

        userManager = new UserManager(this);
        scoreboardManager = new ScoreboardManager(this);
    }

    private void registerEvents() {
        addEvent(new MenuListener());
        addEvent(userManager);
        addEvent(scoreboardManager);
        addEvent(new RandomListeners(this));
        addEvent(new PlayerInteractListener(this));
    }

    public PlayerMenuUtil getPlayerMenuUtil(Player player) {
        PlayerMenuUtil playerMenuUtil;

        if (playerMenuUtilMap.containsKey(player)) {
            return playerMenuUtilMap.get(player);
        } else {
            playerMenuUtil = new PlayerMenuUtil(player);
            playerMenuUtilMap.put(player, playerMenuUtil);

            return playerMenuUtil;
        }
    }

    public void addCommand(String commandName, CommandExecutor commandExecutor) {
        getCommand(commandName).setExecutor(commandExecutor);
        System.out.println("Enabled the /" + commandName + " command");
    }

    public void addEvent(Listener event) {
        getServer().getPluginManager().registerEvents(event, this);
        System.out.println("Enabled the " + event.toString() + " event");
    }
}
