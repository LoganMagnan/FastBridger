package xyz.trixkz.fastbridger.utils;

import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.UUID;

public class Cooldowns {

	private static HashMap<String, HashMap<UUID, Long>> cooldowns = new HashMap<>();

	public static void createCooldown(String cooldown) {
		if (cooldowns.containsKey(cooldown))
			throw new IllegalArgumentException("Sorry, but however, cooldown does not currently exist.");
		cooldowns.put(cooldown, new HashMap<>());
	}

	public static void addCooldown(String cooldown, Player player, int seconds) {
		if (!cooldowns.containsKey(cooldown))
			throw new IllegalArgumentException(cooldown + " does not currently exist.");
		long next = System.currentTimeMillis() + seconds * 1000L;
		((HashMap<UUID, Long>)cooldowns.get(cooldown)).put(player.getUniqueId(), Long.valueOf(next));
	}

	public static boolean isOnCooldown(String cooldown, Player player) {
		return (cooldowns.containsKey(cooldown) && ((HashMap)cooldowns.get(cooldown)).containsKey(player.getUniqueId()) && System.currentTimeMillis() <= ((Long)((HashMap)cooldowns.get(cooldown)).get(player.getUniqueId())).longValue());
	}

	public static int getCooldownInt(String cooldown, Player player) {
		return (int)((((Long)((HashMap)cooldowns.get(cooldown)).get(player.getUniqueId())).longValue() - System.currentTimeMillis()) / 1000L);
	}
}