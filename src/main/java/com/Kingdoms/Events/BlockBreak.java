package com.Kingdoms.Events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.Kingdoms.Area;
import com.Kingdoms.AreaUpgrade;
import com.Kingdoms.Areas;

public class BlockBreak implements Listener {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {

		Block block = event.getBlock();

		if (block.getType() == Material.SPAWNER) {
			Area area = Areas.getChunkOwner(block);
			if (area != null && area.hasAreaUpgrade(AreaUpgrade.SPAWNER)) {
				event.setCancelled(true);
			}
		}



		/*
		if (Events.canBuild(clanPlayer, block)) {
			return;
		}

		if (block.getState() instanceof InventoryHolder) {
			player.sendMessage(ChatColor.RED + "You must capture this area to break that.");
			event.setCancelled(true);
			return;
		}

		event.setDropItems(false);
		Events.saveState(event.getBlock().getState());
		*/
	}

}
