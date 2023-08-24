package com.kingdoms;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.kingdoms.teams.ClanPlayer;

public class Connections implements Listener {

	// Holds all online ClanPlayers
	private static final HashMap<UUID, ClanPlayer> clanPlayers = new HashMap<>();

	public static void loadPlayer(Player player) {
		UUID uuid = player.getUniqueId();
		clanPlayers.put(uuid, new ClanPlayer(uuid));
	}

	public static void unloadPlayer(Player player) {
		UUID uuid = player.getUniqueId();
		clanPlayers.get(uuid).saveData();
		clanPlayers.remove(uuid);
	}

	public static void reload() {
		clanPlayers.clear();

		for (Player player : Kingdoms.instance.getServer().getOnlinePlayers()) {
			loadPlayer(player);
		}
	}

	public static ClanPlayer getClanPlayer(UUID uuid) {
		return clanPlayers.get(uuid);
	}

	public static HashMap<UUID, ClanPlayer> getClanPlayers() {
		return clanPlayers;
	}

}
