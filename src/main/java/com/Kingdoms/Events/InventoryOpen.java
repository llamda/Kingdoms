package com.Kingdoms.Events;

import org.bukkit.event.Listener;

public class InventoryOpen implements Listener {
	/*
	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent event) {
	
		Player player = (Player) event.getPlayer();
		ClanPlayer clanPlayer = Connections.getClanPlayer(player.getUniqueId());
		
		Location loc = event.getInventory().getLocation();
		
		if (loc == null || Events.canBuild(clanPlayer, loc.getBlock())) {
			return;
		}
		
		player.sendMessage(ChatColor.RED + "You must capture this area to open that.");
		event.setCancelled(true);
	}
	*/
}
