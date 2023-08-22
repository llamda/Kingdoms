package com.Kingdoms.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerTeleport implements Listener {

	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		Events.sendAreaChangeMessage(event.getPlayer(), event.getFrom().getChunk(), event.getTo().getChunk());
	}
}
