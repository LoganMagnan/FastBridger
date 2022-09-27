package xyz.trixkz.fastbridger.managers;

import io.github.thatkawaiisam.assemble.AssembleAdapter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.trixkz.fastbridger.Main;
import xyz.trixkz.fastbridger.managers.user.User;
import xyz.trixkz.fastbridger.utils.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ScoreboardManager implements AssembleAdapter, Listener {

    private Main main;

    public ScoreboardManager(Main main) {
        this.main = main;
    }

    @Override
    public String getTitle(Player player) {
        return Utils.translate("&b&lFastbridge");
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> lines = new ArrayList<String>();

        User user = User.getByUUID(player.getUniqueId());

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        lines.add(Utils.translate("&7&m--------------------"));
        lines.add(Utils.translate("&bYou: &f" + player.getName()));
        lines.add(Utils.translate("&bRecord: &f" + decimalFormat.format(user.getPersonalBest())));
        lines.add(Utils.translate(""));
        lines.add(Utils.translate("&bTime: &f" + (user.getTimer() == null ? "0" : decimalFormat.format(user.getElapsedTime()))));
        lines.add(Utils.translate("&bBlocks: &f" + main.getUtils().getBlocks().get(player).size()));
        lines.add(Utils.translate("&bCoins: &f" + user.getCoinsBalance()));
        lines.add(Utils.translate(""));
        lines.add(Utils.translate("&bTop 3:"));
        lines.add(Utils.translate("  " + main.getUtils().getTopOneNameAndValue()));
        lines.add(Utils.translate("  " + main.getUtils().getTopTwoNameAndValue()));
        lines.add(Utils.translate("  " + main.getUtils().getTopThreeNameAndValue()));
        lines.add(Utils.translate(""));
        lines.add(Utils.translate("&fplay.sweatsunited.com"));
        lines.add(Utils.translate("&7&m--------------------"));

        return lines;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.setJoinMessage(null);

        player.getInventory().clear();

        player.teleport(player.getWorld().getSpawnLocation());

        for (String string : Utils.JOIN_MESSAGE) {
            player.sendMessage(Utils.translate(string));
        }
    }
}
