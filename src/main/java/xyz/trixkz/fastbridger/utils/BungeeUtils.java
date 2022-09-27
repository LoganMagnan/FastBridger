package xyz.trixkz.fastbridger.utils;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.trixkz.fastbridger.Main;

import java.util.HashMap;
import java.util.Map;

public class BungeeUtils implements PluginMessageListener {

    private static Map<String, Integer> playersOnline = new HashMap<>();
    private static Main main;

    public BungeeUtils(Main main) {
        new BukkitRunnable() {

            @Override
            public void run() {
                for (String string : main.getFiles().getConfig().getStringList("servers")) {
                    ByteArrayDataOutput globalOut = ByteStreams.newDataOutput();
                    globalOut.writeUTF("PlayerCount");
                    globalOut.writeUTF(string);
                    main.getServer().sendPluginMessage(main, "BungeeCord", globalOut.toByteArray());
                }
            }
        }.runTaskTimer(main, 20L, 20L);
    }

    public static int getPlayerCount(String server) {
        if (server == null) {
            server = "ALL";
        }
        return playersOnline.getOrDefault(server, 0);
    }

    public static void sendPlayerToServer(Player player, String server) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("ConnectOther");
        output.writeUTF(player.getName());
        output.writeUTF(server);
        player.sendPluginMessage(main, "BungeeCord", output.toByteArray());
    }

    public void onPluginMessageReceived(String channel, Player p, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String command = in.readUTF();
        if (!command.equals("PlayerCount")) {
            return;
        }
        String server = in.readUTF();
        int playerCount = in.readInt();
        playersOnline.put(server, playerCount);
    }
}