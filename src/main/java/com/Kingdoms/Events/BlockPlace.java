package com.Kingdoms.Events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.Kingdoms.Area;
import com.Kingdoms.AreaUpgrade;
import com.Kingdoms.Areas;
import com.Kingdoms.Connections;
import com.Kingdoms.Teams.ClanPlayer;

public class BlockPlace implements Listener {

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		ClanPlayer clanPlayer = Connections.getClanPlayer(player.getUniqueId());
		Block block = event.getBlock();
		Area area = Areas.getChunkOwner(block);
		
		if (!Events.canBuild(clanPlayer, area)) {
			if (area.hasAreaUpgrade(AreaUpgrade.TNT) && block.getType() == Material.TNT) {
				event.setCancelled(true);
				return;
			}
			
			if (area.hasAreaUpgrade(AreaUpgrade.CLEAN)) {
				Events.saveState(event.getBlockReplacedState());
			}
		}
		
		
		
		/*
		if (!Events.canBuild(clanPlayer, event.getBlock())) {
			Events.saveState(event.getBlockReplacedState());
			return;
		}
		*/
		
		/*
		if (block.getType() == Material.GOLD_BLOCK) {
			
			
			if (AreaCommands.areaExpand(clanPlayer, new AreaChunk(block.getChunk())) == true) {
				block.setType(Material.AIR);
				event.setCancelled(true);
			}
			
		}	
		*/
	}
	
}
