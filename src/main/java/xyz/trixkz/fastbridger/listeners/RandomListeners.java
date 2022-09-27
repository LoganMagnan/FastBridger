package xyz.trixkz.fastbridger.listeners;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.util.Vector;
import xyz.trixkz.fastbridger.Main;
import xyz.trixkz.fastbridger.managers.user.User;
import xyz.trixkz.fastbridger.utils.Utils;
import java.io.IOException;
import java.text.DecimalFormat;

public class RandomListeners implements Listener {

    private Main main;

    public RandomListeners(Main main) {
        this.main = main;
    }

    private int updateScoreboardTask;

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        if (main.getUtils().getIngameAll().contains(player)) {
            event.setCancelled(true);
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        User user = User.getByUUID(player.getUniqueId());

        Block block = event.getBlock();

        String arena = main.getUtils().getPlayerArena().get(player);

        String abcValue = main.getUtils().getPlayerAbc().get(player);

        String direction = main.getFiles().getArenasConfig().getString(arena + ".direction");

        int distance = main.getFiles().getArenasConfig().getInt(arena + ".distance");

        String worldName = main.getFiles().getArenasConfig().getString(arena + ".world-name");

        assert worldName != null;

        World world = Bukkit.getWorld(worldName);

        double x = main.getFiles().getArenasConfig().getDouble(arena + ".spawns." + abcValue + ".x");
        double y = main.getFiles().getArenasConfig().getDouble(arena + ".spawns." + abcValue + ".y");
        double z = main.getFiles().getArenasConfig().getDouble(arena + ".spawns." + abcValue + ".z");

        Location location = new Location(world, x, y, z);

        if (main.getUtils().getIngameAll().contains(player)) {
            main.getUtils().getBlocks().put(player, block.getLocation());

            user.startTimer();

            if (direction.equals("N") && (block.getX() >= location.getX() + distance || block.getX() <= location.getX() - distance - 1.0D)) {
                event.setCancelled(true);

                return;
            }

            if (direction.equals("E") && (block.getZ() >= location.getZ() + distance || block.getZ() <= location.getZ() - distance - 1.0D)) {
                event.setCancelled(true);

                return;
            }

            if (direction.equals("S") && (block.getX() >= location.getX() + distance || block.getX() <= location.getX() - distance - 1.0D)) {
                event.setCancelled(true);

                return;
            }

            if (direction.equals("W") && (block.getZ() >= location.getZ() + distance || block.getZ() <= location.getZ() - distance - 1.0D)) {
                event.setCancelled(true);

                return;
            }
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if (main.getUtils().getIngameAll().contains(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        User user = User.getByUUID(player.getUniqueId());

        if (main.getUtils().getIngameAll().contains(player)) {
            String arena = main.getUtils().getPlayerArena().get(player);

            String abcValue = main.getUtils().getPlayerAbc().get(player);

            Location location = player.getLocation();

            int height = main.getFiles().getArenasConfig().getInt(arena + ".height");

            String direction = main.getFiles().getArenasConfig().getString(arena + ".direction");

            int distance = main.getFiles().getArenasConfig().getInt(arena + ".distance");

            String worldName = main.getFiles().getArenasConfig().getString(arena + ".world-name");

            World world = Bukkit.getWorld(worldName);

            double x = main.getFiles().getArenasConfig().getDouble(arena + ".spawns." + abcValue + ".x");
            double y = main.getFiles().getArenasConfig().getDouble(arena + ".spawns." + abcValue + ".y");
            double z = main.getFiles().getArenasConfig().getDouble(arena + ".spawns." + abcValue + ".z");

            Location location1 = new Location(world, x, y, z);

            Vector vector = new Vector(x, y, z).normalize();

            vector.multiply(0.3D);

            double vectorX = 0.3D;
            double vectorZ = 0.3D;

            if (direction.equals("N")) {
                if (player.getLocation().getX() >= location1.getX() + distance) {
                    vector.setX(-vectorX);
                    player.setVelocity(vector);
                }

                if (player.getLocation().getX() <= location1.getX() - distance) {
                    vector.setX(vectorX);
                    player.setVelocity(vector);
                }
            }

            if (direction.equals("E")) {
                if (player.getLocation().getZ() >= location1.getZ() + distance) {
                    vector.setZ(-vectorZ);
                    player.setVelocity(vector);
                }

                if (player.getLocation().getZ() <= location1.getZ() - distance) {
                    vector.setZ(vectorZ);
                    player.setVelocity(vector);
                }
            }

            if (direction.equals("S")) {
                if (player.getLocation().getX() >= location1.getX() + distance) {
                    vector.setX(-vectorX);
                    player.setVelocity(vector);
                }

                if (player.getLocation().getX() <= location1.getX() - distance) {
                    vector.setX(vectorX);
                    player.setVelocity(vector);
                }
            }

            if (direction.equals("W") && direction.equals("E")) {
                if (player.getLocation().getZ() >= location1.getZ() + distance) {
                    vector.setZ(-vectorZ);
                    player.setVelocity(vector);
                }

                if (player.getLocation().getZ() <= location1.getZ() - distance) {
                    vector.setZ(vectorZ);
                    player.setVelocity(vector);
                }
            }

            if (location.getY() >= height) {
                if (location.getZ() > location1.getZ() + 5) {
                    if (main.getUtils().getBlocks().get(player).size() >= 25) {
                        player.sendMessage(Utils.translate("&a+5 COINS"));

                        user.addCoinsBalance(5);
                    } else if (main.getUtils().getBlocks().get(player).size() >= 20) {
                        player.sendMessage(Utils.translate("&a+4 COINS"));

                        user.addCoinsBalance(4);
                    } else if (main.getUtils().getBlocks().get(player).size() >= 15) {
                        player.sendMessage(Utils.translate("&a+3 COINS"));

                        user.addCoinsBalance(3);
                    } else if (main.getUtils().getBlocks().get(player).size() >= 10) {
                        player.sendMessage(Utils.translate("&a+2 COINS"));

                        user.addCoinsBalance(2);
                    } else if (main.getUtils().getBlocks().get(player).size() >= 5) {
                        player.sendMessage(Utils.translate("&a+1 COINS"));

                        user.addCoinsBalance(1);
                    }
                }

                user.stopTimer();
                main.getUtils().tpArena(player, arena, abcValue);
                main.getUtils().resetBlocks(player);
                main.getUtils().giveItems(player);
            }

            if (location.getY() <= 70) {
                if (location.getZ() < location1.getZ() - 5) {
                    if (main.getUtils().getBlocks().get(player).size() >= 25) {
                        player.sendMessage(Utils.translate("&a+5 COINS"));

                        user.addCoinsBalance(5);
                    } else if (main.getUtils().getBlocks().get(player).size() >= 20) {
                        player.sendMessage(Utils.translate("&a+4 COINS"));

                        user.addCoinsBalance(4);
                    } else if (main.getUtils().getBlocks().get(player).size() >= 15) {
                        player.sendMessage(Utils.translate("&a+3 COINS"));

                        user.addCoinsBalance(3);
                    } else if (main.getUtils().getBlocks().get(player).size() >= 10) {
                        player.sendMessage(Utils.translate("&a+2 COINS"));

                        user.addCoinsBalance(2);
                    } else if (main.getUtils().getBlocks().get(player).size() >= 5) {
                        player.sendMessage(Utils.translate("&a+1 COINS"));

                        user.addCoinsBalance(1);
                    }
                }

                user.stopTimer();
                main.getUtils().tpArena(player, arena, abcValue);
                main.getUtils().resetBlocks(player);
                main.getUtils().giveItems(player);
            }

            if (location.getBlock().getType() == Material.GOLD_PLATE) {
                DecimalFormat decimalFormat = new DecimalFormat("0.00");

                player.sendMessage(Utils.translate("&a+10 COINS"));

                user.addCoinsBalance(10);

                if (user.getPersonalBest() == 0 || user.getElapsedTime() < user.getPersonalBest()) {
                    user.setPersonalBest(user.getElapsedTime());

                    player.sendMessage(Utils.translate("&b&lNEW PERSONAL BEST: &3&l" + decimalFormat.format(user.getElapsedTime()) + " &b&lWITH &3&l" + main.getUtils().getBlocks().get(player).size() + " &b&lBLOCKS"));
                }

                Player previousPlayer = null;

                double previousTime = 0;

                if (main.getUtils().getTopOne().isEmpty()) {
                    main.getUtils().getTopOne().put(player, user.getElapsedTime());

                    finishBridge(player, user, arena, abcValue);

                    return;
                }

                if(main.getUtils().getTopTwo().isEmpty()) {
                    if(main.getUtils().getTopOne().entrySet().stream().findFirst().get().getKey().equals(player)) {
                        if(user.getElapsedTime() < main.getUtils().getTopOne().entrySet().stream().findFirst().get().getValue() && main.getUtils().getTopOne().entrySet().stream().findFirst().get().getKey() == player) {
                            main.getUtils().getTopOne().put(player, user.getElapsedTime());
                        }

                        finishBridge(player, user, arena, abcValue);

                        return;
                    }

                    if(user.getElapsedTime() > main.getUtils().getTopOne().entrySet().stream().findFirst().get().getValue() && !(main.getUtils().getTopOne().entrySet().stream().findFirst().get().getKey() == player)) {
                        main.getUtils().getTopTwo().put(player, user.getElapsedTime());

                        finishBridge(player, user, arena, abcValue);

                        return;
                    }

                    previousPlayer = main.getUtils().getTopOne().entrySet().stream().findFirst().get().getKey();
                    previousTime = main.getUtils().getTopOne().entrySet().stream().findFirst().get().getValue();

                    main.getUtils().getTopOne().clear();
                    main.getUtils().getTopOne().put(player, user.getElapsedTime());

                    main.getUtils().getTopTwo().put(previousPlayer, previousTime);

                    finishBridge(player, user, arena, abcValue);

                    return;
                }

                if(main.getUtils().getTopThree().isEmpty()) {
                    if(main.getUtils().getTopOne().entrySet().stream().findFirst().get().getKey().equals(player) || main.getUtils().getTopTwo().entrySet().stream().findFirst().get().getKey().equals(player)) {
                        if(main.getUtils().getTopOne().entrySet().stream().findFirst().get().getKey().equals(player)) {
                            if(user.getElapsedTime() < main.getUtils().getTopOne().entrySet().stream().findFirst().get().getValue()) {
                                main.getUtils().getTopOne().put(player, user.getElapsedTime());
                            }

                            finishBridge(player, user, arena, abcValue);
                            
                            return;
                        }

                        if(main.getUtils().getTopTwo().entrySet().stream().findFirst().get().getKey().equals(player)) {
                            if(user.getElapsedTime() < main.getUtils().getTopOne().entrySet().stream().findFirst().get().getValue()) {
                                main.getUtils().getTopTwo().put(player, user.getElapsedTime());
                            }

                            finishBridge(player, user, arena, abcValue);
                            
                            return;
                        }
                    }

                    if(user.getElapsedTime() > main.getUtils().getTopOne().entrySet().stream().findFirst().get().getValue() && user.getElapsedTime() < main.getUtils().getTopTwo().entrySet().stream().findFirst().get().getValue() ) {
                        previousPlayer = main.getUtils().getTopTwo().entrySet().stream().findFirst().get().getKey();
                        previousTime = main.getUtils().getTopTwo().entrySet().stream().findFirst().get().getValue();

                        main.getUtils().getTopTwo().clear();
                        main.getUtils().getTopTwo().put(player, user.getElapsedTime());

                        main.getUtils().getTopThree().put(previousPlayer, previousTime);

                        finishBridge(player, user, arena, abcValue);

                        return;
                    }

                    if(user.getElapsedTime() < main.getUtils().getTopOne().entrySet().stream().findFirst().get().getValue() && main.getUtils().getTopTwo().entrySet().stream().findFirst().get().getValue() > user.getElapsedTime()) {
                        previousPlayer = main.getUtils().getTopOne().entrySet().stream().findFirst().get().getKey();
                        previousTime = main.getUtils().getTopOne().entrySet().stream().findFirst().get().getValue();

                        main.getUtils().getTopOne().clear();
                        main.getUtils().getTopOne().put(player, user.getElapsedTime());

                        Player previousPlayer2 = main.getUtils().getTopTwo().entrySet().stream().findFirst().get().getKey();
                        double previousTime2 = main.getUtils().getTopTwo().entrySet().stream().findFirst().get().getValue();

                        main.getUtils().getTopTwo().clear();
                        main.getUtils().getTopTwo().put(previousPlayer, previousTime);

                        main.getUtils().getTopThree().put(previousPlayer2, previousTime2);

                        finishBridge(player, user, arena, abcValue);

                        return;
                    }

                    main.getUtils().getTopThree().put(player, user.getElapsedTime());

                    finishBridge(player, user, arena, abcValue);

                    return;
                }

                if(user.getElapsedTime() < main.getUtils().getTopThree().entrySet().stream().findFirst().get().getValue() && user.getElapsedTime() > main.getUtils().getTopTwo().entrySet().stream().findFirst().get().getValue()) {
                    main.getUtils().getTopThree().clear();
                    main.getUtils().getTopThree().put(player, user.getElapsedTime());

                    finishBridge(player, user, arena, abcValue);

                    return;
                }

                if(user.getElapsedTime() < main.getUtils().getTopTwo().entrySet().stream().findFirst().get().getValue() && user.getElapsedTime() > main.getUtils().getTopOne().entrySet().stream().findFirst().get().getValue()) {
                    previousPlayer = main.getUtils().getTopTwo().entrySet().stream().findFirst().get().getKey();
                    previousTime = main.getUtils().getTopTwo().entrySet().stream().findFirst().get().getValue();

                    main.getUtils().getTopTwo().clear();
                    main.getUtils().getTopTwo().put(player, user.getElapsedTime());

                    main.getUtils().getTopThree().clear();
                    main.getUtils().getTopThree().put(previousPlayer, previousTime);

                    finishBridge(player, user, arena, abcValue);

                    return;
                }

                if(user.getElapsedTime() < main.getUtils().getTopOne().entrySet().stream().findFirst().get().getValue() && user.getElapsedTime() < main.getUtils().getTopTwo().entrySet().stream().findFirst().get().getValue()) {
                    if(main.getUtils().getTopOne().entrySet().stream().findFirst().get().getKey().equals(player)) {
                        main.getUtils().getTopOne().clear();
                        main.getUtils().getTopOne().put(player, user.getElapsedTime());

                        finishBridge(player, user, arena, abcValue);

                        return;
                    }

                    if(main.getUtils().getTopTwo().entrySet().stream().findFirst().get().getKey().equals(player)) {
                        previousPlayer = main.getUtils().getTopOne().entrySet().stream().findFirst().get().getKey();
                        previousTime = main.getUtils().getTopOne().entrySet().stream().findFirst().get().getValue();

                        main.getUtils().getTopOne().clear();
                        main.getUtils().getTopOne().put(player, user.getElapsedTime());

                        main.getUtils().getTopTwo().clear();
                        main.getUtils().getTopTwo().put(previousPlayer, previousTime);

                        finishBridge(player, user, arena, abcValue);

                        return;
                    }

                    if(main.getUtils().getTopThree().entrySet().stream().findFirst().get().getKey().equals(player)) {

                        previousPlayer = main.getUtils().getTopOne().entrySet().stream().findFirst().get().getKey();
                        previousTime = main.getUtils().getTopOne().entrySet().stream().findFirst().get().getValue();

                        Player previousPlayer2 = main.getUtils().getTopTwo().entrySet().stream().findFirst().get().getKey();
                        double previousTime2 = main.getUtils().getTopTwo().entrySet().stream().findFirst().get().getValue();

                        main.getUtils().getTopOne().clear();
                        main.getUtils().getTopOne().put(player, user.getElapsedTime());

                        main.getUtils().getTopTwo().clear();
                        main.getUtils().getTopTwo().put(previousPlayer, previousTime);

                        main.getUtils().getTopThree().clear();
                        main.getUtils().getTopThree().put(previousPlayer2, previousTime2);

                        finishBridge(player, user, arena, abcValue);

                        return;
                    }

                    previousPlayer = main.getUtils().getTopOne().entrySet().stream().findFirst().get().getKey();
                    previousTime = main.getUtils().getTopOne().entrySet().stream().findFirst().get().getValue();

                    Player previousPlayer2 = main.getUtils().getTopTwo().entrySet().stream().findFirst().get().getKey();
                    double previousTime2 = main.getUtils().getTopTwo().entrySet().stream().findFirst().get().getValue();

                    main.getUtils().getTopOne().clear();
                    main.getUtils().getTopOne().put(player, user.getElapsedTime());

                    main.getUtils().getTopTwo().clear();
                    main.getUtils().getTopTwo().put(previousPlayer, previousTime);

                    main.getUtils().getTopThree().clear();
                    main.getUtils().getTopThree().put(previousPlayer2, previousTime2);

                    finishBridge(player, user, arena, abcValue);

                    return;
                }

                user.stopTimer();

                main.getServer().getScheduler().cancelTask(updateScoreboardTask);

                main.getUtils().tpArena(player, arena, abcValue);

                try {
                    main.getUtils().resetBlocks(player);
                } catch (Exception exception) {

                }

                main.getUtils().giveItems(player);
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        String arena = main.getUtils().getPlayerArena().get(player);

        String abcValue = main.getUtils().getPlayerAbc().get(player);

        if (main.getUtils().getIngameAll().contains(player)) {
            main.getUtils().resetBlocks(player);

            main.getFiles().getArenasConfig().set(arena + ".spawn" + abcValue + ".open", true);

            try {
                main.getFiles().getArenasConfig().save(main.getFiles().getArenasConfigFile());
            } catch (IOException exception) {
                exception.printStackTrace();
            }

            main.getUtils().getArenaPlayer().remove(main.getUtils().getPlayerArena().get(player));

            main.getUtils().getIngameAll().remove(player);

            main.getUtils().getPlayerArena().remove(player);

            main.getUtils().getPlayerAbc().remove(player);

            main.getUtils().updateSign();
        }
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        Player player = event.getPlayer();

        String arena = event.getLine(1);

        if (event.getLine(0).equalsIgnoreCase("Fastbridge") && event.getLine(2).equalsIgnoreCase("")) {
            if (main.getFiles().getArenasConfig().contains(arena)) {
                event.setLine(0, Utils.translate("&8- &b&lFastbridge &8-"));
                event.setLine(1, Utils.translate("&8[&b" + main.getUtils().getCurrentPlayers(arena) + "&7/&3" + main.getFiles().getArenasConfig().getString(arena + ".max-players") + "&8]"));
                event.setLine(2, Utils.translate(arena));
                event.setLine(3, Utils.translate("&aClick to join"));

                Sign sign = (Sign) event.getBlock().getState();

                sign.update();

                main.getUtils().getArenaOnline().put(arena, 0);

                main.getFiles().getConfig().set(arena + ".signs.x", sign.getX());
                main.getFiles().getConfig().set(arena + ".signs.y", sign.getY());
                main.getFiles().getConfig().set(arena + ".signs.z", sign.getZ());
                main.getFiles().getConfig().set(arena + ".signs.world", sign.getWorld().getName());

                try {
                    main.getFiles().getConfig().save(main.getFiles().getConfigFile());
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            } else {
                event.setLine(0, Utils.translate("&8- &b&lFastbridger &8-"));
                event.setLine(1, Utils.translate("&8--------------------"));
                event.setLine(2, Utils.translate("&cArena not found"));
                event.setLine(3, Utils.translate("&8--------------------"));
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        try {
            Player player = event.getPlayer();

            Sign sign = (Sign) event.getClickedBlock().getState();

            String arena = sign.getLine(2);

            if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getState() instanceof Sign) {
                if (sign.getLine(0).equalsIgnoreCase(Utils.translate("&8- &b&lFastbridge &8-"))) {
                    if (sign.getLine(3).equalsIgnoreCase(Utils.translate("&aClick to join"))) {
                        if (main.getFiles().getArenasConfig().contains(arena)) {
                            player.chat("/fastbridger join " + arena);

                            main.getUtils().updateSign();
                        }
                    }
                }
            }
        } catch (Exception exception) {

        }
    }

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();

        String arena = main.getUtils().getPlayerArena().get(player);

        String abcValue = main.getUtils().getPlayerAbc().get(player);

        if (main.getUtils().getIngameAll().contains(player) && !player.getWorld().getName().equalsIgnoreCase(main.getFiles().getArenasConfig().getString(main.getUtils().getPlayerArena().get(player)) + ".world-name")) {
            main.getUtils().resetBlocks(player);

            main.getFiles().getArenasConfig().set(arena + ".spawn" + abcValue + ".open", true);

            try {
                main.getFiles().getArenasConfig().save(main.getFiles().getArenasConfigFile());
            } catch (IOException exception) {
                exception.printStackTrace();
            }

            main.getUtils().getArenaPlayer().remove(main.getUtils().getPlayerArena().get(player));

            main.getUtils().getIngameAll().remove(player);

            main.getUtils().getPlayerArena().remove(player);

            main.getUtils().getPlayerAbc().remove(player);

            main.getUtils().updateSign();
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

    private void finishBridge(Player player, User user, String arena, String abcValue) {
        user.stopTimer();

        main.getServer().getScheduler().cancelTask(updateScoreboardTask);

        main.getUtils().tpArena(player, arena, abcValue);

        try {
            main.getUtils().resetBlocks(player);
        } catch (Exception exception) {

        }

        main.getUtils().giveItems(player);
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
    }
}
