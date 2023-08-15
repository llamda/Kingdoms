package com.Kingdoms.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.Kingdoms.Connections;

public class PlayerQuit implements Listener {

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {

		Connections.unloadPlayer(event.getPlayer());
	}
}
