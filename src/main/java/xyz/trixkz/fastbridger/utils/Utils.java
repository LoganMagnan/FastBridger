package xyz.trixkz.fastbridger.utils;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.trixkz.fastbridger.Main;
import xyz.trixkz.fastbridger.managers.user.User;
import xyz.trixkz.fastbridger.nms.NMS;
import xyz.trixkz.fastbridger.nms.versions.NMS_1_8;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class Utils {
	
	private Main main;

	public static boolean DEBUG_MESSAGE;
	public static String MONGODB_HOST;
	public static String ADMIN_PERMISSION_NODE;
	public static String NO_PERMISSION_MESSAGE;
	public static String SPAWN_POINT_SET_MESSAGE;
	public static String FILES_RELOADED_MESSAGE;
	public static List<String> JOIN_MESSAGE;

	@Getter public List<String> abc = new ArrayList<String>();
	@Getter public HashMap<Player, String> playerAbc = new HashMap<Player, String>();
	@Getter public ArrayList<Player> ingameAll = new ArrayList<Player>();
	@Getter public HashMap<Player, String> playerArena = new HashMap<Player, String>();
	@Getter public HashMap<String, Player> arenaPlayer = new HashMap<String, Player>();
	@Getter public Multimap<Player, Location> blocks = MultimapBuilder.hashKeys().hashSetValues().build();
	@Getter public HashMap<String, Integer> arenaOnline = new HashMap<String, Integer>();
	@Getter public HashMap<Player, Double> topOne = new HashMap<>();
	@Getter public HashMap<Player, Double> topTwo = new HashMap<>();
	@Getter public HashMap<Player, Double> topThree = new HashMap<>();

	public Utils(Main main) {
		this.main = main;

		DEBUG_MESSAGE = main.getFiles().getConfig().getBoolean("debug");
		MONGODB_HOST = main.getFiles().getConfig().getString("mongodb.host");
		ADMIN_PERMISSION_NODE = main.getFiles().getConfig().getString("admin-permission-node");
		NO_PERMISSION_MESSAGE = main.getFiles().getConfig().getString("no-permission-message");
		SPAWN_POINT_SET_MESSAGE = main.getFiles().getConfig().getString("spawn-point-set-message");
		FILES_RELOADED_MESSAGE = main.getFiles().getConfig().getString("files-reloaded-message");
		JOIN_MESSAGE = main.getFiles().getConfig().getStringList("join-message");
	}

	/**
	 * Translate the specified message to have the proper color codes inside of it 
	 * @param String - Gets the message to be translated
	 * @return String
	 */
	public static String translate(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}

	/**
	 * If the "debug" value inside of the "settings.yml" configuration file is enabled, 
	 * then it will send a message to the console 
	 * @param Object - Gets the message to be sent to the console
	 */
	public static void debug(Object... string) {
		if (DEBUG_MESSAGE) {
			for (Object object : string) {
				System.out.println(object);
			}
		}
	}

    public static NMS make_1_8() {
    	return new NMS_1_8();
	}

	public String getDirection(Player player) throws NullPointerException {
		double rotation = ((player.getLocation().getYaw() - 90.0F) % 360.0F);
		if (rotation < 0.0D)
			rotation += 360.0D;
		if (0.0D <= rotation && rotation < 67.5D)
			return "NW";
		if (67.5D <= rotation && rotation < 112.5D)
			return "N";
		if (112.5D <= rotation && rotation < 157.5D)
			return "NE";
		if (157.5D <= rotation && rotation < 202.5D)
			return "E";
		if (202.5D <= rotation && rotation < 247.5D)
			return "SE";
		if (247.5D <= rotation && rotation < 292.5D)
			return "S";
		if (292.5D <= rotation && rotation < 337.5D)
			return "SW";
		if (337.5D <= rotation && rotation < 360.0D)
			return "W";
		return null;
	}

	public void saveDirection(String arena, Player player) {
		String direction = getDirection(player);

		if (direction.equals("N") || direction.equals("E") || direction.equals("S") || direction.equals("W")) {
			main.getFiles().getArenasConfig().set(arena + ".direction", getDirection(player));

			try {
				main.getFiles().getArenasConfig().save(main.getFiles().getArenasConfigFile());
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
	}

	public void saveSpawn(Player player, String spawnName, String arenaName) {
		Location location = player.getLocation();

		double x = location.getX();
		double y = location.getY();
		double z = location.getZ();

		float yaw = location.getYaw();
		float pitch = location.getPitch();

		String worldName = location.getWorld().getName();

		main.getFiles().getArenasConfig().set(arenaName + ".spawns." + spawnName + ".x", x);
		main.getFiles().getArenasConfig().set(arenaName + ".spawns." + spawnName + ".y", y);
		main.getFiles().getArenasConfig().set(arenaName + ".spawns." + spawnName + ".z", z);
		main.getFiles().getArenasConfig().set(arenaName + ".spawns." + spawnName + ".yaw", yaw);
		main.getFiles().getArenasConfig().set(arenaName + ".spawns." + spawnName + ".pitch", pitch);
		main.getFiles().getArenasConfig().set(arenaName + ".spawns." + spawnName + ".world", worldName);

		try {
			main.getFiles().getArenasConfig().save(main.getFiles().getArenasConfigFile());
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	public void fillABC() {
		abc.clear();

		abc.add("a");
		abc.add("b");
		abc.add("c");
		abc.add("d");
		abc.add("e");
		abc.add("f");
		abc.add("g");
		abc.add("h");
		abc.add("i");
		abc.add("j");
		abc.add("k");
		abc.add("l");
		abc.add("m");
		abc.add("n");
		abc.add("o");
		abc.add("p");
		abc.add("q");
		abc.add("r");
		abc.add("s");
		abc.add("t");
		abc.add("u");
		abc.add("v");
		abc.add("w");
		abc.add("x");
		abc.add("y");
		abc.add("z");
	}

	public int getCurrentPlayers(String arena) {
		String world = main.getFiles().getArenasConfig().getString(arena + ".spawns.a.world");

		int current = 0;

		for (Player inGame : Bukkit.getOnlinePlayers()) {
			if (inGame.getWorld().getName().equals(world) && ingameAll.contains(inGame)) {
				current++;
			}
		}

		return current;
	}

	public int getMaxPlayers(String arena) {
		String maxPlayers = main.getFiles().getArenasConfig().getString(arena + ".max-players");

		int maxPlayers1 = Integer.parseInt(maxPlayers);

		return maxPlayers1;
	}

	public void join(Player player, String arena) {
		if (main.getFiles().getArenasConfig().contains(arena)) {
			for (String abc : abc) {
				if (main.getFiles().getArenasConfig().contains(arena + ".spawn" + abc)) {
					if (main.getFiles().getArenasConfig().getBoolean(arena + ".spawn" + abc + ".open")) {
						tpArena(player, arena, abc);

						main.getFiles().getArenasConfig().set(arena + ".spawn" + abc + ".open", false);

						try {
							main.getFiles().getArenasConfig().save(main.getFiles().getArenasConfigFile());
						} catch (IOException exception) {
							exception.printStackTrace();
						}

						playerAbc.put(player, abc);

						break;
					}

					continue;
				}

				player.sendMessage("There was no free island found");
			}
		} else {
			player.sendMessage("The specified arena does not currently exist");
		}
	}
	public void tpArena(Player player, String arena, String abcValue) {
		Location location = player.getLocation();

		double x = main.getFiles().getArenasConfig().getDouble(arena + ".spawns." + abcValue + ".x");
		double y = main.getFiles().getArenasConfig().getDouble(arena + ".spawns." + abcValue + ".y");
		double z = main.getFiles().getArenasConfig().getDouble(arena + ".spawns." + abcValue + ".z");
		double yaw = main.getFiles().getArenasConfig().getDouble(arena + ".spawns." + abcValue + ".yaw");
		double pitch = main.getFiles().getArenasConfig().getDouble(arena + ".spawns." + abcValue + ".pitch");

		String worldName = main.getFiles().getArenasConfig().getString(arena + ".spawns." + abcValue + ".world");

		World welt = Bukkit.getWorld(worldName);

		location.setX(x);
		location.setY(y);
		location.setZ(z);
		location.setYaw((float) yaw);
		location.setPitch((float) pitch);
		location.setWorld(welt);

		player.teleport(location);
	}

	public void giveItems(Player player) {
		User user = User.getByUUID(player.getUniqueId());

		String block = user.getBlock();

		if (block == null) {
			block = "SANDSTONE";

			user.setBlock(block);

			user.getBlocks().add("SANDSTONE");
		}

		player.getInventory().clear();

		player.setAllowFlight(false);

		player.getInventory().setItem(0, new ItemStack(Material.getMaterial(block), 64));
		player.getInventory().setItem(1, new ItemStack(Material.getMaterial(block), 64));

		ItemStack settings = new ItemStack(Material.SLIME_BALL, 1);

		ItemMeta settingsMeta = settings.getItemMeta();

		settingsMeta.setDisplayName(Utils.translate("&bSettings &7(Right Click)"));

		settings.setItemMeta(settingsMeta);

		ItemStack goToHub = new ItemStack(Material.BED, 1);

		ItemMeta goToHubMeta = goToHub.getItemMeta();

		goToHubMeta.setDisplayName(Utils.translate("&bGo To Hub &7(Right Click)"));

		goToHub.setItemMeta(goToHubMeta);

		player.getInventory().setItem(7, settings);
		player.getInventory().setItem(8, goToHub);
	}

	public void setStatus(String arena, boolean status, String abcValue) {
		main.getFiles().getArenasConfig().set(arena + ".spawn" + abcValue + ".open", status);

		try {
			main.getFiles().getArenasConfig().save(main.getFiles().getArenasConfigFile());
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	public void resetBlocks(Player player) {
		blocks.get(player).forEach(location -> location.getBlock().setType(Material.AIR));
		blocks.removeAll(player);
	}

	public void updateSign() {
		for (String arena : main.getFiles().getConfig().getStringList("arenas")) {
			if (main.getFiles().getConfig().contains(arena + ".signs")) {
				int x = main.getFiles().getConfig().getInt(arena + ".signs.x");
				int y = main.getFiles().getConfig().getInt(arena + ".signs.y");
				int z = main.getFiles().getConfig().getInt(arena + ".signs.z");

				String worldName = main.getFiles().getConfig().getString(arena + ".signs.world");

				Location location = new Location(Bukkit.getWorld(worldName), x, y, z);

				Block block = Bukkit.getWorld(worldName).getBlockAt(location);

				Sign sign = (Sign) block.getState();

				sign.setLine(1, Utils.translate("&8[&b" + getCurrentPlayers(arena) + "&7/&3" + main.getFiles().getArenasConfig().getString(arena + ".max-players") + "&8]"));

				sign.update();
			}
		}
	}

	public void updateSignInterval(long seconds) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), new Runnable() {
			public void run() {
				updateSign();
			}
		}, 10L, seconds * 20L);
	}

	public void openAllBridges() {
		fillABC();

		if (main.getFiles().getConfig().getStringList("arenas").size() > 0) {
			for (String arena : main.getFiles().getConfig().getStringList("arenas")) {
				for (String abcValue : abc) {
					if (main.getFiles().getArenasConfig().contains(arena + ".spawns." + abcValue)) {
						setStatus(arena, true, abcValue);
					}
				}
			}
		}
	}

	public String getTopOneNameAndValue() {
		if (topOne.entrySet().isEmpty()) {
			return "&bNone: &f0";
		}

		String name = topOne.entrySet().stream().findFirst().get().getKey().getName();

		double time = topOne.entrySet().stream().findFirst().get().getValue();

		DecimalFormat decimalFormat = new DecimalFormat("0.00");

		return "&b" + name + ": &f" + decimalFormat.format(time);
	}

	public String getTopTwoNameAndValue() {
		if (topTwo.entrySet().isEmpty()) {
			return "&bNone: &f0";
		}

		String name = topTwo.entrySet().stream().findFirst().get().getKey().getName();

		double time = topTwo.entrySet().stream().findFirst().get().getValue();

		DecimalFormat decimalFormat = new DecimalFormat("0.00");

		return "&b" + name + ": &f" + decimalFormat.format(time);
	}

	public String getTopThreeNameAndValue() {
		if (topThree.entrySet().isEmpty()) {
			return "&bNone: &f0";
		}

		String name = topThree.entrySet().stream().findFirst().get().getKey().getName();

		double time = topThree.entrySet().stream().findFirst().get().getValue();

		DecimalFormat decimalFormat = new DecimalFormat("0.00");

		return "&b" + name + ": &f" + decimalFormat.format(time);
	}
}
