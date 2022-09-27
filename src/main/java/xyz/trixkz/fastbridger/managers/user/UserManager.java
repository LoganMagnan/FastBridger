package xyz.trixkz.fastbridger.managers.user;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.trixkz.fastbridger.Main;

public class UserManager implements Listener {

    private Main main;

    public UserManager(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        User user = new User(event.getUniqueId(), event.getName());
        User.getUsers().put(event.getUniqueId(), user);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        User user = User.getByUUID(player.getUniqueId());

        Player previousPlayer = null;

        double previousTime = 0;

        if (user != null) {
            user.save();
        }

        if (main.getUtils().getTopOne().containsKey(player)) {
            main.getUtils().getTopOne().clear();

            if (main.getUtils().getTopTwo().isEmpty()) {
                return;
            }

            previousPlayer = main.getUtils().getTopTwo().entrySet().stream().findFirst().get().getKey();

            previousTime = main.getUtils().getTopTwo().entrySet().stream().findFirst().get().getValue();

            main.getUtils().getTopOne().put(previousPlayer, previousTime);

            main.getUtils().getTopTwo().clear();

            if (main.getUtils().getTopThree().isEmpty()) {
                return;
            }

            previousPlayer = main.getUtils().getTopThree().entrySet().stream().findFirst().get().getKey();

            previousTime = main.getUtils().getTopThree().entrySet().stream().findFirst().get().getValue();

            main.getUtils().getTopTwo().put(previousPlayer, previousTime);

            main.getUtils().getTopThree().clear();
        } else if (main.getUtils().getTopTwo().containsKey(player)) {
            main.getUtils().getTopTwo().clear();

            if (main.getUtils().getTopThree().isEmpty()) {
                return;
            }

            previousPlayer = main.getUtils().getTopThree().entrySet().stream().findFirst().get().getKey();

            previousTime = main.getUtils().getTopThree().entrySet().stream().findFirst().get().getValue();

            main.getUtils().getTopTwo().put(previousPlayer, previousTime);

            main.getUtils().getTopThree().clear();
        } else if (main.getUtils().getTopThree().containsKey(player)) {
            main.getUtils().getTopThree().clear();
        }
    }
}
