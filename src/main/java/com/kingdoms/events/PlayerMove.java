package com.kingdoms.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {

		Events.sendAreaChangeMessage(event.getPlayer(), event.getFrom().getChunk(), event.getTo().getChunk());
	}
}
