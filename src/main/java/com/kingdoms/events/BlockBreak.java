package com.kingdoms.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.kingdoms.Area;
import com.kingdoms.AreaUpgrade;
import com.kingdoms.Areas;

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
	}

}
